package com.pathfinder.attackcalc.fragments


import android.annotation.SuppressLint
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
import java.io.*
import android.widget.ArrayAdapter
import android.widget.AdapterView
import com.pathfinder.attackcalc.adapters.SettingsAdapter
import com.pathfinder.attackcalc.DataClass
import com.pathfinder.attackcalc.FileInfo
import com.pathfinder.attackcalc.R
import com.pathfinder.attackcalc.adapters.SpinAdapter
import com.pathfinder.attackcalc.model.Model
import com.pathfinder.attackcalc.presenters.PresenterGenerateFragment
import com.pathfinder.attackcalc.presenters.PresenterSettingsFragment
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE

class SettingsFragment : Fragment(), Contract.View {

     private lateinit var adapter: SpinAdapter
    private lateinit var spinnerImg1: Spinner
    private lateinit var spinnerImg2: Spinner
    private lateinit var spinnerImg3: Spinner
    private lateinit var Spinner_Sneak: Spinner
    private lateinit var EditButton: Button
    private lateinit var AddButton: Button
    private lateinit var listView: ListView
    var CurrentPositon = 0


    var images = intArrayOf(
        R.drawable.d3,
        R.drawable.d4,
        R.drawable.d6,
        R.drawable.d8,
        R.drawable.d10,
        R.drawable.d12,
    )

    var presenterSt: PresenterSettingsFragment? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_settings, container, false)
        spinnerImg1 = view.findViewById<Spinner>(R.id.Dice_spinner1)
        spinnerImg2 = view.findViewById<Spinner>(R.id.Dice_spinner2)
        spinnerImg3 = view.findViewById<Spinner>(R.id.Dice_spinner3)

        val spinAt1 = view.findViewById<Spinner>(R.id.Spin_n1) as Spinner
        val spinAt2 = view.findViewById<Spinner>(R.id.Spin_n2) as Spinner
        val spinAt3 = view.findViewById<Spinner>(R.id.Spin_n3) as Spinner

        val arrayAdapter: ArrayAdapter<Any> = ArrayAdapter<Any>(context as Activity,
            R.layout.spiner_123, resources.getStringArray(R.array.SpinNumbers))
        spinAt1.adapter = arrayAdapter
        spinAt2.adapter = arrayAdapter
        spinAt3.adapter = arrayAdapter

        presenterSt = PresenterSettingsFragment(this, Model())
        presenterSt!!.readData()

        val Attack_name = view.findViewById<TextView>(R.id.enter_at_name)

        Spinner_Sneak = view.findViewById<Spinner>(R.id.sneak_spinner)
        listView =view.findViewById(R.id.listView)

        //Добавили картинки к спинерам
        adapter = SpinAdapter(context, arrayOf("1", "2","3","4","5","6"), images)

        spinnerImg1.adapter = adapter
        spinnerImg2.adapter = adapter
        spinnerImg3.adapter = adapter
        Spinner_Sneak.adapter = adapter

            val bonus1 = view.findViewById(R.id.Edittext1) as EditText
            val bonus2 = view.findViewById(R.id.Edittext2) as EditText
            val bonus3 = view.findViewById(R.id.Edittext3) as EditText
            val EditModifer = view.findViewById(R.id.editHit) as EditText

        EditButton = view.findViewById(R.id.EditButton)
        AddButton = view.findViewById(R.id.Addbutton)

        val myListAdapter = SettingsAdapter(context as Activity,presenterSt!!,listView)
        listView.adapter = myListAdapter

        EditButton.setOnClickListener {

            var EditText_signed = EditModifer.text.toString()
            if (EditModifer.text.toString().toInt()>0)
                EditText_signed = "+" +EditModifer.text.toString().toInt().toString()

            var Bonus1_signed = bonus1.text.toString()
            var Bonus2_signed = bonus2.text.toString()
            var Bonus3_signed = bonus3.text.toString()
            if (bonus1.text.toString().toInt()>0) {
                Bonus1_signed = "+" + bonus1.text.toString().toInt().toString()
            }
            if (bonus2.text.toString().toInt()>0) {
                Bonus2_signed = "+" + bonus2.text.toString().toInt().toString()
            }
            if (bonus3.text.toString().toInt()>0) {
                Bonus3_signed = "+" + bonus3.text.toString().toInt().toString()
            }

            //проверка на выход за диапазон
            if(presenterSt!!.AllinAll
                    .hitModifier.size <= CurrentPositon || presenterSt!!.AllinAll
                    .hitModifier.isEmpty())
                return@setOnClickListener

            presenterSt!!.AllinAll.hitModifier[CurrentPositon] = EditText_signed

            presenterSt!!.AllinAll.numDice1[CurrentPositon] = spinAt1.selectedItem.toString()
            presenterSt!!.AllinAll.numDice2[CurrentPositon] = spinAt2.selectedItem.toString()
            presenterSt!!.AllinAll.numDice3[CurrentPositon] = spinAt3.selectedItem.toString()

            presenterSt!!.AllinAll.bonus1[CurrentPositon] = Bonus1_signed
            presenterSt!!.AllinAll.bonus2[CurrentPositon] = Bonus2_signed
            presenterSt!!.AllinAll.bonus3[CurrentPositon] = Bonus3_signed

            presenterSt!!.AllinAll.img1[CurrentPositon] = (spinnerImg1.selectedItemId).toString()
            presenterSt!!.AllinAll.img2[CurrentPositon] = (spinnerImg2.selectedItemId).toString()
            presenterSt!!.AllinAll.img3[CurrentPositon] = (spinnerImg3.selectedItemId).toString()

            presenterSt!!.AllinAll.at2Enable[CurrentPositon] = presenterSt!!.EnableAttacks[0].toString()
            presenterSt!!.AllinAll.at3Enable[CurrentPositon] = presenterSt!!.EnableAttacks[1].toString()
            presenterSt!!.AllinAll.attackName[CurrentPositon] = Attack_name.text.toString()

            listView.adapter = SettingsAdapter(context as Activity, presenterSt!!,listView)

            presenterSt!!.writeData()
         }

        AddButton.setOnClickListener {
            var editTextSigned = EditModifer.text.toString()
            if (EditModifer.text.toString().toInt() > 0)
                editTextSigned = "+" +EditModifer.text.toString().toInt().toString()

            var bonus1Signed = bonus1.text.toString()
            var bonus2Signed = bonus2.text.toString()
            var bonus3Signed = bonus3.text.toString()
            if (bonus1.text.toString().toInt()>0)
                bonus1Signed = "+" + bonus1.text.toString().toInt().toString()
            if (bonus2.text.toString().toInt()>0)
                bonus2Signed = "+" + bonus2.text.toString().toInt().toString()
            if (bonus3.text.toString().toInt()>0)
                bonus3Signed = "+" + bonus3.text.toString().toInt().toString()

            presenterSt!!.AllinAll.hitModifier.add(editTextSigned)

            presenterSt!!.AllinAll.numDice1.add(spinAt1.selectedItem.toString())
            presenterSt!!.AllinAll.numDice2.add(spinAt2.selectedItem.toString())
            presenterSt!!.AllinAll.numDice3.add(spinAt3.selectedItem.toString())

            presenterSt!!.AllinAll.bonus1.add(bonus1Signed)
            presenterSt!!.AllinAll.bonus2.add(bonus2Signed)
            presenterSt!!.AllinAll.bonus3.add(bonus3Signed)

            presenterSt!!.AllinAll.img1.add((spinnerImg1.selectedItemId).toString())
            presenterSt!!.AllinAll.img2.add((spinnerImg2.selectedItemId).toString())
            presenterSt!!.AllinAll.img3.add((spinnerImg3.selectedItemId).toString())

            presenterSt!!.AllinAll.at2Enable.add(presenterSt!!.EnableAttacks[0].toString())
            presenterSt!!.AllinAll.at3Enable.add(presenterSt!!.EnableAttacks[1].toString())
            presenterSt!!.AllinAll.attackName.add(Attack_name.text.toString())


            listView.adapter =  SettingsAdapter(context as Activity, presenterSt!!,listView)
            presenterSt!!.writeData()
        }

        var Switch2nd = view.findViewById(R.id.switchscnd) as Switch
        Switch2nd.setOnCheckedChangeListener { _, isChecked ->
            presenterSt!!.EnableAttackSwitch(isChecked,0)
        }

        var Switch3d = view.findViewById(R.id.switchthird) as Switch
        Switch3d.setOnCheckedChangeListener { _, isChecked ->
            presenterSt!!.EnableAttackSwitch(isChecked,1)
        }

        //Если нажать на список
        listView.onItemClickListener = OnItemClickListener { _, _, position, _ ->
            CurrentPositon = position

            EditButton.text = "Edit ".plus((position+1).toString())
            Attack_name.text = presenterSt!!.AllinAll.attackName[position]

            bonus1.setText(presenterSt!!.AllinAll.bonus1[position].toInt().toString())
            spinnerImg1.setSelection(presenterSt!!.AllinAll.img1[position].toInt(),true)
            spinAt1.setSelection(presenterSt!!.AllinAll.numDice1[position].toInt()-1,true)

            if (presenterSt!!.AllinAll.at2Enable[position].toInt() == 1) {
                Switch2nd.isChecked = TRUE
                bonus2.setText(presenterSt!!.AllinAll.bonus1[position].toInt().toString())
                spinnerImg2.setSelection(presenterSt!!.AllinAll.img2[position].toInt(),true)
                spinAt2.setSelection(presenterSt!!.AllinAll.numDice2[position].toInt()-1,true)
            }
            else
                Switch2nd.isChecked = FALSE

            if (presenterSt!!.AllinAll.at3Enable[position].toInt() == 1) {
                Switch3d.isChecked = TRUE
                bonus3.setText(presenterSt!!.AllinAll.bonus1[position].toInt().toString())
                spinnerImg3.setSelection(presenterSt!!.AllinAll.img3[position].toInt(),true)
                spinAt3.setSelection(presenterSt!!.AllinAll.numDice3[position-1].toInt(),true)
            }
            else
                Switch3d.isChecked = FALSE

        }

        val SneakEdit = view.findViewById(R.id.sneak_edit) as EditText
        var SneakSwich = view.findViewById(R.id.sneak_switch) as Switch
        SneakSwich.setOnCheckedChangeListener { _, isChecked ->
            presenterSt!!.sneakSwitchRefresh(isChecked, Spinner_Sneak.selectedItemId.toInt(), SneakEdit.text.toString().toInt())
        }

        //перезаписываем данные при изменении кубика сник атаки
        Spinner_Sneak.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?,itemSelected: View, selectedItemPosition: Int, selectedId: Long) {
                presenterSt!!.sneakSwitchDiceType(Spinner_Sneak.selectedItemId.toInt(), SneakEdit.text.toString().toInt())
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        //перезаписываем при изменении числа кубиков
        SneakEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,before: Int, count: Int) {
                presenterSt!!.sneakSwitchDiceType(Spinner_Sneak.selectedItemId.toInt(), SneakEdit.text.toString().toInt())
            }
        })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}
