package com.pathfinder.attackcalc

import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import java.io.Serializable

class DataClass: Serializable {
    var Hit_modifier2 = arrayOf("+1", "+2", "-3").toCollection(ArrayList())


    //2-4
    var X12 = arrayOf("1", "2", "3").toCollection(ArrayList())
    var X22 = arrayOf("1", "2", "3").toCollection(ArrayList())
    var X32 = arrayOf("1", "2", "3").toCollection(ArrayList())

    //5-7
    var plus12 = arrayOf("-1", "+2", "-3").toCollection(ArrayList())
    var plus22 = arrayOf("-1", "+2", "+0").toCollection(ArrayList())
    var plus32 = arrayOf("-1", "+2", "-3").toCollection(ArrayList())

    //6-9
    var im1_At2 = arrayOf("1", "2", "3").toCollection(ArrayList())
    var im2_At2 = arrayOf("1", "2", "3").toCollection(ArrayList())
    var im3_At2 = arrayOf("4", "4", "4").toCollection(ArrayList())

    //10-13
    var At2_enable = arrayOf("0", "1", "0").toCollection(ArrayList())
    var At3_enable = arrayOf("1", "1", "0").toCollection(ArrayList())
    var Attack_names = arrayOf("sai", "saber +1","kukri").toCollection(ArrayList())
}

class FileInfo {
    private val fileName = "AllinAll24e12e4dddd.txt";
    val fileMain = File("/data/data/com.pathfinder.attackcalc/" + File.separator + fileName)

    private val sneak = "Sneak9.txt"
    val fileSneak = File("/data/data/com.pathfinder.attackcalc/" + File.separator + sneak)

    fun writeToFile(file : File, data : DataClass) {
        try {
            val f = FileOutputStream(file)
            val o = ObjectOutputStream(f)
            o.writeObject(data)
            o.close()
            f.close()
        } catch (e: Exception) {
            System.err.println("Error opening file.");
        }
    }
}

