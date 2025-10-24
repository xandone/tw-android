package com.xandone.twandroid.ui.home

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.xandone.twandroid.R
import com.xandone.twandroid.bean.WordHomeBean
import com.xandone.twandroid.databinding.FragHomeBinding
import com.xandone.twandroid.ui.PracticeActivity
import com.xandone.twandroid.ui.base.BaseVBFragment
import com.xandone.twandroid.views.GridSpacingItemDecoration
import com.gyf.immersionbar.ktx.immersionBar
import kotlinx.coroutines.launch

/**
 * @author: xiao
 * created on: 2025/10/23 17:23
 * description:
 */
class HomeFragment : BaseVBFragment<FragHomeBinding>(FragHomeBinding::inflate) {
    private lateinit var homeViewModel: HomeViewModel
    override fun initView(view: View?) {

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

        mBinding.recycler.apply {
            adapter = rvAdapter
            layoutManager = GridLayoutManager(context, 2)
            addItemDecoration(GridSpacingItemDecoration(2, SizeUtils.dp2px(6f), true))
        }
        rvAdapter.setOnItemClickListener { adapter, view, position ->
            run {
                val intent = Intent(context, PracticeActivity::class.java).putExtra(
                    "key_tableName",
                    homeViewModel.firstList[position].tablename
                )
                startActivity(intent)
            }
        }

        homeViewModel = HomeViewModel()
        lifecycleScope.launch {
            homeViewModel.loadData0()
            rvAdapter.submitList(homeViewModel.firstList)
        }
    }


    override fun onResume() {
        super.onResume()
        immersionBar {
            statusBarDarkFont(true)
            statusBarColor(R.color.app_bg_color)
            navigationBarColor(R.color.white)
        }
    }
}