package com.baizhi.mgz.service;

import com.baizhi.mgz.entity.Banner;

import java.util.Map;

public interface BannerService {
    Map ShowAllBanners(Integer page,Integer rows);
    Map add(Banner banner);
    Map update(Banner banner);
    Map delete(String[] id);
    void updateByPrimaryKeySelective(Banner banner);
}
