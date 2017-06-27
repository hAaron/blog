package com.aaron.aspect;

import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import com.aaron.entity.sys.SysLog;
import com.aaron.service.SysLogService;

/**
 * 日志管理 spring aop @Aspect 注解方式
 * 
 * @author Aaron
 * @date 2017年6月27日
 * @version 1.0
 * @package_name com.aaron.aspect
 */
@Aspect
public class LogAspect {

	@Resource
	private SysLogService sysLogService;

	/**
	 * 添加业务逻辑方法切入点
	 */
	@Pointcut("execution(* com.aaron.service.*.*.add*(..))")
	public void insertCell() {
	}

	/**
	 * 修改业务逻辑方法切入点
	 */
	@Pointcut("execution(* com.aaron.service.*.*.update*(..))")
	public void updateCell() {
	}

	/**
	 * 删除业务逻辑方法切入点
	 */
	@Pointcut("execution(* com.aaron.service.*.*.delete*(..))")
	public void deleteCell() {
	}

	/**
	 * 添加操作日志(后置通知)
	 * 
	 * @param joinPoint
	 * @param rtv
	 */
	@AfterReturning(value = "insertCell()", argNames = "rtv", returning = "rtv")
	public void insertLog(JoinPoint joinPoint, Object rtv) throws Throwable {
		// TODO
		initSysLog(joinPoint);
	}

	/**
	 * 管理员修改操作日志(后置通知)
	 * 
	 * @param joinPoint
	 * @param rtv
	 * @throws Throwable
	 */
	@AfterReturning(value = "updateCell()", argNames = "rtv", returning = "rtv")
	public void updateLog(JoinPoint joinPoint, Object rtv) throws Throwable {
		// TODO
		initSysLog(joinPoint);
	}

	/**
	 * 删除操作
	 * 
	 * @param joinPoint
	 * @param rtv
	 */
	@AfterReturning(value = "deleteCell()", argNames = "rtv", returning = "rtv")
	public void deleteLog(JoinPoint joinPoint, Object rtv) throws Throwable {
		// TODO
		initSysLog(joinPoint);
	}

	/**
	 * 使用Java反射来获取被拦截方法(insert、update)的参数值， 将参数值拼接为操作内容
	 * 
	 * @param args
	 * @param mName
	 * @return
	 */
	public String optionContent(Object[] args, String mName) {
		if (args == null) {
			return null;
		}
		StringBuffer rs = new StringBuffer();
		rs.append(mName);
		String className = null;
		int index = 1;
		// 遍历参数对象
		for (Object info : args) {
			// 获取对象类型
			className = info.getClass().getName();
			className = className.substring(className.lastIndexOf(".") + 1);
			rs.append("[参数" + index + "，类型:" + className + "，值:");
			// 获取对象的所有方法
			Method[] methods = info.getClass().getDeclaredMethods();
			// 遍历方法，判断get方法
			for (Method method : methods) {
				String methodName = method.getName();
				// 判断是不是get方法
				if (methodName.indexOf("get") == -1) {// 不是get方法
					continue;// 不处理
				}
				Object rsValue = null;
				try {
					// 调用get方法，获取返回值
					rsValue = method.invoke(info);
				} catch (Exception e) {
					continue;
				}
				// 将值加入内容中
				rs.append("(" + methodName + ":" + rsValue + ")");
			}
			rs.append("]");
			index++;
		}
		return rs.toString();
	}

	/**
	 * 抽取公共方法
	 * 
	 * @param joinPoint
	 */
	private void initSysLog(JoinPoint joinPoint) {
		// 获取方法名
		String methodName = joinPoint.getSignature().getName();
		// 获取操作内容
		String opContent = optionContent(joinPoint.getArgs(), methodName);
		SysLog sysLog = new SysLog();
		sysLog.setMethod(methodName);
		sysLog.setParams(opContent);
		sysLogService.insertLog(sysLog);
	}
}
