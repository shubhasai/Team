package `in`.vikins.team

import `in`.vikins.team.about.userprofile
import `in`.vikins.team.databinding.FragmentGroupBinding
import `in`.vikins.team.models.postdata
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class GroupFragment : Fragment(),grpClicked {
    private lateinit var binding: FragmentGroupBinding
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var grpArray:ArrayList<grpdata>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGroupBinding.inflate(layoutInflater)
        binding.creategroup.setOnClickListener{
            val direction = GroupFragmentDirections.actionGroupFragmentToCreategrpFragment()
            findNavController().navigate(direction)
        }
        binding.groupslist.layoutManager = LinearLayoutManager(activity)
        layoutManager = LinearLayoutManager(activity)
        grpArray = ArrayList()
        getgrouplist()
        return binding.root
    }
    override fun onitemClicked(itemlist: grpdata) {
        val direction = GroupFragmentDirections.actionGroupFragmentToGroupchatFragment(itemlist.name)
        findNavController().navigate(direction)
    }
    fun getgrouplist(){
        val database = FirebaseDatabase.getInstance().getReference("groups")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                grpArray.clear()
                for (datasnapshot: DataSnapshot in snapshot.children){
                    val group = datasnapshot.getValue(grpdata::class.java)
                    Log.d("groupdetails",group!!.name)
                    grpArray.add(group!!)
                }

                val grpAdapter = GroupAdapter(activity as Context?, grpArray,this@GroupFragment)
                binding.groupslist.adapter = grpAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity,"Something Went Wrong", Toast.LENGTH_SHORT).show()
            }

        })
    }

}