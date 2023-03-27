package com.bashirli.buyme.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bashirli.buyme.repo.apirepo.DownloadRepo
import com.bashirli.buyme.view.fragment.EquipmentsFragment
import com.bashirli.buyme.view.fragment.OtherFragment
import javax.inject.Inject

class TabAdapter (
    fragment: Fragment
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
    return 2
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
           0-> return EquipmentsFragment()
            1-> return OtherFragment()
            else -> return EquipmentsFragment()
        }
    }
}