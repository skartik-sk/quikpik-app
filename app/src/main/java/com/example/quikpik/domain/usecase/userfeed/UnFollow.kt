package com.example.quikpik.domain.usecase.feed

import com.example.quikpik.domain.repo.UserFeedRepo
import javax.inject.Inject

class UnFollow @Inject constructor(
    private val userFeedRepository: UserFeedRepo
) {
    operator fun invoke(query: String) = userFeedRepository.unfollow(query)
}