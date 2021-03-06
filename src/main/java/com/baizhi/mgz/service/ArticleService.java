package com.baizhi.mgz.service;

import com.baizhi.mgz.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {

    Article queryById(String id);

    Map queryAll();

    Map queryAllByPage(Integer page, Integer rows);

    Article insert(Article article);

    Article update(Article article);

    boolean deleteById(String id);

    int deleteByIdList(List<String> list);

}