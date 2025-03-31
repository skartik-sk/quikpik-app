package com.example.quikpik.domain.usecase.feed

import com.example.quikpik.domain.repo.UserFeedRepo
import javax.inject.Inject

class GetFollowers @Inject constructor(
    private val userFeedRepository: UserFeedRepo
) {
    operator fun invoke() = userFeedRepository.getFollowers()
}