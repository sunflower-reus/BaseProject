package com.views.tools.utils;

import java.util.Random;

/**
 * Description: 随机数、字符串 工具类
 */
@SuppressWarnings("unused")
public class RandomUtil {
    public static final String CHAR_ALL    = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String CHAR_LETTER = "abcdefghijkllmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String CHAR_NUMBER = "0123456789";

    /**
     * 返回一个定长的随机数字
     *
     * @param length
     *         随机数字长度
     */
    public static String number(int length) {
        StringBuilder builder = new StringBuilder();
        Random        random  = new Random();
        for (int i = 0; i < length; i++) {
            builder.append(CHAR_NUMBER.charAt(random.nextInt(CHAR_NUMBER.length())));
        }
        return builder.toString();
    }

    /**
     * 返回一个定长的随机字符串(只包含大小写字母、数字)
     *
     * @param length
     *         随机字符串长度
     */
    public static String string(int length) {
        StringBuilder builder = new StringBuilder();
        Random        random  = new Random();
        for (int i = 0; i < length; i++) {
            builder.append(CHAR_ALL.charAt(random.nextInt(CHAR_ALL.length())));
        }
        return builder.toString();
    }

    /**
     * 返回一个定长的随机纯字母字符串(只包含大小写字母)
     *
     * @param length
     *         随机字符串长度
     */
    public static String mixString(int length) {
        StringBuilder builder = new StringBuilder();
        Random        random  = new Random();
        for (int i = 0; i < length; i++) {
            builder.append(CHAR_LETTER.charAt(random.nextInt(CHAR_LETTER.length())));
        }
        return builder.toString();
    }

    /**
     * 返回一个定长的随机纯大写字母字符串(只包含大写字母)
     *
     * @param length
     *         随机字符串长度
     */
    public static String lowerString(int length) {
        return mixString(length).toLowerCase();
    }

    /**
     * 返回一个定长的随机纯小写字母字符串(只包含小写字母)
     *
     * @param length
     *         随机字符串长度
     */
    public static String upperString(int length) {
        return mixString(length).toUpperCase();
    }

    /**
     * 生成一个定长的纯0字符串
     *
     * @param length
     *         字符串长度
     */
    public static String zeroString(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append('0');
        }
        return builder.toString();
    }

    /**
     * 根据数字生成一个定长的字符串，长度不够前面补0
     *
     * @param num
     *         数字
     * @param fixedLength
     *         字符串长度
     */
    public static String fixedLengthString(long num, int fixedLength) {
        StringBuilder builder = new StringBuilder();
        String        strNum  = String.valueOf(num);
        if (fixedLength - strNum.length() >= 0) {
            builder.append(zeroString(fixedLength - strNum.length()));
        } else {
            throw new RuntimeException("将数字" + num + "转化为长度为" + fixedLength + "的字符串发生异常！");
        }
        builder.append(strNum);
        return builder.toString();
    }

    /**
     * 根据数字生成一个定长的字符串，长度不够前面补0
     *
     * @param num
     *         数字
     * @param fixedLength
     *         字符串长度
     */
    public static String fixedLengthString(int num, int fixedLength) {
        StringBuilder builder = new StringBuilder();
        String        strNum  = String.valueOf(num);
        if (fixedLength - strNum.length() >= 0) {
            builder.append(zeroString(fixedLength - strNum.length()));
        } else {
            throw new RuntimeException("将数字" + num + "转化为长度为" + fixedLength + "的字符串发生异常！");
        }
        builder.append(strNum);
        return builder.toString();
    }
}