package com.turtle8.room2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.turtle8.room2.databinding.FragmentViewPageBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val Arg_position = "arg_position"

/**
 * A simple [Fragment] subclass.
 * Use the [OnePageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OnePageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var position:Int = 0
    private lateinit var binding: FragmentViewPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt(Arg_position)
        }
        Log.d(LOG_TAG, "onCreated ${position}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewPageBinding.inflate(inflater)
        binding.pageId.text = position.toString()
        return binding.root
    }

    override fun onDestroy() {
        Log.d(LOG_TAG,"onDestroy ${position}")
        super.onDestroy()
    }

    companion object {
        const val LOG_TAG = "OnPageFragment"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ViewPageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(position:Int) =
            OnePageFragment().apply {
                arguments = Bundle().apply {
                    putInt(Arg_position, position)
                }
            }
    }
}