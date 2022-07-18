package org.snaker.engine.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.snaker.engine.entity.po.Process;
import org.snaker.engine.mapper.ProcessMapper;
import org.snaker.engine.service.ProcessService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 流程定义表 服务实现类
 * </p>
 *
 * @author jason
 * @since 2022-07-16
 */
@Service
public class ProcessServiceImpl extends ServiceImpl<ProcessMapper, Process> implements ProcessService {

    public Integer getLatestProcessVersion(String name) {
        return baseMapper.selectLatestProcessVersionByName(name);
    }

    public List<Process> getProcesss(String name, Integer version) {
        QueryWrapper<Process> queryWrapper=new QueryWrapper<Process>();
        queryWrapper.eq("name",name);
        queryWrapper.eq("version",name);
        return this.list(queryWrapper);
    }
}
