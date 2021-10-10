package `in`.vikins.team

import `in`.vikins.team.about.userprofile
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class PersonAdapter(private val context: Context?, private val userlist:ArrayList<userprofile>, val listener:userClicked):RecyclerView.Adapter<PersonAdapter.ViewHolder>() {
    class ViewHolder(view:View):RecyclerView.ViewHolder(view) {
        val txtUserName: TextView = view.findViewById(R.id.txtName)
        val txtemail:TextView = view.findViewById(R.id.txtMail)
        val txtskill:TextView = view.findViewById(R.id.txtSkill)
        val imgUser: CircleImageView = view.findViewById(R.id.imgviewprofile)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_profile_view,parent,false)
        val viewHolder =  ViewHolder(view)
        view.setOnClickListener{
            listener.onitemClicked(userlist[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userlist[position]
        holder.txtUserName.text = user.name
        holder.txtemail.text = user.email
        holder.txtskill.text = user.skill
        Glide.with(context!!).load(user.dp).error(R.drawable.ic_about).into(holder.imgUser)
    }

    override fun getItemCount(): Int {
        return userlist.size
    }
}
interface userClicked{
    fun onitemClicked(itemlist: userprofile){

    }
}