package id.my.arieftb.soad.domain.common.utils.format_validation

class FormatValidator {
    companion object {
        fun isEmail(emailAddress: String?): Boolean {
            if (emailAddress.isNullOrEmpty()) {
                return false
            }
            return Regex("^[A-Za-z](.*)(@)(.+)(\\\\.)(.+)").matches(emailAddress)
        }
    }
}
