package id.my.arieftb.soad.domain.auth.use_case

import id.my.arieftb.soad.domain.auth.repository.AuthRepository
import id.my.arieftb.soad.domain.common.model.ResultEntity
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.string.shouldBeEqualIgnoringCase
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow

class GetAuthUseCaseImplTest : BehaviorSpec({
    val repository: AuthRepository = mockk(relaxed = true)

    Given("nothing") {
        When("invoke") {
            And("repository return result success") {
                val value = ResultEntity.Success("12345")
                coEvery {
                    repository.fetch()
                } returns
                        flow {
                            emit(value)
                        }

                Then("value should be success") {
                    val useCase = GetAuthUseCaseImpl(Dispatchers.Unconfined, repository)

                    useCase.invoke().collect {
                        it.shouldBeInstanceOf<ResultEntity.Success<String>>()
                        it.data.shouldBeEqualIgnoringCase(value.data)
                    }
                }
            }

            And("repository return result failure") {
                val value = ResultEntity.Failure("failure")
                coEvery {
                    repository.fetch()
                } returns
                        flow {
                            emit(value)
                        }

                Then("value should be failure") {
                    val useCase = GetAuthUseCaseImpl(Dispatchers.Unconfined, repository)

                    useCase.invoke().collect {
                        it.shouldBeInstanceOf<ResultEntity.Failure>()
                        it.message.shouldBeEqualIgnoringCase(value.message)
                    }
                }
            }

            And("repository return result error") {
                val value = ResultEntity.Error(RuntimeException("error"))
                coEvery {
                    repository.fetch()
                } returns
                        flow {
                            emit(value)
                        }

                Then("value should be error") {
                    val useCase = GetAuthUseCaseImpl(Dispatchers.Unconfined, repository)

                    useCase.invoke().collect {
                        it.shouldBeInstanceOf<ResultEntity.Error>()
                    }
                }
            }

            And("repository return result exception") {
                val value = RuntimeException("error")
                coEvery {
                    repository.fetch()
                } returns
                        flow {
                            throw value
                        }

                Then("value should be error") {
                    val useCase = GetAuthUseCaseImpl(Dispatchers.Unconfined, repository)

                    useCase.invoke().collect {
                        it.shouldBeInstanceOf<ResultEntity.Error>()
                    }
                }
            }

            And("repository return exception") {
                val value = RuntimeException("error")
                coEvery {
                    repository.fetch()
                } throws value

                Then("value should be error") {
                    val useCase = GetAuthUseCaseImpl(Dispatchers.Unconfined, repository)

                    useCase.invoke().collect {
                        it.shouldBeInstanceOf<ResultEntity.Error>()
                    }
                }
            }
        }
    }
})
