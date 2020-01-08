package com.baizhi.mgz.controller;

import com.baizhi.mgz.DTO.UserDTO;
import com.baizhi.mgz.dao.UserDao;
import com.baizhi.mgz.entity.User;
import com.baizhi.mgz.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private UserDao userDao;


    @RequestMapping("selectOne")
    public User selectOne(String id) {
        return this.userService.queryById(id);
    }

    @RequestMapping("queryAll")
    public Map queryAll(){
        System.out.println("user----queryAll");
        Map map = userService.queryAll();
        return map;
    }

    @RequestMapping("queryAllByPage")
    public Map queryAllByPage(Integer page, Integer rows){
        System.out.println("user----queryAllByPage");
        Map map = userService.queryAllByPage(page, rows);
        return map;
    }

    @RequestMapping("edit")
     // 返回值是void，相当于把空字符 响应ajax回client
    // 参数oper，代表增删改的具体操作类型，名称 固定（jqgrid控制封装好的参数）
    public Map<String, Object> edit(User user, String oper, String[ ] id){
        Map<String, Object> map = new HashMap<>();
        System.out.println("...执行增删改操作...edit...");
        if ("add".equals(oper)){
            System.out.println("添加操作。。。执行");
            User b = userService.insert(user);
            map.put("userId",b.getId());
        } else if ("edit".equals(oper)){
            System.out.println("修改操作。。。执行");
            user.setPhoto(null);
            userService.update(user);
            //map.put("userId",user.getId());
        } else {
            System.out.println("删除操作(可批量删除)。。。执行");
            //userService.deleteById(user.getId());
            int i = userService.deleteByIdList(Arrays.asList(id));
            System.out.println("删除了"+ i + "条数据");
        }
        return map;
    }
    //文件上传
    @RequestMapping("uploadUser")
    public void uploadUser(MultipartFile photo, String userId, HttpSession session) {
        System.out.println("uploadUser：文件上传");
        System.out.println("MultipartFile："+ photo);
        // 获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/img/");
        // 判断该文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            // mkdirs() 多级创建
            file.mkdirs();
        }
        // 防止文件重名
        String originalFilename = photo.getOriginalFilename();
        String newFileName = new Date().getTime() + "_" + originalFilename;
        try {
            photo.transferTo(new File(realPath, newFileName));

        } catch (IOException e) {
            e.printStackTrace();
        }
        // new User(); 设置id(找到原始数据) url(更新使用)
        // userDao.updateByPrimaryKeySetive(user);
        if(originalFilename.equals("")){
            User user = new User();
            user.setId(userId);
            user.setPhoto("/upload/img/"+"默认头像.jpg");
            userService.update(user);
        }else {
            User user = new User();
            user.setId(userId);
            user.setPhoto("/upload/img/"+newFileName);
            if (user.getPhoto().equals("")){
                System.out.println("修改123123123");
                user.setPhoto(null);
            }
            userService.update(user);
        }
    }
    //-------------eacharts:统计展示用户信息--------------------------------------------
    @RequestMapping("showUserTime")
    //用户活跃度分析
    public Map showUserTime(){
        Map map = new HashMap();
        ArrayList manList = new ArrayList();
        manList.add(userDao.queryUserByTime("1",1));
        manList.add(userDao.queryUserByTime("1",7));
        manList.add(userDao.queryUserByTime("1",30));
        manList.add(userDao.queryUserByTime("1",365));
        System.out.println(manList.get(0));
        System.out.println(manList.get(1));
        System.out.println(manList.get(2));
        System.out.println(manList.get(3));
        ArrayList womenList = new ArrayList();
        womenList.add(userDao.queryUserByTime("0",1));
        womenList.add(userDao.queryUserByTime("0",7));
        womenList.add(userDao.queryUserByTime("0",30));
        womenList.add(userDao.queryUserByTime("0",365));
        System.out.println(womenList.get(0));
        System.out.println(womenList.get(1));
        System.out.println(womenList.get(2));
        System.out.println(womenList.get(3));
        map.put("man",manList);
        map.put("women",womenList);
        return map;
    }

    @RequestMapping("showUserLocation")
    //用户地区分布
    public Map showUserLocation(){
        Map map = new HashMap();
        List<UserDTO> mandtoList = userDao.queryUserBySexLocation("1");
        System.out.println("mandtoList = " + mandtoList);
        List<UserDTO> womendtoList = userDao.queryUserBySexLocation("0");
        System.out.println("womendtoList = " + womendtoList);
        map.put("man",mandtoList);
        map.put("women",womendtoList);
        return map;
    }

}