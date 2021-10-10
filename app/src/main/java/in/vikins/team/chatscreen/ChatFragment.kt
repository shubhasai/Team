package `in`.vikins.team.chatscreen

import `in`.vikins.team.databinding.FragmentChatBinding
import `in`.vikins.team.models.userdetails
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class ChatFragment : Fragment() {
    private lateinit var binding:FragmentChatBinding
    lateinit var layoutManager: RecyclerView.LayoutManager
    var reference: DatabaseReference? = null
    var chatList = ArrayList<chat>()
    val arg: ChatFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(layoutInflater)
        binding.chatRecyclerView.layoutManager = LinearLayoutManager(activity)
        layoutManager = LinearLayoutManager(activity)
        binding.btnSendMessage.setOnClickListener {
            val message: String = binding.etMessage.text.toString()

            if (message.isEmpty()) {
                Toast.makeText(activity, "message is empty", Toast.LENGTH_SHORT).show()
                binding.etMessage.setText("")
            } else {
                sendMessage(userdetails.uid, arg.userid, message)
                binding.etMessage.setText("")
            }
        }
        readMessage(userdetails.uid, arg.userid)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvUserName.text = arg.username
    }
    private fun sendMessage(senderId: String, receiverId: String, message: String) {
        var reference: DatabaseReference? = FirebaseDatabase.getInstance().getReference()

        var hashMap: HashMap<String, String> = HashMap()
        hashMap.put("senderId", senderId)
        hashMap.put("receiverId", receiverId)
        hashMap.put("message", message)

        reference!!.child("Chat").push().setValue(hashMap)

    }
    fun readMessage(senderId: String, receiverId: String) {
        val databaseReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Chat")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                chatList.clear()
                for (dataSnapShot: DataSnapshot in snapshot.children) {
                    val chat = dataSnapShot.getValue(chat::class.java)

                    if (chat!!.senderId.equals(senderId) && chat!!.receiverId.equals(receiverId) ||
                        chat!!.senderId.equals(receiverId) && chat!!.receiverId.equals(senderId)
                    ) {
                        chatList.add(chat)
                    }
                }
                val chatAdapter = ChatAdapter(chatList)
                binding.chatRecyclerView.adapter = chatAdapter
            }
        })
    }
}