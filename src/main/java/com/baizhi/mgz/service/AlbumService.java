package com.baizhi.mgz.service;

import com.baizhi.mgz.entity.Album;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface AlbumService {
    Map ShowAllAlbums(Integer page, Integer rows);
    Map add(Album album);
    Map update(Album album);
    Map delete(String[] id);
    void updateByPrimaryKeySelective(Album album);
}
