package id.my.arieftb.soad.presentation.common.view.button

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec
import com.google.android.material.progressindicator.IndeterminateDrawable
import id.my.arieftb.soad.R
import com.google.android.material.R as Material

class Button : MaterialButton, ButtonContract.View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttribute(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initAttribute(attrs)
    }

    private var _text: String = ""

    private val indicator = CircularProgressIndicatorSpec(
        context, null, 0,
        Material.style.Widget_Material3_CircularProgressIndicator_ExtraSmall
    )
    private val progressIndicator = IndeterminateDrawable.createCircularDrawable(context, indicator)

    override fun initAttribute(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.Button)
        _text =
            resources.getText(
                typedArray.getResourceId(
                    R.styleable.Button_android_text,
                    R.string.app_name
                )
            )
                .toString()
        text = _text
    }

    override fun showProgress() {
        icon = progressIndicator
        text = ""
    }

    override fun hideProgress() {
        icon = null
        text = _text
    }
}
