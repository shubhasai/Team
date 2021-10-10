package `in`.vikins.team

import `in`.vikins.team.about.userprofile
import `in`.vikins.team.models.postdata
import `in`.vikins.team.postscreen.PostAdapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class GroupAdapter(private val context: Context?, private val grouplist:ArrayList<grpdata>, val listener:grpClicked) : RecyclerView.Adapter<GroupAdapter.GroupViewholder>() {
    class GroupViewholder(view: View):RecyclerView.ViewHolder(view){
        val txttitle: TextView = view.findViewById(R.id.txtgrpName)
        val txtdes: TextView = view.findViewById(R.id.txtgrpdes)
        val grpdp: ImageView= view.findViewById(R.id.imgviewgrp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_grp_view,parent,false)
        val viewHolder = GroupViewholder(view)
        view.setOnClickListener{
            listener.onitemClicked(grouplist[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: GroupViewholder, position: Int) {
        val group = grouplist[position]
        holder.txttitle.text = group.name
        holder.txtdes.text = group.des
        Glide.with(context!!).load(group.grpdp).error(R.drawable.ic_about).into(holder.grpdp)
    }

    override fun getItemCount(): Int {
        return grouplist.size
    }

}
interface grpClicked{
    fun onitemClicked(itemlist: grpdata){

    }
}
