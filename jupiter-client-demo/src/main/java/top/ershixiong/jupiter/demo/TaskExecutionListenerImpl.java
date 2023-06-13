package top.ershixiong.jupiter.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.ershixiong.jupiter.domain.TaskExecutionContext;
import top.ershixiong.jupiter.domain.TaskExecutionListener;

/**
 * 任务执行监听器
 */
@Component
public class TaskExecutionListenerImpl implements TaskExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskExecutionListenerImpl.class);

    @Override
    public void beforeTaskExecution(TaskExecutionContext taskExecutionContext) {
        LOGGER.info("beforeTaskExecution");
    }

    @Override
    public void afterTaskExecution(TaskExecutionContext taskExecutionContext) {
        LOGGER.info("afterTaskExecution, taskExecutionContext: {}", taskExecutionContext);
    }

    @Override
    public void onTaskExecutionException(TaskExecutionContext taskExecutionContext, Throwable e) {
        LOGGER.info("onTaskExecutionException, taskExecutionContext: {}", taskExecutionContext, e);
    }

}


