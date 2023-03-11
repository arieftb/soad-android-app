package id.my.arieftb.soad.domain.common.utils.format_validation

class FormatValidator {
    companion object {
        fun isEmail(emailAddress: String?): Boolean {
            if (emailAddress.isNullOrEmpty()) {
                return false
            }
            return Regex("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})\$").matches(emailAddress)
        }
    }
}
