package com.pathfinder.attackcalc.presenters

import com.pathfinder.attackcalc.AttackInfo
import com.pathfinder.attackcalc.fragments.SettingsFragment
import com.pathfinder.attackcalc.model.Model


class PresenterSettingsFragment(
    private var SomeView: SettingsFragment,
    private val model: Model
): Contract.Presenter {

    var EnableAttacks= intArrayOf(0, 0)
    var AllinAll =  model.AllinAll
    var CurrentPositon = 0
    init{
        readData()
    }

    //изменение флагов использования 2й и 3й атаки
    fun enableAttackSwitch(CheckFlag: Boolean, num: Int): Boolean {
        if (num > EnableAttacks.size)
            return false

        val rez = if (CheckFlag) 1 else 0
        EnableAttacks[num] = rez
        return true
    }

    //2я атака, свитч
    fun checkAttack2Enable(position: Int): Boolean {
        return if (AllinAll.at2Enable[position].toInt() == 1) {
            SomeView.attack2SetGui(position, this)
            true
        } else
            false
    }

    //3я атака, свитч
    fun checkAttack3Enable(position: Int): Boolean {
        return if (AllinAll.at3Enable[position].toInt() == 1) {
            SomeView.attack3SetGui(position, this)
            true
        } else
            false
    }

    //обновление данных - включена ли сник атака
    fun sneakSwitchRefresh(sneakDicetype: Int, sneaknum: Int) {
        sneakSwitchDiceType(sneakDicetype, sneaknum)
    }

    fun sneakSwitchDiceType(sneakDicetype: Int, sneaknum: Int) {
        AllinAll.sneakDicetype = sneakDicetype
        AllinAll.sneakNum = sneaknum
        writeData()
    }

    //функкция обработки нажатия на EditBtn
    fun editButtonLogic(): Boolean {
        //проверка на выход за диапазон
        if(!AllinAll.checkAttackInfoValidity(CurrentPositon))
            return false
        SomeView.rewritePosition(CurrentPositon, this)
        return true
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