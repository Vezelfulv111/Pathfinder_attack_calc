package com.pathfinder.attackcalc.presenters

import com.pathfinder.attackcalc.AttackInfo
import com.pathfinder.attackcalc.fragments.SettingsFragment
import com.pathfinder.attackcalc.model.Model


class PresenterSettingsFragment(
    private var SomeView: SettingsFragment,
    private val model: Model
): Contract.Presenter {

    var EnableAttacks= intArrayOf(0, 0)
    var AllinAll = AttackInfo()
    var CurrentPositon = 0;

    fun enableAttackSwitch(CheckFlag: Boolean, num: Int) {
        val rez = if (CheckFlag) 1 else 0
        EnableAttacks[num] = rez
    }

    //2я атака, свитч
    fun checkAttack2Enable(position: Int): Boolean {
        return if (AllinAll.at2Enable[position].toInt() == 1) {
            SomeView.attack2SetGui(position)
            true
        } else
            false
    }

    //3я атака, свитч
    fun checkAttack3Enable(position: Int): Boolean {
        return if (AllinAll.at3Enable[position].toInt() == 1) {
            SomeView.attack3SetGui(position)
            true
        } else
            false
    }

    //обновление данных - включена ли сник атака
    fun sneakSwitchRefresh(CheckFlag: Boolean, sneakDicetype: Int, sneaknum: Int) {
        if (CheckFlag) {
            AllinAll.sneakEnable = 1
        } else
            AllinAll.sneakEnable = 0

        sneakSwitchDiceType(sneakDicetype, sneaknum)
    }

    fun sneakSwitchDiceType(sneakDicetype: Int, sneaknum: Int) {
        AllinAll.sneakDicetype = sneakDicetype
        AllinAll.sneakNum = sneaknum
        writeData()
    }

    //функкция обработки нажатия на EditBtn
    fun editButtonLogic() {
        //проверка на выход за диапазон
        if(AllinAll.hitModifier.size <= CurrentPositon || AllinAll.hitModifier.isEmpty())
            return

        SomeView.rewritePosition(CurrentPositon)
    }
    //функция проверки и установки в строке плюса
    fun setPlusSign(string: String): String {
        var inputStr = string

        if (inputStr.toInt()>0)
            inputStr = "+" + inputStr.toInt().toString()

        return inputStr
    }

    override fun onDestroy() {}

    override fun readData() {
        AllinAll = model.readAttacInfo() ?: AllinAll
    }

    fun writeData() {
        model.writeAttacInfo(AllinAll)
    }


}