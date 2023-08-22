package com.pathfinder.attackcalc
import java.io.Serializable

class AttackInfo: Serializable {
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





