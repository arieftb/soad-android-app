package id.my.arieftb.soad.presentation.utils.view.edit_text

import android.content.Context
import android.graphics.Canvas
import android.text.InputType
import android.util.AttributeSet
import android.util.Patterns
import android.view.View
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputEditText
import id.my.arieftb.soad.R

class SoadEditText : TextInputEditText, SoadEditTextContract.View {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }

    override var max: Int = 0
    override var min: Int = 0
    override var isValid: Boolean =
        isValidEmptiness(text.toString()) && isValidMin(text.toString()) && isValidMax(text.toString()) && isValidEmail(
            text.toString()
        )


    override var isRequired: Boolean = false
    override var label: String = context.getString(R.string.label_field_default)
    override var type: Int = InputType.TYPE_CLASS_TEXT
    override var listener: SoadEditTextContract.Listener? = null

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    override fun init() {
        type = inputType
        setOnFocusChangeListener { view, isFocus ->
            validate((view as SoadEditText).text.toString())
            if (isFocus) {
                doAfterTextChanged {
                    validate(it.toString())
                }
            }
        }
    }

    override fun validate(text: String) {
        if (isValidEmptiness(text) && isValidMin(text) && isValidMax(text) && isValidEmail(text)) {
            isValid = true
            listener?.onTextValid()
        }
    }

    override fun isValidEmptiness(text: String): Boolean {
        if (isRequired && text.isEmpty()) {
            listener?.onTextInvalid(
                resources.getString(
                    R.string.error_message_field_cannot_be_empty,
                    label
                )
            )
            return false
        }

        return true
    }

    override fun isValidMax(text: String): Boolean {
        if (max > 0 && text.length > max) {
            listener?.onTextInvalid(
                resources.getString(
                    R.string.error_message_field_cannot_be_more_than_limit,
                    label, max
                )
            )
            return false
        }

        return true
    }

    override fun isValidMin(text: String): Boolean {
        if (min > 0 && text.length < min) {
            listener?.onTextInvalid(
                resources.getString(
                    R.string.error_message_field_cannot_be_less_than_limit,
                    label, min
                )
            )
            return false
        }

        return true
    }


    override fun isValidEmail(text: String): Boolean {
        if ((type == InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS && !Patterns.EMAIL_ADDRESS.matcher(
                text
            ).matches())
        ) {
            listener?.onTextInvalid(
                resources.getString(
                    R.string.error_message_field_email_is_in_invalid_format
                )
            )

            return false
        }

        return true
    }
}