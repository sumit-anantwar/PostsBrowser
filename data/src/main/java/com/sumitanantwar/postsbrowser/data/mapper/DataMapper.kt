package com.sumitanantwar.postsbrowser.data.mapper

interface DataMapper<D, E> {
    fun mapFromData(data: D) : E
    fun mapToData(entity: E) : D
}