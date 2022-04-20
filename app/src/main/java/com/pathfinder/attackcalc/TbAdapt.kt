package com.pathfinder.attackcalc


import androidx.fragment.app.Fragment

import androidx.viewpager2.adapter.FragmentStateAdapter


class TbAdapt(fragmentActivity: MainActivity) :


    FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> generate_fragment()
            else -> settings_fragment()
        }

    }

    override fun getItemCount(): Int {
        return 2;

    }


}