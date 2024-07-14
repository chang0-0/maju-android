package com.app.majuapp.domain.usecase

import com.app.majuapp.domain.repository.ReissueRepository
import javax.inject.Inject

class ReissueUsecase @Inject constructor(private val reissueRepository: ReissueRepository) {

    suspend fun reissue() {
        reissueRepository.reissue()
    }

}