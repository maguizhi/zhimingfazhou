package com.baizhi.mgz.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Banner {
    @Id
    private String id;
    private String title;
    private String url;
    private String href;
    @Column(name = "create_date")
    @JSONField(format = "yyyy-MM-dd")
    private Date create_date;
    private String intro;
    private String status;
}
