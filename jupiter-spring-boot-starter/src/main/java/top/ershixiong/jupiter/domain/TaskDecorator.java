package top.ershixiong.jupiter.domain;

/**
 * 任务装饰器，用于对任务进行装饰
 */
public interface TaskDecorator {

    /**
     * 顺序，值越小越先执行
     */
    int order();

    /**
     * 是否是本装饰器支持的任务
     *
     * @param name  任务名
     * @param group 任务组
     */
    boolean supports(String name, String group);

    /**
     * 对任务进行装饰
     */
    Task decorate(Task task);

}
