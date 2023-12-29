package com.example.memo.game_scene

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.memo.R
import com.example.memo.databinding.CatItemBinding
import android.util.Log
import android.widget.BaseAdapter
import android.widget.ImageView
import com.google.android.material.animation.AnimatableView
import kotlin.concurrent.thread

class ItemCatAdapter(var itemCat: ArrayList<ItemCat>,
                     var context: Context
    ): BaseAdapter(){

    var layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getCount(): Int {
        return itemCat.size
    }

    override fun getItem(position: Int): Any {
        return itemCat[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if(view == null){
            view = layoutInflater.inflate(R.layout.cat_item, parent, false)
        }
        var imView = view?.findViewById<ImageView>(R.id.catImage)

        imView?.setImageResource(itemCat[position].imageId)

        if(itemCat[position].visibility == false){
            imView?.visibility = View.INVISIBLE
        }
        else {imView?.visibility = View.VISIBLE}
        return view!!
    }

    fun showItem(position: Int){
        Log.d(
            "My log", "Item was shown " + position.toString()
        )
        itemCat[position].visibility = true
        notifyDataSetChanged()
    }

    fun showItems(list: ArrayList<ItemCat>){
        itemCat.clear()
        itemCat.addAll(list)
        notifyDataSetChanged()
    }


    fun hideItem(position: Int){

        Log.d(
            "My log", "Item was hiden " + position.toString()
        )
        itemCat[position].visibility = false
        notifyDataSetChanged()
    }
}