package com.xandone.twandroid.ui.home

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.xandone.twandroid.R
import com.xandone.twandroid.bean.WordHomeBean
import com.xandone.twandroid.databinding.FragHomeBinding
import com.xandone.twandroid.ui.base.BaseVBFragment

/**
 * @author: xiao
 * created on: 2025/10/23 17:23
 * description:
 */
class HomeFragment : BaseVBFragment<FragHomeBinding>(FragHomeBinding::inflate) {
    override fun initView(view: View?) {
        val rvAdapter = object : BaseQuickAdapter<WordHomeBean, QuickViewHolder>() {
            override fun onBindViewHolder(
                holder: QuickViewHolder,
                position: Int,
                item: WordHomeBean?
            ) {
                holder.setText(R.id.item_name_tv, item?.name)
            }

            override fun onCreateViewHolder(
                context: Context,
                parent: ViewGroup,
                viewType: Int
            ): QuickViewHolder {
                return QuickViewHolder(R.layout.item_word_home, parent)
            }

        }

        mBinding.recycler.apply {
            adapter = rvAdapter
            layoutManager = GridLayoutManager(context, 3)
        }

        HomeViewModel().loadData0()
    }
}