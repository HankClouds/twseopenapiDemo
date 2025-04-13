package com.hung.myapplication.data

import com.hung.myapplication.twsewebapi.exchangereport.bwibbuall.BwibbuAllDataItem
import com.hung.myapplication.twsewebapi.exchangereport.stockdayall.StockDayAllItem
import com.hung.myapplication.twsewebapi.exchangereport.stockdayavgall.StockDayAvgAllItem

data class MainStockDataModel(
    val Code: String,         //股票代號,

    val bwibbuAllDataItem: BwibbuAllDataItem? = null, //上市個股日本益比、殖利率及股價淨值比

    val stockDayAvgAllItem: StockDayAvgAllItem? = null, //上市個股日收盤價及月平均價

    val stockDayAllItem: StockDayAllItem? = null //上市個股日成交資訊

)