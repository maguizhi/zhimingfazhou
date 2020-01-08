package com.baizhi.mgz.dao;

import com.baizhi.mgz.entity.Banner;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BannerDao extends Mapper<Banner> {
    List<Banner> queryBannersByTime();
}
