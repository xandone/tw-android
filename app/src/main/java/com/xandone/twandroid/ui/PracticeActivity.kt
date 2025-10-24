package com.xandone.twandroid.ui

import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ObjectUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.xandone.twandroid.R
import com.xandone.twandroid.WordRepository
import com.xandone.twandroid.databinding.ActPracticeLayoutBinding
import com.xandone.twandroid.db.DBInfo
import com.xandone.twandroid.ui.base.BaseActivity
import com.xandone.twandroid.ui.practice.CEt4ViewModelFactory
import com.xandone.twandroid.ui.practice.PracticeFragment
import kotlinx.coroutines.launch
import java.util.Locale

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

    private var tablename: String? = null

    override fun initView() {
        immersionBar {
            statusBarDarkFont(true)
            statusBarColor(R.color.white)
            navigationBarColor(R.color.white)
            fitsSystemWindows(true)
            titleBar(mBaseBinding.toolbar)
        }
        tablename = intent.getStringExtra("key_tableName")
        if (ObjectUtils.isEmpty(tablename)) {
            tablename = DBInfo.TABLE_CET4
        }

        mBaseBinding.rightTv.setTextColor(ColorUtils.getColor(R.color.btn_color))
        showHandwriting()
        initWords()
        initFragment()

        mBinding.btnPre.setOnClickListener {
            if (viewModel.mCurrentWordIndex.value!! > 0) {
                mBinding.viewPage2.currentItem = viewModel.mCurrentWordIndex.value!! - 1
            }
        }
        mBinding.btnNext.setOnClickListener {
            if (viewModel.mCurrentWordIndex.value!! < viewModel.pagedWordCEt4.size - 1) {
                mBinding.viewPage2.currentItem = viewModel.mCurrentWordIndex.value!! + 1
            }
        }

        viewModel.mCurrentWordIndex.observe(this) {
            mBinding.countTv.text = String.format(
                Locale.getDefault(),
                "%d/%d",
                viewModel.mCurrentWordIndex.value!! + 1,
                viewModel.pagedWordCEt4.size
            )
            if (viewModel.mCurrentWordIndex.value == 0) {
                mBinding.btnPre.visibility = View.GONE
            } else {
                mBinding.btnPre.visibility = View.VISIBLE
                mBinding.btnPre.text =
                    String.format(
                        Locale.getDefault(),
                        "<- %s",
                        viewModel.pagedWordCEt4[viewModel.mCurrentWordIndex.value!! - 1].word
                    )
            }

            if (viewModel.mCurrentWordIndex.value == viewModel.pagedWordCEt4.size - 1 || viewModel.pagedWordCEt4.size <= 1) {
                mBinding.btnNext.visibility = View.GONE
            } else {
                mBinding.btnNext.visibility = View.VISIBLE
                mBinding.btnNext.text =
                    String.format(
                        Locale.getDefault(),
                        "%s ->",
                        viewModel.pagedWordCEt4[viewModel.mCurrentWordIndex.value!! + 1].word
                    )
            }
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

        mBaseBinding.rightTv.setOnClickListener {
            isShow = !isShow

            if (isShow) {
                if (!handwritingFragment!!.isAdded) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.content_layout, handwritingFragment!!).commit()
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

    private fun initWords() {
        val factory =
            CEt4ViewModelFactory(WordRepository())
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