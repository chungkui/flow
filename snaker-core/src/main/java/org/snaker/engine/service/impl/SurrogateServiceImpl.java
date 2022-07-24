package org.snaker.engine.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.snaker.engine.entity.po.Surrogate;
import org.snaker.engine.mapper.SurrogateMapper;
import org.snaker.engine.service.SurrogateService;
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
        queryWrapper.eq(StringUtils.isNotEmpty(processName),"process_name",processName);
        queryWrapper.eq(StringUtils.isNotEmpty(processName),"operator",operator);
        //todo 时间是否要处理
        queryWrapper.le("sdate",start);
        queryWrapper.ge("edate",end);
        return this.list(queryWrapper);
    }
}
