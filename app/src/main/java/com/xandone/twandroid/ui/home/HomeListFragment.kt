package com.xandone.twandroid.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.xandone.twandroid.R
import com.xandone.twandroid.bean.WordHomeBean
import com.xandone.twandroid.databinding.FragHomeListBinding
import com.xandone.twandroid.ui.PracticeActivity
import com.xandone.twandroid.ui.base.BaseVBFragment
import com.xandone.twandroid.views.GridSpacingItemDecoration

/**
 * @author: xiao
 * created on: 2025/10/27 9:15
 * description:
 */
class HomeListFragment : BaseVBFragment<FragHomeListBinding>(FragHomeListBinding::inflate) {
    private lateinit var homeViewModel: HomeViewModel
    private var mIndex = 0
    override fun initView(view: View?) {
        mIndex = arguments?.getInt(INDEX) ?: 0
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        val rvAdapter = object : BaseQuickAdapter<WordHomeBean, QuickViewHolder>() {
            override fun onBindViewHolder(
                holder: QuickViewHolder,
                position: Int,
                item: WordHomeBean?
            ) {
                holder.setText(R.id.item_name_tv, item?.name)
                holder.setText(R.id.item_description_tv, item?.description)
                holder.setText(R.id.item_length_tv, item?.length)
            }

            override fun onCreateViewHolder(
                context: Context,
                parent: ViewGroup,
                viewType: Int
            ): QuickViewHolder {
                return QuickViewHolder(R.layout.item_word_home, parent)
            }

        }

        rvAdapter.submitList(homeViewModel.list[mIndex])

        mBinding.recycler.apply {
            adapter = rvAdapter
            layoutManager = GridLayoutManager(context, 2)
            addItemDecoration(GridSpacingItemDecoration(2, SizeUtils.dp2px(6f), true))
        }
        rvAdapter.setOnItemClickListener { _, _, position ->
            run {
                val intent = Intent(context, PracticeActivity::class.java).putExtra(
                    "key_tableName",
                    homeViewModel.list[mIndex][position].tablename
                )
                startActivity(intent)
            }
        }
    }

    companion object {
        const val INDEX = "index"

        fun getInstance(type: Int): HomeListFragment {
            val fragment = HomeListFragment()
            val bundle = Bundle()
            bundle.putInt(INDEX, type)
            fragment.arguments = bundle
            return fragment
        }
    }
}