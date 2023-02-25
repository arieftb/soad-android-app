package id.my.arieftb.soad.domain.story.use_case

import id.my.arieftb.soad.domain.common.exception.AuthenticationMissingException
import id.my.arieftb.soad.domain.common.model.ResultEntity
import id.my.arieftb.soad.domain.story.model.Story
import id.my.arieftb.soad.domain.story.model.request.StoryCollectionFetch
import id.my.arieftb.soad.domain.story.repository.StoryRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow

class GetStoryCollectionUseCaseImplTest : BehaviorSpec({
    val repository: StoryRepository = mockk(relaxed = true)
    val useCase = GetStoryCollectionUseCaseImpl(Dispatchers.Unconfined, repository)

    given("right parameter") {
        val param = StoryCollectionFetch(
            page = 1,
            size = 5,
            accessToken = "1291928192898"
        )

        `when`("execute the use case") {

            and("repo result is success with empty list") {
                coEvery { repository.fetchCollection(any()) } returns flow {
                    emit(ResultEntity.Success(mutableListOf()))
                }
                Then("value should be success with empty list").config(true) {
                    useCase.execute(param).collect {
                        it.shouldBeInstanceOf<ResultEntity.Success<List<Story>>>()
                        it.data.shouldBeEmpty()
                    }
                }
            }
            and("repo result is success with not empty list") {
                coEvery { repository.fetchCollection(any()) } returns flow {
                    emit(ResultEntity.Success(mutableListOf<Story>().also {
                        it.add(
                            Story(
                                id = "1",
                                senderName = "Reviewer",
                                photo = "https://images.google.com",
                                date = "2023-22-12",
                                description = "Photo"
                            )
                        )
                    }))
                }
                Then("value should be success with empty list").config(true) {
                    useCase.execute(param).collect {
                        it.shouldBeInstanceOf<ResultEntity.Success<List<Story>>>()
                        it.data.shouldNotBeEmpty()
                    }
                }
            }
            and("repo result is failure") {
                coEvery { repository.fetchCollection(any()) } returns flow {
                    emit(ResultEntity.Failure("something went wrong right now."))
                }
                Then("value should be failure").config(true) {
                    useCase.execute(param).collect {
                        it.shouldBeInstanceOf<ResultEntity.Failure>()
                    }
                }
            }
            and("repo result is error") {
                coEvery { repository.fetchCollection(any()) } returns flow {
                    emit(ResultEntity.Error(RuntimeException()))
                }
                Then("value should be error").config(true) {
                    useCase.execute(param).collect {
                        it.shouldBeInstanceOf<ResultEntity.Error>()
                    }
                }
            }
            and("repo result is exception") {
                coEvery { repository.fetchCollection(any()) } returns flow {
                    throw RuntimeException()
                }
                Then("value should be error").config(true) {
                    useCase.execute(param).collect {
                        it.shouldBeInstanceOf<ResultEntity.Failure>()
                    }
                }
            }
            and("repo result is thrown exception") {
                coEvery { repository.fetchCollection(any()) } throws RuntimeException()
                Then("value should be error").config(true) {
                    useCase.execute(param).collect {
                        it.shouldBeInstanceOf<ResultEntity.Error>()
                    }
                }
            }
        }
    }

    given("no access token") {
        val param = StoryCollectionFetch(
            page = 1,
            size = 5,
        )

        `when`("execute the use case") {
            then("value should be error with authentication missing exception") {
                useCase.execute(param).collect {
                    it.shouldBeInstanceOf<ResultEntity.Error>()
                    it.exception.shouldBeInstanceOf<AuthenticationMissingException>()
                }
            }
        }
    }
})
