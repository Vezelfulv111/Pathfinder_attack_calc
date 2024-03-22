package com.pathfinder.attackcalc.presenters

import Contract
import com.pathfinder.attackcalc.*
import com.pathfinder.attackcalc.fragments.GenerateFragment
import com.pathfinder.attackcalc.model.Model

class PresenterGenerateFragment(
    private var SomeView: GenerateFragment,
    private val model: Model
): Contract.Presenter
{
   var AllinAll = model.AllinAll
   var TemporaryModifers= intArrayOf(0, 0)
    init{
        readData()
    }

    //редактирование временных модификаторов
    fun editModifier(IncreaseFlag: Boolean, position: Int): String {
        if (position > TemporaryModifers.size)
            return "error"

        val added = if (IncreaseFlag) 1 else -1
        TemporaryModifers[position] += added
        return TemporaryModifers[position].toString()
    }

    //изменение состояния свитча скрытности
    fun sneakySwitch(checkState : Boolean) {
        SomeView.enableSneakAttackSwitch(true, checked = checkState)
    }

    //установка надписи - количество кубов, которые бросаются для скрытой атаки
    fun sneakySwitchLabel(): String {
        return AllinAll.sneakNum.toString() + "d" + model.dices[AllinAll.sneakDicetype].toString()
    }

    //расчет броска одной атаки
    fun throwComputation(position: Int, sneakSwithFlag : Boolean): ThrowData {
        val throwData = ThrowData()
        throwData.tempDamageModifier = TemporaryModifers[1]

        val diceThrow = (1..20).random()
        throwData.d20Throw = diceThrow
        throwData.d20Total = AllinAll.hitModifier[position].toInt()+diceThrow+TemporaryModifers[0]

        val at1 =diceThrow(AllinAll.img1[position].toInt(),AllinAll.numDice1[position].toInt())
        throwData.dmgRoll1 = at1 + AllinAll.bonus1[position].toInt()

        val at2 =diceThrow(AllinAll.img2[position].toInt(),AllinAll.numDice2[position].toInt())
        throwData.dmgRoll2 = at2 + AllinAll.bonus2[position].toInt()

        val at3 =diceThrow(AllinAll.img2[position].toInt(),AllinAll.numDice3[position].toInt())
        throwData.dmgRoll3 = at3 + AllinAll.bonus3[position].toInt()

        if (AllinAll.at2Enable[position].toInt() == 0)
            throwData.dmgRoll2 = 0

        if (AllinAll.at3Enable[position].toInt() == 0)
            throwData.dmgRoll3 = 0

        if (sneakSwithFlag)
            throwData.sneakDmg = diceThrow(AllinAll.sneakDicetype, AllinAll.sneakNum)
        else
            throwData.sneakDmg = 0

        return throwData

    }

    //функция броска кубика
    fun diceThrow(inputDicenum:Int, numberofThrows:Int): Int {
        if (numberofThrows < 1)
            return -1
        if (inputDicenum > model.dices.size)
            return -1

        var rez = 0
        for (i in 1..numberofThrows) {
            rez += (1..model.dices[inputDicenum]).random()
        }
        return rez
    }

    //класс хранит информацию о броске кубиков
    class ThrowData {
        var tempDamageModifier : Int = 0;
        var d20Throw: Int = 0
        var d20Total: Int = 0
            var dmgRoll1 : Int = 0
            var dmgRoll2 : Int = 0
            var dmgRoll3 : Int = 0
        var sneakDmg : Int = 0
        val totalDamageNoSneak: Int
            get() = dmgRoll1+dmgRoll2+dmgRoll3 + tempDamageModifier
        val totalDamageWithSneak: Int
            get() = dmgRoll1+dmgRoll2+dmgRoll3+sneakDmg + tempDamageModifier
    }

    override fun onDestroy() {
        //SomeView = null
    }

    //обращаемся к модели для чтения данных из файла
    override fun readData() {
        AllinAll = model.readAttacInfo() ?: AllinAll
    }
}