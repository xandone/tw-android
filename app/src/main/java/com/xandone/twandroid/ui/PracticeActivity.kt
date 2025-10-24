package com.xandone.twandroid.ui

import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.GsonUtils
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.google.gson.reflect.TypeToken
import com.gyf.immersionbar.ktx.immersionBar
import com.xandone.twandroid.ErrorRepository
import com.xandone.twandroid.R
import com.xandone.twandroid.WordRepository
import com.xandone.twandroid.bean.SentencesBean
import com.xandone.twandroid.bean.TransBean
import com.xandone.twandroid.databinding.ActPracticeLayoutBinding
import com.xandone.twandroid.db.AppDatabase
import com.xandone.twandroid.db.DBInfo
import com.xandone.twandroid.db.entity.ErrorWord
import com.xandone.twandroid.db.entity.WordCEt4
import com.xandone.twandroid.ui.base.BaseActivity
import com.xandone.twandroid.ui.practice.CEt4ViewModelFactory
import com.xandone.twandroid.ui.practice.PracticeFragment
import com.xandone.twandroid.utils.MyUtils
import kotlinx.coroutines.launch

/**
 * @author: xiao
 * created on: 2025/10/20 15:38
 * description:
 */
class PracticeActivity : BaseActivity<ActPracticeLayoutBinding>(ActPracticeLayoutBinding::inflate) {
    private var handwritingFragment: HandwritingFragment? = null
    private var isShow = false
    private lateinit var viewModel: CEt4ViewModel

    private val mFragmentList = mutableListOf<PracticeFragment>()

    override fun initView() {
        immersionBar {
            statusBarDarkFont(true)
            statusBarColor(R.color.white)
            navigationBarColor(R.color.white)
            fitsSystemWindows(true)
            titleBar(mBaseBinding.toolbar)
        }
        mBaseBinding.rightTv.setTextColor(ColorUtils.getColor(R.color.btn_color))
        showHandwriting()
        initWords()
        initFragment()

        mBaseBinding.rightTv.setOnClickListener {
            isShow = !isShow

            if (isShow) {
                if (!handwritingFragment!!.isAdded) {
                    supportFragmentManager.beginTransaction()
                        .add(R.id.frame_layout, handwritingFragment!!).commit()
                } else {
                    supportFragmentManager.beginTransaction().show(handwritingFragment!!).commit()
                }
            } else {
                supportFragmentManager.beginTransaction().hide(handwritingFragment!!).commit()
                resetCanvas()
            }
            showHandwriting()
        }
    }

    private fun initFragment() {
        handwritingFragment = HandwritingFragment()
        handwritingFragment!!.writeCallBack = object : WriteCallBack {
            override fun showWrite(keyword: String) {
                Log.d("sfsdfsdfsd", "showWrite: $keyword")

                mFragmentList[viewModel.mCurrentWordIndex.value!!].changeWord(keyword)
                resetCanvas()
            }
        }
    }

    private fun initWords() {
        val factory =
            CEt4ViewModelFactory(WordRepository(AppDatabase.getInstance(this).wordCEt4Dao()))
        viewModel = ViewModelProvider(this, factory)[CEt4ViewModel::class.java]

        lifecycleScope.launch {
            viewModel.loadData0(1, 10)

            for (i in 0 until viewModel.pagedWordCEt4.size) {
                val fragment = PracticeFragment(viewModel.pagedWordCEt4[i])
                mFragmentList.add(fragment)
            }

            mBinding.viewPage2.apply {
                adapter = object : FragmentStateAdapter(this@PracticeActivity) {
                    override fun getItemCount(): Int {
                        return mFragmentList.size
                    }

                    override fun createFragment(position: Int): Fragment {
                        return mFragmentList[position]
                    }
                }

                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        viewModel.mCurrentWordIndex.value = position
                        viewModel.mCurrentWord.value = viewModel.pagedWordCEt4[position]
                    }
                })

            }

        }

    }

    private fun showHandwriting() {
        if (isShow) {
            mBaseBinding.rightTv.text = "键盘"
        } else {
            mBaseBinding.rightTv.text = "手写"
        }

    }

    private fun resetCanvas() {
        handwritingFragment?.reset()
    }


}