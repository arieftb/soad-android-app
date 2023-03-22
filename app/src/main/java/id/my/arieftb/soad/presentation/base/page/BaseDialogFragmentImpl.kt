package id.my.arieftb.soad.presentation.base.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import id.my.arieftb.soad.R

abstract class BaseDialogFragmentImpl<VB : ViewBinding> : DialogFragment(), BaseFragment<VB> {
    override var binding: VB? = null

    abstract override fun initViewBinding(): VB


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.Theme_SOAD_Dialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = initViewBinding()
        return binding?.root
    }

    override fun onDestroyView() {
        detachBinding()
        super.onDestroyView()
    }

    override fun pushFragment(fragment: Fragment, frameId: Int) {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.replace(frameId, fragment).commit()
    }
}
