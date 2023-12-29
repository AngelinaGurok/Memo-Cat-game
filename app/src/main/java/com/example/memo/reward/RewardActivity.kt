package com.example.memo.reward

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.memo.MainActivity
import com.example.memo.databinding.ActivityMainBinding
import com.example.memo.databinding.GetRewardBinding
import com.example.memo.game_scene.GameSceneActivity

class RewardActivity: AppCompatActivity() {
    lateinit var binding : GetRewardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GetRewardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val reward = intent.getStringExtra("coins")

        binding.coinsRewardText.text = reward
        binding.homeImage.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}