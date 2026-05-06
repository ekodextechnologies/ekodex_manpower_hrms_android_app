package com.ekodex.manpowerhrms.News

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ekodex.manpowerhrms.R


class NewsAdapter(var data: List<NewsData>) :
    Adapter<NewsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.news_item_view, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        val item = data[position]

        holder.title.text = item.title
        holder.source.text = item.source

        //glide for image

        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher_round)
            .error(R.mipmap.ic_launcher_round)

        Glide.with(holder.img.context).load(item.img).apply(options).into(holder.img)

        holder.layout.setOnClickListener {
            it.findNavController().navigate(NewsFragmentDirections.actionNewsFragmentToNewsDetailsFragment(item.articleUrl))
        }

    }

    override fun getItemCount() = data.size

}

class NewsViewHolder(itemView: View) : ViewHolder(itemView) {
    val title: TextView = itemView.findViewById(R.id.textView83)
    val source: TextView = itemView.findViewById(R.id.textView84)
    val img: ImageView = itemView.findViewById(R.id.imageView44)
    val layout: CardView = itemView.findViewById(R.id.newsCard)
}


