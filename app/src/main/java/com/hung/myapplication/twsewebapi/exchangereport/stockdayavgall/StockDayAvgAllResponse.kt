package com.hung.myapplication.twsewebapi.exchangereport.stockdayavgall

data class StockDayAvgAllResponse(
    val stat: String,
    val title: String,
    val fields: List<String>,
    val data: List<List<String>>,
    val notes: List<String>
)
