package id.my.arieftb.soad.presentation.page.register

import app.cash.turbine.test
import id.my.arieftb.soad.domain.common.exception.EmailAttributeMissingException
import id.my.arieftb.soad.domain.common.exception.NameAttributeMissingException
import id.my.arieftb.soad.domain.common.exception.PasswordSmallerThanException
import id.my.arieftb.soad.domain.common.model.ResultEntity
import id.my.arieftb.soad.domain.profile.use_case.CreateProfileUseCase
import id.my.arieftb.soad.presentation.common.state.UIState
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterViewModelTest : BehaviorSpec({
    val registerUseCase: CreateProfileUseCase = mockk(relaxed = true)

    beforeEach {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    afterEach {
        Dispatchers.resetMain()
    }

    Given("name: User, email: user@mail.com, password: Qwerty12@") {
        val name = "User"
        val email = "user@mail.com"
        val password = "Qwerty12@"

        When("submit registration") {
            And("registerUseCase result success true") {
                coEvery {
                    registerUseCase.execute(any(), any(), any())
                } returns flow {
                    emit(ResultEntity.Success(true))
                }

                Then("_registerState should be on success true").config(coroutineTestScope = true) {
                    val viewModel = RegisterViewModel(registerUseCase)

                    viewModel.registerState.test {
                        viewModel.submitRegistration(name, email, password)
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

            And("registerUseCase result success false") {
                coEvery {
                    registerUseCase.execute(any(), any(), any())
                } returns flow {
                    emit(ResultEntity.Success(false))
                }

                Then("_registerState should be on success false").config(coroutineTestScope = true) {
                    val viewModel = RegisterViewModel(registerUseCase)
                    viewModel.registerState.test {
                        viewModel.submitRegistration(name, email, password)

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
                    }
                }
            }

            And("registerUseCase result failure") {
                val value = "something went wrong"
                coEvery {
                    registerUseCase.execute(any(), any(), any())
                } returns flow {
                    emit(ResultEntity.Failure(value))
                }

                Then("_registerState should be on error").config(coroutineTestScope = true) {
                    val viewModel = RegisterViewModel(registerUseCase)
                    viewModel.registerState.test {
                        viewModel.submitRegistration(name, email, password)

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
                            message.shouldBeEqualComparingTo(value)
                        }
                        cancelAndConsumeRemainingEvents().shouldBeEmpty()
                    }
                }
            }

            And("registerUseCase result error") {
                val value = RuntimeException("something went wrong")
                coEvery {
                    registerUseCase.execute(any(), any(), any())
                } returns flow {
                    emit(ResultEntity.Error(value))
                }

                Then("_registerState should be on error").config(coroutineTestScope = true) {
                    val viewModel = RegisterViewModel(registerUseCase)
                    viewModel.registerState.test {
                        viewModel.submitRegistration(name, email, password)

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
                            exception.shouldBeSameInstanceAs(value)
                        }
                        cancelAndConsumeRemainingEvents().shouldBeEmpty()
                    }
                }
            }

            And("registerUseCase return exception") {
                val value = RuntimeException("something went wrong")
                coEvery {
                    registerUseCase.execute(any(), any(), any())
                } returns flow {
                    throw value
                }

                Then("_registerState should be on error").config(coroutineTestScope = true) {
                    val viewModel = RegisterViewModel(registerUseCase)
                    viewModel.registerState.test {
                        viewModel.submitRegistration(name, email, password)

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
                    }
                }
            }
        }
    }

    Given("name: User, email: user@mail.com, password smaller than 8") {
        val name = "User"
        val email = "user@mail.com"
        val password = "Qwerty"

        When("submit registration") {
            Then("_registerState should be on error PasswordSmallerThanException").config(
                coroutineTestScope = true
            ) {
                val viewModel = RegisterViewModel(registerUseCase)

                viewModel.registerState.test {
                    viewModel.submitRegistration(name, email, password)

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
                }
            }
        }
    }

    Given("name: User, email: user@mail.com, password empty") {
        val name = "User"
        val email = "user@mail.com"
        val password = ""

        When("submit registration") {
            Then("_registerState should be on error PasswordSmallerThanException").config(
                coroutineTestScope = true
            ) {
                val viewModel = RegisterViewModel(registerUseCase)

                viewModel.registerState.test {
                    viewModel.submitRegistration(name, email, password)

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
                }
            }
        }
    }

    Given("name: User, email: user@mail.com, password null") {
        val name = "User"
        val email = "user@mail.com"
        val password = null

        When("submit registration") {
            Then("_registerState should be on error PasswordSmallerThanException").config(
                coroutineTestScope = true
            ) {
                val viewModel = RegisterViewModel(registerUseCase)

                viewModel.registerState.test {
                    viewModel.submitRegistration(name, email, password)

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
                }
            }
        }
    }

    Given("name: User, email empty, password: Qwerty12@") {
        val name = "User"
        val email = ""
        val password = "Qwerty12@"

        When("submit registration") {
            Then("_registerState should be on error EmailAttributeMissingException").config(
                coroutineTestScope = true
            ) {
                val viewModel = RegisterViewModel(registerUseCase)

                viewModel.registerState.test {
                    viewModel.submitRegistration(name, email, password)

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
                }
            }
        }
    }

    Given("name: User, email null, password: Qwerty12@") {
        val name = "User"
        val email = null
        val password = "Qwerty12@"

        When("submit registration") {
            Then("_registerState should be on error EmailAttributeMissingException").config(
                coroutineTestScope = true
            ) {
                val viewModel = RegisterViewModel(registerUseCase)

                viewModel.registerState.test {
                    viewModel.submitRegistration(name, email, password)

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
                }
            }
        }
    }

    Given("name empty, email: user@mail.com, password: Qwerty12@") {
        val name = ""
        val email = "user@mail.com"
        val password = "Qwerty12@"

        When("submit registration") {
            Then("_registerState should be on error NameAttributeMissingException").config(
                coroutineTestScope = true
            ) {
                val viewModel = RegisterViewModel(registerUseCase)

                viewModel.registerState.test {
                    viewModel.submitRegistration(name, email, password)

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
                        exception.shouldBeInstanceOf<NameAttributeMissingException>()
                    }
                    cancelAndConsumeRemainingEvents().shouldBeEmpty()
                }
            }
        }
    }

    Given("name null, email: user@mail.com, password: Qwerty12@") {
        val name = null
        val email = "user@mail.com"
        val password = "Qwerty12@"

        When("submit registration") {
            Then("_registerState should be on error NameAttributeMissingException").config(
                coroutineTestScope = true
            ) {
                val viewModel = RegisterViewModel(registerUseCase)

                viewModel.registerState.test {
                    viewModel.submitRegistration(name, email, password)

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
                        exception.shouldBeInstanceOf<NameAttributeMissingException>()
                    }
                    cancelAndConsumeRemainingEvents().shouldBeEmpty()
                }
            }
        }
    }
})
