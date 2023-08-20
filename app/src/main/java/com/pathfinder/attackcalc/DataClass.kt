package com.pathfinder.attackcalc

import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import java.io.Serializable

class DataClass: Serializable {
    var hitModifier = arrayOf("+1", "+2", "-3").toCollection(ArrayList())

    //2-4
    var numDice1 = arrayOf("1", "2", "3").toCollection(ArrayList())
    var numDice2 = arrayOf("1", "2", "3").toCollection(ArrayList())
    var numDice3 = arrayOf("1", "2", "3").toCollection(ArrayList())

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

    var sneakEnable = 0
    var sneakNum = 0
    var sneakDicetype = 0

    fun removeAt(position: Int) {
        hitModifier.removeAt(position)

        numDice1.removeAt(position)
        numDice2.removeAt(position)
        numDice3.removeAt(position)

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

fun diceThrow(inputdicenum:Int, numberofThrows:Int): Int {
    var rez = 0
    for (i in 1..numberofThrows) {
        rez += (1..Dices.dices[inputdicenum]).random()
    }
    return rez
}

fun writeToFile(file : File, data : DataClass) {
    try {
        val f = FileOutputStream(file)
        val o = ObjectOutputStream(f)
        o.writeObject(data)
        o.close()
        f.close()
    } catch (e: Exception) {
        System.err.println("Error opening file.")
    }
}

class FileInfo {
    private val fileName = "Data2.txt"
    val fileMain = File("/data/data/com.pathfinder.attackcalc/" + File.separator + fileName)
}

