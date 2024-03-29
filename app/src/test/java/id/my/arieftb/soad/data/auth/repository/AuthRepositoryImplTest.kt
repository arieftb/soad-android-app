package id.my.arieftb.soad.data.auth.repository

import id.my.arieftb.soad.data.auth.model.log_in.AuthLogInRemoteResponse
import id.my.arieftb.soad.data.auth.model.log_in.LoginResult
import id.my.arieftb.soad.data.auth.source.local.AuthLocalSource
import id.my.arieftb.soad.data.auth.source.remote.AuthRemoteSource
import id.my.arieftb.soad.data.common.exception.HTTPException
import id.my.arieftb.soad.domain.common.model.ResultEntity
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class AuthRepositoryImplTest : BehaviorSpec({
    val remote: AuthRemoteSource = mockk(relaxed = true)
    val local: AuthLocalSource = mockk(relaxed = true)

    Given("email: user@mail.com, password: Qwerty12@") {
        val email = "user@mail.com"
        val password = "Qwerty12@"

        When("log in") {
            And("remote return error false") {
                val value =
                    AuthLogInRemoteResponse(false, LoginResult("user", "1234", "1235"), "success")
                coEvery {
                    remote.logIn(any())
                } returns flow {
                    emit(value)
                }

                Then("value should be result success true").config(coroutineTestScope = true) {
                    val repository = AuthRepositoryImpl(remote, local)
                    repository.logIn(email, password).collect {
                        it.shouldBeInstanceOf<ResultEntity.Success<Boolean>>()
                        it.data.shouldBeTrue()
                    }
                }
            }

            And("remote return error true") {
                val value =
                    AuthLogInRemoteResponse(true, null, "error")
                coEvery {
                    remote.logIn(any())
                } returns flow {
                    emit(value)
                }

                Then("value should be result failure").config(coroutineTestScope = true) {
                    val repository = AuthRepositoryImpl(remote, local)
                    repository.logIn(email, password).collect {
                        it.shouldBeInstanceOf<ResultEntity.Failure>()
                        it.message.shouldBeEqualComparingTo(value.message)
                    }
                }
            }

            And("remote return http exception") {
                val value =
                    AuthLogInRemoteResponse(true, null, "error")
                coEvery {
                    remote.logIn(any())
                } returns flow {
                    throw HTTPException(500, value.message)
                }

                Then("value should be result failure").config(coroutineTestScope = true) {
                    val repository = AuthRepositoryImpl(remote, local)
                    repository.logIn(email, password).collect {
                        it.shouldBeInstanceOf<ResultEntity.Failure>()
                        it.message.shouldBeEqualComparingTo(value.message)
                    }
                }
            }

            And("remote return exception beside HTTP Exception") {
                val value =
                    RuntimeException("error")
                coEvery {
                    remote.logIn(any())
                } returns flow {
                    throw value
                }

                Then("value should be catch").config(coroutineTestScope = true) {
                    val repository = AuthRepositoryImpl(remote, local)
                    repository.logIn(email, password).catch {
                        it.shouldBeSameInstanceAs(value)
                    }.collect()
                }
            }

            And("remote thrown exception") {
                val value =
                    RuntimeException("error")
                coEvery {
                    remote.logIn(any())
                } throws value

                Then("value should be error").config(coroutineTestScope = true) {
                    val repository = AuthRepositoryImpl(remote, local)
                    repository.logIn(email, password).collect {
                        it.shouldBeInstanceOf<ResultEntity.Error>()
                        it.exception.shouldBeSameInstanceAs(value)
                    }
                }
            }
        }
    }

    Given("nothing") {
        When("fetch") {
            And("local return not empty") {
                val value = "0291029"
                coEvery {
                    local.fetch()
                } returns
                        flow {
                            emit(value)
                        }

                Then("value should be success not empty") {
                    val repository = AuthRepositoryImpl(remote, local)
                    repository.fetch().collect {
                        it.shouldBeInstanceOf<ResultEntity.Success<String>>()
                        it.data.shouldBeEqualComparingTo(value)
                    }
                }
            }

            And("local return empty") {
                val value = ""
                coEvery {
                    local.fetch()
                } returns
                        flow {
                            emit(value)
                        }

                Then("value should be success empty") {
                    val repository = AuthRepositoryImpl(remote, local)
                    repository.fetch().collect {
                        it.shouldBeInstanceOf<ResultEntity.Success<String>>()
                        it.data.shouldBeEqualComparingTo(value)
                    }
                }
            }

            And("local thrown exception") {
                val value = RuntimeException("error")
                coEvery {
                    local.fetch()
                } throws value

                Then("value should be error") {
                    val repository = AuthRepositoryImpl(remote, local)
                    repository.fetch().collect {
                        it.shouldBeInstanceOf<ResultEntity.Error>()
                        it.exception.shouldBeSameInstanceAs(value)
                    }
                }
            }
        }

        When("delete") {
            And("local return true") {
                val value = true
                coEvery {
                    local.delete()
                } returns flow {
                    emit(value)
                }

                Then("value should be true") {
                    val repository = AuthRepositoryImpl(remote, local)
                    repository.delete().collect {
                        it.shouldBeInstanceOf<ResultEntity.Success<Boolean>>()
                        it.data.shouldBeTrue()
                    }
                }
            }

            And("local return false") {
                val value = false
                coEvery {
                    local.delete()
                } returns flow {
                    emit(value)
                }

                Then("value should be false") {
                    val repository = AuthRepositoryImpl(remote, local)
                    repository.delete().collect {
                        it.shouldBeInstanceOf<ResultEntity.Success<Boolean>>()
                        it.data.shouldBeFalse()
                    }
                }
            }

            And("local thrown exception") {
                val value = RuntimeException("error")
                coEvery {
                    local.delete()
                } throws value

                Then("value should be false") {
                    val repository = AuthRepositoryImpl(remote, local)
                    repository.delete().collect {
                        it.shouldBeInstanceOf<ResultEntity.Success<Boolean>>()
                        it.data.shouldBeFalse()
                    }
                }
            }
        }
    }
})
