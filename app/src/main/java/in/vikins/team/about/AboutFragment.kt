package `in`.vikins.team.about

import `in`.vikins.team.R
import `in`.vikins.team.databinding.FragmentAboutBinding
import `in`.vikins.team.models.userdetails
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class AboutFragment : Fragment() {
    var imgurl:String = ""
    var skills:String = ""
    lateinit var binding: FragmentAboutBinding
    private lateinit var mfirebasedatabase: DatabaseReference
    private lateinit var mfirebasestorage: StorageReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("userid", userdetails.uid)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        readdata()
        binding = FragmentAboutBinding.inflate(layoutInflater)
        mfirebasedatabase = Firebase.database.reference

        val loadimg = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) {
            binding.imageView.setImageURI(it)
            if (it != null) {
                imgurl = it.toString()
            }
            else{
                val directions = AboutFragmentDirections.actionAboutFragmentToPersonFragment()
                findNavController().navigate(directions)
            }

        }
        binding.imageView.setOnClickListener {
            loadimg.launch("image/*")
        }
        // Inflate the layout for this fragment
        binding.buttonsave.setOnClickListener {
            binding.uploading.visibility = View.VISIBLE
            if(imgurl ==""){
                Toast.makeText(activity,"Select Image",Toast.LENGTH_SHORT).show()
                loadimg.launch("image/*")
            }
            else{
                selectskill()
                savedata()
            }
        }
        return binding.root
    }
    private fun savedata(){

        val userid = userdetails.uid
        mfirebasestorage = Firebase.storage.reference
        val file:Uri = Uri.parse(imgurl)
        mfirebasestorage.child("image/$userid").putFile(file).addOnSuccessListener{
            binding.uploading.visibility = View.INVISIBLE
            mfirebasestorage.child("image/$userid").downloadUrl.addOnSuccessListener {
                mfirebasedatabase.child("users").child(userid).child("dp").setValue(it.toString())
                imgurl = it.toString()
            }
            val name = binding.editTextTextPersonName.text.toString().lowercase()
            val email = binding.emailid.text.toString().lowercase()
            val dp = imgurl
            if(userid.isNotEmpty()||name.isNotEmpty()||email.isNotEmpty()||skills.isNotEmpty()){
                val user = userprofile(userid,name,email,skills,dp)
                mfirebasedatabase.child("users").child(userid).setValue(user)
                Toast.makeText(activity,"Saved",Toast.LENGTH_SHORT).show()
                readdata()
                val direction= AboutFragmentDirections.actionAboutFragmentToPersonFragment()
                findNavController().navigate(direction)
            }
            else{
                Toast.makeText(activity,"Enter All Fields",Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun selectskill(){
        when(binding.radioGroup.checkedRadioButtonId){
            R.id.radioButton ->{
                skills = "android"
                Log.d("skills",skills)
            }
            R.id.radioButton2 ->{
                skills = "web"
                Log.d("skills",skills)
            }
            R.id.radioButton3 ->{
                skills = "ai/ml"
                Log.d("skills",skills)
            }
            R.id.radioButton4 ->{
                skills = "blockchain"
                Log.d("skills",skills)
            }
        }
    }
    fun readdata(){
        mfirebasestorage = Firebase.storage.reference
        mfirebasestorage.child("image/${userdetails.uid}").downloadUrl.addOnSuccessListener {
            mfirebasedatabase.child("users").child(userdetails.uid).child("dp").setValue(it.toString())
        }
        val database = FirebaseDatabase.getInstance().getReference("users")
        database.child(userdetails.uid).get().addOnSuccessListener {
            if(it.exists()){
                val p = it.child("dp").value
                Glide.with(this).load(p).error(R.drawable.ic_about).into(binding.imageView)
                val n = it.child("name").value.toString().uppercase()
                binding.editTextTextPersonName.setText(n)
                userdetails.name = n
                val e = it.child("email").value.toString()
                binding.emailid.setText(e)
                val s = it.child("skill").value.toString()
                when(s){
                    "android"->{
                        binding.radioButton.isChecked = true
                    }
                    "web"->{
                        binding.radioButton2.isChecked = true
                    }
                    "ai/ml"->{
                        binding.radioButton3.isChecked = true
                    }
                    "blockchain"->{
                        binding.radioButton4.isChecked = true
                    }
                }
            }
        }
    }
    
}