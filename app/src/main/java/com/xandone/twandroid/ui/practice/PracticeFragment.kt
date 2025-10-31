package com.xandone.twandroid.ui.practice

import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.ObjectUtils
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.google.gson.reflect.TypeToken
import com.xandone.twandroid.R
import com.xandone.twandroid.bean.SentencesBean
import com.xandone.twandroid.bean.TransBean
import com.xandone.twandroid.bean.WordBean
import com.xandone.twandroid.databinding.FragPracticeBinding
import com.xandone.twandroid.db.AppDatabase
import com.xandone.twandroid.db.DBInfo
import com.xandone.twandroid.db.entity.BaseWordEntity
import com.xandone.twandroid.db.entity.ErrorWord
import com.xandone.twandroid.db.entity.PracticeWord
import com.xandone.twandroid.event.RefreshDbEvent
import com.xandone.twandroid.repository.ErrorRepository
import com.xandone.twandroid.repository.HomeRespository
import com.xandone.twandroid.ui.CEt4ViewModel
import com.xandone.twandroid.ui.base.BaseVBFragment
import com.xandone.twandroid.utils.MyUtils
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

/**
 * @author: xiao
 * created on: 2025/10/24 13:52
 * description:
 * @deprecated 使用view代替fragment
 */
class PracticeFragment(var wordEntity: WordBean, private val tablename: String) :
    BaseVBFragment<FragPracticeBinding>(FragPracticeBinding::inflate) {

//    private lateinit var viewModel: CEt4ViewModel

    override fun initView(view: View?) {
//        viewModel = ViewModelProvider(requireActivity())[CEt4ViewModel::class.java]

        initWords()
    }

    private fun initWords() {
        Log.d("initWords", "initWords")
        mBinding.errorTv.paintFlags = mBinding.errorTv.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        if (ObjectUtils.isNotEmpty(wordEntity.errorWord)) {
            mBinding.errorTv.text = wordEntity.errorWord
            mBinding.errorTv.visibility = View.VISIBLE
        } else {
            mBinding.errorTv.text = ""
            mBinding.errorTv.visibility = View.GONE
        }

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
                holder.setText(
                    R.id.pos_tv,
                    MyUtils.addHighLight(item?.pos, wordEntity.word)
                )
                holder.setText(R.id.cn_tv, item?.cn)
            }

        }

        val rvAdapter2 = object : BaseQuickAdapter<SentencesBean, QuickViewHolder>() {

            override fun onCreateViewHolder(
                context: Context,
                parent: ViewGroup,
                viewType: Int
            ): QuickViewHolder {
                return QuickViewHolder(R.layout.item_sentences, parent)
            }

            override fun onBindViewHolder(
                holder: QuickViewHolder,
                position: Int,
                item: SentencesBean?
            ) {
                holder.setText(
                    R.id.pos_tv,
                    MyUtils.addHighLight(item?.c, wordEntity.word)
                )
                holder.setText(R.id.cn_tv, item?.cn)
            }

        }


        mBinding.transRv.apply {
            adapter = ConcatAdapter(rvAdapter, rvAdapter2)
            layoutManager = LinearLayoutManager(requireActivity())
        }


        mBinding.phonetic0Tv.text =
            String.format("[%s]", wordEntity.phonetic0)
        if (wordEntity.keyword.isNullOrEmpty()) {
            mBinding.wordTv.text = wordEntity.word
        } else {
            mBinding.wordTv.text =
                MyUtils.addHighLight2(wordEntity.word, wordEntity.keyword)
        }

        val trans: List<TransBean> = GsonUtils.fromJson(
            wordEntity.trans,
            object : TypeToken<List<TransBean>>() {}.type
        )

        val sentences: List<SentencesBean> = GsonUtils.fromJson(
            wordEntity.sentences,
            object : TypeToken<List<SentencesBean>>() {}.type
        )

        rvAdapter.submitList(trans)
        rvAdapter2.submitList(sentences)
    }

}