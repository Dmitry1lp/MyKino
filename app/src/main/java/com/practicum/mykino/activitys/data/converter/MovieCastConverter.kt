package com.practicum.mykino.activitys.data.converter

import com.practicum.mykino.activitys.data.cast.ActorShort
import com.practicum.mykino.activitys.data.cast.CastShortItem
import com.practicum.mykino.activitys.data.cast.DirectorsShort
import com.practicum.mykino.activitys.data.cast.FullCastResponse
import com.practicum.mykino.activitys.data.cast.OtherShort
import com.practicum.mykino.activitys.data.cast.WritersShort
import com.practicum.mykino.activitys.domain.models.MovieCast
import com.practicum.mykino.activitys.domain.models.MovieCastPerson

class MovieCastConverter {

    fun convert(response: FullCastResponse): MovieCast {
        return with(response) {
            MovieCast(
                imdbId = this.imDbId,
                fullTitle = this.fullTitle,
                directors = convertDirectors(this.directors),
                others = convertOthers(this.others),
                writers = convertWriters(this.writers),
                actors = convertActors(this.actors)
            )
        }
    }

    private fun convertDirectors(directorsResponse: DirectorsShort): List<MovieCastPerson> {
        return directorsResponse.items.map { it.toMovieCastPerson() }
    }

    private fun convertOthers(othersResponses: List<OtherShort>): List<MovieCastPerson> {
        return othersResponses.flatMap { otherResponse ->
            otherResponse.items.map { it.toMovieCastPerson(jobPrefix = otherResponse.job) }
        }
    }

    private fun convertWriters(writersResponse: WritersShort): List<MovieCastPerson> {
        return writersResponse.items.map { it.toMovieCastPerson() }
    }

    private fun convertActors(actorsResponses: List<ActorShort>): List<MovieCastPerson> {
        return actorsResponses.map { actor ->
            MovieCastPerson(
                id = actor.id,
                name = actor.name,
                description = actor.asCharacter,
                image = actor.image,
            )
        }
    }

    private fun CastShortItem.toMovieCastPerson(jobPrefix: String = ""): MovieCastPerson {
        return MovieCastPerson(
            id = this.id,
            name = this.name,
            description = if (jobPrefix.isEmpty()) this.description else "$jobPrefix -- ${this.description}",
            image = null,
        )
    }

}