package com.views.tools.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: 中文相关的操作方法
 */
@SuppressWarnings("unused")
public class ChineseUtil {
    /**
     * 将字符串中的中文转化为拼音,其他字符不变
     */
    public static String getPingYin(String inputString) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

        char[] input  = inputString.trim().toCharArray();
        String output = "";

        try {
            for (char c : input) {
                if (Character.toString(c).matches("[\\u4E00-\\u9FA5]+")) {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(c, format);
                    output += temp[0];
                } else {
                    output += Character.toString(c);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return output;
    }

    /**
     * 获取汉字串拼音首字母，英文字符不变
     *
     * @param chinese
     *         汉字串
     */
    public static String getFirstSpell(String chinese) {
        StringBuilder           builder       = new StringBuilder();
        char[]                  arr           = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char c : arr) {
            if (c > 128) {
                try {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat);
                    if (temp != null) {
                        builder.append(temp[0].charAt(0));
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                builder.append(c);
            }
        }
        return builder.toString().replaceAll("\\W", "").trim();
    }

    /**
     * 获取汉字串拼音，英文字符不变
     *
     * @param chinese
     *         汉字串
     *
     * @return 汉语拼音
     */
    public static String getFullSpell(String chinese) {
        StringBuilder           builder       = new StringBuilder();
        char[]                  arr           = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char c : arr) {
            if (c > 128) {
                try {
                    builder.append(PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat)[0]);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    /**
     * 检查name是否是中文名
     *
     * @param name
     *         待检测姓名
     */
    public static boolean isChineseName(String name) {
        if (TextUtil.isEmpty(name)) {
            return false;
        } else if (isChinese(name) && name.length() >= 2 && name.length() <= 15) {
            return true;
        }
        return false;
    }

    /**
     * 完整的判断中文汉字和符号
     */
    public static boolean isChinese(String strName) {
        for (char c : strName.toCharArray()) {
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判定输入字符是否是汉字
     *
     * @param c
     *         待检测字符
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }

    /**
     * 获取一个字符串中中文字符的个数
     */
    public static int ChineseLength(String str) {
        Pattern p = Pattern.compile("[\u4E00-\u9FA5]+");
        Matcher m = p.matcher(str);
        int     i = 0;
        while (m.find()) {
            String temp = m.group(0);
            i += temp.length();
        }
        return i;
    }

    /**
     * 判断是否是乱码
     */
    public static boolean isMessyCode(String strName) {
        Pattern p        = Pattern.compile("\\s*|\t*|\r*|\n*");
        Matcher m        = p.matcher(strName);
        String  after    = m.replaceAll("");
        String  temp     = after.replaceAll("\\p{P}", "");
        char[]  ch       = temp.trim().toCharArray();
        float   chLength = 0;
        float   count    = 0;

        for (char c : ch) {
            if (!Character.isLetterOrDigit(c)) {
                if (!ChineseUtil.isChinese(c)) {
                    count = count + 1;
                }
                chLength++;
            }
        }

        float result = count / chLength;
        return result > 0.4;
    }
}