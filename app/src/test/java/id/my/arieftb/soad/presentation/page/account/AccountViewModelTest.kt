package id.my.arieftb.soad.presentation.page.account

import app.cash.turbine.test
import id.my.arieftb.soad.domain.auth.use_case.LogOutAuthUseCase
import id.my.arieftb.soad.domain.common.model.ResultEntity
import id.my.arieftb.soad.presentation.common.state.UIState
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

class AccountViewModelTest : BehaviorSpec({
    val logOutUseCase: LogOutAuthUseCase = mockk(relaxed = true)

    beforeEach {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    afterEach {
        Dispatchers.resetMain()
    }

    Given("nothing") {
        When("logOut") {
            And("${LogOutAuthUseCase::class.java.canonicalName} return result success true") {
                val value = ResultEntity.Success(true)
                coEvery {
                    logOutUseCase.invoke()
                } returns flow {
                    emit(value)
                }
                Then("value should be success true").config(coroutineTestScope = true) {
                    val viewModel = AccountViewModel(logOutUseCase)
                    viewModel.logOutState.test {
                        viewModel.logOut()
                        awaitItem().shouldBeInstanceOf<UIState.Idle>()
                        awaitItem().apply {
                            shouldBeInstanceOf<UIState.Loading>()
                            isLoading.shouldBeTrue()
                        }
                        awaitItem().apply {
                            shouldBeInstanceOf<UIState.Loading>()
                            isLoading.shouldBeFalse()
                        }
                        awaitItem().apply {
                            shouldBeInstanceOf<UIState.Success<Boolean>>()
                            data.shouldBeTrue()
                        }
                        cancelAndConsumeRemainingEvents().shouldBeEmpty()
                        cancel()
                    }
                }
            }

            And("${LogOutAuthUseCase::class.java.canonicalName} return result success false") {
                val value = ResultEntity.Success(false)
                coEvery {
                    logOutUseCase.invoke()
                } returns flow {
                    emit(value)
                }
                Then("value should be success false").config(coroutineTestScope = true) {
                    val viewModel = AccountViewModel(logOutUseCase)
                    viewModel.logOutState.test {
                        viewModel.logOut()
                        awaitItem().shouldBeInstanceOf<UIState.Idle>()
                        awaitItem().apply {
                            shouldBeInstanceOf<UIState.Loading>()
                            isLoading.shouldBeTrue()
                        }
                        awaitItem().apply {
                            shouldBeInstanceOf<UIState.Loading>()
                            isLoading.shouldBeFalse()
                        }
                        awaitItem().apply {
                            shouldBeInstanceOf<UIState.Success<Boolean>>()
                            data.shouldBeFalse()
                        }
                        cancelAndConsumeRemainingEvents().shouldBeEmpty()
                        cancel()
                    }
                }
            }

            And("${LogOutAuthUseCase::class.java.canonicalName} return result failure") {
                val value = ResultEntity.Failure("failure")
                coEvery {
                    logOutUseCase.invoke()
                } returns flow {
                    emit(value)
                }
                Then("value should be success false").config(coroutineTestScope = true) {
                    val viewModel = AccountViewModel(logOutUseCase)
                    viewModel.logOutState.test {
                        viewModel.logOut()
                        awaitItem().shouldBeInstanceOf<UIState.Idle>()
                        awaitItem().apply {
                            shouldBeInstanceOf<UIState.Loading>()
                            isLoading.shouldBeTrue()
                        }
                        awaitItem().apply {
                            shouldBeInstanceOf<UIState.Loading>()
                            isLoading.shouldBeFalse()
                        }
                        awaitItem().apply {
                            shouldBeInstanceOf<UIState.Success<Boolean>>()
                            data.shouldBeFalse()
                        }
                        cancelAndConsumeRemainingEvents().shouldBeEmpty()
                        cancel()
                    }
                }
            }

            And("${LogOutAuthUseCase::class.java.canonicalName} return result error") {
                val value = ResultEntity.Error(RuntimeException("failure"))
                coEvery {
                    logOutUseCase.invoke()
                } returns flow {
                    emit(value)
                }
                Then("value should be success false").config(coroutineTestScope = true) {
                    val viewModel = AccountViewModel(logOutUseCase)
                    viewModel.logOutState.test {
                        viewModel.logOut()
                        awaitItem().shouldBeInstanceOf<UIState.Idle>()
                        awaitItem().apply {
                            shouldBeInstanceOf<UIState.Loading>()
                            isLoading.shouldBeTrue()
                        }
                        awaitItem().apply {
                            shouldBeInstanceOf<UIState.Loading>()
                            isLoading.shouldBeFalse()
                        }
                        awaitItem().apply {
                            shouldBeInstanceOf<UIState.Success<Boolean>>()
                            data.shouldBeFalse()
                        }
                        cancelAndConsumeRemainingEvents().shouldBeEmpty()
                        cancel()
                    }
                }
            }

            And("${LogOutAuthUseCase::class.java.canonicalName} return exception") {
                val value = RuntimeException("failure")
                coEvery {
                    logOutUseCase.invoke()
                } returns flow {
                    throw value
                }
                Then("value should be success false").config(coroutineTestScope = true) {
                    val viewModel = AccountViewModel(logOutUseCase)
                    viewModel.logOutState.test {
                        viewModel.logOut()
                        awaitItem().shouldBeInstanceOf<UIState.Idle>()
                        awaitItem().apply {
                            shouldBeInstanceOf<UIState.Loading>()
                            isLoading.shouldBeTrue()
                        }
                        awaitItem().apply {
                            shouldBeInstanceOf<UIState.Loading>()
                            isLoading.shouldBeFalse()
                        }
                        awaitItem().apply {
                            shouldBeInstanceOf<UIState.Success<Boolean>>()
                            data.shouldBeFalse()
                        }
                        cancelAndConsumeRemainingEvents().shouldBeEmpty()
                        cancel()
                    }
                }
            }
        }
    }
})
