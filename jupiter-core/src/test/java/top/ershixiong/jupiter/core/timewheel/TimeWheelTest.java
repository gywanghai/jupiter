package top.ershixiong.jupiter.core.timewheel;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeWheelTest {

    @Test
    public void testTimeWheel(){
        int tickMs = 50;
        int wheelSize = 256;
        int levels = 3;

        TimeWheel timeWheel = TimeWheelFactory.createTimerWheels(tickMs, wheelSize, levels);
        timeWheel.start();
        Runnable runnable = () -> System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date()));
        timeWheel.addTask(new TimeWheelTask(0, runnable));
        timeWheel.addTask(new TimeWheelTask(1000, runnable));
        timeWheel.addTask(new TimeWheelTask(2000, runnable));
        timeWheel.addTask(new TimeWheelTask(3000, runnable));
        timeWheel.addTask(new TimeWheelTask(4000, runnable));
        timeWheel.addTask(new TimeWheelTask(11000, runnable));
        timeWheel.addTask(new TimeWheelTask(12000, runnable));
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
