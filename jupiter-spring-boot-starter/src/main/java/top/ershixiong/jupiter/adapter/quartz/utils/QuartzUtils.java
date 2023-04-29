package top.ershixiong.jupiter.adapter.quartz.utils;

import top.ershixiong.jupiter.domain.vo.TaskDetail;

/**
 * QuartzUtils
 */
public class QuartzUtils {

    /**
     * 获取组名称
     *
     * @param taskDetail 任务详情
     * @return 任务名称
     */
    public static final String getGroupName(TaskDetail taskDetail) {
        return taskDetail.getName() + "-Group";
    }

}
