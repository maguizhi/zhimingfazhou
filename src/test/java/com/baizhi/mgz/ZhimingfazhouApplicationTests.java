package com.baizhi.mgz;

import com.baizhi.mgz.dao.AdminDao;
import com.baizhi.mgz.entity.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ZhimingfazhouApplicationTests {
    @Autowired
    private AdminDao adminDao;
    @Test
    void contextLoads() {
        List<Admin> admins = adminDao.selectAll();
        for (Admin admin : admins) {
            System.out.println(admin);
        }
    }

}
