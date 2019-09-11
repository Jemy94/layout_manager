package com.example.custommanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.text

/**
 */
class MyAdapter(private val list: List<String>) : RecyclerView.Adapter<MyHolder>() {


  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
    val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
    return MyHolder(itemView)
  }

  override fun onBindViewHolder(holder: MyHolder, position: Int) {
    holder.myText.text = list[position]
  }


  override fun getItemCount(): Int {
    return list.size
  }

}

class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
  var myText = itemView.text
}