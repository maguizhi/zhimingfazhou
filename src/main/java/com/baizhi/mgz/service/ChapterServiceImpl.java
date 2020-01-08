package com.baizhi.mgz.service;

import com.baizhi.mgz.dao.ChapterDao;
import com.baizhi.mgz.entity.Chapter;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService{
    @Autowired
    private ChapterDao chapterDao;
    @Override
    public List<Chapter> ShowAllChapters(Chapter chapter,Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        List<Chapter> chapters = chapterDao.selectByRowBounds(chapter, new RowBounds((page - 1) * rows, rows));
        int records = chapterDao.selectCount(new Chapter());
        int total = records%rows==0?records/rows:records/rows+1;
        hashMap.put("rows",chapters);
        hashMap.put("page",page);
        hashMap.put("records",records);
        hashMap.put("total",total);
        return chapters;
    }

    @Override
    public Map add(Chapter chapter) {
        HashMap hashMap = new HashMap();
        Date date = new Date();
        chapter.setCreateTime(date);
        chapterDao.insert(chapter);
        hashMap.put("status",200);
        return hashMap;
    }

    @Override
    public Map update(Chapter chapter) {
        HashMap hashMap = new HashMap();
        chapter.setUrl(null);
        System.out.println(chapter);
        chapterDao.updateByPrimaryKeySelective(chapter);
        hashMap.put("status",200);
        hashMap.put("msg","修改成功");
        return null;
    }

    @Override
    public Map delete(String[] id) {
        HashMap hashMap = new HashMap();
        for (String s : id) {
            chapterDao.deleteByPrimaryKey(s);
        }
        hashMap.put("status",200);
        hashMap.put("msg","删除成功");
        return hashMap;
    }

    @Override
    public void updateByPrimaryKeySelective(Chapter chapter) {
        chapterDao.updateByPrimaryKeySelective(chapter);
    }

    @Override
    public Integer selectCount(Chapter chapter) {
        int i = chapterDao.selectCount(chapter);
        return i;
    }
}

