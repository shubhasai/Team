package `in`.vikins.team

import `in`.vikins.team.databinding.FragmentCreategrpBinding
import `in`.vikins.team.models.userdetails
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage


class CreategrpFragment : Fragment() {
    lateinit var binding: FragmentCreategrpBinding
    private lateinit var mfirebasestorage: StorageReference
    lateinit var name: String
    lateinit var des: String
    var imgurl: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreategrpBinding.inflate(layoutInflater)
        val loadimg = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) {
            binding.imageView.setImageURI(it)
            if (it != null) {
                imgurl = it.toString()
            } else {
                val directions =
                    CreategrpFragmentDirections.actionCreategrpFragmentToPersonFragment()
                findNavController().navigate(directions)
            }

        }
        binding.imageView.setOnClickListener {
            loadimg.launch("image/*")
        }
        binding.buttonsave.setOnClickListener {
            creategroup()
        }
        return binding.root
    }

    fun creategroup() {
        mfirebasestorage = Firebase.storage.reference
        val file: Uri = Uri.parse(imgurl)
        name = binding.grpname.text.toString()
        des = binding.grpdes.text.toString()
        mfirebasestorage.child("image/groups/$name").putFile(file).addOnSuccessListener {
            binding.uploading.visibility = View.INVISIBLE
            mfirebasestorage.child("image/groups/$name").downloadUrl.addOnSuccessListener {
                imgurl = it.toString()
                val database = FirebaseDatabase.getInstance().getReference("users")
                database.child(userdetails.uid).get().addOnSuccessListener {
                    if (it.exists()) {
                        val n = it.child("name").value.toString()
                        var admin = n
                        val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference()
                        val hashMap: HashMap<String, Any> = HashMap()
                        val members: HashMap<String, String> = HashMap()
                        if (name.isEmpty() || des.isEmpty()) {
                            Toast.makeText(activity, "Input All Field", Toast.LENGTH_SHORT).show()
                        } else {
                            hashMap.put("name", name)
                            hashMap.put("grpdp", imgurl)
                            hashMap.put("des", des)
                            hashMap.put("admin", admin)
                            hashMap.put("members","")
                            members.put("id",userdetails.uid)
                            reference.child("groups").child(name).setValue(hashMap)
                            reference.child("groups").child(name).child("members").push().setValue(members)
                            Toast.makeText(activity, "Uploaded Successfully", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        }
    }
}

