package id.my.arieftb.soad.presentation.page.splash

import app.cash.turbine.test
import id.my.arieftb.soad.domain.auth.use_case.GetAuthStatusUseCase
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

class SplashScreenViewModelTest : BehaviorSpec({
    val getLogInStatus: GetAuthStatusUseCase = mockk(relaxed = true)

    beforeEach {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    afterEach {
        Dispatchers.resetMain()
    }

    Given("nothing") {
        When("fetch login status") {
            And("${GetAuthStatusUseCase::class.java.canonicalName} return result success true") {
                val value = ResultEntity.Success(true)
                coEvery {
                    getLogInStatus.invoke()
                } returns flow {
                    emit(value)
                }

                Then("_loginStatusState should be success true").config(coroutineTestScope = true) {
                    val viewModel = SplashScreenViewModel(getLogInStatus)

                    viewModel.loginStatusState.test {
                        viewModel.fetchLoginStatus()
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

            And("${GetAuthStatusUseCase::class.java.canonicalName} return result success false") {
                val value = ResultEntity.Success(false)
                coEvery {
                    getLogInStatus.invoke()
                } returns flow {
                    emit(value)
                }

                Then("_loginStatusState should be success false").config(coroutineTestScope = true) {
                    val viewModel = SplashScreenViewModel(getLogInStatus)

                    viewModel.loginStatusState.test {
                        viewModel.fetchLoginStatus()
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

            And("${GetAuthStatusUseCase::class.java.canonicalName} return result failure") {
                val value = ResultEntity.Failure("failure")
                coEvery {
                    getLogInStatus.invoke()
                } returns flow {
                    emit(value)
                }

                Then("_loginStatusState should be success false").config(coroutineTestScope = true) {
                    val viewModel = SplashScreenViewModel(getLogInStatus)

                    viewModel.loginStatusState.test {
                        viewModel.fetchLoginStatus()
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

            And("${GetAuthStatusUseCase::class.java.canonicalName} return result error") {
                val value = ResultEntity.Error(RuntimeException("error"))
                coEvery {
                    getLogInStatus.invoke()
                } returns flow {
                    emit(value)
                }

                Then("_loginStatusState should be success false").config(coroutineTestScope = true) {
                    val viewModel = SplashScreenViewModel(getLogInStatus)

                    viewModel.loginStatusState.test {
                        viewModel.fetchLoginStatus()
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

            And("${GetAuthStatusUseCase::class.java.canonicalName} return exception") {
                val value = RuntimeException("error")
                coEvery {
                    getLogInStatus.invoke()
                } returns flow {
                    throw value
                }

                Then("_loginStatusState should be success false").config(coroutineTestScope = true) {
                    val viewModel = SplashScreenViewModel(getLogInStatus)

                    viewModel.loginStatusState.test {
                        viewModel.fetchLoginStatus()
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
