package com.views.tools.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Description: 常用的日期格式化方法
 */
@SuppressWarnings("unused")
public class DateUtil {
    public enum Format {
        /**
         * 日期 + 时间类型格式，到秒
         */
        SECOND("yyyy-MM-dd HH:mm:ss"),
        /**
         * 日期 + 时间类型格式，到分
         */
        MINUTE("yyyy-MM-dd HH:mm"),
        /**
         * 日期类型格式，到日
         */
        DATE("yyyy-MM-dd"),
        /**
         * 日期类型格式，到月
         */
        MONTH("yyyy-MM"),
        /**
         * 日期类型格式，到月
         */
        MONTH_CHINA("yyyy年MM月"),
        /**
         * 时间类型的格式
         */
        TIME("HH:mm:ss");
        // 格式化格式
        private String value;

        Format(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    // 注意SimpleDateFormat不是线程安全的
    private static SoftHashMap<String, ThreadLocal<SimpleDateFormat>> map = new SoftHashMap<>();

    /**
     * 日期格式化
     */
    public static String formatter(Format format, Object date) {
        if (date == null) {
            return "";
        } else {
            SimpleDateFormat sdf;
            String key = format.getValue();
            if (map.containsKey(key)) {
                sdf = map.get(key).get();
            } else {
                sdf = new SimpleDateFormat(key, Locale.getDefault());
                ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<>();
                threadLocal.set(sdf);
                map.put(key, threadLocal);
            }
            return sdf.format(new Date(ConverterUtil.getLong(date.toString())));
        }
    }

    /**
     * 日期格式化
     */
    public static String formatter(String format, Object date) {
        if (date == null) {
            return "";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
            return sdf.format(new Date(ConverterUtil.getLong(date.toString())));
        }
    }

    /**
     * 日期格式化
     */
    public static String formatterDate(String format, Date date) {
        if (date == null) {
            return "";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
            return sdf.format(date);
        }
    }

    /**
     * 初略的剩余时间，年、个月、天、小时、分钟
     */
    public static String getTimeLeft(Object time) {
        if (time == null) {
            return "";
        } else {
            long diffValue = ConverterUtil.getLong(time.toString());

            long minute = 1000 * 60;
            long hour = minute * 60;
            long day = hour * 24;
            long month = day * 30;
            long year = month * 12;

            long _year = diffValue / year;
            long _month = diffValue / month;
            long _day = diffValue / day;
            long _hour = diffValue / hour;
            long _min = diffValue / minute;

            if (_year >= 1) {
                return (_year) + "年";
            } else if (_month >= 1) {
                return (_month) + "个月";
            } else if (_day >= 1) {
                return (_day) + "天";
            } else if (_hour >= 1) {
                return (_hour) + "小时";
            } else {
                return (_min) + "分钟";
            }
        }
    }

    /**
     * 倒计时格式化，时:分:秒
     */
    public static String getCountdownTime(Object time) {
        if (time == null) {
            return "";
        } else {
            long diffValue = ConverterUtil.getLong(time.toString());
            long day = diffValue / (24 * 60 * 60 * 1000);
            long hour = (diffValue / (60 * 60 * 1000) - day * 24);
            long min = ((diffValue / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long sec = (diffValue / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            return (hour > 9 ? hour : ("0" + hour)) + ":" + (min > 9 ? min : ("0" + min)) + ":" + (sec > 9 ? sec : ("0" + sec));
        }
    }

    /**
     *获取某月第一天与最后一天的日期
     */
    public static Map<String, String> getFirstdayAndLastdayOfMonth(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Date theDate = calendar.getTime();

        //上个月第一天
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first);
        day_first = str.toString();

        //上个月最后一天
        calendar.add(Calendar.MONTH, 1);    //加一个月
        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
        String day_last = df.format(calendar.getTime());
        StringBuffer endStr = new StringBuffer().append(day_last);
        day_last = endStr.toString();

        Map<String, String> map = new HashMap<String, String>();
        map.put("first", day_first);
        map.put("last", day_last);
        return map;
    }


}