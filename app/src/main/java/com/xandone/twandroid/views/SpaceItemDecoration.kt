package com.xandone.twandroid.views

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.SizeUtils

/**
 * @author: xiao
 * created on: 2025/11/4 16:52
 * description:
 */
class SpaceItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) = with(outRect) {
        val temp = SizeUtils.dp2px(space.toFloat())
        if (parent.getChildAdapterPosition(view) == 0) {
            top = temp
        }
        left = temp
        right = temp
        bottom = temp
    }
}
