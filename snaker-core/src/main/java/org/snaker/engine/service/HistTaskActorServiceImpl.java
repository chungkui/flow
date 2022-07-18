package org.snaker.engine.service;

import org.snaker.engine.entity.po.HistTaskActor;
import org.snaker.engine.mapper.HistTaskActorMapper;
import org.snaker.engine.service.HistTaskActorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 历史任务参与者表 服务实现类
 * </p>
 *
 * @author jason
 * @since 2022-07-16
 */
@Service
public class HistTaskActorServiceImpl extends ServiceImpl<HistTaskActorMapper, HistTaskActor> implements HistTaskActorService {

}
