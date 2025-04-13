package com.hung.myapplication.twsewebapi.exchangereport.bwibbuall

/**
 * 上市個股日本益比、殖利率及股價淨值比（依代碼查詢）
 */
data class BwibbuAllDataItem(
    val Code: String,        //股票代號,
    val Name: String,         //股票名稱,
    val PEratio: String,      //本益比,
    val DividendYield: String,    //殖利率(%),
    val PBratio: String,      //股價淨值比

//{"Code":"1101","Name":"台泥","PEratio":"21.06","DividendYield":"3.25","PBratio":"0.96"}
)