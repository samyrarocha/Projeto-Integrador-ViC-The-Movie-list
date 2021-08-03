package com.example.projeto_integrador.common.data.api.models.mappers

import com.example.projeto_integrador.common.data.api.models.ApiCast
import com.example.projeto_integrador.common.data.api.models.ApiCredits
import com.example.projeto_integrador.common.data.api.models.ApiCrew
import com.example.projeto_integrador.common.domain.model.movies.details.credits.Credits
import com.example.projeto_integrador.common.domain.model.movies.details.credits.cast.Cast
import com.example.projeto_integrador.common.domain.model.movies.details.credits.crew.Crew



class ApiCreditsMapper constructor(
    private val apiCastMapper: ApiCastMapper,
    private val apiCrewMapper: ApiCrewMapper
    ): ApiMapper<ApiCredits, Credits> {

    override fun mapToDomain(apiEntity: ApiCredits): Credits {
        return Credits(
            creditsMovieId = apiEntity.creditMovieMovieId,
            creditCast = apiCastMapper.mapToDomain(apiEntity.creditCast),
            creditCrew = apiCrewMapper.mapToDomain(apiEntity.creditCrew)
        )
    }
}

class ApiCastMapper: ApiMapper<List<ApiCast>, List<Cast>> {

    override fun mapToDomain(apiEntity: List<ApiCast>): List<Cast> {
        val castResultsList: MutableList<Cast> = mutableListOf()
        for (item in apiEntity) {
            val cast = Cast(
            castId = item.castId,
            castCharacter = item.castCharacter.orEmpty(),
            castName = item.castName.orEmpty(),
            castPosterPath = item.castPosterPath.orEmpty()
            )
            castResultsList.add(cast)
        }
        return castResultsList
    }
}

class ApiCrewMapper: ApiMapper<List<ApiCrew>, List<Crew>> {

    override fun mapToDomain(apiEntity: List<ApiCrew>): List<Crew> {
        val crewResultList: MutableList<Crew> = mutableListOf()
        for (item in apiEntity) {
            val crew = Crew(
                crewId = item.crewId,
                crewName = item.crewName,
                crewJob = item.crewJob,
                crewPosterPath = item.crewPosterPath
            )
            crewResultList.add(crew)
        }
        return crewResultList
    }
}


