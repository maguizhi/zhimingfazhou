package com.baizhi.mgz.service;

import com.baizhi.mgz.dao.AlbumDao;
import com.baizhi.mgz.entity.Album;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.*;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService{
    @Autowired
    private AlbumDao albumDao;
    @Override
    public Map ShowAllAlbums(Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        List<Album> albums = albumDao.selectByRowBounds(new Album(), new RowBounds((page - 1) * rows, rows));
        int records = albumDao.selectCount(new Album());
        int total = records%rows==0?records/rows:records/rows+1;
        hashMap.put("rows",albums);
        hashMap.put("page",page);
        hashMap.put("records",records);
        hashMap.put("total",total);
        return hashMap;
    }

    @Override
    public Map add(Album album) {
        System.out.println("添加");
        HashMap hashMap = new HashMap();

        Date date = new Date();
        album.setCreateDate(date);

        albumDao.insert(album);
        hashMap.put("status",200);
        return hashMap;
    }

    @Override
    public Map update(Album album) {
        HashMap hashMap = new HashMap();
        albumDao.updateByPrimaryKeySelective(album);
        hashMap.put("status",200);
        hashMap.put("msg","修改成功");
        return null;
    }

    @Override
    public Map delete(String[] id) {
        HashMap hashMap = new HashMap();
        for (String s : id) {
            albumDao.deleteByPrimaryKey(s);
        }
        hashMap.put("status",200);
        hashMap.put("msg","删除成功");
        return hashMap;
    }

    @Override
    public void updateByPrimaryKeySelective(Album album) {
        System.out.println("图片");
        System.out.println(album);
        album.setCover(null);
        albumDao.updateByPrimaryKeySelective(album);
    }
}