package com.baizhi.mgz.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 651217479739994947L;
    @Id
    private String id;
    
    private String phone;
    
    private String password;
    
    private String salt;
    
    private String status;
    
    private String photo;
    
    private String name;
    
    private String nickName;
    
    private String sex;
    
    private String sign;
    
    private String location;
    @Column(name = "rigest_date")
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date rigestDate;
    @Column(name = "last_login")
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date lastLogin;


}