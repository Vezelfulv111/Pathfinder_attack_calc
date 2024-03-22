package com.pathfinder.attackcalc.fragments


import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pathfinder.attackcalc.Adapters.SettingsAdapter
import com.pathfinder.attackcalc.R
import com.pathfinder.attackcalc.Adapters.SpinAdapter
import com.pathfinder.attackcalc.model.Model
import com.pathfinder.attackcalc.presenters.PresenterSettingsFragment

class SettingsFragment : Fragment(), Contract.View {

     private lateinit var adapter: SpinAdapter
    private lateinit var spinnerImg1: Spinner
    private lateinit var spinnerImg2: Spinner
    private lateinit var spinnerImg3: Spinner
    private lateinit var Spinner_Sneak: Spinner
    private lateinit var EditButton: Button
    private lateinit var AddButton: Button
    private lateinit var listView: RecyclerView

    var images = intArrayOf(
        R.drawable.d3,
        R.drawable.d4,
        R.drawable.d6,
        R.drawable.d8,
        R.drawable.d10,
        R.drawable.d12,
    )

    private lateinit var presenterSt: PresenterSettingsFragment


    private lateinit var bonus1: EditText
    private lateinit var bonus2: EditText
    private lateinit var bonus3: EditText

    private lateinit var spinAt1: Spinner
    private lateinit var spinAt2: Spinner
    private lateinit var spinAt3: Spinner

    private lateinit var Switch2nd : Switch
    private lateinit var Switch3d : Switch

    private lateinit var EditModifer : EditText
    private lateinit var Attack_name :TextView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_settings, container, false)
        spinnerImg1 = view.findViewById(R.id.Dice_spinner1)
        spinnerImg2 = view.findViewById(R.id.Dice_spinner2)
        spinnerImg3 = view.findViewById(R.id.Dice_spinner3)

        spinAt1 = view.findViewById(R.id.Spin_n1) as Spinner
        spinAt2 = view.findViewById(R.id.Spin_n2) as Spinner
        spinAt3 = view.findViewById(R.id.Spin_n3) as Spinner

        val arrayAdapter: ArrayAdapter<Any> = ArrayAdapter<Any>(context as Activity,
            R.layout.spiner_123, resources.getStringArray(R.array.SpinNumbers))
        spinAt1.adapter = arrayAdapter
        spinAt2.adapter = arrayAdapter
        spinAt3.adapter = arrayAdapter

        presenterSt = PresenterSettingsFragment(this, Model())

        Attack_name = view.findViewById(R.id.enter_at_name)
        Spinner_Sneak = view.findViewById(R.id.sneak_spinner)
        listView =view.findViewById(R.id.listView)

        //Добавили картинки к спинерам
        adapter = SpinAdapter(context, arrayOf("1", "2","3","4","5","6"), images)

        spinnerImg1.adapter = adapter
        spinnerImg2.adapter = adapter
        spinnerImg3.adapter = adapter
        spinnerImg1.setSelection(2)
        spinnerImg2.setSelection(3)
        spinnerImg3.setSelection(5)
        Spinner_Sneak.adapter = adapter
        Spinner_Sneak.setSelection(2)

        bonus1 = view.findViewById(R.id.Edittext1)
        bonus2 = view.findViewById(R.id.Edittext2)
        bonus3 = view.findViewById(R.id.Edittext3)
        EditModifer = view.findViewById(R.id.editHit)

        EditButton = view.findViewById(R.id.EditButton)
        AddButton = view.findViewById(R.id.Addbutton)

        val myListAdapter = SettingsAdapter(context as Activity,presenterSt,listView, this)
        listView.adapter = myListAdapter
        listView.layoutManager = LinearLayoutManager(context as Activity)
        listView.recycledViewPool.setMaxRecycledViews(0, 0)

        EditButton.setOnClickListener {
            presenterSt.editButtonLogic()
        }

        AddButton.setOnClickListener {
            val editTextSigned = presenterSt.setPlusSign(EditModifer.text.toString())
            val bonus1Signed = presenterSt.setPlusSign(bonus1.text.toString())
            val bonus2Signed = presenterSt.setPlusSign(bonus2.text.toString())
            val bonus3Signed = presenterSt.setPlusSign(bonus3.text.toString())

            presenterSt.AllinAll.hitModifier.add(editTextSigned)

            presenterSt.AllinAll.numDice1.add(spinAt1.selectedItem.toString())
            presenterSt.AllinAll.numDice2.add(spinAt2.selectedItem.toString())
            presenterSt.AllinAll.numDice3.add(spinAt3.selectedItem.toString())

            presenterSt.AllinAll.bonus1.add(bonus1Signed)
            presenterSt.AllinAll.bonus2.add(bonus2Signed)
            presenterSt.AllinAll.bonus3.add(bonus3Signed)

            presenterSt.AllinAll.img1.add((spinnerImg1.selectedItemId).toString())
            presenterSt.AllinAll.img2.add((spinnerImg2.selectedItemId).toString())
            presenterSt.AllinAll.img3.add((spinnerImg3.selectedItemId).toString())

            presenterSt.AllinAll.at2Enable.add(presenterSt.EnableAttacks[0].toString())
            presenterSt.AllinAll.at3Enable.add(presenterSt.EnableAttacks[1].toString())
            presenterSt.AllinAll.attackName.add(Attack_name.text.toString())

            listView.adapter =  SettingsAdapter(context as Activity, presenterSt,listView, this)
            presenterSt.writeData()
        }

        Switch2nd = view.findViewById(R.id.switchscnd)
        Switch2nd.setOnCheckedChangeListener { _, isChecked ->
            presenterSt.enableAttackSwitch(isChecked,0)
        }

        Switch3d = view.findViewById(R.id.switchthird)
        Switch3d.setOnCheckedChangeListener { _, isChecked ->
            presenterSt.enableAttackSwitch(isChecked,1)
        }

        val SneakEdit: EditText = view.findViewById(R.id.sneak_edit)

        //перезаписываем данные при изменении кубика сник атаки
        Spinner_Sneak.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?,itemSelected: View, selectedItemPosition: Int, selectedId: Long) {
                presenterSt.sneakSwitchDiceType(Spinner_Sneak.selectedItemId.toInt(), SneakEdit.text.toString().toInt())
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        //перезаписываем при изменении числа кубиков
        SneakEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int,before: Int, count: Int) {
                if (SneakEdit.text.isEmpty())
                    return

                presenterSt.sneakSwitchDiceType(Spinner_Sneak.selectedItemId.toInt(), SneakEdit.text.toString().toInt())
            }
        })
        return view
    }

    //Изменение Gui для 2й атаки
    fun attack2SetGui(position: Int, presenterSt : PresenterSettingsFragment) {
        bonus2.setText(presenterSt.AllinAll.bonus1[position].toInt().toString())
        spinnerImg2.setSelection(presenterSt.AllinAll.img2[position].toInt(),true)
        spinAt2.setSelection(presenterSt.AllinAll.numDice2[position].toInt()-1,true)
    }

    //Изменение Gui для 3й атаки
    fun attack3SetGui(position: Int, presenterSt : PresenterSettingsFragment) {
        bonus3.setText(presenterSt.AllinAll.bonus1[position].toInt().toString())
        spinnerImg3.setSelection(presenterSt.AllinAll.img3[position].toInt(),true)
        spinAt3.setSelection(presenterSt.AllinAll.numDice3[position].toInt()-1,true)
    }

    fun rewritePosition(position: Int, presenterSt : PresenterSettingsFragment) {
        val EditText_signed = presenterSt.setPlusSign(EditModifer.text.toString())
        val Bonus1_signed =  presenterSt.setPlusSign(bonus1.text.toString())
        val Bonus2_signed =  presenterSt.setPlusSign(bonus2.text.toString())
        val Bonus3_signed =  presenterSt.setPlusSign(bonus3.text.toString())

        presenterSt.AllinAll.hitModifier[position] = EditText_signed

        presenterSt.AllinAll.numDice1[position] = spinAt1.selectedItem.toString()
        presenterSt.AllinAll.numDice2[position] = spinAt2.selectedItem.toString()
        presenterSt.AllinAll.numDice3[position] = spinAt3.selectedItem.toString()

        presenterSt.AllinAll.bonus1[position] = Bonus1_signed
        presenterSt.AllinAll.bonus2[position] = Bonus2_signed
        presenterSt.AllinAll.bonus3[position] = Bonus3_signed

        presenterSt.AllinAll.img1[position] = (spinnerImg1.selectedItemId).toString()
        presenterSt.AllinAll.img2[position] = (spinnerImg2.selectedItemId).toString()
        presenterSt.AllinAll.img3[position] = (spinnerImg3.selectedItemId).toString()

        presenterSt.AllinAll.at2Enable[position] = presenterSt.EnableAttacks[0].toString()
        presenterSt.AllinAll.at3Enable[position] = presenterSt.EnableAttacks[1].toString()
        presenterSt.AllinAll.attackName[position] = Attack_name.text.toString()

        listView.adapter = SettingsAdapter(context as Activity, presenterSt, listView, this)

        presenterSt.writeData()
    }

    override fun onDestroy() {
        presenterSt.onDestroy()
        super.onDestroy()
    }

}
