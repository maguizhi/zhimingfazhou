package com.baizhi.mgz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.baizhi.mgz.dao")
public class ZhimingfazhouApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhimingfazhouApplication.class, args);
    }

}
