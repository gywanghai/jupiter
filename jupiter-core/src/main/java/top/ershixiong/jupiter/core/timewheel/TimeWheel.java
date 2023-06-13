package top.ershixiong.jupiter.core.timewheel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 时间轮
 */
public class TimeWheel {
    /**
     * 每个时间槽的时长，单位：毫秒
     */
    private int tickMs;

    /**
     * 时间轮的槽数量
     */
    private int wheelSize;

    /**
     * 下一级时间轮（更细粒度的时间轮）
     */
    private TimeWheel nextTimeWheel;

    /**
     * 上一级时间轮(更粗粒度的时间轮)
     */
    private TimeWheel prevTimeWheel;

    /**
     * 时间轮的槽位，每个槽位存放一个任务优先级队列
     */
    private List<PriorityBlockingQueue<TimeWheelTask>> slots;

    /**
     * 当前时间轮的槽位索引
     */
    private int curSlotIndex;

    /**
     * 定时任务执行器
     */
    private ScheduledExecutorService scheduledExecutorService;

    public int getTickMs() {
        return tickMs;
    }

    public void setTickMs(int tickMs) {
        this.tickMs = tickMs;
    }

    public int getWheelSize() {
        return wheelSize;
    }

    public void setWheelSize(int wheelSize) {
        this.wheelSize = wheelSize;
    }

    public TimeWheel getNextTimeWheel() {
        return nextTimeWheel;
    }

    public void setNextTimeWheel(TimeWheel nextTimeWheel) {
        this.nextTimeWheel = nextTimeWheel;
    }

    public TimeWheel getPrevTimeWheel() {
        return prevTimeWheel;
    }

    public void setPrevTimeWheel(TimeWheel prevTimeWheel) {
        this.prevTimeWheel = prevTimeWheel;
    }

    public List<PriorityBlockingQueue<TimeWheelTask>> getSlots() {
        return slots;
    }

    public void setSlots(List<PriorityBlockingQueue<TimeWheelTask>> slots) {
        this.slots = slots;
    }

    public int getCurSlotIndex() {
        return curSlotIndex;
    }

    public void setCurSlotIndex(int curSlotIndex) {
        this.curSlotIndex = curSlotIndex;
    }

    public TimeWheel(int tickMs, int wheelSize) {
        this(tickMs, wheelSize, null, null);
    }


    public TimeWheel(int tickMs, int wheelSize, TimeWheel prevTimeWheel, TimeWheel nextTimeWheel) {
        this.tickMs = tickMs;
        this.wheelSize = wheelSize;
        this.slots = new ArrayList<>();
        for(int i = 0; i < wheelSize; i++){
            slots.add(new PriorityBlockingQueue<>(1000,(o1, o2) -> (int) (o1.getDelayMs() - o2.getDelayMs())));
        }
        this.prevTimeWheel = prevTimeWheel;
        this.nextTimeWheel = nextTimeWheel;
    }

    public void start(){
        scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            tick();
        }, 0, tickMs, TimeUnit.MILLISECONDS);
    }

    public long getMaxDelayMs(){
        TimeWheel timeWheel = this;
        while (timeWheel.prevTimeWheel != null){
            timeWheel = timeWheel.prevTimeWheel;
        }
        return timeWheel.tickMs * timeWheel.wheelSize;
    }

    /**
     * 时间轮转动一格
     */
    public void tick(){
        PriorityBlockingQueue<TimeWheelTask> curSlot = slots.get(curSlotIndex);
        while(!curSlot.isEmpty()){
            TimeWheelTask timeWheelTask = curSlot.poll();
            timeWheelTask.getTask().run();
        }
        curSlotIndex = (curSlotIndex + 1) % wheelSize;
        if(curSlotIndex == 0){
            if(prevTimeWheel != null){
                prevTimeWheel.tick();
            }
        }
        if(prevTimeWheel != null){
            prevTimeWheel.degradeTask();
        }
    }

    /**
     * 添加任务
     * @param task
     * @return
     */
    public boolean addTask(TimeWheelTask task) {
        long delayMs = task.getDelayMs();
        long maxDelayMs = getMaxDelayMs();
        if(delayMs > maxDelayMs){
            throw new IllegalStateException("delayMs is out of range, delayMs: " + delayMs + ", maxDelayMs: " + maxDelayMs);
        }
        int targetSlotIndex = (int) (delayMs / tickMs + curSlotIndex);
        // 如果目标槽位在当前时间轮的槽位范围内，则添加到对应的槽位中
        if(targetSlotIndex <= wheelSize - 1){
            slots.get(targetSlotIndex).offer(task);
        } else {
            // 否则，添加到上一级时间轮的槽位中
            if(prevTimeWheel != null){
                prevTimeWheel.addTask(task);
            }
        }
        return true;
    }

    /**
     * 任务降级
     */
    public void degradeTask(){
        for(PriorityBlockingQueue<TimeWheelTask> curSlot : slots){
            TimeWheelTask timeWheelTask = curSlot.peek();
            while (timeWheelTask != null){
                timeWheelTask.updateDelayMs();
                if(timeWheelTask.getDelayMs() <= nextTimeWheel.tickMs){
                    nextTimeWheel.addTask(timeWheelTask);
                    curSlot.poll();
                } else {
                    break;
                }
                timeWheelTask = curSlot.peek();
            }
        }
        if(prevTimeWheel != null){
            prevTimeWheel.degradeTask();
        }
    }
}

