package org.snaker.engine.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.snaker.engine.entity.po.HistOrder;
import org.snaker.engine.mapper.HistOrderMapper;
import org.snaker.engine.service.HistOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 历史流程实例表 服务实现类
 * </p>
 *
 * @author jason
 * @since 2022-07-16
 */
@Service
public class HistOrderServiceImpl extends ServiceImpl<HistOrderMapper, HistOrder> implements HistOrderService {

    public List<HistOrder> listByProcessId(String processId) {
        QueryWrapper<HistOrder> queryWrapper=new QueryWrapper();
        queryWrapper.eq("process_id",processId);
        return this.list(queryWrapper);
    }
}
