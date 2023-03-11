package id.my.arieftb.soad.domain.auth.use_case

import id.my.arieftb.soad.domain.auth.repository.AuthRepository
import id.my.arieftb.soad.domain.common.exception.EmailAttributeMissingException
import id.my.arieftb.soad.domain.common.exception.EmailWrongFormatException
import id.my.arieftb.soad.domain.common.exception.PasswordSmallerThanException
import id.my.arieftb.soad.domain.common.model.ResultEntity
import id.my.arieftb.soad.domain.common.utils.format_validation.FormatValidator
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow

class LogInAuthUseCaseImplTest : BehaviorSpec({
    val repository: AuthRepository = mockk(relaxed = true)

    Given("email: user@mail.com, password: Qwerty12@") {
        val email = "user@mail.com"
        val password = "Qwerty12@"

        When("invoke the use case") {
            And("repository result is success true") {
                val value = true
                coEvery {
                    repository.logIn(any(), any())
                } returns flow {
                    emit(ResultEntity.Success(value))
                }

                Then("value should be success true").config(coroutineTestScope = true) {
                    val useCase = LogInAuthUseCaseImpl(Dispatchers.Unconfined, repository)
                    useCase.invoke(email, password).collect {
                        it.shouldBeInstanceOf<ResultEntity.Success<Boolean>>()
                        it.data.shouldBeEqualComparingTo(value)
                    }
                }
            }

            And("repository result is success false") {
                val value = false
                coEvery {
                    repository.logIn(any(), any())
                } returns flow {
                    emit(ResultEntity.Success(value))
                }

                Then("value should be success true").config(coroutineTestScope = true) {
                    val useCase = LogInAuthUseCaseImpl(Dispatchers.Unconfined, repository)
                    useCase.invoke(email, password).collect {
                        it.shouldBeInstanceOf<ResultEntity.Success<Boolean>>()
                        it.data.shouldBeEqualComparingTo(value)
                    }
                }
            }

            And("repository result is failure") {
                val value = "failure"
                coEvery {
                    repository.logIn(any(), any())
                } returns flow {
                    emit(ResultEntity.Failure(value))
                }

                Then("value should be failure").config(coroutineTestScope = true) {
                    val useCase = LogInAuthUseCaseImpl(Dispatchers.Unconfined, repository)
                    useCase.invoke(email, password).collect {
                        it.shouldBeInstanceOf<ResultEntity.Failure>()
                        it.message.shouldBeEqualComparingTo(value)
                    }
                }
            }

            And("repository result is error") {
                val value = RuntimeException("error")
                coEvery {
                    repository.logIn(any(), any())
                } returns flow {
                    emit(ResultEntity.Error(value))
                }

                Then("value should be error").config(coroutineTestScope = true) {
                    val useCase = LogInAuthUseCaseImpl(Dispatchers.Unconfined, repository)
                    useCase.invoke(email, password).collect {
                        it.shouldBeInstanceOf<ResultEntity.Error>()
                        it.exception.shouldBeSameInstanceAs(value)
                    }
                }
            }

            And("repository result thrown exception") {
                val value = RuntimeException("error")
                coEvery {
                    repository.logIn(any(), any())
                } returns flow {
                    throw value
                }

                Then("value should be error").config(coroutineTestScope = true) {
                    val useCase = LogInAuthUseCaseImpl(Dispatchers.Unconfined, repository)
                    useCase.invoke(email, password).collect {
                        it.shouldBeInstanceOf<ResultEntity.Error>()
                        it.exception.shouldBeInstanceOf<Exception>()
                    }
                }
            }

            And("repository thrown exception") {
                val value = RuntimeException("error")
                coEvery {
                    repository.logIn(any(), any())
                } throws value

                Then("value should be error").config(coroutineTestScope = true) {
                    val useCase = LogInAuthUseCaseImpl(Dispatchers.Unconfined, repository)
                    useCase.invoke(email, password).collect {
                        it.shouldBeInstanceOf<ResultEntity.Error>()
                        it.exception.shouldBeSameInstanceAs(value)
                    }
                }
            }
        }
    }

    Given("email: user@mail.com, password smaller than 8") {
        val email = "user@mail.com"
        val password = "qwerty"

        When("invoke the use case") {
            Then("value should be error of PasswordSmallerThanException").config(coroutineTestScope = true) {
                val useCase = LogInAuthUseCaseImpl(Dispatchers.Unconfined, repository)
                useCase.invoke(email, password).collect {
                    it.shouldBeInstanceOf<ResultEntity.Error>()
                    it.exception.shouldBeInstanceOf<PasswordSmallerThanException>()
                }
            }
        }
    }

    Given("email: user@mail.com, password is empty") {
        val email = "user@mail.com"
        val password = ""

        When("invoke the use case") {
            Then("value should be error of PasswordSmallerThanException").config(coroutineTestScope = true) {
                val useCase = LogInAuthUseCaseImpl(Dispatchers.Unconfined, repository)
                useCase.invoke(email, password).collect {
                    it.shouldBeInstanceOf<ResultEntity.Error>()
                    it.exception.shouldBeInstanceOf<PasswordSmallerThanException>()
                }
            }
        }
    }

    Given("email is not valid format, password: Qwerty12@") {
        val email = "user-mail.com"
        val password = "Qwerty12@"

        When("invoke the use case") {
            mockkObject(FormatValidator)
            every {
                FormatValidator.isEmail(any())
            } returns false

            Then("value should be error of EmailWrongFormatException").config(coroutineTestScope = true) {
                val useCase = LogInAuthUseCaseImpl(Dispatchers.Unconfined, repository)
                useCase.invoke(email, password).collect {
                    it.shouldBeInstanceOf<ResultEntity.Error>()
                    it.exception.shouldBeInstanceOf<EmailWrongFormatException>()
                }
            }
        }
    }

    Given("email is empty, password: Qwerty12@") {
        val email = ""
        val password = "Qwerty12@"

        When("invoke the use case") {
            mockkObject(FormatValidator)
            every {
                FormatValidator.isEmail(any())
            } returns false

            Then("value should be error of EmailAttributeMissingException").config(coroutineTestScope = true) {
                val useCase = LogInAuthUseCaseImpl(Dispatchers.Unconfined, repository)
                useCase.invoke(email, password).collect {
                    it.shouldBeInstanceOf<ResultEntity.Error>()
                    it.exception.shouldBeInstanceOf<EmailAttributeMissingException>()
                }
            }
        }
    }
})
