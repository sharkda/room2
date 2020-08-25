package com.turtle8.room2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.turtle8.room2.database.FragmentStateDao
import com.turtle8.room2.database.StateDatabase
import com.turtle8.room2.databinding.OneFragmentBinding
import com.turtle8.room2.viewModels.OneViewModel
import com.turtle8.room2.viewModels.OneViewModelFactory

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
    private lateinit var viewModel: OneViewModel
    private lateinit var stateDao:FragmentStateDao
    private var _position:Int = 0

    var tonePageChangeCallback = object:
        ViewPager2.OnPageChangeCallback(){
        override fun onPageSelected(position: Int) {
            _position = position
            Log.d("OneFragment::tonePageChangeCallBackObj",position.toString())
            //MediaPlayer.create(requireContext(), R.raw.navigation_forward_selection).start()
            if (position != 0) viewModel.setPage(position) //this is always page0 when resumed vp need to load
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
        val application = requireNotNull(this.activity).application
        stateDao = StateDatabase.getInstance(application).fragmentStateDao
        val viewModelFactory = OneViewModelFactory(
            stateDao
        )
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(OneViewModel::class.java)

        viewModel.pagePositionLiveData.observe(viewLifecycleOwner,
        Observer {

            if (it != _position){
                Log.d(LOG_TAG, "LiveData Observer pos from ${_position} to ${it}")
            binding.viewpager
                .setCurrentItem(it, false)
            }else{
                Log.d(LOG_TAG, "liveData observer ${it}")
            }
        })
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

//    override fun onResume() {
//        super.onResume()
//        val pos1 = viewModel.pagePositionLiveData.value
//        Log.d(LOG_TAG, "OnResume... ${pos1}")
//        if (pos1 > 0 ){
//            binding.viewpager
//                .setCurrentItem(pos1, false)
//        }
//    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(LOG_TAG, "OnSaveInstanceState ${viewModel.pagePositionLiveData.value}")
        outState.putInt(Bundle_key_Page, viewModel.pagePositionLiveData.value ?: 0 )
    }

    override fun onDestroy() {
        Log.d(LOG_TAG,"onDestroy... ${viewModel.pagePositionLiveData.value}")
        super.onDestroy()
    }



}