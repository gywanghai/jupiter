package top.ershixiong.jupiter.domain.vo;

/**
 * 任务状态枚举
 */
public enum TaskStatus {
    // 初始状态
    INITIALIZED,

    // 调度中
    SCHEDULING,

    // 已停止调度
    STOPPED,

    // 已删除
    DELETED
}

