package `in`.vikins.team

import `in`.vikins.team.databinding.FragmentPlaylistplayerBinding
import `in`.vikins.team.models.userdetails
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class playlistplayerFragment : Fragment(),videoClicked {
    lateinit var binding:FragmentPlaylistplayerBinding
    lateinit var vdoArray:ArrayList<videodetails>
    var targetArray:ArrayList<playlistdata> = ArrayList()
    val arg:playlistplayerFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPlaylistplayerBinding.inflate(layoutInflater)
        binding.addvideo.setOnClickListener {
            val intent = Intent(activity,AddVideo::class.java)
            intent.putExtra("pname", arg.link)
            startActivity(intent)
        }
        vdoArray = ArrayList()
        getvdo()
        return binding.root
    }
    fun getvdo(){
        val database = FirebaseDatabase.getInstance().getReference("users").child(userdetails.uid).child("playlists").child(arg.link).child("links")
        database.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                vdoArray.clear()
                for (datasnapshot:DataSnapshot in snapshot.children){
                    val video = datasnapshot.getValue(videodetails::class.java)
                    if (video != null) {
                        vdoArray.add(video)
                    }
                }
                binding.videolist.layoutManager  = LinearLayoutManager(activity)
                val videoadapter = PlaylistAdapter(activity as Context, vdoArray,this@playlistplayerFragment)
                binding.videolist.adapter = videoadapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    override fun onitemClicked(playlist: videodetails) {
        val videoplayer = binding.videoplayer
        videoplayer.setVideoURI(Uri.parse(playlist.link))
        val control = MediaController(activity)
        control.setAnchorView(videoplayer)
        videoplayer.setMediaController(control)
        videoplayer.start()
        videoplayer.setOnCompletionListener {
            getcomplete()
        }
    }
    fun getcomplete(){
        val database = FirebaseDatabase.getInstance().getReference("users").child(userdetails.uid).child("playlists").child(arg.link)
        database.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                targetArray.clear()
                val data = snapshot.getValue(playlistdata::class.java)
                var no = data?.complete?.toFloat()
                if (no != null) {
                    no += 1
                }
                database.child("complete").setValue(no)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}