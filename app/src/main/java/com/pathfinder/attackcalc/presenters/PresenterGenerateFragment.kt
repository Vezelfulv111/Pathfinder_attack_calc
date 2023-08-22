package com.pathfinder.attackcalc.presenters

import Contract
import com.pathfinder.attackcalc.*
import com.pathfinder.attackcalc.fragments.GenerateFragment

class PresenterGenerateFragment(
    private var SomeView: GenerateFragment,
    private val model: Contract.Model
): Contract.Presenter

{
   var AllinAll = DataClass()
   var TemporaryModifers= intArrayOf(0, 0)

    fun editMoifier(IncreaseFlag: Boolean, position: Int): String {
        val added = if (IncreaseFlag) 1 else -1
        TemporaryModifers[position] += added
        return TemporaryModifers[position].toString()
    }

    fun sneakySwitch() {
        if (AllinAll.sneakEnable == 0) {
            SomeView.showToastMsg("Set it in settings window!")
            SomeView.enableSneakAttackSwitch(false, checked = false)
        }
        else {
            SomeView.enableSneakAttackSwitch(true, checked = false)
        }
    }

    fun sneakySwitchLabel(): String {
        var str = if (AllinAll.sneakEnable == 1) {
            AllinAll.sneakNum.toString() +"d"+ Dices.dices[AllinAll.sneakDicetype].toString()
        } else {
            "none"
        }
        return str
    }

    //расчет броска одной атаки
    fun throwComputation(position: Int, sneakSwithFlag : Boolean): ThrowData {
        val throwData = ThrowData()

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

        if (AllinAll.sneakEnable == 1 && sneakSwithFlag)
            throwData.sneakDmg = diceThrow(AllinAll.sneakDicetype, AllinAll.sneakNum)
        else
            throwData.sneakDmg = 0

        return throwData

    }

    override fun onDestroy() {
        //SomeView = null
    }

    override fun readData() {
        AllinAll = model.readAttacInfo() ?: AllinAll
    }
}