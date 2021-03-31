package com.views.tools.utils;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: 常用的字符串相关的工具方法
 */
@SuppressWarnings("unused")
public class TextUtil {
    /**
     * 判断是否是空字符串 null 或者 长度为0
     */
    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    /**
     * 拼接字符串.
     */
    public static String join(Object[] tokens, CharSequence delimiter) {
        StringBuilder sb = new StringBuilder();
        boolean firstTime = true;
        for (Object token : tokens) {
            if (firstTime) {
                firstTime = false;
            } else {
                sb.append(delimiter);
            }
            sb.append(token);
        }
        return sb.toString();
    }

    /**
     * 去除字符串中的空格、回车、换行符、制表符
     */
    public static String replaceBlank(String str) {
        if (!isEmpty(str)) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            str = m.replaceAll("");
        }
        return str;
    }

    /**
     * 删除所有的标点符号
     */
    public static String trimPunctuation(String str) {
        return str.replaceAll("[\\pP\\p{Punct}]", "");
    }

    /**
     * 格式化一个float
     *
     * @param format 要格式化成的格式 such as #.00, #.#
     */
    public static String formatFloat(float f, String format) {
        DecimalFormat df = new DecimalFormat(format);
        return df.format(f);
    }

    /**
     * 将list 用传入的分隔符组装为String
     */
    public static String listToStringSlipStr(List list, String slipStr) {
        StringBuilder builder = new StringBuilder();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                builder.append(list.get(i)).append(slipStr);
            }
        }
        if (builder.toString().length() > 0) {
            return builder.toString().substring(0, builder.toString().lastIndexOf(slipStr));
        } else {
            return "";
        }
    }

    /**
     * 全角括号转为半角
     */
    public static String replaceBracketStr(String str) {
        if (!isEmpty(str)) {
            str = str.replaceAll("（", "(");
            str = str.replaceAll("）", ")");
        }
        return str;
    }

    /**
     * 全角字符变半角字符
     */
    public static String full2Half(String str) {
        if (isEmpty(str)) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c >= 65281 && c < 65373) {
                builder.append((char) (c - 65248));
            } else {
                builder.append(str.charAt(i));
            }
        }
        return builder.toString();
    }

    /**
     * 解析字符串返回map键值对(例：a=1&b=2 => a=1,b=2)
     *
     * @param query   源参数字符串
     * @param split1  键值对之间的分隔符（例：&）
     * @param split2  key与value之间的分隔符（例：=）
     * @param dupLink 重复参数名的参数值之间的连接符，连接后的字符串作为该参数的参数值，可为null
     *                null：不允许重复参数名出现，则靠后的参数值会覆盖掉靠前的参数值。
     * @return map
     */
    public static Map<String, String> parseQuery(String query, char split1, char split2, String dupLink) {
        if (!isEmpty(query) && query.indexOf(split2) > 0) {
            Map<String, String> result = new HashMap<>();

            String name = null;
            String value = null;
            String tempValue;
            for (int i = 0; i < query.length(); i++) {
                char c = query.charAt(i);
                if (c == split2) {
                    value = "";
                } else if (c == split1) {
                    if (!isEmpty(name) && value != null) {
                        if (dupLink != null) {
                            tempValue = result.get(name);
                            if (tempValue != null) {
                                value += dupLink + tempValue;
                            }
                        }
                        result.put(name, value);
                    }
                    name = null;
                    value = null;
                } else if (value != null) {
                    value += c;
                } else {
                    name = (name != null) ? (name + c) : "" + c;
                }
            }

            if (!isEmpty(name) && value != null) {
                if (dupLink != null) {
                    tempValue = result.get(name);
                    if (tempValue != null) {
                        value += dupLink + tempValue;
                    }
                }
                result.put(name, value);
            }
            return result;
        }
        return null;
    }

    public static String stripHtml(String content) {
        // <p>段落替换为换行
        content = content.replaceAll("<p .*?>", "\r\n");
        // <br><br/>替换为换行
        content = content.replaceAll("<br\\s*/?>", "\r\n");
        // 去掉其它的<>之间的东西
        content = content.replaceAll("\\<.*?>", "");
        // 还原HTML
        // content = HTMLDecoder.decode(content);
        return content;
    }

    /**
     * 截取字符串中的数值部分
     */
    public static String substringNumber(String params) {
        Matcher matcher = Pattern.compile("[0-9,.%]+").matcher(params);
        if (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            return params.substring(start, end);
        } else {
            return "0";
        }
    }


    /**
     * 转换为金额类型18888->18,888.00
     */
    public static String toMoneyType(String params) {

        double d = Double.valueOf(params);
        DecimalFormat myformat = new DecimalFormat();
        myformat.applyPattern("##,###.00");
        String s = myformat.format(d);

        if (d <= -1.00) {
            return s;
        } else if (d < 1.00) {
            return s.replace(".", "0.");
        } else {
            return s;
        }
    }

    /**
     * 转换为金额类型18888->18,888
     * 不带小数点及小数
     */
    public static String toMoneyTypeInt(String params) {

        double d = Double.valueOf(params);
        DecimalFormat myformat = new DecimalFormat();
        myformat.applyPattern("##,###.00");
        String s = myformat.format(d);
        if (params.equals("0")) {
            return "0";
        } else if (d <= -1.00) {
            return s;
        } else if (d < 1.00) {
            return s.substring(0, s.length() - 3);
        } else {
            return s.substring(0, s.length() - 3);
        }
    }


}