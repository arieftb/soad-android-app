package id.my.arieftb.soad.presentation.page.home

import id.my.arieftb.soad.databinding.FragmentHomeBinding
import id.my.arieftb.soad.presentation.base.page.BaseFragmentImpl

class HomeFragment : BaseFragmentImpl<FragmentHomeBinding>() {
    override fun initViewBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }


    companion object {
        fun navigate(): HomeFragment {
            return HomeFragment()
        }
    }
}
