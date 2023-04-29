package top.ershixiong.jupiter.domain;

import org.quartz.Trigger;
import top.ershixiong.jupiter.domain.vo.TaskDetail;

/**
 * QuartzTriggerFactory 用于定义触发器工厂
 */
public interface QuartzTriggerFactory {

    Class<? extends JobTrigger> jobTriggerClass();

    Trigger createTrigger(TaskDetail taskDetail);
}
