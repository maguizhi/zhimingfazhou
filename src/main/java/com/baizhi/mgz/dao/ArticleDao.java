package com.baizhi.mgz.dao;


import com.baizhi.mgz.entity.Article;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface ArticleDao extends Mapper<Article>, InsertListMapper<Article>, DeleteByIdListMapper<Article,String> {
}
