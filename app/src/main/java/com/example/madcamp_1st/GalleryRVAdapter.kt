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

class GalleryRVAdapter(val context : Context?, val itemList:ArrayList<Image>) :
    RecyclerView.Adapter<GalleryRVAdapter.Holder>() {

    //화면 최초 로딩, view가 없으면 xml파일을 inflate하여 ViewHolder를 생성한다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.gallery_rv_item, parent, false)
        return Holder(view)
    }

    //RecyclerView로 만들어지는 item의 총 개수를 반환한다.
    override fun getItemCount(): Int {
        return itemList.size
    }

    //위의 onCreateViewHolder에서 만든 view와 실제 입력되는 각각의 데이터를 연결한다.
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(itemList.get(position))
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gallPhoto = itemView.findViewById<ImageView>(R.id.gallphoto)
        //종류 정하기, layout과 연결
        fun bind(Im: Image) {
//            for (i in itemList) {
            gallPhoto.setImageURI(Im.uri)
//            notifyDataSetChanged()
//            itemView.setOnClickListener{itemClick(Im)}
        }
    }
}












