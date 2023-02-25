package id.my.arieftb.soad.domain.common.use_case

import id.my.arieftb.soad.domain.common.model.ResultEntity

/**
 * Use case
 *
 * This interface is for abstract the use case interface
 *
 * @param P is for Parameter data type
 * @param R is for Result data type
 * @constructor Create empty Use case
 */
interface UseCase<P, R> {
    fun build(param: P): ResultEntity<R>
}
