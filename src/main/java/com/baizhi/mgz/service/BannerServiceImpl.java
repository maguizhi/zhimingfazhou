package com.baizhi.mgz.service;

import com.baizhi.mgz.dao.BannerDao;
import com.baizhi.mgz.entity.Banner;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class BannerServiceImpl implements BannerService{
    @Autowired
    private BannerDao bannerDao;
    @Override
    public Map ShowAllBanners(Integer page, Integer rows) {
        HashMap hashMap = new HashMap();
        List<Banner> banners = bannerDao.selectByRowBounds(new Banner(), new RowBounds((page - 1) * rows, rows));
        int records = bannerDao.selectCount(new Banner());
        int total = records%rows==0?records/rows:records/rows+1;
        hashMap.put("rows",banners);
        hashMap.put("page",page);
        hashMap.put("records",records);
        hashMap.put("total",total);
        return hashMap;
    }

    @Override
    public Map add(Banner banner) {
        HashMap hashMap = new HashMap();
        String s = UUID.randomUUID().toString();
        System.out.println(s);
        Date date = new Date();
        banner.setCreate_date(date);
        banner.setId(s);
        bannerDao.insert(banner);
        hashMap.put("bannerId",s);
        hashMap.put("status",200);
        return hashMap;
    }

    @Override
    public Map update(Banner banner) {
        HashMap hashMap = new HashMap();
        banner.setUrl(null);
        bannerDao.updateByPrimaryKeySelective(banner);
        hashMap.put("status",200);
        hashMap.put("msg","修改成功");
        return null;
    }

    @Override
    public Map delete(String[] id) {
        HashMap hashMap = new HashMap();
        for (String s : id) {
            bannerDao.deleteByPrimaryKey(s);
        }
        hashMap.put("status",200);
        hashMap.put("msg","删除成功");
        return hashMap;
    }

    @Override
    public void updateByPrimaryKeySelective(Banner banner) {
        bannerDao.updateByPrimaryKeySelective(banner);
    }
}
