package com.pathfinder.attackcalc.presenters

import Contract
import com.pathfinder.attackcalc.DataClass
import com.pathfinder.attackcalc.ThrowData
import com.pathfinder.attackcalc.diceThrow
import com.pathfinder.attackcalc.fragments.GenerateFragment

class PresenterGenerateFragment(
    private var SomeView: GenerateFragment
): Contract.Presenter

{

    fun sneakySwitch(AllinAll : DataClass) {
        if (AllinAll.sneakEnable == 0) {
            SomeView.showToastMsg("Set it in settings window!")
            SomeView.enableSneakAttackSwitch(false)
        }
        else {
            SomeView.enableSneakAttackSwitch(true)
        }
    }

    //расчет броска одной атаки
    fun throwComputation(AllinAll: DataClass, position: Int, TempModifiers: IntArray, sneakSwithFlag : Boolean): ThrowData {
        val throwData = ThrowData()

        val diceThrow = (1..20).random()
        throwData.d20Throw = diceThrow
        throwData.d20Total = AllinAll.hitModifier[position].toInt()+diceThrow+TempModifiers[0]

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
}