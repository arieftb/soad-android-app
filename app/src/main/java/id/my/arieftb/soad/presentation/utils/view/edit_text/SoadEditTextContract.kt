package id.my.arieftb.soad.presentation.utils.view.edit_text

class SoadEditTextContract {
    interface View {
        var max: Int
        var min: Int
        var isValid: Boolean
        var isRequired: Boolean
        var label: String
        var type: Int
        var listener: Listener?

        fun init()
        fun validate(text: String)
        fun isValidEmptiness(text: String): Boolean
        fun isValidMax(text: String): Boolean
        fun isValidMin(text: String): Boolean
        fun isValidEmail(text: String) : Boolean
    }

    interface Listener {
        fun onTextInvalid(message: String)
        fun onTextValid()
    }
}