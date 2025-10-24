package com.xandone.twandroid.ui

import android.view.View
import com.xandone.twandroid.mltik.StrokeManager
import com.xandone.twandroid.mltik.StrokeManager.DownloadedModelsChangedListener
import com.xandone.twandroid.databinding.FragHandwringLayoutBinding
import com.xandone.twandroid.ui.base.BaseVBFragment

/**
 * @author: xiao
 * created on: 2025/10/20 16:04
 * description:
 */
class HandwritingFragment :
    BaseVBFragment<FragHandwringLayoutBinding>(FragHandwringLayoutBinding::inflate),
    DownloadedModelsChangedListener,
    StrokeManager.ContentChangedListener {
    val strokeManager = StrokeManager()

    override fun initView(view: View?) {
        mBinding.handwritingView.setStrokeManager(strokeManager)
        initMl()
    }

    private fun initMl() {
        strokeManager.apply {
            setContentChangedListener(this@HandwritingFragment)
            setActiveModel("zh-Hani-CN")
            setDownloadedModelsChangedListener(this@HandwritingFragment)
            setClearCurrentInkAfterRecognition(true)
            setTriggerRecognitionAfterInput(false)

            download()

            recognize()

            refreshDownloadedModelsStatus()

            reset()
        }


    }

    var writeCallBack: WriteCallBack? = null
        set(value) {
            field = value
        }

    override fun onContentChanged() {
        val content = strokeManager.content[0].text
        writeCallBack?.showWrite(content)

        strokeManager.reset()
    }

    fun reset() {
        mBinding.handwritingView.clearCanvas()
        strokeManager.reset()
    }

    override fun onDownloadedModelsChanged(downloadedLanguageTags: MutableSet<String>?) {
    }

}