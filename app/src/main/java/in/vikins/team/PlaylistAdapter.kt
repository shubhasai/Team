package `in`.vikins.team

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlaylistAdapter (private val context: Context?, private val videolist:ArrayList<videodetails>, val listener:videoClicked):
    RecyclerView.Adapter<PlaylistAdapter.videoViewholder>(){
    class videoViewholder(view: View):RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.vdoname)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): videoViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_video_details,parent,false)
        val viewHolder = videoViewholder(view)
        view.setOnClickListener{
            listener.onitemClicked(videolist[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: videoViewholder, position: Int) {
        val data = videolist[position]
        holder.title.text = data.videoname
    }

    override fun getItemCount(): Int {
        return videolist.size
    }
}
interface videoClicked{
    fun onitemClicked(playlist: videodetails){

    }
}