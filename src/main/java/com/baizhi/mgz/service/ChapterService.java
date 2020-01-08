package com.baizhi.mgz.service;

import com.baizhi.mgz.entity.Chapter;

import java.util.List;
import java.util.Map;

public interface ChapterService {
    List<Chapter> ShowAllChapters(Chapter chapter, Integer page, Integer rows);
    Map add(Chapter chapter);
    Map update(Chapter chapter);
    Map delete(String[] id);
    void updateByPrimaryKeySelective(Chapter chapter);
    Integer selectCount(Chapter chapter);

}
