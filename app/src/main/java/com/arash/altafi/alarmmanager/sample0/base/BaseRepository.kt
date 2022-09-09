package com.arash.altafi.alarmmanager.sample0.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

abstract class BaseRepository {

    fun <T> callApi(networkCall: suspend () -> Response<T>) = flow {
        val response = networkCall()
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun <T> callDatabase(databaseCall: suspend () -> T) = flow {
        val response = databaseCall()
        emit(response)
    }.flowOn(Dispatchers.IO)
}