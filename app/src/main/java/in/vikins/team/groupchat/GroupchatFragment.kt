package `in`.vikins.team

import `in`.vikins.team.databinding.FragmentGroupBinding
import `in`.vikins.team.databinding.FragmentGroupchatBinding
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

class GroupchatFragment : Fragment() {
    private lateinit var binding:FragmentGroupchatBinding
    val arg:GroupchatFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGroupchatBinding.inflate(layoutInflater)
        binding.addmenber.setOnClickListener {
            val direction = GroupchatFragmentDirections.actionGroupchatFragmentToSelectmemberFragment(arg.groupid)
            findNavController().navigate(direction)
        }
        binding.tvgrprName.text = arg.groupid
        return binding.root
    }


}