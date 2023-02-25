package id.my.arieftb.soad.domain.common.model

/**
 * Result entity
 *
 * This sealed class is used for wrapped the result of the use case execution, no logic here
 *
 * @param M is the model object of real data result
 * @constructor Create empty Result entity
 */
sealed class ResultEntity<out M> private constructor() {
    class Success<out D : Any>(val data: D) :
        ResultEntity<D>()

    class Failure(val message: String) : ResultEntity<Nothing>()
    class Error(val exception: Exception) : ResultEntity<Nothing>()
}
