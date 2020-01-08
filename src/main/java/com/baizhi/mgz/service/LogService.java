package com.baizhi.mgz.service;


import com.baizhi.mgz.entity.Log;

import java.util.List;

public interface LogService {
    public void addLog(Log log);
    public List<Log> queryAllLog();
}
