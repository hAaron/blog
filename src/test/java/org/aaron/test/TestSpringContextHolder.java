package org.aaron.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aaron.entity.Blogger;
import com.aaron.service.BloggerService;
import com.aaron.util.SpringContextHolder;

/**
 * 测试SpringContextHolder类，注意必须在Spring配置文件中指定该类。 
 * 
 * @author Aaron
 * @date 2017年6月14日
 * @version 1.0
 * @package_name org.aaron.test
 */
@ContextConfiguration(locations = { "classpath*:spring-context*.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestSpringContextHolder {

	@Test
	public void testHolder() {
		BloggerService bloggerService = SpringContextHolder
				.getBean(BloggerService.class);
		Blogger blogger = bloggerService.getByUserName("aaron");
		System.out.println(blogger);
	}

}
