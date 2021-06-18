package com.abanoub.news.main

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.abanoub.news.R
import com.abanoub.news.data.model.Article
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.article_single_item.view.*


class SearchAdapter(var list: ArrayList<Article>, var itemCallback: OnItemClick) :
    RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.article_single_item, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var item = list.get(position)

        holder.itemView.title.setText(item.title)

        Glide.with(holder.itemView.context)
            .load(item.urlToImage)
            .placeholder(R.drawable.placeholder)
            .into(holder.itemView.image)

        holder.itemView.rootView.clipToOutline=true

        holder.itemView.setOnClickListener{itemCallback.onItemClicked(item)}
    }

    public interface OnItemClick {
        fun onItemClicked(article: Article)
    }
}