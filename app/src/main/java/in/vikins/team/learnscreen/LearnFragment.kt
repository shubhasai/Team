package `in`.vikins.team

import `in`.vikins.team.about.userprofile
import `in`.vikins.team.databinding.FragmentLearnBinding
import `in`.vikins.team.models.userdetails
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class LearnFragment : Fragment(),playlistClicked {
    lateinit var binding:FragmentLearnBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLearnBinding.inflate(layoutInflater)
        binding.materialButton.setOnClickListener {
            val name = binding.playlisttitle.text.toString()
            val hashmap:HashMap<String,Any> = HashMap()
            if(name.isNotEmpty()){
                hashmap.put("name",name)
                hashmap.put("complete",0)
                hashmap.put("links","")
                val database = FirebaseDatabase.getInstance().getReference("users").child(userdetails.uid)
                database.child("playlists").child(name).setValue(hashmap)
                gettarget()
            }
        }
        gettarget()
        return binding.root
    }
    override fun onitemClicked(plalist: playlistdata) {
        val direction = LearnFragmentDirections.actionLearnFragmentToPlaylistplayerFragment(plalist.name)
        findNavController().navigate(direction)
    }
    fun gettarget(){
        val database = FirebaseDatabase.getInstance().getReference("users").child(userdetails.uid).child("playlists")
        database.addValueEventListener(object: ValueEventListener{
            var targetArray:ArrayList<playlistdata> = ArrayList()
            override fun onDataChange(snapshot: DataSnapshot) {
                targetArray.clear()
                for (datasnapshot:DataSnapshot in snapshot.children){
                    val playlist = datasnapshot.getValue(playlistdata::class.java)
                    if (playlist != null) {
                        targetArray.add(playlist)
                    }
                }
                binding.recyclerViewtask.layoutManager = LinearLayoutManager(activity)
                val targetAdapter = LearnAdapter(activity as Context, targetArray,this@LearnFragment)
                binding.recyclerViewtask.adapter = targetAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}