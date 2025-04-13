package com.hung.myapplication.data

import com.hung.myapplication.twsewebapi.exchangereport.bwibbuall.BwibbuAllResponse
import com.hung.myapplication.twsewebapi.exchangereport.stockdayall.StockDayAllResponse
import com.hung.myapplication.twsewebapi.exchangereport.stockdayavgall.StockDayAvgAllResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("exchangeReport/BWIBBU_ALL")
    suspend fun fetchBwibbuAllData(): Response<BwibbuAllResponse>

    @GET("/exchangeReport/STOCK_DAY_ALL")
    suspend fun fetchStockDayAllData(): Response<StockDayAllResponse>

    @GET("/exchangeReport/STOCK_DAY_AVG_ALL")
    suspend fun fetchDayAvgAllData(): Response<StockDayAvgAllResponse>
}