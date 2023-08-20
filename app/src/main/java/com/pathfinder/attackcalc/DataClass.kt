package com.pathfinder.attackcalc

import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import java.io.Serializable

class DataClass: Serializable {
    var hitModifier = arrayOf("+1", "+2", "-3").toCollection(ArrayList())


    //2-4
    var X12 = arrayOf("1", "2", "3").toCollection(ArrayList())
    var X22 = arrayOf("1", "2", "3").toCollection(ArrayList())
    var X32 = arrayOf("1", "2", "3").toCollection(ArrayList())

    //5-7
    var bonus1 = arrayOf("-1", "+2", "-3").toCollection(ArrayList())
    var bonus2 = arrayOf("-1", "+2", "+0").toCollection(ArrayList())
    var bonus3 = arrayOf("-1", "+2", "-3").toCollection(ArrayList())

    //6-9
    var img1 = arrayOf("1", "2", "3").toCollection(ArrayList())
    var img2 = arrayOf("1", "2", "3").toCollection(ArrayList())
    var img3 = arrayOf("4", "4", "4").toCollection(ArrayList())

    //10-13
    var at2Enable = arrayOf("0", "1", "0").toCollection(ArrayList())
    var at3Enable = arrayOf("1", "1", "0").toCollection(ArrayList())
    var attackName = arrayOf("sai", "saber +1","kukri").toCollection(ArrayList())

    fun removeAt(position: Int) {
        hitModifier.removeAt(position)

        X12.removeAt(position)
        X22.removeAt(position)
        X32.removeAt(position)

        bonus1.removeAt(position)
        bonus2.removeAt(position)
        bonus3.removeAt(position)

        img1.removeAt(position)
        img2.removeAt(position)
        img3.removeAt(position)

        at2Enable.removeAt(position)
        at3Enable.removeAt(position)

        attackName.removeAt(position)
    }

}

class FileInfo {
    private val fileName = "AllinAll24e12e4ddddd.txt";
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

