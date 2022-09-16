package jp.co.yumemi.android.code_check

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

val diff_util = object: DiffUtil.ItemCallback<item>(){
    override fun areItemsTheSame(oldItem: item, newItem: item): Boolean
    {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem : item, newItem : item): Boolean
    {
        return oldItem == newItem
    }

}

class CustomAdapter(private val itemClickListener : OnItemClickListener) :
    ListAdapter<item, CustomAdapter.ViewHolder>(diff_util){

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)

    interface OnItemClickListener{
        fun itemClick(item: item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val rootView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item, parent, false)
        return ViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val item = getItem(position)
        (holder.itemView.findViewById<View>(R.id.repositoryNameView) as TextView).text=
            item.name

        holder.itemView.setOnClickListener{
            itemClickListener.itemClick(item)
        }
    }
}