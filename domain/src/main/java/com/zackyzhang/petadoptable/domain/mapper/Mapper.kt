package com.zackyzhang.petadoptable.domain.mapper

/**
 * Interface for model mappers. It provides helper methods that facilitate
 * retrieving of models from outer layers
 *
 * @param <D> the view model input type
 * @param <V> the domain model output type
 */
interface Mapper<out V, in P, in S> {

    fun mapToPetDetail(pet: P, shelter: S): V

}
