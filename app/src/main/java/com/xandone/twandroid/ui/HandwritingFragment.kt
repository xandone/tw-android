package com.xandone.twandroid.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xandone.twandroid.mltik.StrokeManager
import com.xandone.twandroid.mltik.StrokeManager.DownloadedModelsChangedListener
import com.xandone.twandroid.databinding.FragHandwringLayoutBinding

/**
 * @author: xiao
 * created on: 2025/10/20 16:04
 * description:
 */
class HandwritingFragment : Fragment(), DownloadedModelsChangedListener,
    StrokeManager.ContentChangedListener {
    private lateinit var _binding: FragHandwringLayoutBinding

    val strokeManager = StrokeManager()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragHandwringLayoutBinding.inflate(layoutInflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding.handwritingView.setStrokeManager(strokeManager)
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

    override fun onDownloadedModelsChanged(downloadedLanguageTags: MutableSet<String>?) {
    }

}