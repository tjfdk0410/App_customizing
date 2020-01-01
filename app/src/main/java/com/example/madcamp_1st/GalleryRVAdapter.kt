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
        holder.bind(itemList[position])
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gallPhoto = itemView.findViewById<ImageView>(R.id.gallphoto)


        fun bind(Im: Image) {
            if (Im.name !=""){
                if (context != null) {
                    val resourceId = context.resources.getIdentifier(Im.name,
                        "drawable",
                        context.packageName
                    )
                    gallPhoto.setImageResource(resourceId)
                }

            }
            else  {
                gallPhoto.setImageURI(Im.uri)
            }
        }
    }
}












