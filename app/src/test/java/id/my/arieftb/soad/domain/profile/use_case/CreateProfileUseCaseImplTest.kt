package id.my.arieftb.soad.domain.profile.use_case

import id.my.arieftb.soad.domain.common.exception.EmailAttributeMissingException
import id.my.arieftb.soad.domain.common.exception.EmailWrongFormatException
import id.my.arieftb.soad.domain.common.exception.NameAttributeMissingException
import id.my.arieftb.soad.domain.common.exception.PasswordSmallerThanException
import id.my.arieftb.soad.domain.common.model.ResultEntity
import id.my.arieftb.soad.domain.common.utils.format_validation.FormatValidator
import id.my.arieftb.soad.domain.profile.repository.ProfileRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow

class CreateProfileUseCaseImplTest : BehaviorSpec({
    val repository: ProfileRepository = mockk(relaxed = true)
    val useCase = CreateProfileUseCaseImpl(Dispatchers.Unconfined, repository)

    Given("name: User, email: user@mail.com, password: Qwerty12@") {
        val name = "User"
        val email = "user@mail.com"
        val password = "Qwerty12@"

        When("execute the use case") {
            mockkObject(FormatValidator.Companion)
            every {
                FormatValidator.isEmail(any())
            } returns true

            and("repo result is success true") {
                coEvery {
                    repository.create(any(), any(), any())
                } returns
                        flow {
                            emit(ResultEntity.Success(true))
                        }

                Then("value should be success true").config(true) {
                    useCase.execute(name, email, password).collect {
                        it.shouldBeInstanceOf<ResultEntity.Success<Boolean>>()
                        it.data.shouldBeTrue()
                    }
                }
            }

            and("repo result is success false") {
                coEvery {
                    repository.create(any(), any(), any())
                } returns
                        flow {
                            emit(ResultEntity.Success(false))
                        }

                Then("value should be success false").config(true) {
                    useCase.execute(name, email, password).collect {
                        it.shouldBeInstanceOf<ResultEntity.Success<Boolean>>()
                        it.data.shouldBeFalse()
                    }
                }
            }

            and("repo result is failure") {
                coEvery {
                    repository.create(any(), any(), any())
                } returns
                        flow {
                            emit(ResultEntity.Failure("failure"))
                        }

                Then("value should be success false").config(true) {
                    useCase.execute(name, email, password).collect {
                        it.shouldBeInstanceOf<ResultEntity.Success<Boolean>>()
                        it.data.shouldBeFalse()
                    }
                }
            }

            and("repo result is error") {
                coEvery {
                    repository.create(any(), any(), any())
                } returns
                        flow {
                            emit(ResultEntity.Error(RuntimeException("error")))
                        }

                Then("value should be success false").config(true) {
                    useCase.execute(name, email, password).collect {
                        it.shouldBeInstanceOf<ResultEntity.Success<Boolean>>()
                        it.data.shouldBeFalse()
                    }
                }
            }

            and("repo result is exception") {
                coEvery {
                    repository.create(any(), any(), any())
                } returns
                        flow {
                            throw RuntimeException("Error")
                        }

                Then("value should be success false").config(true) {
                    useCase.execute(name, email, password).collect {
                        it.shouldBeInstanceOf<ResultEntity.Success<Boolean>>()
                        it.data.shouldBeFalse()
                    }
                }
            }

            and("repo thrown exception") {
                coEvery {
                    repository.create(any(), any(), any())
                } throws RuntimeException("Error")

                Then("value should be success false").config(true) {
                    useCase.execute(name, email, password).collect {
                        it.shouldBeInstanceOf<ResultEntity.Success<Boolean>>()
                        it.data.shouldBeFalse()
                    }
                }
            }
        }
    }

    Given("name: empty, email: user@mail.com, password: Qwerty12@") {
        val name = ""
        val email = "user@mail.com"
        val password = "Qwerty12@"

        When("execute the use case") {
            Then("value should be error name missing").config(true) {
                useCase.execute(name, email, password).collect {
                    it.shouldBeInstanceOf<ResultEntity.Error>()
                    it.exception.shouldBeInstanceOf<NameAttributeMissingException>()
                }
            }
        }
    }

    Given("name: user, email: empty, password: Qwerty12@") {
        val name = "user"
        val email = ""
        val password = "Qwerty12@"

        When("execute the use case") {
            Then("value should be error email missing").config(true) {
                useCase.execute(name, email, password).collect {
                    it.shouldBeInstanceOf<ResultEntity.Error>()
                    it.exception.shouldBeInstanceOf<EmailAttributeMissingException>()
                }
            }
        }
    }

    Given("name: user, email: not valid format, password: Qwerty12@") {
        val name = "user"
        val email = "mail"
        val password = "Qwerty12@"

        When("execute the use case") {
            mockkObject(FormatValidator.Companion)
            every {
                FormatValidator.isEmail(any())
            } returns false

            Then("value should be error email wrong format").config(true) {
                useCase.execute(name, email, password).collect {
                    it.shouldBeInstanceOf<ResultEntity.Error>()
                    it.exception.shouldBeInstanceOf<EmailWrongFormatException>()
                }
            }
        }
    }

    Given("name: user, email: user@mail.com, password: smaller than 8") {
        val name = "user"
        val email = "user@mail.com"
        val password = "Qwerty1"

        When("execute the use case") {
            mockkObject(FormatValidator.Companion)
            every {
                FormatValidator.isEmail(any())
            } returns true

            Then("value should be error password smaller than").config(true) {
                useCase.execute(name, email, password).collect {
                    it.shouldBeInstanceOf<ResultEntity.Error>()
                    it.exception.shouldBeInstanceOf<PasswordSmallerThanException>()
                }
            }
        }
    }
})
