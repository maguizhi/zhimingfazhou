package com.baizhi.mgz.service;

import com.baizhi.mgz.dao.AdminDao;
import com.baizhi.mgz.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;

    @Override
    public Admin queryByName(String name) {
        Admin admin = adminDao.selectOne(new Admin("1", name, "admin"));
        return admin;
    }
}
