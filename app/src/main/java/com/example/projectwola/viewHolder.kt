package com.example.projectwola

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class viewHolder (view: View): RecyclerView.ViewHolder(view){
    var txtTitleItem: TextView = view.findViewById(R.id.txtTitleItem)
    var imageView: ImageView = view.findViewById(R.id.imageView)
}
