package com.xandone.twandroid.ui.home

import android.content.Context
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.blankj.utilcode.util.ColorUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.xandone.twandroid.R
import com.xandone.twandroid.databinding.FragHomeBinding
import com.xandone.twandroid.event.RefreshDbEvent
import com.xandone.twandroid.ui.base.BaseVBFragment
import com.xandone.twandroid.views.ViewPager2Helper
import kotlinx.coroutines.launch
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * @author: xiao
 * created on: 2025/10/23 17:23
 * description:
 */
class HomeFragment : BaseVBFragment<FragHomeBinding>(FragHomeBinding::inflate) {
    private lateinit var homeViewModel: HomeViewModel
    private val mFragments = mutableListOf<HomeListFragment>()
    private val mTitleDataList = mutableListOf<String>()
    override fun initView(view: View?) {
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        lifecycleScope.launch {
            homeViewModel.loadData0()

            homeViewModel.oneDArray.distinctBy { it.category }.forEach {
                mFragments.add(HomeListFragment.getInstance(it.category))
                mTitleDataList.add(it.category)
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

            initMagicIndicator()
        }
    }

    private fun initMagicIndicator() {
        val commonNavigator = CommonNavigator(requireActivity())
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return mTitleDataList.size
            }

            override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
                val colorTransitionPagerTitleView = ColorTransitionPagerTitleView(context)
                colorTransitionPagerTitleView.apply {
                    normalColor = ColorUtils.getColor(R.color.text_secend_color)
                    selectedColor = ColorUtils.getColor(R.color.btn_color)
                    text = mTitleDataList[index]
                    textSize = 16f
                    setOnClickListener {
                        mBinding.viewPage2.currentItem = index
                    }
                }

                return colorTransitionPagerTitleView
            }

            override fun getIndicator(context: Context?): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_WRAP_CONTENT
                indicator.setColors(ColorUtils.getColor(R.color.btn_color))
                indicator.startInterpolator = AccelerateInterpolator()
                indicator.endInterpolator = DecelerateInterpolator(2f)
                return indicator
            }
        }

        mBinding.typeMi.navigator = commonNavigator
        ViewPager2Helper.bind(mBinding.typeMi, mBinding.viewPage2);
    }


    override fun onResume() {
        super.onResume()
        immersionBar {
            statusBarDarkFont(true)
            statusBarColor(R.color.app_bg_color)
            navigationBarColor(R.color.white)
        }
    }

    override fun isEventBusRegistered(): Boolean {
        return true
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onRefreshDb(event: RefreshDbEvent?) {
        lifecycleScope.launch {
            homeViewModel.oneDArray.first { it.tablename == event?.tablename }.let {
                it.learnlen++
            }
            homeViewModel.refreshDB.value = homeViewModel.refreshDB.value!! + 1
        }
    }
}