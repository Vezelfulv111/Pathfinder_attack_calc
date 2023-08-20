package com.pathfinder.attackcalc

import java.io.File

class DataClass {
    var Hit_modifier2 = arrayOf("+9", "+20", "-2")

    //2-4
    var X12 = arrayOf("1", "2", "3")
    var X22 = arrayOf("1", "2", "3")
    var X32 = arrayOf("1", "2", "3")

    //5-7
    var plus12 = arrayOf("-1", "+2", "-3")
    var plus22 = arrayOf("-1", "+2", "+0")
    var plus32 = arrayOf("-1", "+2", "-3")

    //6-9
    var im1_At2 = arrayOf("1", "2", "3")
    var im2_At2 = arrayOf("1", "2", "3")
    var im3_At2 = arrayOf("4", "4", "4")

    //10-13
    var At2_enable = arrayOf("0", "1", "0")
    var At3_enable = arrayOf("1", "1", "0")
    var Attack_names = arrayOf("sai", "saber +1","kukri")
}

class FileInfo {
    private val fileName = "AllinAll9.txt";
    val fileMain = File("/data/data/com.pathfinder.attackcalc/" + File.separator + fileName)

    private val sneak = "Sneak9.txt"
    val fileSneak = File("/data/data/com.pathfinder.attackcalc/" + File.separator + sneak)

}

