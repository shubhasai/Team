package `in`.vikins.team.postscreen

import `in`.vikins.team.databinding.ActivityAddPostBinding
import `in`.vikins.team.models.userdetails
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddPost : AppCompatActivity() {
    lateinit var title:String
    lateinit var des:String
    lateinit var binding:ActivityAddPostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.uploadpost.setOnClickListener {
//            if(title.isEmpty()||des.isEmpty()){
//                Toast.makeText(this,"Input All Field",Toast.LENGTH_SHORT).show()
//            }
//            else{
//
//            }
            uploadpost()
        }
    }
    private fun uploadpost(){
        title = binding.blogtitle.text.toString()
        des = binding.blogdes.text.toString()
        val database = FirebaseDatabase.getInstance().getReference("users")
        database.child(userdetails.uid).get().addOnSuccessListener {
            if(it.exists()){
                val n = it.child("name").value.toString()
                var name = n
                val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference()
                val hashMap: HashMap<String, String> = HashMap()
                if(name== ""||title.isEmpty()||des.isEmpty()){
                    Toast.makeText(this,"Input All Field",Toast.LENGTH_SHORT).show()
                }
                else{
                    hashMap.put("name", name)
                    hashMap.put("title", title)
                    hashMap.put("des", des)
                    reference.child("post").push().setValue(hashMap)
                    Toast.makeText(this,"Uploaded Successfully", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }
}