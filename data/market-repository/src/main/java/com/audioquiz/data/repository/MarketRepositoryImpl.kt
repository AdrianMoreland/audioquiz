@file:Suppress("MagicNumber", "TooGenericExceptionCaught", "MaxLineLength")

package com.audioquiz.data.repository

import android.util.Log
import com.audioquiz.data.mapper.toChart
import com.audioquiz.data.mapper.toDetail
import com.audioquiz.data.mapper.toMarket
import com.audioquiz.data.mapper.toMarketEntity
import com.audioquiz.data.mapper.toRemoteMarketDto
import com.audioquiz.domain.model.Chart
import com.audioquiz.domain.model.Market
import com.audioquiz.domain.model.MarketDetail
import com.audioquiz.domain.repository.MarketRepository
import com.audioquiz.localdatasource.database.MarketDao
import com.audioquiz.network.Errors
import com.audioquiz.network.Resource
import com.audioquiz.network.mapMessageStatusCode
import com.audioquiz.network.onError
import com.audioquiz.network.onException
import com.audioquiz.network.statusCode
import com.audioquiz.network.suspendMap
import com.audioquiz.network.suspendOnError
import com.audioquiz.network.suspendOnException
import com.audioquiz.network.suspendOnSuccess
import com.audioquiz.remotedatasource.api.MarketsApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MarketRepositoryImpl @Inject constructor(
    private val api: MarketsApi,
    private val dao: MarketDao,
) : MarketRepository {

    override fun getMarketList(): Flow<List<Market>> =
        dao.getMarketList().map { list -> list.map { it.toMarket() } }

    override fun getFavoriteMarketList(): Flow<List<Market>> =
        dao.getFavoriteMarketList().map { list -> list.map { it.toMarket() } }

    override suspend fun syncMarketList() {
        api.getMarkets(
            "usd",
            "market_cap_desc",
            20,
            1,
            false,
        ).suspendOnSuccess {
            val toRemoteMarketDto = data.map {
                it.toRemoteMarketDto()
            }
            dao.upsertMarket(toRemoteMarketDto)
        }.onError {
            Log.d("debug", message)
        }.onException {
            Log.d("debug", message.toString())
        }
    }

    override suspend fun toggleFavoriteMarket(oldMarket: Market) {
        val news = oldMarket.copy(isFavorite = !oldMarket.isFavorite).toMarketEntity()
        dao.insertMarket(news)
    }

    override fun fetchChart(id: String): Flow<Resource<Chart, Errors>> = flow {
        val chart = api.getMarketChart(id, "usd", 1)
        chart.suspendOnSuccess {
            suspendMap {
                emit(Resource.Success(it.data.toChart()))
            }
        }.suspendOnError {
            suspendMap {
                emit(
                    Resource.Error(
                        error = Errors.ApiError(
                            it.statusCode.mapMessageStatusCode(),
                            it.statusCode.code,
                        ),
                    ),
                )
            }
        }.suspendOnException {
            suspendMap {
                emit(Resource.Error(Errors.ExceptionError(it.message, throwable)))
            }
        }
    }

    override fun fetchDetail(id: String): Flow<Resource<MarketDetail, Errors>> = flow {
        val detail = api.getMarketDetail(id)
        detail.suspendOnSuccess {
            suspendMap {
                emit(Resource.Success(data.toDetail()))
            }
        }.suspendOnError {
            suspendMap {
                emit(
                    Resource.Error(
                        Errors.ApiError(
                            it.statusCode.mapMessageStatusCode(),
                            it.statusCode.code,
                        ),
                    ),
                )
            }
        }.suspendOnException {
            suspendMap {
                emit(
                    Resource.Error(Errors.ExceptionError(it.message, it.throwable)),
                )
            }
        }
    }
}
