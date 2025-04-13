package com.hung.myapplication.twsewebapi.exchangereport.bwibbuall

data class BwibbuAllResponse(
    val stat: String,
    val date: String,
    val title: String,
    val fields: List<String>,
    val data: List<List<String>>,
    val notes: List<String>
)


