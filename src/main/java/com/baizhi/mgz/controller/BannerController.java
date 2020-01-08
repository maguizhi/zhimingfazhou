package com.baizhi.mgz.controller;

import com.alibaba.excel.EasyExcel;
import com.baizhi.mgz.dao.BannerDao;
import com.baizhi.mgz.entity.Banner;
import com.baizhi.mgz.entity.BannerListener;
import com.baizhi.mgz.service.BannerService;
import com.baizhi.mgz.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private BannerDao bannerDao;
    @RequestMapping("showAllBanner")
    public Map ShowAllBanners(Integer page, Integer rows){
        Map map = bannerService.ShowAllBanners(page, rows);
        return map;
    }
    @RequestMapping("edit")
    public Map edit(String oper, Banner banner,String[] id){
        Map hashMap = new HashMap();
        if (oper.equals("add")){
            System.out.println(banner.getId());
            hashMap = bannerService.add(banner);
        }else if ("edit".equals(oper)){
            bannerService.update(banner);
        }else {
            bannerService.delete(id);
        }
        System.out.println(banner);
        System.out.println("以下为map");
        System.out.println(hashMap);
        return hashMap;
    }
    @RequestMapping("upload")
    public void upload(MultipartFile url, String bannerId, HttpSession session, HttpServletRequest httpServletRequest) throws UnknownHostException {
        String realPath = session.getServletContext().getRealPath("/upload/img");
        File file = new File(realPath);
        if (!file.exists()){
            file.mkdir();
        }
        String originalFilename = url.getOriginalFilename();
        originalFilename = new Date().getTime()+"_"+originalFilename;
        try {
            url.transferTo(new File(realPath,originalFilename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String scheme = httpServletRequest.getScheme();
        String localHost = InetAddress.getLocalHost().toString();
        int serverPort = httpServletRequest.getServerPort();
        String contextPath = httpServletRequest.getContextPath();
        Banner banner = new Banner();
        String aaa = scheme+"://"+localHost.split("/")[1]+":"+serverPort+contextPath+"/upload/img/"+originalFilename;
        banner.setId(bannerId);
        System.out.println(bannerId);
        banner.setUrl(aaa);
        bannerService.updateByPrimaryKeySelective(banner);
    }
    //导出信息，生成Excel（下载文件）
    @RequestMapping("exportBanner")
    @ResponseBody
    public void exportBanner(HttpServletResponse response) throws IOException {
        System.out.println("111111");
        List<Banner> bannerList = bannerDao.selectAll();
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("Banner信息"+new Date().getTime(), "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), Banner.class).sheet("Banner模板").doWrite(bannerList);
    }

    //上传 + 从Excel导入信息
    @RequestMapping("importBanner")
    @ResponseBody
    public String importBanner(MultipartFile bannerFile, HttpServletRequest request) throws IOException {
        System.out.println("222222");
        System.out.println("importBanner:上传 + 从Excel导入信息");
        //上传
        String uploadPath = UploadUtil.getUploadPath(bannerFile, request, "/upload/bannerExcel/");
        // String httpPath = HttpUtil.getHttp(bannerFile, request, "/upload/bannerExcel/");
        //String uploadPath = UploadUtil.getUploadPath(bannerFile, request, "\\upload\\bannerExcel\\");
        System.out.println("uploadPath = " + uploadPath);
        //String httpPath = HttpUtil.getHttp(bannerFile, request, "\\upload\\bannerExcel\\");
        String realPath = request.getServletContext().getRealPath(uploadPath);

        System.out.println("realPath = " + realPath);

        EasyExcel.read(realPath, Banner.class, new BannerListener(bannerService)).sheet().doRead();
        return "sss";
    }

    //测试 导出Excel(项目中没使用)
    @RequestMapping("outBanner")
    @ResponseBody
    public void outBanner(){
        System.out.println("333333");
        List<Banner> bannerList = bannerDao.selectAll();
        //String fileName = "D:\\后期项目\\day7-poiEasyExcel\\示例\\"+new Date().getTime()+".xlsx";
        String fileName = "E:\\轮播图"+new Date().getTime()+".xlsx";
        // write() 参数1:文件路径 参数2:实体类.class sheet()指定写入工作簿的名称 doWrite(List数据) 写入操作
        // 如需下载使用 参数1:outputSteam 参数2:实体类.class
        EasyExcel.write(fileName, Banner.class) // 指定文件导出的路径及样式
                .sheet("轮播图")           // 指定导出到哪个sheet工作簿
                //.doWrite(Arrays.asList(new DemoData(UUID.randomUUID().toString(),18,new Date(),"Rxx"),new DemoData(UUID.randomUUID().toString(),18,new Date(),"Rxx"),new DemoData(UUID.randomUUID().toString(),18,new Date(),"Rxx"),new DemoData(UUID.randomUUID().toString(),18,new Date(),"Rxx")));
                .doWrite(bannerList);
        // 导出操作 准备数据
    }
}
