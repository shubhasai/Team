package `in`.vikins.team.chatscreen

import `in`.vikins.team.R
import `in`.vikins.team.models.userdetails
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(private val chatList: ArrayList<chat>) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    private val MESSAGE_TYPE_LEFT = 0
    private val MESSAGE_TYPE_RIGHT = 1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == MESSAGE_TYPE_RIGHT) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.chat_right_side, parent, false)
            return ViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.chat_left_side, parent, false)
            return ViewHolder(view)
        }

    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chat = chatList[position]
        holder.txtUserName.text = chat.message
        //Glide.with(context).load(user.profileImage).placeholder(R.drawable.profile_image).into(holder.imgUser)

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtUserName: TextView = view.findViewById(R.id.tvMessage)

    }

    override fun getItemViewType(position: Int): Int {
        if (chatList[position].senderId == userdetails.uid) {
            return MESSAGE_TYPE_RIGHT
        } else {
            return MESSAGE_TYPE_LEFT
        }

    }
}