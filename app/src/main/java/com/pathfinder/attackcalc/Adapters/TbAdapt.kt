package com.pathfinder.attackcalc.Adapters


import androidx.fragment.app.Fragment

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.pathfinder.attackcalc.Fragments.GenerateFragment
import com.pathfinder.attackcalc.Fragments.SettingsFragment
import com.pathfinder.attackcalc.MainActivity


class TbAdapt(fragmentActivity: MainActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> GenerateFragment()
            else -> SettingsFragment()
        }
    }
    override fun getItemCount(): Int {
        return 2
    }


}