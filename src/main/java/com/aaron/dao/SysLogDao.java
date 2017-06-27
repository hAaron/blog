package com.aaron.dao;

import java.util.List;
import java.util.Map;

import com.aaron.entity.sys.SysLog;

public interface SysLogDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    SysLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);

	List<SysLog> findAll();

	List<SysLog> list(Map<String, Object> map);

	Long getTotal(Map<String, Object> map);
}