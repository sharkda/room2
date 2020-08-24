package com.turtle8.room2

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.turtle8.room2.databinding.OneFragmentBinding

class OneFragment : Fragment() {

    companion object {
        const val LOG_TAG = "OneFragment"
        const val Bundle_key_Page = "bundle_key_page"
        @JvmStatic
        fun newInstance(bundelPageNo:Int) =
            OneFragment().apply {
                arguments = Bundle().apply {
                    putInt(Bundle_key_Page, bundelPageNo)
                }
            }
    }
    private var bundlePageNo:Int = 0

    private lateinit var binding: OneFragmentBinding
    private val viewModel:OneViewModel by viewModels()

    var tonePageChangeCallback = object:
        ViewPager2.OnPageChangeCallback(){
        override fun onPageSelected(position: Int) {
            Log.d("OneFragment::tonePageChangeCallBackObj",position.toString())
            //MediaPlayer.create(requireContext(), R.raw.navigation_forward_selection).start()
            viewModel.position = position //this is always page0 when resumed vp need to load
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null){
            bundlePageNo = savedInstanceState.getInt(Bundle_key_Page)
            Log.d(LOG_TAG, "onActivityCreated -> ${Bundle_key_Page} reads ${bundlePageNo}")
        }else{
            Log.d(LOG_TAG, "OnActivityCreated savedIS is null!")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            Log.d(LOG_TAG, "onCreate savedInstanceState is null!!")
        }
        arguments?.let{
            bundlePageNo = it.getInt(Bundle_key_Page) ?: 5
        }
        Log.d(LOG_TAG, "OnCreate -> ${Bundle_key_Page} reads ${bundlePageNo}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OneFragmentBinding.inflate(inflater)
        val viewPageAdapter = ViewPager2Adapter(
            requireActivity(), 8)
        binding.viewpager.adapter = viewPageAdapter
        binding.viewpager.registerOnPageChangeCallback(
            tonePageChangeCallback
        )
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val pos1 = viewModel.position
        Log.d(LOG_TAG, "OnResume... ${pos1}")
        if (pos1 > 0 ){
            binding.viewpager
                .setCurrentItem(pos1, false)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(LOG_TAG, "OnSaveInstanceState ${viewModel.position}")
        outState.putInt(Bundle_key_Page, viewModel.position)
    }

    override fun onDestroy() {
        Log.d(LOG_TAG,"onDestroy... ${viewModel.position}")
        super.onDestroy()
    }



}