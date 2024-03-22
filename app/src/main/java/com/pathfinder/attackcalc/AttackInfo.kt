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

    var sneakNum = 1
    var sneakDicetype = 0

    fun removeAt(position: Int): Boolean {
        val checkAttackInfoValidity = checkAttackInfoValidity(position, this)
        if (!checkAttackInfoValidity)
            return false

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

        return true
    }

    //проверка валидности AttackInfo - вставки или удаления
    fun checkAttackInfoValidity(position: Int, AllinAll : AttackInfo = this): Boolean {
        if (AllinAll.bonus1.size < position || AllinAll.bonus2.size < position ||  AllinAll.bonus3.size < position )
            return false
        if (AllinAll.numDice1.size < position || AllinAll.numDice2.size < position ||  AllinAll.numDice3.size < position )
            return false
        if (AllinAll.img1.size < position || AllinAll.img2.size < position ||  AllinAll.img3.size < position )
            return false
        if (AllinAll.at2Enable.size < position || AllinAll.at3Enable.size < position)
            return false
        if (AllinAll.attackName.size < position)
            return false

        return true
    }

}





