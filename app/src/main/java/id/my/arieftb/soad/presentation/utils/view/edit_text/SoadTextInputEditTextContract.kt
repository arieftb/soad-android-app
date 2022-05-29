package id.my.arieftb.soad.presentation.utils.view.edit_text

class SoadTextInputEditTextContract {
    interface View {
        var max: Int
        var min: Int
        var isValid: Boolean
        var isRequired: Boolean
        var type: Int
        var editText: SoadEditText

        fun init()
    }
}