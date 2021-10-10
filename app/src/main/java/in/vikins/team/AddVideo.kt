package `in`.vikins.team

import `in`.vikins.team.databinding.ActivityAddPostBinding
import `in`.vikins.team.databinding.ActivityAddVideoBinding
import `in`.vikins.team.models.userdetails
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AddVideo : AppCompatActivity() {
    lateinit var binding:ActivityAddVideoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddVideoBinding.inflate(layoutInflater)
        val pname = intent.getStringExtra("pname")
        binding.btnaddvdo.setOnClickListener {
            val vdoname = binding.videoname.text.toString()
            val link = binding.link.text.toString()
            val video: HashMap<String, String> = HashMap()
            video.put("videoname",vdoname)
            video.put("link",link)
            val database = FirebaseDatabase.getInstance().getReference("users").child(userdetails.uid)
            database.child("playlists").child(pname!!).child("links").push().setValue(video)
        }
        setContentView(binding.root)
    }
}