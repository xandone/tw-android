package com.xandone.twandroid.ui

import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.ObjectUtils
import com.blankj.utilcode.util.ResourceUtils
import com.blankj.utilcode.util.SPUtils
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.google.gson.reflect.TypeToken
import com.gyf.immersionbar.ktx.immersionBar
import com.xandone.twandroid.R
import com.xandone.twandroid.bean.SentencesBean
import com.xandone.twandroid.bean.TransBean
import com.xandone.twandroid.bean.WordBean
import com.xandone.twandroid.config.Constants
import com.xandone.twandroid.databinding.ActPracticeLayoutBinding
import com.xandone.twandroid.db.AppDatabase
import com.xandone.twandroid.db.DBInfo
import com.xandone.twandroid.db.entity.ErrorWord
import com.xandone.twandroid.db.entity.PracticeWord
import com.xandone.twandroid.event.RefreshDbEvent
import com.xandone.twandroid.repository.ErrorRepository
import com.xandone.twandroid.repository.HomeRespository
import com.xandone.twandroid.repository.WordRepository
import com.xandone.twandroid.ui.base.BaseActivity
import com.xandone.twandroid.ui.practice.CEt4ViewModelFactory
import com.xandone.twandroid.utils.MdDialogUtils
import com.xandone.twandroid.utils.MyUtils
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import java.util.Locale

/**
 * @author: xiao
 * created on: 2025/10/20 15:38
 * description:
 */
class PracticeActivity : BaseActivity<ActPracticeLayoutBinding>(ActPracticeLayoutBinding::inflate) {
    private var handwritingFragment: HandwritingFragment? = null
    private var isShowHand = false
    private lateinit var viewModel: CEt4ViewModel

    private lateinit var tablename: String

    private var isRepeatMode = false

    override fun initView() {
        tablename = intent.getStringExtra("key_tableName") ?: DBInfo.TABLE_CET4
        isShowHand = SPUtils.getInstance().getBoolean(Constants.SP_HANDMODE)
        if (ObjectUtils.isEmpty(tablename)) {
            tablename = DBInfo.TABLE_CET4
        }

        mBaseBinding.rightTv.setTextColor(ColorUtils.getColor(R.color.btn_color))

        initWords()
        initFragment()

        mBinding.btnPre.setOnClickListener {
            if (viewModel.mCurrentWordIndex.value!! > 0) {
                mBinding.viewPage2.currentItem = viewModel.mCurrentWordIndex.value!! - 1
            }
        }
        mBinding.btnNext.setOnClickListener {
            if (viewModel.mCurrentWordIndex.value!! < viewModel.pagedWordCEt4.size - 1) {
                mBinding.viewPage2.currentItem = viewModel.mCurrentWordIndex.value!! + 1
            }
        }

        viewModel.mCurrentWordIndex.observe(this) {
            mBinding.countTv.text = String.format(
                Locale.getDefault(),
                "%d/%d",
                viewModel.mCurrentWordIndex.value!! + 1,
                viewModel.pagedWordCEt4.size
            )
            if (viewModel.mCurrentWordIndex.value == 0) {
                mBinding.btnPre.visibility = View.GONE
            } else {
                mBinding.btnPre.visibility = View.VISIBLE
                mBinding.btnPre.text =
                    String.format(
                        Locale.getDefault(),
                        "<- %s",
                        viewModel.pagedWordCEt4[viewModel.mCurrentWordIndex.value!! - 1].word
                    )
            }

            if (viewModel.mCurrentWordIndex.value == viewModel.pagedWordCEt4.size - 1 || viewModel.pagedWordCEt4.size <= 1) {
                mBinding.btnNext.visibility = View.GONE
            } else {
                mBinding.btnNext.visibility = View.VISIBLE
                mBinding.btnNext.text =
                    String.format(
                        Locale.getDefault(),
                        "%s ->",
                        viewModel.pagedWordCEt4[viewModel.mCurrentWordIndex.value!! + 1].word
                    )
            }
        }

    }

    private fun initFragment() {
        handwritingFragment = HandwritingFragment()
        if (isShowHand) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.content_layout, handwritingFragment!!).commit()
        }
        handwritingFragment!!.writeCallBack = object : WriteCallBack {
            override fun showWrite(keyword: String) {
                Log.d("sfsdfsdfsd", "showWrite: $keyword")

                val lowerKeyword = keyword.lowercase(Locale.getDefault())
                val wordBean = viewModel.pagedWordCEt4[viewModel.mCurrentWordIndex.value!!]
                wordBean.keyword = lowerKeyword
                changeWord(wordBean)
                resetCanvas()
                if (lowerKeyword == viewModel.mCurrentWord.value!!.word && viewModel.mCurrentWordIndex.value!! < viewModel.pagedWordCEt4.size) {
                    mBinding.viewPage2.currentItem = viewModel.mCurrentWordIndex.value!! + 1
                }
            }
        }

        mBaseBinding.rightTv.setOnClickListener {
            isShowHand = !isShowHand
            switchHand()
        }

        showHandwriting()
    }

    lateinit var vpAdapter: BaseQuickAdapter<WordBean, QuickViewHolder>
    private fun initWords() {
        val factory =
            CEt4ViewModelFactory(WordRepository())
        viewModel = ViewModelProvider(this, factory)[CEt4ViewModel::class.java]
        viewModel.tablename = tablename

        lifecycleScope.launch {
            viewModel.loadData0(tablename, 1, viewModel.dailyCount)

            vpAdapter = object : BaseQuickAdapter<WordBean, QuickViewHolder>() {
                override fun onBindViewHolder(
                    holder: QuickViewHolder,
                    position: Int,
                    itemWord: WordBean?
                ) {
                    val errorTv = holder.getView<TextView>(R.id.error_tv)
                    errorTv.paintFlags = errorTv.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    if (ObjectUtils.isNotEmpty(itemWord?.errorWord)) {
                        errorTv.text = itemWord?.errorWord
                        errorTv.visibility = View.VISIBLE
                    } else {
                        errorTv.text = ""
                        errorTv.visibility = View.GONE
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

                            if (isRepeatMode) {
                                posTv.text = MyUtils.addMask(item?.c, itemWord?.word)
                            } else {
                                posTv.text = MyUtils.addHighLight(item?.c, itemWord?.word)
                            }

                            holder.setText(R.id.cn_tv, item?.cn)
                        }

                    }


                    holder.getView<RecyclerView>(R.id.trans_rv).apply {
                        adapter = ConcatAdapter(rvAdapter, rvAdapter2)
                        layoutManager = LinearLayoutManager(this@PracticeActivity)
                    }


                    holder.getView<TextView>(R.id.phonetic0_tv).text =
                        String.format("[%s]", itemWord?.phonetic0)
                    val wordTv = holder.getView<TextView>(R.id.word_tv)
                    if (itemWord?.keyword.isNullOrEmpty()) {
                        wordTv.text = itemWord?.word
                    } else {
                        wordTv.text = MyUtils.addHighLight2(itemWord?.word, itemWord?.keyword)
                    }
                    if (isRepeatMode) {
                        wordTv.foreground = ResourceUtils.getDrawable(R.drawable.gradient_mask)
                    } else {
                        wordTv.foreground = null
                    }

                    val trans: List<TransBean> = GsonUtils.fromJson(
                        itemWord?.trans,
                        object : TypeToken<List<TransBean>>() {}.type
                    )

                    val sentences: List<SentencesBean> = GsonUtils.fromJson(
                        itemWord?.sentences,
                        object : TypeToken<List<SentencesBean>>() {}.type
                    )

                    rvAdapter.submitList(trans)
                    rvAdapter2.submitList(sentences)
                }

                override fun onCreateViewHolder(
                    context: Context,
                    parent: ViewGroup,
                    viewType: Int
                ): QuickViewHolder {
                    return QuickViewHolder(R.layout.item_practice_word, parent)
                }

            }

            vpAdapter.submitList(viewModel.pagedWordCEt4)

            mBinding.viewPage2.apply {
                adapter = vpAdapter

                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        viewModel.mCurrentWordIndex.value = position
                        viewModel.mCurrentWord.value = viewModel.pagedWordCEt4[position]
                    }
                })

            }

        }

    }

    private fun showHandwriting() {
        if (isShowHand) {
            mBaseBinding.rightTv.text = "键盘"
        } else {
            mBaseBinding.rightTv.text = "手写"
        }

    }

    private fun switchHand() {
        if (isShowHand) {
            if (!handwritingFragment!!.isAdded) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.content_layout, handwritingFragment!!).commit()
            } else {
                supportFragmentManager.beginTransaction().show(handwritingFragment!!).commit()
            }
        } else {
            supportFragmentManager.beginTransaction().hide(handwritingFragment!!).commit()
            resetCanvas()
        }

        showHandwriting()
    }

    private fun resetCanvas() {
        handwritingFragment?.reset()
    }

    fun changeWord(wordBean: WordBean) {
        if (wordBean.keyword != wordBean.word) {
            wordBean.errorWord = wordBean.keyword
            vpAdapter.notifyItemChanged(viewModel.mCurrentWordIndex.value!!)
            val b: Boolean = viewModel.pagedWordCEt4.groupingBy { it }
                .eachCount()
                .any { it.value < 2 }
            if (b) {
                viewModel.mCurrentWord.value?.let { viewModel.pagedWordCEt4.add(it) }
                vpAdapter.notifyItemInserted(viewModel.pagedWordCEt4.size)
                mBinding.countTv.text = String.format(
                    Locale.getDefault(),
                    "%d/%d",
                    viewModel.mCurrentWordIndex.value!! + 1,
                    viewModel.pagedWordCEt4.size
                )
            }

            saveError2db(wordBean)
        } else {
            wordBean.errorWord = ""
            vpAdapter.notifyItemChanged(viewModel.mCurrentWordIndex.value!!, Any())
            if (viewModel.mCurrentWordIndex.value!! == viewModel.pagedWordCEt4.size - 1) {
                showRepeatePracticeDaidlog()
            }
            savePractice2db(wordBean)
        }
    }

    /**
     * 错误记录
     */
    private fun saveError2db(wordBean: WordBean) {
        val repository = ErrorRepository(AppDatabase.getInstance().errorWordDao())

        lifecycleScope.launch {
            Log.d("saveError2db", GsonUtils.toJson(wordBean))
            val errorWord = repository.getErrorWordById(wordBean.wid!!, tablename)
            if (errorWord != null) {
                errorWord.errorcount++
                repository.updateErrorWord(errorWord)
            } else {
                val temp = ErrorWord(
                    errortable = tablename,
                    errorwid = wordBean.wid,
                    errorid = wordBean.id,
                    word = wordBean.word,
                    phonetic0 = wordBean.phonetic0,
                    trans = wordBean.trans,
                    sentences = wordBean.sentences,
                    errorcount = 1
                )
                repository.insertErrorWord(temp)
            }
        }
    }

    /**
     * 练习记录，不包括错误
     */
    private fun savePractice2db(wordBean: WordBean) {
        val repository = HomeRespository()

        lifecycleScope.launch {
            val practiceWord = repository.getPracticeWordById(wordBean.wid!!, tablename)
            if (practiceWord != null) {
                practiceWord.practicecount++
                repository.updatePracticeWord(practiceWord)
            } else {
                val temp = PracticeWord(
                    practicetable = tablename,
                    practicewid = wordBean.wid,
                    practiceid = wordBean.id,
                    word = wordBean.word,
                    practicecount = 1
                )
                repository.insertPracticeWordAndRefreshHomeData(temp)
                EventBus.getDefault().post(RefreshDbEvent(tablename))
            }
        }
    }

    private fun showRepeatePracticeDaidlog() {
        MdDialogUtils.showDialog(this, "准备开始默写模式？") {
            isRepeatMode = true
            lifecycleScope.launch {
                viewModel.loadData0(tablename, 1, viewModel.dailyCount)
                vpAdapter.submitList(viewModel.pagedWordCEt4)
            }
        }
    }


}