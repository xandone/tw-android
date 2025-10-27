package com.xandone.twandroid.ui.home

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
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
    private val mFragments = mutableListOf<HomeListFragment>()
    override fun initView(view: View?) {
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        lifecycleScope.launch {
            homeViewModel.loadData0()

            for (i in 0 until homeViewModel.list.size) {
                mFragments.add(HomeListFragment.getInstance(i))
            }

            mBinding.viewPage2.adapter = object :
                FragmentStateAdapter(this@HomeFragment) {
                override fun getItemCount(): Int {
                    return mFragments.size
                }

                override fun createFragment(position: Int): Fragment {
                    return mFragments[position]
                }
            }

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