package com.views.tools.utils;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Description: 字符串格式化
 */
@SuppressWarnings("unused")
public class StringFormat {
    /**
     * 保留小数点后两位
     */
    public static String twoDecimalFormat(Object args) {
        if (null == args) {
            return "0.00";
        }
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(ConverterUtil.getDouble(args.toString()));
    }

    /**
     * 银行卡格式化(每四位一个空格)
     *
     * @param args
     *         银行卡号
     */
    public static String bankcardFormat(String args) {
        if (TextUtils.isEmpty(args)) {
            return args;
        }
        return args.replaceAll("([\\d]{4})(?=\\d)", "$1 ");
    }

    /**
     * 获得隐私保护银行卡号
     */
    public static String bankcardHideFormat(String args) {
        if (TextUtils.isEmpty(args)) {
            return args;
        }
        return args.replaceAll("^(.{4})(.*)(.{4})$", "$1 **** **** $3");
    }

    /**
     * 手机号格式化
     *
     * @param args
     *         手机号
     */
    public static String phoneFormat(String args) {
        if (TextUtils.isEmpty(args)) {
            return args;
        }
        return args.replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1 $2 $3");
    }

    /**
     * 获得隐私保护手机号
     */
    public static String phoneHideFormat(String args) {
        if (TextUtils.isEmpty(args)) {
            return args;
        }
        return args.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 获得隐私保护邮箱号
     */
    public static String emailHideFormat(String args) {
        if (TextUtils.isEmpty(args)) {
            return args;
        }
        String email = args.split("@")[0];
        return email.substring(0, 1) + "***" + email.substring(email.length() - 1) + args.substring(args.indexOf("@"));
    }

    /**
     * 身份证号格式化
     *
     * @param IDCard
     *         身份证号,330424 900202 121
     */
    public static String IDCardFormat(String IDCard) {
        if (TextUtils.isEmpty(IDCard)) {
            return IDCard;
        }
        int length = IDCard.length();
        if (length == 15) {
            return IDCard.replaceAll("(\\d{6})(\\d{6})(\\d{3})", "$1 $2 $3");
        } else if (length == 18) {
            return IDCard.replaceAll("(\\d{6})(\\d{4})(\\d{4})(\\S{4})", "$1 $2 $3 $4");
        } else {
            return IDCard;
        }
    }

    /**
     * 获得隐私保护身份证号
     */
    public static String IDCardHideFormat(String IDCard) {
        if (TextUtils.isEmpty(IDCard)) {
            return IDCard;
        }
        int    length = IDCard.length();
        String result;
        if (length == 15) {
            result = IDCard.replaceAll("(\\d{4})\\d{7}(\\d{4})", "$1 *** **** $2");
        } else if (length == 18) {
            result = IDCard.replaceAll("(\\d{4})\\d{10}(\\S{4})", "$1 *** *** **** $2");
        } else {
            result = "";
        }
        return result;
    }

    /**
     * 去掉多余的.与0
     */
    public static String subZeroAndDot(Object args) {
        if (null == args) {
            return "";
        }
        String str = args.toString();
        if (str.indexOf(".") > 0) {
            // 去掉多余的0
            str = str.replaceAll("0+?$", "");
            // 如最后一位是.则去掉
            str = str.replaceAll("[.]+?$", "");
        }
        return str;
    }

    /**
     * 数值格式化 - 12,345.00
     */
    public static String doubleFormat(Object args) {
        if (args != null && !TextUtil.isEmpty(args.toString())) {
            String number = args.toString();
            try {
                DecimalFormat df = new DecimalFormat();
                if (ConverterUtil.getDouble(number) < 1) {
                    df.applyPattern("0.00");
                } else {
                    df.applyPattern("##,###,###,###.00");
                }
                return df.format(ConverterUtil.getDouble(number));
            } catch (Exception e) {
                e.printStackTrace();
                return number;
            }
        } else {
            return "0.00";
        }
    }

    /**
     * 金额两位小数格式化 - (type = 1)
     */
    public static String doubleMoney(Object formatArgs) {
        return doubleMoney(formatArgs, 1);
    }

    /**
     * 金额两位小数格式化
     *
     * @param formatArgs
     *         待格式化数据
     * @param type
     *         0 - 不足一万，则单位为“元”，否则单位为“万元”
     *         1 - 单位均为“元”
     */
    public static String doubleMoney(Object formatArgs, int type) {
        if (formatArgs != null && !TextUtils.isEmpty(formatArgs.toString())) {
            String arg = formatArgs.toString();
            switch (type) {
                case 0:
                    try {
                        double money = Double.valueOf(arg);
                        if (money > 10000) {
                            return doubleFormat(money / 10000) + "万元";
                        } else {
                            return doubleFormat(money) + "元";
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return arg;
                    }

                case 1:
                default:
                    return doubleFormat(formatArgs) + "元";
            }
        } else {
            return "0.00元";
        }
    }

    /**
     * 不足1万，则常规格式化，否则，以“万”为单位格式化
     *
     * @param args
     *         待格式化数据
     */
    public static String doubleFormatForW(Object args) {
        if (null != args && !TextUtil.isEmpty(args.toString())) {
            String number = args.toString();
            try {
                double digit = ConverterUtil.getDouble(number);
                if (digit >= 10000) {
                    return doubleFormat(digit / 10000) + "万";
                } else {
                    return doubleFormat(digit);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return number;
            }
        } else {
            return "0.00";
        }
    }

    /**
     * 数值格式化 - 12,345
     */
    public static String intFormat(Object args) {
        if (args != null && !TextUtil.isEmpty(args.toString())) {
            String number = args.toString();
            try {
                DecimalFormat df = new DecimalFormat();
                if (ConverterUtil.getDouble(number) < 1) {
                    df.applyPattern("0");
                } else {
                    df.applyPattern("##,###,###,###");
                }
                return df.format(ConverterUtil.getDouble(number));
            } catch (Exception e) {
                e.printStackTrace();
                return number;
            }
        } else {
            return "0";
        }
    }

    /**
     * 补足两位数 - 12
     */
    public static String int2Format(int args) {
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumIntegerDigits(2);
        formatter.setGroupingUsed(false);
        String s = formatter.format(args);
        return s;
    }

    /**
     * 不足1万，则常规格式化，否则，以“万”为单位格式化
     *
     * @param args
     *         待格式化数据
     */
    public static String intFormatForW(Object args) {
        if (null != args && !TextUtil.isEmpty(args.toString())) {
            String number = args.toString();
            try {
                double digit = ConverterUtil.getDouble(number);
                if (digit >= 10000) {
                    return intFormat(digit / 10000) + "万";
                } else {
                    return intFormat(digit);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return number;
            }
        } else {
            return "0";
        }
    }
}