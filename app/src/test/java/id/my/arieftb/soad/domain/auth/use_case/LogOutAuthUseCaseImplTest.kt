package id.my.arieftb.soad.domain.auth.use_case

import id.my.arieftb.soad.domain.auth.repository.AuthRepository
import id.my.arieftb.soad.domain.common.model.ResultEntity
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow

class LogOutAuthUseCaseImplTest : BehaviorSpec({
    val repository: AuthRepository = mockk(relaxed = true)

    Given("nothing") {
        When("invoke") {
            And("repository return result success true") {
                val value = ResultEntity.Success(true)
                coEvery {
                    repository.delete()
                } returns flow {
                    emit(value)
                }
                Then("value should be success true") {
                    val useCase = LogOutAuthUseCaseImpl(Dispatchers.Unconfined, repository)
                    useCase.invoke().collect {
                        it.shouldBeInstanceOf<ResultEntity.Success<Boolean>>()
                        it.data.shouldBeTrue()
                    }
                }
            }

            And("repository return result success false") {
                val value = ResultEntity.Success(false)
                coEvery {
                    repository.delete()
                } returns flow {
                    emit(value)
                }
                Then("value should be success false") {
                    val useCase = LogOutAuthUseCaseImpl(Dispatchers.Unconfined, repository)
                    useCase.invoke().collect {
                        it.shouldBeInstanceOf<ResultEntity.Success<Boolean>>()
                        it.data.shouldBeFalse()
                    }
                }
            }

            And("repository return result failure") {
                val value = ResultEntity.Failure("failure")
                coEvery {
                    repository.delete()
                } returns flow {
                    emit(value)
                }
                Then("value should be success false") {
                    val useCase = LogOutAuthUseCaseImpl(Dispatchers.Unconfined, repository)
                    useCase.invoke().collect {
                        it.shouldBeInstanceOf<ResultEntity.Success<Boolean>>()
                        it.data.shouldBeFalse()
                    }
                }
            }

            And("repository return result error") {
                val value = ResultEntity.Error(RuntimeException("error"))
                coEvery {
                    repository.delete()
                } returns flow {
                    emit(value)
                }
                Then("value should be success false") {
                    val useCase = LogOutAuthUseCaseImpl(Dispatchers.Unconfined, repository)
                    useCase.invoke().collect {
                        it.shouldBeInstanceOf<ResultEntity.Success<Boolean>>()
                        it.data.shouldBeFalse()
                    }
                }
            }

            And("repository return exception") {
                val value = RuntimeException("error")
                coEvery {
                    repository.delete()
                } returns flow {
                    throw value
                }
                Then("value should be success false") {
                    val useCase = LogOutAuthUseCaseImpl(Dispatchers.Unconfined, repository)
                    useCase.invoke().collect {
                        it.shouldBeInstanceOf<ResultEntity.Success<Boolean>>()
                        it.data.shouldBeFalse()
                    }
                }
            }

            And("repository thrown exception") {
                val value = RuntimeException("error")
                coEvery {
                    repository.delete()
                } throws value
                Then("value should be success false") {
                    val useCase = LogOutAuthUseCaseImpl(Dispatchers.Unconfined, repository)
                    useCase.invoke().collect {
                        it.shouldBeInstanceOf<ResultEntity.Success<Boolean>>()
                        it.data.shouldBeFalse()
                    }
                }
            }
        }
    }
})
