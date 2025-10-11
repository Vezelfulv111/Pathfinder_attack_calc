package com.pathfinder.attackcalc.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pathfinder.attackcalc.*
import com.pathfinder.attackcalc.Adapters.GenerateAdapter
import com.pathfinder.attackcalc.model.Model
import com.pathfinder.attackcalc.presenters.PresenterGenerateFragment



class GenerateFragment : Fragment(), Contract.View {

    private lateinit var listView: RecyclerView
    private lateinit var GenButton: Button
    private lateinit var RefreshButton: Button

    private lateinit var Plus1: ImageButton
    private lateinit var Minus1: ImageButton
    private lateinit var Plus2: ImageButton
    private lateinit var Minus2: ImageButton

    private lateinit var sneakySwitch: Switch

    private lateinit var presenterGen: PresenterGenerateFragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view: View = inflater.inflate(R.layout.generate_fragment, container, false)
        listView =view.findViewById(R.id.result_list)
        listView.layoutManager = LinearLayoutManager(context as Activity)
        listView.recycledViewPool.setMaxRecycledViews(0, 0)

        presenterGen = PresenterGenerateFragment(this, Model())

        sneakySwitch = view.findViewById(R.id.snky_switch)
        sneakySwitch.setOnClickListener {
            presenterGen.sneakySwitch(sneakySwitch.isChecked)
        }

        listView.adapter =  GenerateAdapter(0,sneakySwitch.isChecked,presenterGen, this)
        GenButton = view.findViewById(R.id.gen_but)

        GenButton.setOnClickListener {
           listView.adapter = GenerateAdapter(1,sneakySwitch.isChecked,presenterGen, this)
        }

        RefreshButton = view.findViewById(R.id.refresh)
        RefreshButton.setOnClickListener {
            presenterGen.readData()
            listView.adapter = GenerateAdapter(0, sneakySwitch.isChecked,presenterGen, this)
        }

        Plus1 = view.findViewById(R.id.Fstplus)
        Minus1 = view.findViewById(R.id.Fstminus)
        Plus2 = view.findViewById(R.id.Fstplus2)
        Minus2 = view.findViewById(R.id.Fstminus2)
        val hitbonus = view.findViewById(R.id.hitbonus) as TextView
        val hitbonus2 = view.findViewById(R.id.hitbonus2) as TextView

        //Temporary modifiers - hit and damage
        Plus1.setOnClickListener {
            hitbonus.text = presenterGen.editModifier(true,0)
        }
        Minus1.setOnClickListener {
            hitbonus.text =  presenterGen.editModifier(false,0)
        }
        Plus2.setOnClickListener {
            hitbonus2.text = presenterGen.editModifier(true,1)
        }
        Minus2.setOnClickListener {
            hitbonus2.text =  presenterGen.editModifier(false,1)
        }

        return view

        }
    override fun showToastMsg(msg: String) {
        Toast.makeText(context as Activity, msg, Toast.LENGTH_SHORT).show()
    }
    fun enableSneakAttackSwitch(enable: Boolean, checked: Boolean) {
       sneakySwitch.isEnabled = enable
       sneakySwitch.isChecked = checked
    }

    override fun onResume() {
        super.onResume()
        presenterGen.readData()
        listView = requireView().findViewById(R.id.result_list)
        listView.adapter = GenerateAdapter(0,sneakySwitch.isChecked,presenterGen, this)
        sneakySwitch.text = presenterGen.sneakySwitchLabel()
    }

    override fun onDestroy() {
        presenterGen.onDestroy()
        super.onDestroy()
    }
}