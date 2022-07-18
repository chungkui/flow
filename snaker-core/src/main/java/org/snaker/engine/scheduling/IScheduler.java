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
package org.snaker.engine.scheduling;

/**
 * 调度器接口，与具体的定时调度框架无关
 *
 * @author yuqs
 * @since 1.4
 */
public interface IScheduler {
    String CONFIG_REPEAT = "scheduler.repeat";
    String CONFIG_USECALENDAR = "scheduler.useCalendar";
    String CONFIG_HOLIDAYS = "scheduler.holidays";
    String CONFIG_WEEKS = "scheduler.weeks";
    String CONFIG_WORKTIME = "scheduler.workTime";

    String CALENDAR_NAME = "snakerCalendar";

    String KEY = "id";
    String MODEL = "model";
    String GROUP = "snaker";

    String TYPE_EXECUTOR = "executor.";
    String TYPE_REMINDER = "reminder.";

    /**
     * 调度执行方法
     *
     * @param entity 调度DTO
     */
    void schedule(JobEntity entity);

    /**
     * 停止调度
     *
     * @param key job主键
     */
    void delete(String key);
}
