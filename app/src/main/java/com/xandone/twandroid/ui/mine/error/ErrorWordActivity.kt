package com.xandone.twandroid.ui.mine.error

import android.animation.ArgbEvaluator
import android.content.Context
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.GsonUtils
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.google.gson.reflect.TypeToken
import com.xandone.twandroid.R
import com.xandone.twandroid.bean.SentencesBean
import com.xandone.twandroid.bean.TransBean
import com.xandone.twandroid.databinding.ActErrorWordBinding
import com.xandone.twandroid.db.AppDatabase
import com.xandone.twandroid.db.entity.ErrorWord
import com.xandone.twandroid.repository.ErrorRepository
import com.xandone.twandroid.ui.base.BaseActivity
import com.xandone.twandroid.utils.MyUtils
import com.xandone.twandroid.views.SpaceItemDecoration
import kotlinx.coroutines.launch
import java.util.Locale

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

    private lateinit var mAdapter: BaseQuickAdapter<ErrorWord, QuickViewHolder>
    override fun initView() {
        mAdapter = object : BaseQuickAdapter<ErrorWord, QuickViewHolder>() {
            override fun onBindViewHolder(
                holder: QuickViewHolder,
                position: Int,
                itemWord: ErrorWord?
            ) {
                holder.setText(
                    R.id.serial_tv,
                    String.format(Locale.getDefault(), "%d.", position + 1)
                )
                holder.setText(R.id.word_tv, itemWord?.word)
                holder.setText(R.id.phonetic0_tv, String.format("[%s]", itemWord?.phonetic0))
                holder.setGone(R.id.trans_rv, !itemWord?.isSelect!!)
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

                        val posTv = holder.getView<TextView>(R.id.pos_tv)
                        posTv.text = MyUtils.addHighLight(item?.c, itemWord.word)
                        holder.setText(R.id.cn_tv, item?.cn)
                    }

                }


                holder.getView<RecyclerView>(R.id.trans_rv).apply {
                    adapter = ConcatAdapter(rvAdapter, rvAdapter2)
                    layoutManager = LinearLayoutManager(this@ErrorWordActivity)
                }


                val trans: List<TransBean> = GsonUtils.fromJson(
                    itemWord.trans,
                    object : TypeToken<List<TransBean>>() {}.type
                )

                val sentences: List<SentencesBean> = GsonUtils.fromJson(
                    itemWord.sentences,
                    object : TypeToken<List<SentencesBean>>() {}.type
                )

                rvAdapter.submitList(trans)
                rvAdapter2.submitList(sentences)

                val arrowIv = holder.getView<ImageView>(R.id.arrow_iv)
                arrowIv.rotation = if (itemWord.isSelect) -180f else 0f

            }

            override fun onCreateViewHolder(
                context: Context,
                parent: ViewGroup,
                viewType: Int
            ): QuickViewHolder {
                return QuickViewHolder(R.layout.item_error_word, parent)
            }
        }
        mBinding.wordRv.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@ErrorWordActivity)
            addItemDecoration(SpaceItemDecoration(6))
        }
        lifecycleScope.launch {
            mAdapter.submitList(viewModel.loadData())
        }

        mAdapter.setOnItemClickListener { _, _, position ->
            run {
                val errorWord = mAdapter.getItem(position)
                if (errorWord != null) {
                    errorWord.isSelect = !errorWord.isSelect
                    mAdapter.notifyItemChanged(position)
                }

            }
        }
    }
}