package com.example.homework4
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class DreamListAdapter : ListAdapter<Dream, DreamListAdapter.DreamViewHolder> (DreamComparator()) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DreamViewHolder {
        context = parent.context
        return DreamViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: DreamViewHolder, position: Int) {
        val currentDream = getItem(position)
        val id = currentDream.id
        val title = currentDream.title
        holder.bindText(id, title, holder.textView_id, holder.textView_title)
        holder.itemView.setOnClickListener{
            val intent = Intent(context, ThirdActivity::class.java)
            intent.putExtra("DreamId", currentDream.id.toString())
            context.startActivity(intent)
        }
    }

    class DreamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView_id: TextView = itemView.findViewById(R.id.textView_id)
        val textView_title: TextView = itemView.findViewById(R.id.textView_title)

        // write a helper function that takes a string and a textView
        // assign text to textView
        fun bindText(id:Int, title:String, textView_id: TextView, textView_title: TextView){
            textView_id.text = id.toString()
            textView_title.text = title
        }

        companion object{
            fun create (parent: ViewGroup) : DreamViewHolder{
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dream, parent, false)
                return DreamViewHolder(view)
            }
        }

    }

    class DreamComparator : DiffUtil.ItemCallback<Dream>(){
        override fun areContentsTheSame(oldItem: Dream, newItem: Dream): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areItemsTheSame(oldItem: Dream, newItem: Dream): Boolean {
            return oldItem === newItem
        }
    }



}