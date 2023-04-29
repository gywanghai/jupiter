package top.ershixiong.jupiter.domain;

import java.util.Date;

/**
 * 任务触发器
 */
public interface JobTrigger {

    /**
     * 剩余执行次数
     *
     * @return 剩余执行次数
     */
    int remainCount();

    /**
     * 减少剩余执行次数
     *
     * @return 剩余执行次数
     */
    int decreaseRemainCount();

    /**
     * 开始时间
     *
     * @return 下次执行时间
     */
    default Date startAt() {
        return null;
    }

    /**
     * 结束时间
     *
     * @return 结束时间
     */
    default Date endAt() {
        return null;
    }

}
