package com.xandone.twandroid.ui.practice

import android.content.Context
import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
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
import com.xandone.twandroid.databinding.FragPracticeBinding
import com.xandone.twandroid.db.AppDatabase
import com.xandone.twandroid.db.DBInfo
import com.xandone.twandroid.db.entity.BaseWordEntity
import com.xandone.twandroid.db.entity.ErrorWord
import com.xandone.twandroid.repository.ErrorRepository
import com.xandone.twandroid.ui.base.BaseVBFragment
import com.xandone.twandroid.utils.MyUtils
import kotlinx.coroutines.launch

/**
 * @author: xiao
 * created on: 2025/10/24 13:52
 * description:
 */
class PracticeFragment(private val wordEntity: BaseWordEntity) :
    BaseVBFragment<FragPracticeBinding>(FragPracticeBinding::inflate) {

//    private lateinit var viewModel: CEt4ViewModel

    private var errorWord: String? = null

    override fun initView(view: View?) {
//        viewModel = ViewModelProvider(requireActivity())[CEt4ViewModel::class.java]

        initWords()
    }

    private fun initWords() {
        mBinding.errorTv.paintFlags = mBinding.errorTv.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        if (ObjectUtils.isNotEmpty(errorWord)) {
            mBinding.errorTv.text = errorWord
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
        mBinding.wordTv.text = wordEntity.word

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

    fun changeWord(keyword: String) {
        if (keyword != wordEntity.word) {
            errorWord = keyword
            mBinding.errorTv.visibility = View.VISIBLE
            saveError2db()
        } else {
            errorWord = ""
            mBinding.errorTv.visibility = View.GONE
        }
        mBinding.errorTv.text = errorWord
        mBinding.wordTv.text =
            MyUtils.addHighLight2(mBinding.wordTv.text.toString(), keyword)
    }

    private fun saveError2db() {
        val word = wordEntity
        val repository = ErrorRepository(AppDatabase.getInstance().errorWordDao())

        lifecycleScope.launch {
            val errorWord = repository.getErrorWordById(word.wid!!)
            if (errorWord != null) {
                errorWord.errorcount++
                repository.updateErrorWord(errorWord)
            } else {
                val temp = ErrorWord(
                    errortable = DBInfo.TABLE_CET4,
                    errorwid = word.wid,
                    errorid = word.id,
                    word = word.word,
                    errorcount = 1
                )
                repository.insertErrorWord(temp)
            }
        }
    }
}