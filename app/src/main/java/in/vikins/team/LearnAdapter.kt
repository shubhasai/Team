package `in`.vikins.team

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mikhaellopez.circularprogressbar.CircularProgressBar

class LearnAdapter(private val context: Context?, private val playlist:ArrayList<playlistdata>, val listener:playlistClicked):RecyclerView.Adapter<LearnAdapter.PlaylistViewholder>(){
    class PlaylistViewholder(view:View):RecyclerView.ViewHolder(view) {
        val tasktitle:TextView = view.findViewById(R.id.taskname)
        val taskprogress:CircularProgressBar = view.findViewById(R.id.taskprogress)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_taskview,parent,false)
        val viewHolder = PlaylistViewholder(view)
        view.setOnClickListener{
            listener.onitemClicked(playlist[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: PlaylistViewholder, position: Int) {
        val group = playlist[position]
        holder.tasktitle.text = group.name
        holder.taskprogress.progressMax = playlist.size.toFloat()
        holder.taskprogress.apply {
            setProgressWithAnimation(group.complete.toFloat())
        }
    }

    override fun getItemCount(): Int {
        return playlist.size
    }
}
interface playlistClicked{
    fun onitemClicked(playlist: playlistdata){

    }
}