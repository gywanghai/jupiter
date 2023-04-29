package top.ershixiong.jupiter.common.utils;

import java.util.Date;

/**
 * 时间工具类
 */
public class DateUtils {

    /**
     * 判断当前时间是否在两个时间之间
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return true：在两个时间之间，false：不在两个时间之间
     */
    public static boolean isBetween(Date startTime, Date endTime) {
        Date now = new Date();
        return now.after(startTime) && now.before(endTime);
    }
}
