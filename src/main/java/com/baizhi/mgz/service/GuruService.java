package com.baizhi.mgz.service;

import com.baizhi.mgz.entity.Guru;
import java.util.List;
import java.util.Map;

public interface GuruService {

    Guru queryById(String id);

    Map queryAllMap();

    List<Guru> queryAll();

    Map queryAllByPage(Integer page, Integer rows);

    Guru insert(Guru guru);

    Guru update(Guru guru);

    boolean deleteById(String id);

    int deleteByIdList(List<String> list);

}