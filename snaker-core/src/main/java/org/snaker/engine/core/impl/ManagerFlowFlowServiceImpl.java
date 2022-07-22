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

import java.util.List;

import org.snaker.engine.core.ManagerFlowService;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.cost.FlowState;
import org.snaker.engine.entity.po.Surrogate;
import org.snaker.engine.helper.AssertHelper;
import org.snaker.engine.helper.DateHelper;
import org.snaker.engine.helper.StringHelper;
import org.snaker.engine.service.SurrogateService;

/**
 * 管理服务类
 * @author yuqs
 * @since 1.4
 */
public class ManagerFlowFlowServiceImpl implements ManagerFlowService {
	SurrogateService surrogateService;
	public void saveOrUpdate(Surrogate surrogate) {
		AssertHelper.notNull(surrogate);
		surrogate.setState(FlowState.STATE_ACTIVE.getState());
		if(StringHelper.isEmpty(surrogate.getId())) {
			surrogate.setId(StringHelper.getPrimaryKey());
			surrogateService.save(surrogate);
		} else {
			surrogateService.updateById(surrogate);
		}
	}

	public void deleteSurrogate(String id) {
		Surrogate surrogate = getSurrogate(id);
		AssertHelper.notNull(surrogate);
		surrogateService.removeById(id);

	}

	public Surrogate getSurrogate(String id) {
		return surrogateService.getById(id);
	}



	public String getSurrogate(String operator, String processName) {
		AssertHelper.notEmpty(operator);
		QueryFilter filter = new QueryFilter().
				setOperator(operator).
				setOperateTime(DateHelper.getTime());
		if(StringHelper.isNotEmpty(processName)) {
			filter.setName(processName);
		}
		List<Surrogate> surrogates = surrogateService.list( operator, processName,DateHelper.getTime(),DateHelper.getTime());
		if(surrogates == null || surrogates.isEmpty()) return operator;
		StringBuffer buffer = new StringBuffer(50);
		for(Surrogate surrogate : surrogates) {
			String result = getSurrogate(surrogate.getSurrogate(), processName);
			buffer.append(result).append(",");
		}
		buffer.deleteCharAt(buffer.length() - 1);
		return buffer.toString();
	}
}
