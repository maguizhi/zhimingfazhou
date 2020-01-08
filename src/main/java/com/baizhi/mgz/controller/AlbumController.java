package com.baizhi.mgz.controller;

import com.baizhi.mgz.entity.Album;
import com.baizhi.mgz.service.AlbumService;
import com.baizhi.mgz.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    AlbumService albumService;
    @RequestMapping("showAllAlbums")
    public Map showAllAlbums(Integer page,Integer rows){
        Map hashMap = albumService.ShowAllAlbums(page, rows);
        return hashMap;
    }
    @RequestMapping("editAlbum")
    // oper 标示 album 数据 id 删除的id
    public Map editAlbum(String oper,Album album,String[] id,HttpSession session){
        HashMap hashMap = new HashMap();
        // 添加逻辑
        if (oper.equals("add")) {
            String albumId = UUID.randomUUID().toString();
            System.out.println(albumId);
            session.setAttribute("albumId", albumId);
            album.setId(albumId);
            albumService.add(album);
            hashMap.put("albumId", albumId);
            // 修改逻辑
        } else if (oper.equals("edit")) {
            albumService.updateByPrimaryKeySelective(album);
            hashMap.put("albumId", album.getId());
            // 删除
        } else {
            albumService.delete(id);
        }
        return hashMap;
    }
    @RequestMapping("uploadAlbum")
    public Map uploadAlbum(HttpSession session,MultipartFile cover, String albumId, HttpServletRequest request){
        HashMap hashMap = new HashMap();
        String realPath = session.getServletContext().getRealPath("/upload/albumImg/");
        File file = new File(realPath);
        if (!file.exists()){
            file.mkdirs();
        }
        // 网络路径
        String http = HttpUtil.getHttp(cover, request, "/upload/albumImg/");
        System.out.println(http);
        Album album = new Album();
        String albumId1 = (String) session.getAttribute("albumId");
        album.setId(albumId1);
        album.setCover(http);
        System.out.println(album);
        albumService.updateByPrimaryKeySelective(album);
        hashMap.put("status",200);
        return hashMap;
    }
}
