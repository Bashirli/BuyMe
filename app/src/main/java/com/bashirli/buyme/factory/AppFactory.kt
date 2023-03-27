package com.bashirli.buyme.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bashirli.buyme.repo.apirepo.DownloadRepo
import com.bashirli.buyme.view.fragment.MainFragment
import com.bashirli.buyme.view.fragment.SearchFragment
import javax.inject.Inject

class AppFactory @Inject constructor(
     var repo: DownloadRepo
): FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            MainFragment::class.java.name->MainFragment()
            else->return super.instantiate(classLoader, className)
        }
    }
}