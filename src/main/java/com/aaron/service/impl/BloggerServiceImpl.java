package com.aaron.service.impl;

import java.io.File;
import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.aaron.constant.FileConstants;
import com.aaron.constant.RedisConstants;
import com.aaron.dao.BloggerDao;
import com.aaron.entity.Blogger;
import com.aaron.service.BloggerService;
import com.aaron.util.RedisUtil;
import com.sun.org.apache.xml.internal.security.Init;

/**
 * 博主Service实现类
 * 
 * @author Administrator
 * 
 */
@Service("bloggerService")
public class BloggerServiceImpl implements BloggerService {

	@Resource
	private BloggerDao bloggerDao;

	public Blogger find() {
		Blogger blogger = bloggerDao.find();
		dealImageName(blogger);
		return blogger;
	}

	public Blogger getByUserName(String userName) {
		Blogger blogger = bloggerDao.getByUserName(userName);
		dealImageName(blogger);
		return blogger;
	}

	public Integer update(Blogger blogger) {
		return bloggerDao.update(blogger);
	}

	/**
	 * 博主头像从nginx服务器上取
	 * 
	 * @param blogger
	 */
	private static void dealImageName(Blogger blogger) {
		blogger.setImageName(FileConstants.httpPath + File.separator
				+ blogger.getImageName());
	}

}
