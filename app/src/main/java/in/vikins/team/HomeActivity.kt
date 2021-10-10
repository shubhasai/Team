package `in`.vikins.team

import `in`.vikins.team.about.userprofile
import `in`.vikins.team.databinding.ActivityHomeBinding
import `in`.vikins.team.models.userdetails
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.json.JSONObject
import com.sawolabs.androidsdk.LOGIN_SUCCESS_MESSAGE

class HomeActivity : AppCompatActivity() {
    lateinit var  binding:ActivityHomeBinding
    lateinit var userdata:JSONObject
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val msg = intent.getStringExtra(LOGIN_SUCCESS_MESSAGE)
        try {
            userdata = JSONObject(msg)
        } catch (e: Exception) {
            Toast.makeText(this, "Some Error Occurred! Please try again.", Toast.LENGTH_LONG).show()
        }
        userdetails.uid = userdata.getString("user_id")
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)
        actionBarDrawerToggle = ActionBarDrawerToggle(this,binding.drawerLayout,R.string.app_name,R.string.app_name)
        actionBarDrawerToggle.syncState()
        val Navhost = supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
        binding.navMenu.setupWithNavController(Navhost.navController)

        val headerView : View = binding.navView.getHeaderView(0)
        val navUsername : TextView = headerView.findViewById(R.id.username)
        val navUserdp : ImageView = headerView.findViewById(R.id.userdp)
        val database = FirebaseDatabase.getInstance().getReference("users")
        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (datasnapshot:DataSnapshot in snapshot.children){
                    val user = datasnapshot.getValue(userprofile::class.java)
                    if(user!!.userid == userdetails.uid){
                        navUsername.text = user.name!!.uppercase()
                        Glide.with(this@HomeActivity).load(user.dp).error(R.drawable.ic_about).into(navUserdp)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        val navhost =  supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
        binding.navView.setupWithNavController(navhost.navController)
        Log.d("userid", userdetails.uid)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}