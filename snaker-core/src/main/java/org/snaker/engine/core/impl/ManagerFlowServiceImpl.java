/* Copyright 2013-2015 www.snakerflow.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.snaker.engine.core.impl;

import org.snaker.engine.core.ManagerFlowService;
import org.snaker.engine.cost.FlowState;
import org.snaker.engine.entity.po.Surrogate;
import org.snaker.engine.helper.AssertHelper;
import org.snaker.engine.helper.DateHelper;
import org.snaker.engine.service.SurrogateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 管理服务类
 *
 * @author yuqs
 * @since 1.4
 */
@Service
public class ManagerFlowServiceImpl implements ManagerFlowService {
    @Autowired
    SurrogateService surrogateService;

    public void saveOrUpdate(Surrogate surrogate) {
        AssertHelper.notNull(surrogate);
        surrogate.setState(FlowState.STATE_ACTIVE.getState());
        //todo id改成自增
        surrogateService.saveOrUpdate(surrogate);
    }

    public void deleteSurrogate(String id) {
        Surrogate surrogate = getSurrogate(id);
        AssertHelper.notNull(surrogate);
        surrogateService.removeById(id);

    }

    public Surrogate getSurrogate(String id) {
        return surrogateService.getById(id);
    }


    //返回数据库中不存在的处理人
    public String getSurrogate(String operator, String processName) {
        AssertHelper.notEmpty(operator);
        String operateTime = DateHelper.getTime();
        List<Surrogate> surrogates = surrogateService.list(operator,
                processName,
                operateTime,
                operateTime);
        if (surrogates == null || surrogates.isEmpty()) {
            return operator;
        }
        StringBuffer buffer = new StringBuffer(50);
        for (Surrogate surrogate : surrogates) {
            String result = getSurrogate(surrogate.getSurrogate(), processName);
            buffer.append(result).append(",");
        }
        buffer.deleteCharAt(buffer.length() - 1);
        return buffer.toString();
    }
}
