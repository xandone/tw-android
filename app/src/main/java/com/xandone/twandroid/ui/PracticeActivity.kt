package com.xandone.twandroid.ui

import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.GsonUtils
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.google.gson.reflect.TypeToken
import com.xandone.twandroid.ErrorRepository
import com.xandone.twandroid.R
import com.xandone.twandroid.WordRepository
import com.xandone.twandroid.bean.SentencesBean
import com.xandone.twandroid.bean.TransBean
import com.xandone.twandroid.databinding.ActPracticeLayoutBinding
import com.xandone.twandroid.db.AppDatabase
import com.xandone.twandroid.db.DBInfo
import com.xandone.twandroid.db.entity.ErrorWord
import com.xandone.twandroid.db.entity.WordCEt4
import com.xandone.twandroid.ui.base.BaseActivity
import com.xandone.twandroid.utils.MyUtils
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
                resetCanvas()
            }
            showHandwriting()
        }
    }

    private fun initFragment() {
        handwritingFragment = HandwritingFragment()
        handwritingFragment!!.writeCallBack = object : WriteCallBack {
            override fun showWrite(keyword: String) {
                Log.d("sfsdfsdfsd", "showWrite: $keyword")
                if (keyword != mBinding.wordTv.text.toString()) {
                    mBinding.errorTv.text = keyword
                    mBinding.errorTv.visibility = View.VISIBLE
                    saveError2db()
                } else {
                    mBinding.errorTv.text = ""
                    mBinding.errorTv.visibility = View.GONE
                    viewModel.changeWord()
                }
                mBinding.wordTv.text =
                    MyUtils.addHighLight2(mBinding.wordTv.text.toString(), keyword)
                resetCanvas()
            }
        }
    }

    private fun initWords() {
        mBinding.errorTv.paintFlags = mBinding.errorTv.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
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
                holder.setText(
                    R.id.pos_tv,
                    MyUtils.addHighLight(item?.pos, viewModel.mCurrentWord.value?.word)
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
                    MyUtils.addHighLight(item?.c, viewModel.mCurrentWord.value?.word)
                )
                holder.setText(R.id.cn_tv, item?.cn)
            }

        }


        mBinding.transRv.apply {
            adapter = ConcatAdapter(rvAdapter, rvAdapter2)
            layoutManager = LinearLayoutManager(this@PracticeActivity)
        }


        lifecycleScope.launch {
            viewModel.loadData0(1, 10)
        }

        viewModel.mCurrentWord.observe(this) {
            mBinding.phonetic0Tv.text =
                String.format("[%s]", it.phonetic0)
            mBinding.wordTv.text = it.word

            val trans: List<TransBean> = GsonUtils.fromJson(
                it.trans,
                object : TypeToken<List<TransBean>>() {}.type
            )

            val sentences: List<SentencesBean> = GsonUtils.fromJson(
                it.sentences,
                object : TypeToken<List<SentencesBean>>() {}.type
            )

            rvAdapter.submitList(trans)
            rvAdapter2.submitList(sentences)
        }
    }

    private fun showHandwriting() {
        if (isShow) {
            mBaseBinding.rightTv.text = "键盘"
        } else {
            mBaseBinding.rightTv.text = "手写"
        }

    }


    private fun resetCanvas() {
        handwritingFragment?.reset()
    }

    private fun saveError2db() {
        val word = viewModel.mCurrentWord.value
        val repository = ErrorRepository(AppDatabase.getInstance(this).errorWordDao())

        lifecycleScope.launch {
            val errorWord = repository.getErrorWordById(word?.wid!!)
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