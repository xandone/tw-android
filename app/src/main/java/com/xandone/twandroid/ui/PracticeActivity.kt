package com.xandone.twandroid.ui

import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.JsonUtils
import com.xandone.twandroid.R
import com.xandone.twandroid.WordRepository
import com.xandone.twandroid.databinding.ActPracticeLayoutBinding
import com.xandone.twandroid.db.AppDatabase
import com.xandone.twandroid.ui.base.BaseActivity
import com.xandone.twandroid.utils.MyUtils
import kotlinx.coroutines.flow.collectLatest
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

    override fun initView() {
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
                handwritingFragment?.reset()
            }
            showHandwriting()
        }
    }

    private fun initFragment() {
        handwritingFragment = HandwritingFragment()
        handwritingFragment!!.writeCallBack = object : WriteCallBack {
            override fun showWrite(content: String) {
                Log.d("sfsdfsdfsd", "showWrite: $content")
                mBinding.wordTv.text =
                    MyUtils.addHighLight2(mBinding.wordTv.text.toString(), content)
            }
        }
    }

    private fun initWords() {
        viewModel = CEt4ViewModel(WordRepository(AppDatabase.getInstance(this).wordCEt4Dao()))

//        lifecycleScope.launch {
//            Log.d("sfsdfsdfsd", "showHandwriting:开始")
//            viewModel.pagedWordCEt4.collectLatest {
//                Log.d("sfsdfsdfsd", "showHandwriting: ${GsonUtils.toJson(it)}")
//            }
//        }

        val respository = WordRepository(AppDatabase.getInstance(this).wordCEt4Dao())
        lifecycleScope.launch {
            val hasData = respository.checkDataExists()
            Log.d("sfsdfsdfsd", "数据库是否有数据：$hasData")
        }
    }

    private fun showHandwriting() {
        if (isShow) {
            mBaseBinding.rightTv.text = "键盘"
        } else {
            mBaseBinding.rightTv.text = "手写"
        }

    }


}