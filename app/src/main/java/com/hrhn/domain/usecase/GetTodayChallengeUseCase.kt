package com.hrhn.domain.usecase

import com.hrhn.domain.model.Challenge
import com.hrhn.domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class GetTodayChallengeUseCase @Inject constructor(
    private val repository: ChallengeRepository
) {
    operator fun invoke(): Flow<Challenge?> {
        return repository.getChallengesWithPeriod(
            LocalDate.now().atStartOfDay(),
            LocalDate.now().plusDays(1).atStartOfDay()
        ).map {
            if (it.isNotEmpty()) it.getOrNull(0) else null
        }
    }
}