package com.xandone.twandroid.ui

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.xandone.twandroid.R
import com.xandone.twandroid.databinding.ActPracticeLayoutBinding
import com.xandone.twandroid.utils.MyUtils

/**
 * @author: xiao
 * created on: 2025/10/20 15:38
 * description:
 */
class PracticeActivity : AppCompatActivity() {

    private lateinit var _binding: ActPracticeLayoutBinding

    val transaction = supportFragmentManager.beginTransaction()
    var handwritingFragment: HandwritingFragment? = null
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActPracticeLayoutBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        handwritingFragment = HandwritingFragment()
        handwritingFragment!!.writeCallBack = object : WriteCallBack {
            override fun showWrite(content: String) {
                Log.d("sfsdfsdfsd", "showWrite: $content")
                _binding.wordTv.text =
                    MyUtils.addHighLight2(_binding.wordTv.text.toString(), content)
            }
        }

        _binding.btn.setOnClickListener {
            transaction.add(R.id.frame_layout, handwritingFragment!!).commit()
        }
    }

}