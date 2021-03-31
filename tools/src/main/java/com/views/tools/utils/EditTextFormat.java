package com.views.tools.utils;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.Spanned;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Description: 输入框格式化
 */
public class EditTextFormat {
    /** 输入框小数的位数 */
    private static final int DECIMAL_DIGITS = 2;

    public interface EditTextFormatWatcher {
        void OnTextWatcher(String str);
    }

    private static void addSpaceToString(final EditText mEditText, final EditTextFormatWatcher watcher, final int[] rules) {
        mEditText.addTextChangedListener(new TextWatcher() {
            int beforeTextLength = 0;
            int onTextLength = 0;
            boolean isChanged = false;
            // 记录光标的位置
            int location = 0;
            private char[] tempChar;
            private StringBuffer buffer = new StringBuffer();
            // 空格数量
            int blankNumber = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforeTextLength = s.length();
                if (buffer.length() > 0) {
                    buffer.delete(0, buffer.length());
                }
                blankNumber = 0;
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == ' ') {
                        blankNumber++;
                        break;
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onTextLength = s.length();
                buffer.append(s.toString());
                if (onTextLength == beforeTextLength || onTextLength <= 3 || isChanged) {
                    isChanged = false;
                    return;
                }
                isChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isChanged) {
                    location = mEditText.getSelectionEnd();
                    int index = 0;
                    while (index < buffer.length()) {
                        if (buffer.charAt(index) == ' ') {
                            buffer.deleteCharAt(index);
                        } else {
                            index++;
                        }
                    }

                    index = 0;
                    int blankNumber = 0;
                    while (index < buffer.length()) {
                        for (int rule : rules) {
                            if (index == rule) {
                                buffer.insert(index, ' ');
                                blankNumber++;
                            }
                        }
                        index++;
                    }

                    if (blankNumber > this.blankNumber) {
                        location += (blankNumber - this.blankNumber);
                    }

                    tempChar = new char[buffer.length()];
                    buffer.getChars(0, buffer.length(), tempChar, 0);
                    String str = buffer.toString();
                    if (location > str.length()) {
                        location = str.length();
                    } else if (location < 0) {
                        location = 0;
                    }

                    // s.replace(0, s.length(), str);
                    mEditText.setText(str);
                    Selection.setSelection(mEditText.getText(), location);
                    isChanged = false;
                    if (watcher != null) {
                        watcher.OnTextWatcher(s.toString());
                    }
                }
            }
        });
    }

    /** 银行卡号码的格式 */
    public static void bankCardNumAddSpace(final EditText mEditText, final EditTextFormatWatcher watcher) {
        addSpaceToString(mEditText, watcher, new int[]{4, 9, 14, 19});
    }

    /** 手机号码的格式 */
    public static void phoneNumAddSpace(final EditText mEditText, final EditTextFormatWatcher watcher) {
        addSpaceToString(mEditText, watcher, new int[]{3, 8});
    }

    /** 身份证的格式 */
    public static void IDCardNumAddSpace(final EditText mEditText, final EditTextFormatWatcher watcher) {
        addSpaceToString(mEditText, watcher, new int[]{6, 11, 16});
    }

    /**
     * 去除字符串中的空格
     */
    public static String getTrimStr(String str) {
        return str.replaceAll("\\s*", "");
    }

    /**
     * 限制EditText只能输入两位小数
     * <p/>
     * E.G.
     * view.setFilters(new InputFilter[]{EditTextFormat.getLengthFilter()});
     */
    public static InputFilter getLengthFilter() {
        return lengthFilter;
    }

    /**
     * 限制EditText不能输入空格
     */
    public static InputFilter getBlankFilter() {
        return blankFilter;
    }

    /**
     * 添加新的Filter
     */
    public static void addFilter(EditText view, InputFilter filter) {
        InputFilter[] old      = view.getFilters();
        InputFilter[] filters  = new InputFilter[old.length + 1];
        int           position = 0;
        for (; position < old.length; position++) {
            filters[position] = old[position];
        }
        filters[position] = filter;
        view.setFilters(filters);
    }

    /** 设置小数位数控制 */
    private static InputFilter lengthFilter = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dStart, int dEnd) {
            // ""等特殊字符，直接返回
            if ("".equals(source.toString())) {
                return null;
            }
            String   dValue     = dest.toString();
            String[] splitArray = dValue.split("\\.");
            if (splitArray.length > 1) {
                String dotValue = splitArray[1];
                int    diff     = dotValue.length() + 1 - DECIMAL_DIGITS;
                if (diff > 0) {
                    return source.subSequence(start, end - diff);
                }
            }
            return null;
        }
    };
    /** 不允许输入空格 */
    private static InputFilter blankFilter  = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dStart, int dEnd) {
            if (source.equals(" ")) {
                return "";
            } else {
                return null;
            }
        }
    };

    public static void addClearStartO(final EditText mEditText) {
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                int    len  = s.toString().length();
                if (len == 1 && text.equals("0")) {
                    s.clear();
                }
            }
        });
    }
}