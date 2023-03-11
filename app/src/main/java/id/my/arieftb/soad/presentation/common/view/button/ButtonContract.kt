package id.my.arieftb.soad.presentation.common.view.button

import android.util.AttributeSet

interface ButtonContract {
    interface View {
        fun initAttribute(attrs: AttributeSet?)
        fun showProgress()
        fun hideProgress()
    }
}
