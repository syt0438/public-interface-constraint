package com.publicinterface.constraint.utils;

import lombok.Data;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.Serializable;
import java.util.*;

/**
 * 时间工具类
 *
 * @author LinShu
 * Please contact me if you have any questions.
 * My e-mail is syt0438@163.com
 */
@SuppressWarnings("all")
public class DateUtil {

    public static Date getHour() {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public static Date getNextHour() {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        calendar.add(Calendar.HOUR, 1);

        return calendar.getTime();
    }

    public static Date getToday() {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public static Date getTomorrow() {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        calendar.add(Calendar.DAY_OF_YEAR, 1);

        return calendar.getTime();
    }

    public static Date getStartMonthOfThisMonth() {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public static Date getEndMonthOfThisMonth() {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        return calendar.getTime();
    }


    /**
     * 为目标日期减去指定天数, 包括当天
     */
    public static Date substractOfDaysForTargetDate(Date targetDate, Integer days) {
        if (Objects.isNull(targetDate)) {
            throw new IllegalArgumentException();
        }

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(targetDate);
        calendar.add(Calendar.DAY_OF_YEAR, (-days + 1));

        return calendar.getTime();
    }

    public static Date getStartMonthOfThisYear() {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public static Date getLastMonthOfThisYear() {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(getStartMonthOfThisYear());
        calendar.add(Calendar.YEAR, 1);
        calendar.add(Calendar.MONTH, -1);

        return calendar.getTime();
    }

    public static Date getLastDayOfThisYear() {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(getStartMonthOfThisYear());
        calendar.add(Calendar.YEAR, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        return calendar.getTime();
    }

    public static List<StartToEndTimeBean> getAllSecondStartToEndTime(Date startTime, Date endTime) {
        return getAllSecondStartToEndTime(startTime, endTime, null);
    }

    public static List<StartToEndTimeBean> getAllMinuteStartToEndTime(Date startTime, Date endTime) {
        return getAllMinuteStartToEndTime(startTime, endTime, null);
    }

    public static List<StartToEndTimeBean> getAllHourStartToEndTime(Date startTime, Date endTime) {
        return getAllHourStartToEndTime(startTime, endTime, null);
    }

    public static List<StartToEndTimeBean> getAllDayStartToEndTime(Date startTime, Date endTime) {
        return getAllDayStartToEndTime(startTime, endTime, null);
    }

    public static List<StartToEndTimeBean> getAllMonthStartToEndTime(Date startTime, Date endTime) {
        return getAllMonthStartToEndTime(startTime, endTime, null);
    }

    public static List<StartToEndTimeBean> getAllYearStartToEndTime(Date startTime, Date endTime) {
        return getAllYearStartToEndTime(startTime, endTime, null);
    }

    public static List<StartToEndTimeBean> getAllSecondStartToEndTime(Date startTime, Date endTime, Integer duration) {
        duration = Objects.requireNonNullElse(duration, 1);

        List<StartToEndTimeBean> data = new LinkedList<>();
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();

        startCalendar.setTime(startTime);
        endCalendar.setTime(endTime);

        startCalendar.set(Calendar.MILLISECOND, 0);

        endCalendar.set(Calendar.MILLISECOND, 0);

        Calendar curruent = startCalendar;
        while (curruent.before(endCalendar)) {
            StartToEndTimeBean dataItem = new StartToEndTimeBean();
            dataItem.setStartTime(curruent.getTime());

            curruent.add(Calendar.SECOND, duration);

            dataItem.setEndTime(curruent.getTime());
            data.add(dataItem);
        }

        return data;
    }

    public static List<StartToEndTimeBean> getAllMinuteStartToEndTime(Date startTime, Date endTime, Integer duration) {
        duration = Objects.requireNonNullElse(duration, 1);

        List<StartToEndTimeBean> data = new LinkedList<>();
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();

        startCalendar.setTime(startTime);
        endCalendar.setTime(endTime);

        startCalendar.set(Calendar.SECOND, 0);
        startCalendar.set(Calendar.MILLISECOND, 0);

        endCalendar.set(Calendar.SECOND, 0);
        endCalendar.set(Calendar.MILLISECOND, 0);

        Calendar curruent = startCalendar;
        while (curruent.before(endCalendar)) {
            StartToEndTimeBean dataItem = new StartToEndTimeBean();
            dataItem.setStartTime(curruent.getTime());

            curruent.add(Calendar.MINUTE, duration);

            dataItem.setEndTime(curruent.getTime());
            data.add(dataItem);
        }

        return data;
    }

    public static List<StartToEndTimeBean> getAllHourStartToEndTime(Date startTime, Date endTime, Integer duration) {
        duration = Objects.requireNonNullElse(duration, 1);

        List<StartToEndTimeBean> data = new LinkedList<>();
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();

        startCalendar.setTime(startTime);
        endCalendar.setTime(endTime);

        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        startCalendar.set(Calendar.MILLISECOND, 0);

        endCalendar.set(Calendar.MINUTE, 0);
        endCalendar.set(Calendar.SECOND, 0);
        endCalendar.set(Calendar.MILLISECOND, 0);

        Calendar curruent = startCalendar;
        while (curruent.before(endCalendar)) {
            StartToEndTimeBean dataItem = new StartToEndTimeBean();
            dataItem.setStartTime(curruent.getTime());

            curruent.add(Calendar.HOUR, duration);

            dataItem.setEndTime(curruent.getTime());
            data.add(dataItem);
        }

        return data;
    }

    public static List<StartToEndTimeBean> getAllDayStartToEndTime(Date startTime, Date endTime, Integer duration) {
        duration = Objects.requireNonNullElse(duration, 1);

        List<StartToEndTimeBean> data = new LinkedList<>();
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();

        startCalendar.setTime(startTime);
        endCalendar.setTime(endTime);

        startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        startCalendar.set(Calendar.MILLISECOND, 0);

        endCalendar.set(Calendar.HOUR_OF_DAY, 0);
        endCalendar.set(Calendar.MINUTE, 0);
        endCalendar.set(Calendar.SECOND, 0);
        endCalendar.set(Calendar.MILLISECOND, 0);

        endCalendar.add(Calendar.DAY_OF_YEAR, 1);

        Calendar curruent = startCalendar;
        while (curruent.before(endCalendar)) {
            StartToEndTimeBean dataItem = new StartToEndTimeBean();
            dataItem.setStartTime(curruent.getTime());

            curruent.add(Calendar.DAY_OF_YEAR, duration);

            dataItem.setEndTime(curruent.getTime());
            data.add(dataItem);
        }

        return data;
    }

    public static List<StartToEndTimeBean> getAllMonthStartToEndTime(Date startTime, Date endTime, Integer duration) {
        duration = Objects.requireNonNullElse(duration, 1);

        List<StartToEndTimeBean> data = new LinkedList<>();
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();

        startCalendar.setTime(startTime);
        endCalendar.setTime(endTime);

        startCalendar.set(Calendar.DAY_OF_MONTH, 1);
        startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        startCalendar.set(Calendar.MILLISECOND, 0);

        endCalendar.set(Calendar.DAY_OF_MONTH, 1);
        endCalendar.set(Calendar.HOUR_OF_DAY, 0);
        endCalendar.set(Calendar.MINUTE, 0);
        endCalendar.set(Calendar.SECOND, 0);
        endCalendar.set(Calendar.MILLISECOND, 0);

        endCalendar.add(Calendar.MONTH, 1);

        Calendar curruent = startCalendar;
        while (curruent.before(endCalendar)) {
            StartToEndTimeBean dataItem = new StartToEndTimeBean();
            dataItem.setStartTime(curruent.getTime());

            curruent.add(Calendar.MONTH, duration);

            dataItem.setEndTime(curruent.getTime());
            data.add(dataItem);
        }

        return data;
    }

    public static List<StartToEndTimeBean> getAllYearStartToEndTime(Date startTime, Date endTime, Integer duration) {
        duration = Objects.requireNonNullElse(duration, 1);

        List<StartToEndTimeBean> data = new LinkedList<>();
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();

        startCalendar.setTime(startTime);
        endCalendar.setTime(endTime);

        startCalendar.set(Calendar.DAY_OF_MONTH, 1);
        startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        startCalendar.set(Calendar.MILLISECOND, 0);

        endCalendar.set(Calendar.DAY_OF_MONTH, 1);
        endCalendar.set(Calendar.HOUR_OF_DAY, 0);
        endCalendar.set(Calendar.MINUTE, 0);
        endCalendar.set(Calendar.SECOND, 0);
        endCalendar.set(Calendar.MILLISECOND, 0);

        endCalendar.add(Calendar.MONTH, 1);

        Calendar curruent = startCalendar;
        while (curruent.before(endCalendar)) {
            StartToEndTimeBean dataItem = new StartToEndTimeBean();
            dataItem.setStartTime(curruent.getTime());

            curruent.add(Calendar.YEAR, duration);

            dataItem.setEndTime(curruent.getTime());
            data.add(dataItem);
        }

        return data;
    }

    @Data
    public static final class StartToEndTimeBean implements Serializable {
        private Date startTime;
        private Date endTime;
    }

    public static void main(String[] args) {

        System.out.println("hour: " + DateFormatUtils.format(getHour(), DateFormatUtils.ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT.getPattern()));
        System.out.println("nexthour: " + DateFormatUtils.format(getNextHour(), DateFormatUtils.ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT.getPattern()));

        System.out.println("\n\n");

        List<StartToEndTimeBean> data = getAllSecondStartToEndTime(getHour(), getNextHour());
//        List<StartToEndTimeBean> data = getAllDayStartToEndTime(substractOfDaysForTargetDate(getToday(), 60), getToday());

        data.forEach(dataItem -> {
            System.out.println("--------------------------------------------------------------------------------------------------");
            System.out.println("start: " + DateFormatUtils.format(dataItem.getStartTime(), DateFormatUtils.ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT.getPattern()));
            System.out.println("end: " + DateFormatUtils.format(dataItem.getEndTime(), DateFormatUtils.ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT.getPattern()));
        });
//
//        System.out.println("other: " + DateFormatUtils.format(getLastMonthOfThisYear(), DateFormatUtils.ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT.getPattern()));
//        System.out.println("other: " + DateFormatUtils.format(getLastDayOfThisYear(), DateFormatUtils.ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT.getPattern()));
//
//        List<StartToEndTimeBean> data = getAllYearStartToEndTime(new Date(1199980800000L), getStartMonthOfThisYear());
//
//        data.forEach(dataItem -> {
//            System.out.println("--------------------------------------------------------------------------------------------------");
//            System.out.println("start: " + DateFormatUtils.format(dataItem.getStartTime(), DateFormatUtils.ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT.getPattern()));
//            System.out.println("end: " + DateFormatUtils.format(dataItem.getEndTime(), DateFormatUtils.ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT.getPattern()));
//        });
    }
}
