package com.bashirli.buyme.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.bashirli.buyme.R
import com.bashirli.buyme.adapter.TabAdapter
import com.bashirli.buyme.databinding.FragmentMainBinding
import com.bashirli.buyme.repo.apirepo.DownloadRepo
import com.google.android.material.tabs.TabLayout
import javax.inject.Inject

class MainFragment : Fragment() {

    private lateinit var binding:FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        setupBar()
        onClick()
    }

    private fun onClick(){
        binding.search.setOnClickListener {
            var action = MainFragmentDirections.actionMainFragmentToSearchFragment()
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
                .navigate(action)
        }
        binding.myLocation.setOnClickListener {
        var action = MainFragmentDirections.actionMainFragmentToLocationsFragment()
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
                .navigate(action)

        }
    }

    private fun setupBar(){
        var adapter= TabAdapter(this)
        binding.viewPager2.adapter=adapter
        binding.tabLayout.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewPager2.setCurrentItem(tab!!.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
        binding.viewPager2.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.getTabAt(position)!!.select()
            }
        })
    }
    }

