package org.snaker.engine.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.snaker.engine.entity.po.HistTask;
import org.snaker.engine.mapper.HistTaskMapper;
import org.snaker.engine.service.HistTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 历史任务表 服务实现类
 * </p>
 *
 * @author jason
 * @since 2022-07-16
 */
@Service
public class HistTaskServiceImpl extends ServiceImpl<HistTaskMapper, HistTask> implements HistTaskService {


    public List<HistTask> listByOrderId(String orderId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_id", orderId);
        return this.list(queryWrapper);
    }
}
