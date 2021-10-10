package `in`.vikins.team

import `in`.vikins.team.about.userprofile
import `in`.vikins.team.databinding.FragmentPersonBinding
import `in`.vikins.team.databinding.FragmentSelectmemberBinding
import `in`.vikins.team.models.userdetails
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class SelectmemberFragment : Fragment(),userClicked {
    lateinit var binding:FragmentSelectmemberBinding
    private var res = false
    private var query:String = ""
    val membersArray:ArrayList<members> = ArrayList()
    val arg:SelectmemberFragmentArgs by navArgs()
    lateinit var layoutManager: RecyclerView.LayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSelectmemberBinding.inflate(layoutInflater)
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
        getmember()
        getuserlist()
        return binding.root
    }
    private fun showToast(str: String) {
        Toast.makeText(activity, str, Toast.LENGTH_SHORT).show()
    }
    fun getuserlist(){

        val personArray:ArrayList<userprofile> = ArrayList()
        val database = FirebaseDatabase.getInstance().getReference("users")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                personArray.clear()
                for (datasnapshot: DataSnapshot in snapshot.children){
                    val user = datasnapshot.getValue(userprofile::class.java)

                    for(item in membersArray){
                        res = user!!.userid != userdetails.uid && user.userid != item.id
                    }
                    if(res){
                        if (user != null) {
                            personArray.add(user)
                        }
                    }
                }
                val personAdapter = PersonAdapter(activity as Context?, personArray,this@SelectmemberFragment)
                binding.recyclerView.adapter = personAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    override fun onitemClicked(itemlist: userprofile) {
        val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference()
        val members: HashMap<String, String> = HashMap()
        members.put("id", itemlist.userid.toString())
        reference.child("groups").child(arg.groupname).child("members").push().setValue(members)
        getmember()
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
                val personAdapter = PersonAdapter(activity as Context, personArray,this@SelectmemberFragment)
                binding.recyclerView.adapter = personAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    fun getmember(){
        val memberdatabase = FirebaseDatabase.getInstance().getReference("groups").child(arg.groupname).child("members")
        memberdatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                membersArray.clear()
                for (datasnapshot: DataSnapshot in snapshot.children){
                    val memberdetails = datasnapshot.getValue(members::class.java)
                    if (memberdetails != null) {
                        membersArray.add(memberdetails)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        Log.d("membersize",membersArray.size.toString())
        getuserlist()
    }
}