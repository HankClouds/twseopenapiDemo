package com.hung.myapplication.twsewebapi.exchangereport.stockdayall

data class StockDayAllResponse(
    val stat: String,
    val date: String,
    val title: String,
    val fields: List<String>,
    val data: List<List<String>>,
    val notes: List<String>
)
