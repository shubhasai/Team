package `in`.vikins.team.postscreen

import `in`.vikins.team.databinding.FragmentPostBinding
import `in`.vikins.team.models.postdata
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class PostFragment : Fragment() {
    lateinit var postArray:ArrayList<postdata>
    private lateinit var binding:FragmentPostBinding
    lateinit var layoutManager: RecyclerView.LayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPostBinding.inflate(layoutInflater)
        binding.postrecyclerview.layoutManager = LinearLayoutManager(activity)
        layoutManager = LinearLayoutManager(activity)
        postArray = ArrayList()
        getpostlist()
        binding.addpost.setOnClickListener {
            startActivity(Intent(activity, AddPost::class.java))
        }
        return binding.root
    }
    fun getpostlist(){
        val database = FirebaseDatabase.getInstance().getReference("post")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                postArray.clear()
                for (datasnapshot: DataSnapshot in snapshot.children){
                    val post = datasnapshot.getValue(postdata::class.java)
                    postArray.add(post!!)
                }
                val postAdapter = PostAdapter(activity as Context?, postArray)
                binding.postrecyclerview.adapter =postAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity,"Something Went Wrong",Toast.LENGTH_SHORT).show()
            }

        })
    }

}