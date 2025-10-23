package com.xandone.twandroid.ui

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.JsonUtils
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.google.gson.reflect.TypeToken
import com.xandone.twandroid.R
import com.xandone.twandroid.WordRepository
import com.xandone.twandroid.bean.TransBean
import com.xandone.twandroid.databinding.ActPracticeLayoutBinding
import com.xandone.twandroid.db.AppDatabase
import com.xandone.twandroid.db.entity.WordCEt4
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
        val rvAdapter = object : BaseQuickAdapter<TransBean, QuickViewHolder>() {

            override fun onCreateViewHolder(
                context: Context,
                parent: ViewGroup,
                viewType: Int
            ): QuickViewHolder {
                return QuickViewHolder(R.layout.item_trans, parent)
            }

            override fun onBindViewHolder(
                holder: QuickViewHolder,
                position: Int,
                item: TransBean?
            ) {
                holder.setText(R.id.pos_tv, item?.pos)
                holder.setText(R.id.cn_tv, item?.cn)
            }

        }



        mBinding.transRv.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(this@PracticeActivity)
        }


        lifecycleScope.launch {
            viewModel.loadData0(1, 10)
            Log.d("sfsdfsdfsd", "initWords: ${GsonUtils.toJson(viewModel.pagedWordCEt4)}")

            mBinding.phonetic0Tv.text = viewModel.pagedWordCEt4[0].phonetic0
            mBinding.wordTv.text = viewModel.pagedWordCEt4[0].word

            val trans: List<TransBean> = GsonUtils.fromJson(
                viewModel.pagedWordCEt4[0].trans,
                object : TypeToken<List<TransBean>>() {}.type
            )
            rvAdapter.submitList(trans)
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