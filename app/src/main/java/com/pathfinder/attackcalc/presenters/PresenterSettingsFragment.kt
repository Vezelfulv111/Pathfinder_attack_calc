package com.pathfinder.attackcalc.presenters

import com.pathfinder.attackcalc.DataClass
import com.pathfinder.attackcalc.fragments.SettingsFragment
import java.io.FileOutputStream
import java.io.ObjectOutputStream


class PresenterSettingsFragment(
    private var SomeView: SettingsFragment,
    private val model: Contract.Model
): Contract.Presenter {

    var EnableAttacks= intArrayOf(0, 0)

    var AllinAll = DataClass()

    fun EnableAttackSwitch(CheckFlag: Boolean, num: Int) {
        val rez = if (CheckFlag) 1 else 0
        EnableAttacks[num] = rez
    }

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

    override fun onDestroy() {}

    override fun readData() {
        AllinAll = model.readAttacInfo() ?: AllinAll
    }

    fun writeData() {
        model.writeAttacInfo(AllinAll)
    }


}