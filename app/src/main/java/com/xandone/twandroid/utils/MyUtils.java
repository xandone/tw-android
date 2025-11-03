package com.xandone.twandroid.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

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
        if (ObjectUtils.isNotEmpty(keyword) && ObjectUtils.isNotEmpty(content)) {
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

    public static CharSequence addMask(String content, String keyword) {
        SpannableString spannable = new SpannableString(content);
        if (ObjectUtils.isNotEmpty(keyword) && ObjectUtils.isNotEmpty(content)) {
            int index = content.indexOf(keyword);
            if (index == -1) {
                index = content.toLowerCase().indexOf(keyword.toLowerCase());
            }
            if (index > -1) {
                int end = index + keyword.length();
                spannable.setSpan(new ForegroundColorSpan(ColorUtils.getColor(R.color.purple_500)),
                        index, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannable.setSpan(new BackgroundColorSpan(ColorUtils.getColor(R.color.purple_500)),
                        index, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return spannable;
    }

    public static String findKey(String content, String keyword) {
        if (ObjectUtils.isEmpty(keyword) || ObjectUtils.isEmpty(content)) {
            return null;
        }
        if (content.toLowerCase().startsWith(keyword.toLowerCase())) {
            return keyword;
        }
        if (keyword.length() <= 1) {
            return null;
        }
        return findKey(content, keyword.substring(0, keyword.length() - 1));
    }

    public static CharSequence addHighLight2(String content, String keyword) {
        keyword = findKey(content, keyword);
        if (ObjectUtils.isEmpty(keyword)) {
            return content;
        }
        SpannableString spannable = new SpannableString(content);
        int end = keyword.length();
        spannable.setSpan(new ForegroundColorSpan(ColorUtils.getColor(R.color.teal_200)),
                0, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannable;
    }
}
