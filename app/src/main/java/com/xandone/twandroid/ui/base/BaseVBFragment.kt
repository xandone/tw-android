package com.xandone.twandroid.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * @author: xiao
 * created on: 2025/10/21 9:18
 * description:
 */
abstract class BaseVBFragment<VB : ViewBinding>(private val initVb: (LayoutInflater) -> VB) :
    Fragment() {
    private var _binding: VB? = null
    protected val mBinding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = initVb(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    protected abstract fun initView(view: View?)

    override fun onDestroyView() {
        super.onDestroyView()
        if (_binding != null) {
            _binding = null
        }
    }
}