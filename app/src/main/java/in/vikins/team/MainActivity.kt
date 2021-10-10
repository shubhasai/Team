package `in`.vikins.team

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.sawolabs.androidsdk.Sawo

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn = findViewById<Button>(R.id.signin)
        btn.setOnClickListener {
            Sawo(
                this,
                "e9aaef85-38ac-4a9a-9e20-cc3db391ab3a", // your api key
                "614ff0beec39c0c001ec610bS4bOhY3G8yImd0Pd81pNsmW7"  // your api key secret
            ).login(
                "email", // can be one of 'email' or 'phone_number_sms'
                HomeActivity::class.java.name // Callback class name
            )
            finish()
        }
    }
}