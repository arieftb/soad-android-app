package id.my.arieftb.soad.data.profile.repository

import id.my.arieftb.soad.data.common.exception.HTTPException
import id.my.arieftb.soad.data.profile.model.create.ProfileCreateRemoteResponse
import id.my.arieftb.soad.data.profile.source.remote.ProfileRemoteDataSource
import id.my.arieftb.soad.domain.common.model.ResultEntity
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.string.shouldBeEqualIgnoringCase
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow

class ProfileRepositoryImplTest : BehaviorSpec({
    val remote: ProfileRemoteDataSource = mockk(relaxed = true)
    val repository = ProfileRepositoryImpl(remote)

    Given("name: User, email: user@mail.com, password: Qwerty12@") {
        val name = "User"
        val email = "user@mail.com"
        val password = "Qwerty12@"

        When("create profile") {
            And("remote return error false") {
                val response = ProfileCreateRemoteResponse(
                    error = false,
                    message = "success"
                )
                coEvery {
                    remote.create(any())
                } returns flow {
                    emit(response)
                }

                Then("value should be result success true").config(true) {
                    repository.create(name, email, password).collect {
                        it.shouldBeInstanceOf<ResultEntity.Success<Boolean>>()
                        it.data.shouldBeTrue()
                    }
                }
            }

            And("remote return error true") {
                val response = ProfileCreateRemoteResponse(
                    error = true,
                    message = "error"
                )
                coEvery {
                    remote.create(any())
                } returns flow {
                    emit(response)
                }

                Then("value should be result failure with message").config(true) {
                    repository.create(name, email, password).collect {
                        it.shouldBeInstanceOf<ResultEntity.Failure>()
                        it.message.shouldBeEqualIgnoringCase(response.message)
                    }
                }
            }

            And("remote return http exception") {
                val response = ProfileCreateRemoteResponse(
                    error = true,
                    message = "error"
                )
                coEvery {
                    remote.create(any())
                } returns flow {
                    throw HTTPException(500, response.message)
                }

                Then("value should be result failure with message").config(true) {
                    repository.create(name, email, password).collect {
                        it.shouldBeInstanceOf<ResultEntity.Failure>()
                        it.message.shouldBeEqualIgnoringCase(response.message)
                    }
                }
            }

            And("remote return exception") {
                coEvery {
                    remote.create(any())
                } returns flow {
                    throw RuntimeException("error")
                }

                Then("value should be result error").config(true) {
                    repository.create(name, email, password).collect {
                        it.shouldBeInstanceOf<ResultEntity.Error>()
                    }
                }
            }

            And("remote thrown exception") {
                coEvery {
                    remote.create(any())
                } throws RuntimeException("error")

                Then("value should be result error").config(true) {
                    repository.create(name, email, password).collect {
                        it.shouldBeInstanceOf<ResultEntity.Error>()
                    }
                }
            }
        }
    }
})
