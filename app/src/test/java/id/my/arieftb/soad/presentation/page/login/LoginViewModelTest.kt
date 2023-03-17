package id.my.arieftb.soad.presentation.page.login

import app.cash.turbine.test
import id.my.arieftb.soad.domain.auth.use_case.LogInAuthUseCase
import id.my.arieftb.soad.domain.common.exception.EmailAttributeMissingException
import id.my.arieftb.soad.domain.common.exception.EmailWrongFormatException
import id.my.arieftb.soad.domain.common.exception.PasswordSmallerThanException
import id.my.arieftb.soad.domain.common.model.ResultEntity
import id.my.arieftb.soad.domain.common.utils.format_validation.FormatValidator
import id.my.arieftb.soad.presentation.common.state.UIState
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

class LoginViewModelTest : BehaviorSpec({
    val logInUseCase: LogInAuthUseCase = mockk(relaxed = true)

    beforeEach {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    afterEach {
        Dispatchers.resetMain()
    }

    Given("email: user@mail.com, password: qwerty12@") {
        val email = "user@mail.com"
        val password = "qwerty12@"

        When("submit login") {
            And("logInUseCase result success true") {
                coEvery {
                    logInUseCase.invoke(any(), any())
                } returns flow {
                    emit(ResultEntity.Success(true))
                }

                Then("_loginState should be on success").config(coroutineTestScope = true) {
                    val viewModel = LoginViewModel(logInUseCase)

                    viewModel.logInState.test {
                        viewModel.submitLogin(email, password)
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

            And("logInUseCase result success false") {
                coEvery {
                    logInUseCase.invoke(any(), any())
                } returns flow {
                    emit(ResultEntity.Success(false))
                }

                Then("_loginState should be on success").config(coroutineTestScope = true) {
                    val viewModel = LoginViewModel(logInUseCase)

                    viewModel.logInState.test {
                        viewModel.submitLogin(email, password)
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

            And("logInUseCase result failure") {
                val value = ResultEntity.Failure("failure")
                coEvery {
                    logInUseCase.invoke(any(), any())
                } returns flow {
                    emit(value)
                }

                Then("_loginState should be on failure").config(coroutineTestScope = true) {
                    val viewModel = LoginViewModel(logInUseCase)

                    viewModel.logInState.test {
                        viewModel.submitLogin(email, password)
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
                            shouldBeInstanceOf<UIState.Failure>()
                            message.shouldBeEqualComparingTo(value.message)
                        }
                        cancelAndConsumeRemainingEvents().shouldBeEmpty()
                        cancel()
                    }
                }
            }

            And("logInUseCase result error") {
                val value = ResultEntity.Error(RuntimeException("error"))
                coEvery {
                    logInUseCase.invoke(any(), any())
                } returns flow {
                    emit(value)
                }

                Then("_loginState should be on error").config(coroutineTestScope = true) {
                    val viewModel = LoginViewModel(logInUseCase)

                    viewModel.logInState.test {
                        viewModel.submitLogin(email, password)
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
                            shouldBeInstanceOf<UIState.Error>()
                            exception.shouldBeSameInstanceAs(value.exception)
                        }
                        cancelAndConsumeRemainingEvents().shouldBeEmpty()
                        cancel()
                    }
                }
            }

            And("logInUseCase result exception") {
                val value = RuntimeException("error")
                coEvery {
                    logInUseCase.invoke(any(), any())
                } returns flow {
                    throw value
                }

                Then("_loginState should be on error").config(coroutineTestScope = true) {
                    val viewModel = LoginViewModel(logInUseCase)

                    viewModel.logInState.test {
                        viewModel.submitLogin(email, password)
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
                            shouldBeInstanceOf<UIState.Error>()
                            exception.shouldBeInstanceOf<Exception>()
                        }
                        cancelAndConsumeRemainingEvents().shouldBeEmpty()
                        cancel()
                    }
                }
            }
        }
    }

    Given("email: user@mail.com, password smaller than 8") {
        val email = "user@mail.com"
        val password = "qwerty"

        When("submit login") {
            Then("_login state must be error PasswordSmallerThanException").config(
                coroutineTestScope = true
            ) {
                val viewModel = LoginViewModel(logInUseCase)

                viewModel.logInState.test {
                    viewModel.submitLogin(email, password)
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
                        shouldBeInstanceOf<UIState.Error>()
                        exception.shouldBeInstanceOf<PasswordSmallerThanException>()
                    }
                    cancelAndConsumeRemainingEvents().shouldBeEmpty()
                    cancel()
                }
            }
        }
    }

    Given("email: user@mail.com, password empty") {
        val email = "user@mail.com"
        val password = ""

        When("submit login") {
            Then("_login state must be error PasswordSmallerThanException").config(
                coroutineTestScope = true
            ) {
                val viewModel = LoginViewModel(logInUseCase)

                viewModel.logInState.test {
                    viewModel.submitLogin(email, password)
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
                        shouldBeInstanceOf<UIState.Error>()
                        exception.shouldBeInstanceOf<PasswordSmallerThanException>()
                    }
                    cancelAndConsumeRemainingEvents().shouldBeEmpty()
                    cancel()
                }
            }
        }
    }

    Given("email: user@mail.com, password null") {
        val email = "user@mail.com"
        val password = null

        When("submit login") {
            Then("_login state must be error PasswordSmallerThanException").config(
                coroutineTestScope = true
            ) {
                val viewModel = LoginViewModel(logInUseCase)

                viewModel.logInState.test {
                    viewModel.submitLogin(email, password)
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
                        shouldBeInstanceOf<UIState.Error>()
                        exception.shouldBeInstanceOf<PasswordSmallerThanException>()
                    }
                    cancelAndConsumeRemainingEvents().shouldBeEmpty()
                    cancel()
                }
            }
        }
    }

    Given("email not right format, password: qwerty12@") {
        val email = "usermail.com"
        val password = "qwerty12@"

        When("submit login") {
            mockkObject(FormatValidator)
            every {
                FormatValidator.isEmail(any())
            } returns false

            Then("_login state must be error EmailWrongFormatException").config(
                coroutineTestScope = true
            ) {
                val viewModel = LoginViewModel(logInUseCase)

                viewModel.logInState.test {
                    viewModel.submitLogin(email, password)
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
                        shouldBeInstanceOf<UIState.Error>()
                        exception.shouldBeInstanceOf<EmailWrongFormatException>()
                    }
                    cancelAndConsumeRemainingEvents().shouldBeEmpty()
                    cancel()
                }
            }
        }
    }

    Given("email empty, password: qwerty12@") {
        val email = ""
        val password = "qwerty12@"

        When("submit login") {
            Then("_login state must be error EmailAttributeMissingException").config(
                coroutineTestScope = true
            ) {
                val viewModel = LoginViewModel(logInUseCase)

                viewModel.logInState.test {
                    viewModel.submitLogin(email, password)
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
                        shouldBeInstanceOf<UIState.Error>()
                        exception.shouldBeInstanceOf<EmailAttributeMissingException>()
                    }
                    cancelAndConsumeRemainingEvents().shouldBeEmpty()
                    cancel()
                }
            }
        }
    }

    Given("email null, password: qwerty12@") {
        val email = null
        val password = "qwerty12@"

        When("submit login") {
            Then("_login state must be error EmailAttributeMissingException").config(
                coroutineTestScope = true
            ) {
                val viewModel = LoginViewModel(logInUseCase)

                viewModel.logInState.test {
                    viewModel.submitLogin(email, password)
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
                        shouldBeInstanceOf<UIState.Error>()
                        exception.shouldBeInstanceOf<EmailAttributeMissingException>()
                    }
                    cancelAndConsumeRemainingEvents().shouldBeEmpty()
                    cancel()
                }
            }
        }
    }
})
