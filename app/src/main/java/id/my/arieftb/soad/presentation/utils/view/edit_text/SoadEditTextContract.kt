package id.my.arieftb.soad.presentation.utils.view.edit_text

class SoadEditTextContract {
    interface View {
        var limit: Int
        var isValid: Boolean
        var isRequired: Boolean
        var label: String
        var type: Int
        var listener: Listener?

        fun init()
        fun validate(text: String)
        fun isValidEmptiness(text: String): Boolean
        fun isValidLimit(text: String): Boolean
        fun isValidEmail(text: String) : Boolean
    }

    interface Listener {
        fun onTextInvalid(message: String)
        fun onTextValid()
    }
}