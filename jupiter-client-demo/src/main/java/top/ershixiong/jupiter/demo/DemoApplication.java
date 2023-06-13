package top.ershixiong.jupiter.demo;

import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import top.ershixiong.jupiter.adapter.quartz.QuartzTaskScheduler;
import top.ershixiong.jupiter.domain.FixedRateTaskTrigger;
import top.ershixiong.jupiter.domain.Task;
import top.ershixiong.jupiter.domain.TaskExecutionContext;
import top.ershixiong.jupiter.domain.TaskScheduler;
import top.ershixiong.jupiter.domain.TaskTrigger;
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

    private static final int[] RETRY_INTERVALS = new int[]{1000, 2000, 3000, 4000, 5000};

    private static final int SLEEP_TIME = 60000;

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = SpringApplication.run(DemoApplication.class, args);
        TaskScheduler taskScheduler = applicationContext.getBean(QuartzTaskScheduler.class);
//        TaskTrigger taskTrigger = new CronTaskTrigger("0/10 * * * * ?");
//        TaskTrigger taskTrigger = new FixedRateTaskTrigger(1000, 0);
        taskScheduler.subscribeTaskExecutionListener(new TaskExecutionListenerImpl());
        taskScheduler.subscribeSchedulerListener(new TaskSchedulerListenerImpl());
        TaskDetail taskDetail = new TaskDetail();
        taskDetail.setTenantId("10000000000");
        taskDetail.setTenantName("租户1");
        taskDetail.setProjectName("项目1");
        taskDetail.setName("任务1");
        taskDetail.setDescription("任务1描述");
        taskDetail.setType(TaskType.NORMAL);
        taskDetail.setShardingIndex(0);
        taskDetail.setShardingCount(1);
        TaskTrigger taskTrigger = new FixedRateTaskTrigger(1000, 0);
        taskDetail.setTaskTrigger(taskTrigger);
//        taskScheduler.addTaskDecorator(new TaskDecorator() {
//            @Override
//            public int order() {
//                return 0;
//            }
//
//            @Override
//            public boolean supports(String name, String group) {
//                return true;
//            }
//
//            @Override
//            public Task decorate(Task task) {
//                return new Task() {
//                    @Override
//                    public void execute(TaskExecutionContext context) {
//                        LOGGER.info("任务执行前装饰器1");
//                        task.execute(context);
//                        LOGGER.info("任务执行后装饰器1");
//                    }
//                };
//            }
//        });
//        taskScheduler.addTaskDecorator(new TaskDecorator() {
//            @Override
//            public int order() {
//                return 1;
//            }
//
//            @Override
//            public boolean supports(String name, String group) {
//                return true;
//            }
//
//            @Override
//            public Task decorate(Task task) {
//                return new Task() {
//                    @Override
//                    public void execute(TaskExecutionContext context) {
//                        LOGGER.info("任务执行前装饰器2");
//                        task.execute(context);
//                        LOGGER.info("任务执行后装饰器2");
//                    }
//                };
//            }
//        });
        taskScheduler.scheduleJob(taskDetail, new Task() {
            @Override
            public void execute(TaskExecutionContext context) {
                LOGGER.info("Hello World! {}, ThreadName {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), Thread.currentThread().getName());
            }
        });
        taskScheduler.start();
        Thread.sleep(SLEEP_TIME);
        LOGGER.info("================================================");
//        taskDetail.setTaskTrigger(new FixedDelayTaskTrigger(1000, 0));
//        taskScheduler.modifyJob(taskDetail, new Task() {
//            @Override
//            public void execute(TaskExecutionContext context) {
//                LOGGER.info("Hello World! {}, ThreadName {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), Thread.currentThread().getName());
//            }
//        });
//        Thread.sleep(10000);
//        taskScheduler.deleteJob(taskDetail);
    }
}
