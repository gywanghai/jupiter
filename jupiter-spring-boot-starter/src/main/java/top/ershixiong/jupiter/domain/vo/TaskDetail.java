package top.ershixiong.jupiter.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import top.ershixiong.jupiter.domain.TaskTrigger;

/**
 * 任务详情
 */
@Data
@ToString
@NoArgsConstructor
public class TaskDetail {

    // 租户ID
    private String tenantId;

    // 租户名称
    private String tenantName;

    // 项目名称
    private String projectName;

    private String id;

    // 任务名称
    private String name;

    // 任务描述
    private String description;

    // 任务相关参数
    private String parameters;

    // 任务分片总数
    private int shardingCount;

    // 当前分片索引
    private int shardingIndex;

    // 任务类型
    private TaskType type;

    // 任务状态
    private TaskStatus status;
    /**
     * 任务触发信息
     */
    private TaskTrigger taskTrigger;
}
