package id.my.arieftb.soad.presentation.page.account

import id.my.arieftb.soad.databinding.FragmentAccountBinding
import id.my.arieftb.soad.presentation.base.page.BaseFragmentImpl

class AccountFragment : BaseFragmentImpl<FragmentAccountBinding>() {
    override fun initViewBinding(): FragmentAccountBinding {
        return FragmentAccountBinding.inflate(layoutInflater)
    }

    companion object {
        fun navigate(): AccountFragment {
            return AccountFragment()
        }
    }
}
