package com.sumitanantwar.postsbrowser.network.mapper

interface ModelMapper<in M, out D> {
    fun mapFromModel(model: M) : D
}