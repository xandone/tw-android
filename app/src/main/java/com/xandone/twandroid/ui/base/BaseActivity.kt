package com.xandone.twandroid.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.xandone.twandroid.databinding.ActBaseBinding

/**
 * @author: xiao
 * created on: 2025/10/21 9:10
 * description:
 */
abstract class BaseActivity<VB : ViewBinding>(private val initVb: (LayoutInflater) -> VB) :
    AppCompatActivity() {

    protected var mBaseBinding: ActBaseBinding? = null

    private var _binding: VB? = null
    protected val mBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBaseBinding = ActBaseBinding.inflate(layoutInflater)
        _binding = initVb(layoutInflater)
        mBaseBinding!!.frameLayout.addView(mBinding.root)
        setContentView(mBaseBinding!!.root)
        initView()

        mBaseBinding!!.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    abstract fun initView()

    override fun onDestroy() {
        super.onDestroy()
        if (_binding != null) {
            _binding = null
        }
        if (mBaseBinding != null) {
            mBaseBinding = null
        }
    }

}