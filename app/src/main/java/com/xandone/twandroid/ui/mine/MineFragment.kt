package com.xandone.twandroid.ui.mine

import android.view.View
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

        mBinding.statisticCv.setOnClickListener {

        }

        mBinding.handSb.isChecked = SPUtils.getInstance().getBoolean(Constants.SP_HANDMODE)
        mBinding.handSb.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                SPUtils.getInstance().put(Constants.SP_HANDMODE, true)
            } else {
                SPUtils.getInstance().put(Constants.SP_HANDMODE, false)
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