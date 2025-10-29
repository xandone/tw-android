package com.xandone.twandroid.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.JsonUtils
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.xandone.twandroid.R
import com.xandone.twandroid.databinding.FragHomeListBinding
import com.xandone.twandroid.db.entity.WordHomeEntity
import com.xandone.twandroid.ui.PracticeActivity
import com.xandone.twandroid.ui.base.BaseVBFragment
import com.xandone.twandroid.views.GridSpacingItemDecoration
import java.util.ArrayList
import java.util.Locale

/**
 * @author: xiao
 * created on: 2025/10/27 9:15
 * description:
 */
class HomeListFragment : BaseVBFragment<FragHomeListBinding>(FragHomeListBinding::inflate) {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var mCategory: String
    private var mDatas = MutableLiveData<ArrayList<WordHomeEntity>>().apply { value = ArrayList() }
    override fun initView(view: View?) {
        mCategory = arguments?.getString(CATEGORY) ?: ""
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        mDatas.value?.addAll(homeViewModel.oneDArray.filter { it.category == mCategory })
        val rvAdapter = object : BaseQuickAdapter<WordHomeEntity, QuickViewHolder>() {
            override fun onBindViewHolder(
                holder: QuickViewHolder,
                position: Int,
                item: WordHomeEntity?
            ) {
                holder.setText(R.id.item_name_tv, item?.name)
                holder.setText(R.id.item_description_tv, item?.description)
                holder.setText(
                    R.id.item_length_tv,
                    String.format(Locale.getDefault(), "%d/%s", item?.learnlen, item?.length)
                )
            }

            override fun onCreateViewHolder(
                context: Context,
                parent: ViewGroup,
                viewType: Int
            ): QuickViewHolder {
                return QuickViewHolder(R.layout.item_word_home, parent)
            }

        }


        rvAdapter.submitList(mDatas.value)

        mBinding.recycler.apply {
            adapter = rvAdapter
            layoutManager = GridLayoutManager(context, 2)
            addItemDecoration(GridSpacingItemDecoration(2, SizeUtils.dp2px(6f), true))
        }
        rvAdapter.setOnItemClickListener { _, _, position ->
            run {
                val intent = Intent(context, PracticeActivity::class.java).putExtra(
                    "key_tableName",
                    mDatas.value?.get(position)?.tablename
                )
                startActivity(intent)
            }
        }

        homeViewModel.refreshDB.observe(this) {
            rvAdapter.notifyDataSetChanged()
        }
    }

    companion object {
        const val CATEGORY = "intent_category"

        fun getInstance(category: String): HomeListFragment {
            val fragment = HomeListFragment()
            val bundle = Bundle()
            bundle.putString(CATEGORY, category)
            fragment.arguments = bundle
            return fragment
        }
    }
}