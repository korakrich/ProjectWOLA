package com.example.projectwola

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class BranchAdapter(val branchList:List<Branch>): RecyclerView.Adapter<viewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(LayoutInflater.from(parent.context).inflate(R.layout.itemhorizontal, parent, false))
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val dataModel = branchList[position]
        holder.txtTitleItem.text = dataModel.title
        Picasso.get().load(dataModel.image)
            .error(R.drawable.placeholder)
            .placeholder(R.drawable.placeholder)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return branchList.size
    }


}
