package com.uxapp.oktava.viewmodels.dataModels

import com.uxapp.oktava.utils.MyTime

data class WeeksSelectedModel(
    val monday: MyTime?,
    val tuesday: MyTime?,
    val wednesday: MyTime?,
    val thursday: MyTime?,
    val friday: MyTime?,
    val saturday: MyTime?,
    val sunday: MyTime?,
)