package top.ershixiong.jupiter.demo;

import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import top.ershixiong.jupiter.adapter.quartz.QuartzJobScheduler;
import top.ershixiong.jupiter.domain.CronJobTrigger;
import top.ershixiong.jupiter.domain.FixedDelayJobTrigger;
import top.ershixiong.jupiter.domain.JobScheduler;
import top.ershixiong.jupiter.domain.JobTrigger;
import top.ershixiong.jupiter.domain.Task;
import top.ershixiong.jupiter.domain.TaskDecorator;
import top.ershixiong.jupiter.domain.TaskExecutionContext;
import top.ershixiong.jupiter.domain.vo.TaskDetail;
import top.ershixiong.jupiter.domain.vo.TaskType;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DemoApplication 应用程序入口
 */
@SpringBootApplication
public class DemoApplication {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext applicationContext = SpringApplication.run(DemoApplication.class, args);
        JobScheduler jobScheduler = applicationContext.getBean(QuartzJobScheduler.class);
        JobTrigger jobTrigger = new CronJobTrigger("0/10 * * * * ?");
//        JobTrigger jobTrigger = new FixedRateJobTrigger(1000, 0);
        jobScheduler.subscribeTaskExecutionListener(new TaskExecutionListenerImpl());
        jobScheduler.subscribeTaskExecutionListener(new TaskExecutionListenerImpl2());
        jobScheduler.subscribeSchedulerListener(new JobSchedulerListenerImpl());
        TaskDetail taskDetail = new TaskDetail();
        taskDetail.setTenantId(1L);
        taskDetail.setTenantName("租户1");
        taskDetail.setProjectName("项目1");
        taskDetail.setName("任务1");
        taskDetail.setDescription("任务1描述");
        taskDetail.setType(TaskType.NORMAL);
        taskDetail.setShardingIndex(0);
        taskDetail.setShardingCount(1);
        taskDetail.setJobTrigger(jobTrigger);
        jobScheduler.addTaskDecorator(new TaskDecorator() {
            @Override
            public int order() {
                return 0;
            }

            @Override
            public boolean supports(String name, String group) {
                return true;
            }

            @Override
            public Task decorate(Task task) {
                return new Task() {
                    @Override
                    public void execute(TaskExecutionContext context) {
                        LOGGER.info("任务执行前装饰器1");
                        task.execute(context);
                        LOGGER.info("任务执行后装饰器1");
                    }
                };
            }
        });
        jobScheduler.addTaskDecorator(new TaskDecorator() {
            @Override
            public int order() {
                return 1;
            }

            @Override
            public boolean supports(String name, String group) {
                return true;
            }

            @Override
            public Task decorate(Task task) {
                return new Task() {
                    @Override
                    public void execute(TaskExecutionContext context) {
                        LOGGER.info("任务执行前装饰器2");
                        task.execute(context);
                        LOGGER.info("任务执行后装饰器2");
                    }
                };
            }
        });
        jobScheduler.scheduleJob(taskDetail, new Task() {

            @Override
            public void execute(TaskExecutionContext context) {
                LOGGER.info("Hello World! {}, ThreadName {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), Thread.currentThread().getName());
            }

        });
        jobScheduler.start();
        Thread.sleep(10000);
        LOGGER.info("================================================");
        taskDetail.setJobTrigger(new FixedDelayJobTrigger(1000, 0));
        jobScheduler.modifyJob(taskDetail, new Task() {
            @Override
            public void execute(TaskExecutionContext context) {
                LOGGER.info("Hello World! {}, ThreadName {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), Thread.currentThread().getName());
            }
        });
        Thread.sleep(10000);
        jobScheduler.deleteJob(taskDetail);
    }

}
