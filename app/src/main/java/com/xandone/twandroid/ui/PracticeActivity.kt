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
    val transaction = supportFragmentManager.beginTransaction()
    var handwritingFragment: HandwritingFragment? = null


    override fun initView() {
        handwritingFragment = HandwritingFragment()
        handwritingFragment!!.writeCallBack = object : WriteCallBack {
            override fun showWrite(content: String) {
                Log.d("sfsdfsdfsd", "showWrite: $content")
                mBinding.wordTv.text =
                    MyUtils.addHighLight2(mBinding.wordTv.text.toString(), content)
            }
        }

        mBinding.btn.setOnClickListener {
            transaction.add(R.id.frame_layout, handwritingFragment!!).commit()
        }
    }


}