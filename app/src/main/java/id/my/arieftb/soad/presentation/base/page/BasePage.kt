package id.my.arieftb.soad.presentation.base.page

import androidx.viewbinding.ViewBinding

interface BasePage<VB: ViewBinding> {
    var binding: VB?

    fun initViewBinding(): VB

    fun detachBinding() {
        binding = null
    }
}