package org.snaker.engine.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.snaker.engine.entity.po.Surrogate;
import org.snaker.engine.mapper.SurrogateMapper;
import org.snaker.engine.service.SurrogateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 委托代理表 服务实现类
 * </p>
 *
 * @author jason
 * @since 2022-07-16
 */
@Service
public class SurrogateServiceImpl extends ServiceImpl<SurrogateMapper, Surrogate> implements SurrogateService {

    public List<Surrogate> list(String operator, String processName, String start, String end) {
        QueryWrapper<Surrogate>queryWrapper=new QueryWrapper<Surrogate>();
        queryWrapper.eq("process_name",processName);
        queryWrapper.eq("operator",operator);
        queryWrapper.le("sdate",start);
        queryWrapper.ge("edate",end);
        return this.list(queryWrapper);
    }
}
