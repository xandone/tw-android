package com.xandone.twandroid.ui

import android.util.Log
import com.xandone.twandroid.R
import com.xandone.twandroid.databinding.ActPracticeLayoutBinding
import com.xandone.twandroid.ui.base.BaseActivity
import com.xandone.twandroid.utils.MyUtils

/**
 * @author: xiao
 * created on: 2025/10/20 15:38
 * description:
 */
class PracticeActivity : BaseActivity<ActPracticeLayoutBinding>(ActPracticeLayoutBinding::inflate) {
    var handwritingFragment: HandwritingFragment? = null
    var isShow = false

    override fun initView() {
        showHandwriting()
        handwritingFragment = HandwritingFragment()
        handwritingFragment!!.writeCallBack = object : WriteCallBack {
            override fun showWrite(content: String) {
                Log.d("sfsdfsdfsd", "showWrite: $content")
                mBinding.wordTv.text =
                    MyUtils.addHighLight2(mBinding.wordTv.text.toString(), content)
            }
        }

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

    private fun showHandwriting() {
        if (isShow) {
            mBaseBinding.rightTv.text = "键盘"
        } else {
            mBaseBinding.rightTv.text = "手写"
        }

    }


}