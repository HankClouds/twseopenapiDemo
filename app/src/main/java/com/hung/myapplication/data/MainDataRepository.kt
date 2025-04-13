package com.hung.myapplication.data

import com.hung.myapplication.twsewebapi.exchangereport.bwibbuall.BwibbuAllDataItem
import com.hung.myapplication.twsewebapi.exchangereport.stockdayall.StockDayAllItem
import com.hung.myapplication.twsewebapi.exchangereport.stockdayavgall.StockDayAvgAllItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

class MainDataRepository @Inject constructor(private val apiService: ApiService) {

    fun fetchStockData(): Flow<Result<List<MainStockDataModel>>> =
        flow {
            val flowA: Flow<Result<List<BwibbuAllDataItem>>> = fetchBwibbuAllReportData()
            val flowB: Flow<Result<List<StockDayAvgAllItem>>> = fetchStockDayAvgAllData()
            val flowC: Flow<Result<List<StockDayAllItem>>> = fetchStockDayAllData()

            flowA.zip(flowB) { resultA, resultB ->
                Pair(resultA, resultB)
            }.zip(flowC) { pairAB, resultC ->
                Triple(pairAB.first, pairAB.second, resultC)
            }.collect { (resultA, resultB, resultC) ->
                val errors = mutableListOf<Throwable>()

                val dataA: List<BwibbuAllDataItem> = resultA.getOrNull() ?: emptyList()
                val dataB: List<StockDayAvgAllItem> = resultB.getOrNull() ?: emptyList()
                val dataC: List<StockDayAllItem> = resultC.getOrNull() ?: emptyList()

                // 簡單的合併邏輯，Code 是唯一的鍵
                val mergedMap = mutableMapOf<String, MainStockDataModel>()

                dataA.forEach {
                    mergedMap[it.Code] = (mergedMap[it.Code] ?: MainStockDataModel(it.Code)).copy(bwibbuAllDataItem = it)
                }
                dataB.forEach {
                    mergedMap[it.Code] = (mergedMap[it.Code] ?: MainStockDataModel(it.Code)).copy(stockDayAvgAllItem = it)
                }
                dataC.forEach {
                    mergedMap[it.Code] = (mergedMap[it.Code] ?: MainStockDataModel(it.Code)).copy(stockDayAllItem = it)
                }

                if (errors.isEmpty() && mergedMap.isNotEmpty()) {
                    emit(
                        Result.success(
                            mergedMap.values.toList().sortedByDescending { it.Code })
                    )
                } else if (mergedMap.isNotEmpty()) {
                    emit(
                        Result.success(
                            mergedMap.values.toList().sortedByDescending { it.Code })
                    )
                } else {
                    emit(Result.failure<List<MainStockDataModel>>(Exception("Failed to fetch data")))
                }
            }
        }.flowOn(Dispatchers.IO)

    private fun fetchBwibbuAllReportData(): Flow<Result<List<BwibbuAllDataItem>>> = flow {
        try {
            val response = apiService.fetchBwibbuAllData()
            if (response.isSuccessful) {
                response.body()?.let { response ->
                    val stockDataItems = response.data.map {
                        BwibbuAllDataItem(
                            Code = it[0],
                            Name = it[1],
                            PEratio = it[2],
                            DividendYield = it[3],
                            PBratio = it[4]
                        )
                    }
                    emit(Result.success(stockDataItems))
                } ?: emit(Result.failure(Exception("BwibbuAll內容錯誤")))
            } else {
                emit(Result.failure(Exception("BwibbuAll失敗: ${response.code()}")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    private fun fetchStockDayAvgAllData(): Flow<Result<List<StockDayAvgAllItem>>> = flow {
        try {
            val response = apiService.fetchDayAvgAllData()
            if (response.isSuccessful) {
                response.body()?.let { response ->
                    val stockDataItems = response.data.map {
                        StockDayAvgAllItem(
                            Code = it[0],
                            Name = it[1],
                            ClosingPrice = it[2],
                            MonthlyAveragePrice = it[3]
                        )
                    }
                    emit(Result.success(stockDataItems))
                } ?: emit(Result.failure(Exception("BwibbuAll內容錯誤")))
            } else {
                emit(Result.failure(Exception("BwibbuAll失敗: ${response.code()}")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    private fun fetchStockDayAllData(): Flow<Result<List<StockDayAllItem>>> = flow {
        try {
            val response = apiService.fetchStockDayAllData()
            if (response.isSuccessful) {
                response.body()?.let { response ->
                    val stockDataItems = response.data.map {
                        StockDayAllItem(
                            Code = it[0],
                            Name = it[1],
                            TradeVolume = it[2],
                            TradeValue = it[3],
                            OpeningPrice = it[4],
                            HighestPrice = it[5],
                            LowestPrice = it[6],
                            ClosingPrice = it[7],
                            Change = it[8],
                            Transaction = it[9]
                        )
                    }
                    emit(Result.success(stockDataItems))
                } ?: emit(Result.failure(Exception("StockDayAll內容錯誤")))
            } else {
                emit(Result.failure(Exception("StockDayAll失敗: ${response.code()}")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

}