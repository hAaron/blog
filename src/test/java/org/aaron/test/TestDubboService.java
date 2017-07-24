package org.aaron.test;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aaron.common.api.DubboService;
import com.aaron.util.SpringContextHolder;

/**
 * 测试dubbo服务接口
 * 
 * @author Aaron
 * @date 2017年6月12日
 * @version 1.0
 * @package_name org.aaron.test
 */
@ContextConfiguration(locations = { "classpath*:spring-context*.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestDubboService {

	@Test
	public void testService() throws IOException {

		DubboService dubboService = SpringContextHolder
				.getBean(DubboService.class);
		String hello = dubboService.sayHello("asdf");
		System.out.println(hello);

		List list = dubboService.getUsers();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i));
			}
		}
		System.in.read();
	}

}
