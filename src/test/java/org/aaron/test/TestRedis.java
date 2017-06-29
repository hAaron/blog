package org.aaron.test;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aaron.constant.RedisConstants;
import com.aaron.dao.BlogDao;
import com.aaron.entity.Blog;
import com.aaron.util.RedisUtil;
import com.aaron.util.SpringContextHolder;

/**
 * redis 测试类. 注意：使用redis工具类，实现序列化接口； 注意redis 版本
 * 
 * 
 * @author Aaron
 * @date 2017年6月12日
 * @version 1.0
 * @package_name org.aaron.test
 */
@ContextConfiguration(locations = { "classpath*:spring-context*.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestRedis {

	// @Resource
	// private RedisTemplate<Serializable, Object> redisTemplate;
	@Resource
	private BlogDao blogDao;

	@Test
	public void testRe() {

//		String blog_id = "71";
//		System.out.println(RedisUtil.exists(blog_id));
//		if (!RedisUtil.exists(blog_id)) {
//			System.out.println("redis没有，先去数据库查询，然后对象放进缓存");
//			Blog blog = blogDao.findById(Integer.valueOf(blog_id));
//			RedisUtil.set(blog_id, blog);
//		} else {
//			Blog blog = (Blog) RedisUtil.get(blog_id);
//			System.out.println("在redis找到" + blog.getTitle());
//		}
	}

}
