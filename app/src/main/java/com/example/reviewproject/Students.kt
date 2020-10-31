package com.example.reviewproject

import java.util.*

data class Students(
    var Stu_ID: UUID = UUID.randomUUID(),
    var Stu_Name: String = " ",
    var Num:Int = 0,
    var pass: Boolean = true)
