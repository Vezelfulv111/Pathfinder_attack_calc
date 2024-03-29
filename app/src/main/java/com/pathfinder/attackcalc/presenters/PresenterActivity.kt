package com.pathfinder.attackcalc.presenters

import Contract
import com.pathfinder.attackcalc.MainActivity

class PresenterActivity(
    private var SomeView: MainActivity
): Contract.Presenter

{
    fun onViewPagerClick(position: Int) {
        val stringToShow = (position).toString() + "That is selected"
        SomeView.showToastMsg(stringToShow)
    }

    override fun onDestroy() {
        //SomeView = null
    }

    override fun readData() {}
}