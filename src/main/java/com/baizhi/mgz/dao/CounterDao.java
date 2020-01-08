package com.baizhi.mgz.dao;


import com.baizhi.mgz.entity.Counter;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface CounterDao extends Mapper<Counter>, InsertListMapper<Counter>, DeleteByIdListMapper<Counter,String> {
}
