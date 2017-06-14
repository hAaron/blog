package org.aaron.test;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aaron.dao.BlogDao;
import com.aaron.entity.Blog;

/**
 * 
 * 
 * @author Aaron
 * @date 2017Äê6ÔÂ12ÈÕ
 * @version 1.0
 * @package_name org.aaron.test
 */
@ContextConfiguration(locations = { "classpath*:spring-context*.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestDao {
	@Resource
	private BlogDao blogDao;

	@Test
	public void testQuery() {
//		List<Blog> list = blogDao.list(new HashMap<String, Object>());
//		System.out.println(list);
	}
}
