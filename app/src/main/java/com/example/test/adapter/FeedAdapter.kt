package com.example.test.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.inter.ItemClickListener
import com.example.test.model.RSSObject


class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {

    val txtTitle:TextView = itemView.findViewById(R.id.txtTitle)
    val txtPubDate:TextView = itemView.findViewById(R.id.txtPubDate)
    val txtContent:TextView = itemView.findViewById(R.id.txtContent)
    private var itemClickListener: ItemClickListener? = null

    init {
        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener =itemClickListener
    }

    override fun onClick(v: View?) {
        itemClickListener!!.onClick(v, absoluteAdapterPosition,false)
    }

    override fun onLongClick(v: View?): Boolean {
        itemClickListener!!.onClick(v, absoluteAdapterPosition,true)
        return true
    }

}

class FeedAdapter(private val rssObject: RSSObject, private val mContext: Context) : RecyclerView.Adapter<FeedViewHolder>() {

    val inflater: LayoutInflater = LayoutInflater.from(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val itemView = inflater.inflate(R.layout.item, parent, false)
        return FeedViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return rssObject.items.size
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.txtTitle.text = rssObject.items[position].title
        holder.txtPubDate.text = rssObject.items[position].pubDate
        holder.txtContent.text = rssObject.items[position].content

        holder.setItemClickListener(ItemClickListener { view, position, isLongClick ->

            if(!isLongClick)
            {
                val sharingIntent = Intent(Intent.ACTION_VIEW)
                sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                sharingIntent.data = Uri.parse(rssObject.items[position].link)

                val chooserIntent = Intent.createChooser(sharingIntent, "Open With")
                chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                mContext.startActivity(chooserIntent)

            }
        })
    }
}