package com.xandone.twandroid.ui.mine

import android.text.InputType
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.blankj.utilcode.util.SPUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.xandone.twandroid.R
import com.xandone.twandroid.config.Constants
import com.xandone.twandroid.databinding.FragMineBinding
import com.xandone.twandroid.ui.base.BaseVBFragment

/**
 * @author: xiao
 * created on: 2025/10/23 17:29
 * description:
 */
class MineFragment : BaseVBFragment<FragMineBinding>(FragMineBinding::inflate) {
    override fun initView(view: View?) {

        mBinding.dailyTv.text = SPUtils.getInstance().getInt(Constants.SP_DAILY, 20).toString()

        mBinding.handSb.isChecked = SPUtils.getInstance().getBoolean(Constants.SP_HANDMODE)
        mBinding.handSb.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                SPUtils.getInstance().put(Constants.SP_HANDMODE, true)
            } else {
                SPUtils.getInstance().put(Constants.SP_HANDMODE, false)
            }
        }

        mBinding.dailyMcv.setOnClickListener {
            MaterialDialog(requireActivity()).show {
                title(text = "提示")
                input(
                    hint = "请输入每日单词数",
                    inputType = InputType.TYPE_CLASS_NUMBER,
                    maxLength = 4,
                    waitForPositiveButton = true
                ) { _, input ->
                    val inputStr = input.toString()
                    if (inputStr.isNotEmpty()) {
                        SPUtils.getInstance().put(Constants.SP_DAILY, inputStr.toInt())
                        mBinding.dailyTv.text = inputStr
                    }
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
//        immersionBar {
//            statusBarDarkFont(true)
//            statusBarColor(R.color.purple_700)
//            navigationBarColor(R.color.white)
//        }
    }
}