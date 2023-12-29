package com.example.memo.game_scene

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.memo.R
import android.util.Log
import kotlinx.coroutines.*
import kotlin.time.*

import android.view.View
import android.widget.Chronometer
import android.widget.GridLayout
import android.widget.GridView


import com.example.memo.databinding.CatItemBinding
import com.example.memo.databinding.GameSceneBinding
import com.example.memo.reward.RewardActivity
import kotlinx.coroutines.delay
import kotlin.time.TimeSource.*

class GameSceneActivity: AppCompatActivity() {
    lateinit var binding: GameSceneBinding

    private val imageIdList = listOf(
        R.drawable.cat1, R.drawable.cat1, R.drawable.cat2,
        R.drawable.cat2, R.drawable.cat3, R.drawable.cat3,
        R.drawable.cat4, R.drawable.cat4, R.drawable.cat5,
        R.drawable.cat5, R.drawable.cat6, R.drawable.cat6
    )
    @kotlin.time.ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GameSceneBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val imageList = imageIdList.shuffled()

        val catList = ArrayList<ItemCat>()
        for (i in 0 until imageList.size) {
            catList.add(ItemCat(imageList[i], false, Status.ITEM_CLOSED))
        }


        var adapter = ItemCatAdapter(catList, this)

        binding.apply {
            gridViewv.adapter = adapter
            val start = getCurrentTime()
            chronometrView.start()
            gridViewv.setOnItemClickListener { parent, view, position, id ->

                if (checkOpen(catList) == -1) {
                    catList[position].status = Status.ITEM_OPEN
                    adapter.showItem(position)
                } else {
                    val open = checkOpen(catList)
                    //catList[position].status = Status.ITEM_OPEN
                    adapter.showItem(position)
                    if (catList[position].imageId == catList[open].imageId) {
                        catList[position].status = Status.ITEM_DELETED
                        catList[open].status = Status.ITEM_DELETED
                        if(gameOver(catList)){
                            val finish = getCurrentTime()
                            chronometrView.stop()

                            val gameTime = (finish - start).inWholeSeconds.toString().toInt()
                            val coins = countReward(gameTime)
                            coinsText.text = coins.toString()
                            openRewardWindow(coins)
                        }
                    } else {
                        adapter.hideItem(open)
                        adapter.hideItem(position)
                        catList[open].status = Status.ITEM_CLOSED
                        catList[open].status = Status.ITEM_CLOSED
                    }
                }
            }
        }
    }

    fun openRewardWindow(coins: Int){
        val intent = Intent(this, RewardActivity::class.java).apply {
            putExtra("coins", coins.toString())
        }
        startActivity(intent)
    }
    fun checkOpen(list: ArrayList<ItemCat>): Int {
        for (i in 0 until list.size) {
            if (list[i].status == Status.ITEM_OPEN) {
                return i
            }
        }
        return -1
    }

    fun gameOver(list: ArrayList<ItemCat>): Boolean {
        var sum = 0
        for (i in 0 until list.size) {
            sum += list[i].status.getV()
        }
        if (sum == 0) {
            return true
        } else return false
    }

    @kotlin.time.ExperimentalTime
    fun getCurrentTime(): TimeSource.Monotonic.ValueTimeMark{
        val timeSource = TimeSource.Monotonic
        return timeSource.markNow()
    }

    fun countReward(value: Int) : Int{
        if(value <= 20){
            return 100
        }
        else {
            val temp = 100 - (value - 20) * 5
            if(temp <= 10){
                return 10
            }
            else return temp
        }
    }
}