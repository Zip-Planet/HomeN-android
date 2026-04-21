package com.devndev.homen.core.domain.usecase.home

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.home.CreateHome
import com.devndev.homen.core.domain.model.home.HomeResponseDomainModel
import com.devndev.homen.core.domain.repository.HomeRepository

class CreateHomeUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(createHome: CreateHome): ApiResult<HomeResponseDomainModel> {
        return homeRepository.createHome(createHome)
    }
}