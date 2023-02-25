package id.my.arieftb.soad.domain.common.use_case

import id.my.arieftb.soad.domain.common.model.ResultEntity
import kotlinx.coroutines.flow.Flow

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
    fun execute(param: P): Flow<ResultEntity<R>>
}
