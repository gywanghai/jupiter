package top.ershixiong.jupiter.adapter.quartz;

import org.quartz.Trigger;
import top.ershixiong.jupiter.domain.TaskTrigger;
import top.ershixiong.jupiter.domain.vo.TaskDetail;

/**
 * QuartzTriggerFactory 用于定义触发器工厂
 */
public interface QuartzTriggerFactory {

    Class<? extends TaskTrigger> jobTriggerClass();

    Trigger createTrigger(TaskDetail taskDetail);

}
