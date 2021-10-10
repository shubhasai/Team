package `in`.vikins.team.postscreen

import `in`.vikins.team.R
import `in`.vikins.team.models.postdata
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class PostAdapter(private val context: Context?, private val postlist:ArrayList<postdata>):
    RecyclerView.Adapter<PostAdapter.MyViewHolder>() {
    class MyViewHolder (view:View):RecyclerView.ViewHolder(view){
        val txttitle: TextView = view.findViewById(R.id.blogt)
        val txtdes: TextView = view.findViewById(R.id.blogd)
        val txtauth: TextView = view.findViewById(R.id.bloga)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_singleview,parent,false)
        val viewHolder = MyViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = postlist[position]
        holder.txttitle.text = user.title
        holder.txtdes.text = user.des
        holder.txtauth.text = user.name
    }

    override fun getItemCount(): Int {
        return postlist.size
    }


}