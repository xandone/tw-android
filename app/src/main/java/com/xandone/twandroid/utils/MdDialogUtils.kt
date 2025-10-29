package com.xandone.twandroid.utils

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog

/**
 * @author: xiao
 * created on: 2025/10/29 9:19
 * description:
 */
object MdDialogUtils {
    fun showDialog(ctx: Context) {
        MaterialDialog(ctx).show {
            title(text = "提示")
            message(text = "错误练习")
            positiveButton(text = "确定") {

            }
            negativeButton(text = "取消")
        }
    }
}