package top.ershixiong.jupiter.autoconfigure;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.ershixiong.jupiter.adapter.quartz.QuartzTaskScheduler;
import top.ershixiong.jupiter.domain.TaskScheduler;

/**
 * Jupiter 配置类
 */
@Configuration
public class JupiterConfiguration {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(JupiterConfiguration.class);

    @Bean
    @ConditionalOnMissingBean
    public TaskScheduler jobScheduler() {
        Scheduler scheduler = null;
        TaskScheduler taskScheduler = null;
        try {
            StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
            scheduler = schedulerFactory.getScheduler();
            taskScheduler = new QuartzTaskScheduler(scheduler);
        } catch (SchedulerException e) {
            LOGGER.error("创建scheduler失败", e);
        }
        return taskScheduler;
    }

}
