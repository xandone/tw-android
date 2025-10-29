package com.xandone.twandroid.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import org.greenrobot.eventbus.EventBus

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
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isEventBusRegistered()) {
            EventBus.getDefault().register(this)
        }
        initView(view)
    }

    protected abstract fun initView(view: View?)

    override fun onDestroyView() {
        super.onDestroyView()
        if (isEventBusRegistered()) {
            EventBus.getDefault().unregister(this)
        }
        if (_binding != null) {
            _binding = null
        }
    }

    open fun isEventBusRegistered(): Boolean {
        return false
    }
}