package com.xandone.twandroid.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.xandone.twandroid.R;

/**
 * @author: xiao
 * created on: 2025/10/20 17:01
 * description:
 */
public class MyUtils {
    public static CharSequence addHighLight(String content, String keyword) {
        SpannableString spannable = new SpannableString(content);
        if (keyword != null && !keyword.isEmpty() && ObjectUtils.isNotEmpty(content)) {
            int index = content.indexOf(keyword);
            if (index == -1) {
                index = content.toLowerCase().indexOf(keyword.toLowerCase());
            }
            if (index > -1) {
                int end = index + keyword.length();
                spannable.setSpan(new ForegroundColorSpan(ColorUtils.getColor(R.color.teal_200)),
                        index, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return spannable;
    }
}
