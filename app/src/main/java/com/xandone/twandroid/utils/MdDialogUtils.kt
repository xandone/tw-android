package com.xandone.twandroid.utils

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog

/**
 * @author: xiao
 * created on: 2025/10/29 9:19
 * description:
 */
object MdDialogUtils {

    fun showDialog(ctx: Context, message: String, onPositiveClick: () -> Unit) {
        MaterialDialog(ctx).show {
            title(text = "提示")
            message(text = message)
            positiveButton(text = "确定") {
                onPositiveClick()
            }
            negativeButton(text = "取消")
        }
    }
}