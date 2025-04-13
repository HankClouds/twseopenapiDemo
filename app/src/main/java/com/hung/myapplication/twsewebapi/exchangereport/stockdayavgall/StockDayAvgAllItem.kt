package com.hung.myapplication.twsewebapi.exchangereport.stockdayavgall

/**
 * 上市個股日收盤價及月平均價
 */
data class StockDayAvgAllItem(
    val Code: String,         //股票代號,
    val Name: String,         //股票名稱,
    val ClosingPrice: String, //收盤價,
    val MonthlyAveragePrice: String   //月平均價

)
