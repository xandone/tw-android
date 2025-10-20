package com.xandone.twandroid

/**
 * @author: xiao
 * created on: 2025/10/20 14:46
 * description:
 */
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xandone.twandroid.databinding.ActivityMainBinding
import com.xandone.twandroid.ui.PracticeActivity

class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        _binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_home -> {
                    true
                }

                R.id.item_mine -> {
                    true
                }

                else -> false
            }
        }

        _binding.btn.setOnClickListener{
            startActivity(Intent(this, PracticeActivity::class.java))
        }
    }
}