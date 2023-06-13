package top.ershixiong.jupiter.core.timewheel;

public class TimeWheelFactory {
    public static TimeWheel createTimerWheels(int tickMs, int wheelSize, int numLevels) {
        TimeWheel result = null;
        TimeWheel prevTimeWheel = null;
        TimeWheel nextTimeWheel = null;
        for(int i = 0; i < numLevels; i++){
            TimeWheel timeWheel = null;
            if(nextTimeWheel == null){
                timeWheel = new TimeWheel(tickMs, wheelSize, prevTimeWheel, nextTimeWheel);
                result = timeWheel;
            }
            else {
                timeWheel = new TimeWheel(nextTimeWheel.getTickMs() * nextTimeWheel.getWheelSize(),
                        wheelSize, prevTimeWheel, nextTimeWheel);
            }
            if(nextTimeWheel != null){
                nextTimeWheel.setPrevTimeWheel(timeWheel);
            }
            nextTimeWheel = timeWheel;
        }
        return result;
    }
}
