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

    private var _baseBinding: ActBaseBinding? = null
    protected val mBaseBinding
        get() = _baseBinding!!

    private var _binding: VB? = null
    protected val mBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _baseBinding = ActBaseBinding.inflate(layoutInflater)
        _binding = initVb(layoutInflater)
        mBaseBinding.frameLayout.addView(mBinding.root)
        setContentView(mBaseBinding.root)
        mBaseBinding.toolbar.setNavigationOnClickListener {
            finish()
        }
        mBaseBinding.titleTv.text = title

        initView()
    }

    abstract fun initView()

    override fun onDestroy() {
        super.onDestroy()
        if (_binding != null) {
            _binding = null
        }
        if (_baseBinding != null) {
            _baseBinding = null
        }
    }

}