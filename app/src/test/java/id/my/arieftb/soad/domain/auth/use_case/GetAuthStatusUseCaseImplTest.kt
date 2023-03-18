package id.my.arieftb.soad.domain.auth.use_case

import id.my.arieftb.soad.domain.common.model.ResultEntity
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow

class GetAuthStatusUseCaseImplTest : BehaviorSpec({
    val getAuthUseCase: GetAuthUseCase = mockk(relaxed = true)

    Given("nothing") {
        When("invoke") {
            And("${getAuthUseCase::class.java.canonicalName} return result success not empty") {
                val value = ResultEntity.Success("12121")
                coEvery {
                    getAuthUseCase.invoke()
                } returns flow {
                    emit(value)
                }

                Then("value should be success true").config(coroutineTestScope = true) {
                    val useCase = GetAuthStatusUseCaseImpl(
                        Dispatchers.Unconfined,
                        getAuthUseCase,
                    )

                    useCase.invoke().collect {
                        it.shouldBeInstanceOf<ResultEntity.Success<Boolean>>()
                        it.data.shouldBeTrue()
                    }
                }
            }

            And("${getAuthUseCase::class.java.canonicalName} return result success empty") {
                val value = ResultEntity.Success("")
                coEvery {
                    getAuthUseCase.invoke()
                } returns flow {
                    emit(value)
                }

                Then("value should be success false").config(coroutineTestScope = true) {
                    val useCase = GetAuthStatusUseCaseImpl(
                        Dispatchers.Unconfined,
                        getAuthUseCase,
                    )

                    useCase.invoke().collect {
                        it.shouldBeInstanceOf<ResultEntity.Success<Boolean>>()
                        it.data.shouldBeFalse()
                    }
                }
            }

            And("${getAuthUseCase::class.java.canonicalName} return result failure") {
                val value = ResultEntity.Failure("failure")
                coEvery {
                    getAuthUseCase.invoke()
                } returns flow {
                    emit(value)
                }

                Then("value should be success false").config(coroutineTestScope = true) {
                    val useCase = GetAuthStatusUseCaseImpl(
                        Dispatchers.Unconfined,
                        getAuthUseCase,
                    )

                    useCase.invoke().collect {
                        it.shouldBeInstanceOf<ResultEntity.Success<Boolean>>()
                        it.data.shouldBeFalse()
                    }
                }
            }

            And("${getAuthUseCase::class.java.canonicalName} return result error") {
                val value = ResultEntity.Error(RuntimeException("error"))
                coEvery {
                    getAuthUseCase.invoke()
                } returns flow {
                    emit(value)
                }

                Then("value should be success false").config(coroutineTestScope = true) {
                    val useCase = GetAuthStatusUseCaseImpl(
                        Dispatchers.Unconfined,
                        getAuthUseCase,
                    )

                    useCase.invoke().collect {
                        it.shouldBeInstanceOf<ResultEntity.Success<Boolean>>()
                        it.data.shouldBeFalse()
                    }
                }
            }

            And("${getAuthUseCase::class.java.canonicalName} return result exception") {
                val value = RuntimeException("error")
                coEvery {
                    getAuthUseCase.invoke()
                } returns flow {
                    throw value
                }

                Then("value should be success false").config(coroutineTestScope = true) {
                    val useCase = GetAuthStatusUseCaseImpl(
                        Dispatchers.Unconfined,
                        getAuthUseCase,
                    )

                    useCase.invoke().collect {
                        it.shouldBeInstanceOf<ResultEntity.Success<Boolean>>()
                        it.data.shouldBeFalse()
                    }
                }
            }

            And("${getAuthUseCase::class.java.canonicalName} throw exception") {
                val value = RuntimeException("error")
                coEvery {
                    getAuthUseCase.invoke()
                } throws value

                Then("value should be success false").config(coroutineTestScope = true) {
                    val useCase = GetAuthStatusUseCaseImpl(
                        Dispatchers.Unconfined,
                        getAuthUseCase,
                    )

                    useCase.invoke().collect {
                        it.shouldBeInstanceOf<ResultEntity.Success<Boolean>>()
                        it.data.shouldBeFalse()
                    }
                }
            }
        }
    }
})
