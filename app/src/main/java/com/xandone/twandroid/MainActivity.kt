package com.xandone.twandroid

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.xandone.twandroid.databinding.ActivityMainBinding
import com.xandone.twandroid.ui.base.BaseActivity
import com.xandone.twandroid.ui.home.HomeFragment
import com.xandone.twandroid.ui.mine.MineFragment

/**
 * @author: xiao
 * created on: 2025/10/20 14:46
 * description:
 */
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val mFragments = mutableListOf<Fragment>()

    override fun initView() {
        mBaseBinding.appBar.visibility = View.GONE
        mFragments.add(HomeFragment())
        mFragments.add(MineFragment())

        mBinding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_home -> {
                    mBinding.viewPage2.currentItem = 0
                    true
                }

                R.id.item_mine -> {
                    mBinding.viewPage2.currentItem = 1
                    true
                }

                else -> false
            }
        }

        mBinding.viewPage2.apply {
            adapter = object : FragmentStateAdapter(this@MainActivity) {
                override fun getItemCount(): Int {
                    return mFragments.size
                }

                override fun createFragment(position: Int): Fragment {
                    return mFragments[position]
                }
            }
        }
    }
}