package com.example.madcamp_1st

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.gallery.*

class GalleryRVAdapter(val context : Context?, val itemList:ArrayList<Image>) : RecyclerView.Adapter<GalleryRVAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.gallery_rv_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val gallPhoto = holder.itemView.findViewById<ImageView>(R.id.gallphoto)
        gallPhoto.setImageURI(itemList.get(position).uri)
        holder.bind(itemList.get(position))
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gallPhoto = itemView.findViewById<ImageView>(R.id.gallphoto)
//        gallPhoto.setImageURI(Im.uri)

        fun bind(Im: Image) {
//            for (i in itemList) {
        //            println("11111111111111111111111111111111111111111"+Im.uri)
            gallPhoto.setImageURI(Im.uri)
//            notifyDataSetChanged()
//            itemView.setOnClickListener{itemClick(Im)}
        }
    }
}












