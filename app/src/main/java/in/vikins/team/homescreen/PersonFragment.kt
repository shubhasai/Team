package `in`.vikins.team.homescreen

import `in`.vikins.team.PersonAdapter
import `in`.vikins.team.R
import `in`.vikins.team.about.userprofile
import `in`.vikins.team.databinding.FragmentPersonBinding
import `in`.vikins.team.userClicked
import `in`.vikins.team.models.userdetails
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PersonFragment : Fragment(), userClicked {
    private var query:String = ""
    private lateinit var binding: FragmentPersonBinding
    lateinit var layoutManager: RecyclerView.LayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonBinding.inflate(layoutInflater)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        layoutManager = LinearLayoutManager(activity)
        binding.toggleButtonGroup.addOnButtonCheckedListener { toggleButtonGroup, checkedId, isChecked ->

            if (isChecked) {
                when (checkedId) {
                    R.id.android -> {
                        showToast("Android")
                        query = "android"
                        filter()
                    }
                    R.id.web -> {
                        showToast("Web")
                        query = "web"
                        filter()
                    }
                    R.id.aiml -> {
                        showToast("AI/ML")
                        query = "ai/ml"
                        filter()
                    }
                    R.id.blockchain ->{
                        showToast("Blockchain")
                        query = "blockchain"
                        filter()
                    }
                }
            } else {
                if (toggleButtonGroup.checkedButtonId == View.NO_ID) {
                    getuserlist()
                }
            }

        }
        getuserlist()
        // Inflate the layout for this fragment
        return binding.root
    }
    private fun showToast(str: String) {
        Toast.makeText(activity, str, Toast.LENGTH_SHORT).show()
    }
    fun getuserlist(){
        val database = FirebaseDatabase.getInstance().getReference("users")
        val personArray:ArrayList<userprofile> = ArrayList()
        database.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                personArray.clear()
                for (datasnapshot:DataSnapshot in snapshot.children){
                    val user = datasnapshot.getValue(userprofile::class.java)
                    if(user!!.userid != userdetails.uid){
                        personArray.add(user)
                    }
                }
                val personAdapter = PersonAdapter(activity as Context?, personArray,this@PersonFragment)
                binding.recyclerView.adapter = personAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    override fun onitemClicked(itemlist: userprofile) {
        val direction =
            PersonFragmentDirections.actionPersonFragmentToChatFragment(
                itemlist.name.toString(),
                itemlist.userid.toString()
            )
        findNavController().navigate(direction)
    }
   fun filter(){
       val database = FirebaseDatabase.getInstance().getReference("users").orderByChild("skill").equalTo(query)
       val personArray:ArrayList<userprofile> = ArrayList()
       database.addValueEventListener(object :ValueEventListener{
           override fun onDataChange(snapshot: DataSnapshot) {
               personArray.clear()
               for (datasnapshot:DataSnapshot in snapshot.children){
                   val user = datasnapshot.getValue(userprofile::class.java)
                   if(user!!.userid != userdetails.uid){
                       personArray.add(user)
                   }
               }
               val personAdapter = PersonAdapter(activity as Context, personArray,this@PersonFragment)
               binding.recyclerView.adapter = personAdapter
           }

           override fun onCancelled(error: DatabaseError) {
               TODO("Not yet implemented")
           }
       })
   }
}

