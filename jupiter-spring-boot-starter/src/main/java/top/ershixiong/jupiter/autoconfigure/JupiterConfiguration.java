package top.ershixiong.jupiter.autoconfigure;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.ershixiong.jupiter.adapter.quartz.QuartzJobScheduler;
import top.ershixiong.jupiter.domain.JobScheduler;

import java.util.Properties;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Jupiter 配置类
 */
@Configuration
public class JupiterConfiguration {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(JupiterConfiguration.class);

    private static Properties getQuartzProperties(ThreadPoolExecutor threadPool) {
        Properties properties = new Properties();
        properties.setProperty("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        properties.setProperty("org.quartz.threadPool.threadCount", String.valueOf(threadPool.getMaximumPoolSize()));
        properties.setProperty("org.quartz.threadPool.threadPriority", "5");
        properties.setProperty("org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread", "true");
        return properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public JobScheduler jobScheduler() {
        Scheduler scheduler = null;
        JobScheduler jobScheduler = null;
        try {
            StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
            scheduler = schedulerFactory.getScheduler();
            jobScheduler = new QuartzJobScheduler(scheduler);
        } catch (SchedulerException e) {
            LOGGER.error("创建scheduler失败", e);
        }
        return jobScheduler;
    }

}
