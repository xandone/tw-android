package com.xandone.twandroid.ui.mine.error

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.xandone.twandroid.databinding.ActErrorWordBinding
import com.xandone.twandroid.db.AppDatabase
import com.xandone.twandroid.repository.ErrorRepository
import com.xandone.twandroid.ui.base.BaseActivity
import kotlinx.coroutines.launch

/**
 * @author: xiao
 * created on: 2025/10/31 11:22
 * description:
 */
class ErrorWordActivity : BaseActivity<ActErrorWordBinding>(ActErrorWordBinding::inflate) {

    private val viewModel by lazy {
        val factory =
            ErrorViewModelFactory(ErrorRepository(AppDatabase.getInstance().errorWordDao()))
        ViewModelProvider(this, factory)[ErrorViewModel::class.java]
    }

    override fun initView() {
        lifecycleScope.launch {
            val datas = viewModel.loadData()
            Log.d("ErrorWordActivity", "datas: ${datas.size}")
        }
    }
}