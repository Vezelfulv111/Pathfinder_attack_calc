package com.pathfinder.attackcalc

import Contract
import android.widget.Toast

class Presenter(
    private var mainActivityView: Contract.View?
): Contract.Presenter

{

    override fun onViewPagerClick(position: Int) {
        //идет какая то обработка
        val stringToShow = (position).toString() + "That is selected"
        mainActivityView?.showToastMsg(stringToShow)
    }

    override fun onDestroy() {
       mainActivityView = null
    }
}