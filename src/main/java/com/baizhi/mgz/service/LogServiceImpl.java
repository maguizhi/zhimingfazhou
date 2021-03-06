package com.baizhi.mgz.service;


import com.baizhi.mgz.dao.LogDao;
import com.baizhi.mgz.entity.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LogServiceImpl implements LogService {
     @Autowired
     LogDao logDao;

    //每次都开启事务!!!!！(记录日志，无论记录的操作成功与否都要添加日志)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void addLog(Log log) {
     logDao.insert(log);
    }

    @Override
    public List<Log> queryAllLog() {
        List<Log> list = logDao.selectAll();
        return list;
    }
}
