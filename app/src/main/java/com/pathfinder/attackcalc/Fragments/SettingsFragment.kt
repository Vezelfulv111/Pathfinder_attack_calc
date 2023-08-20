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
import com.pathfinder.attackcalc.writeToFile
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE

class SettingsFragment : Fragment() {

     private lateinit var adapter: SpinAdapter
    private lateinit var spinnerImg1: Spinner
    private lateinit var spinnerImg2: Spinner
    private lateinit var spinnerImg3: Spinner
    private lateinit var Spinner_Sneak: Spinner
    private lateinit var EditButton: Button
    private lateinit var AddButton: Button
    private lateinit var listView: ListView
    var CurrentPositon = 0;


    var images = intArrayOf(
        R.drawable.d3,
        R.drawable.d4,
        R.drawable.d6,
        R.drawable.d8,
        R.drawable.d10,
        R.drawable.d12,
    )

    var Enable_attacks= intArrayOf(0, 0)

    var Dices = arrayOf("1", "2","3","4","5","6")
    var AllinAll2 = DataClass()
    var fileInfo = FileInfo()

    @SuppressLint("ResourceType")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? {
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

        if (fileInfo.fileMain.exists())
        {
            val ois = ObjectInputStream(FileInputStream(fileInfo.fileMain))
            AllinAll2 =  ois.readObject() as DataClass
            ois.close()
        }

        var Attack_name = view.findViewById<TextView>(R.id.enter_at_name)

        Spinner_Sneak = view.findViewById<Spinner>(R.id.sneak_spinner)
        listView =view.findViewById(R.id.listView)

        //Добавили картинки к спинерам
        adapter = SpinAdapter(context, Dices, images)

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

        val myListAdapter = SettingsAdapter(context as Activity,AllinAll2,listView)
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
            if(AllinAll2.hitModifier.size < CurrentPositon)
                return@setOnClickListener

            AllinAll2.hitModifier[CurrentPositon] = EditText_signed

            AllinAll2.numDice1[CurrentPositon] = spinAt1.selectedItem.toString()
            AllinAll2.numDice2[CurrentPositon] = spinAt2.selectedItem.toString()
            AllinAll2.numDice3[CurrentPositon] = spinAt3.selectedItem.toString()

            AllinAll2.bonus1[CurrentPositon] = Bonus1_signed
            AllinAll2.bonus2[CurrentPositon] = Bonus2_signed
            AllinAll2.bonus3[CurrentPositon] = Bonus3_signed

            AllinAll2.img1[CurrentPositon] = (spinnerImg1.selectedItemId).toString()
            AllinAll2.img2[CurrentPositon] = (spinnerImg2.selectedItemId).toString()
            AllinAll2.img3[CurrentPositon] = (spinnerImg3.selectedItemId).toString()

            AllinAll2.at2Enable[CurrentPositon] = Enable_attacks[0].toString()
            AllinAll2.at3Enable[CurrentPositon] = Enable_attacks[1].toString()
            AllinAll2.attackName[CurrentPositon] = Attack_name.text.toString()

            listView.adapter = SettingsAdapter(context as Activity, AllinAll2,listView)

            writeToFile(fileInfo.fileMain, AllinAll2)
        }

        AddButton.setOnClickListener {
            var AllinAll2 = DataClass()
            if (fileInfo.fileMain.exists()) {
                val ois = ObjectInputStream(FileInputStream(fileInfo.fileMain))
                AllinAll2 =  ois.readObject() as DataClass
                ois.close()
            }

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

            AllinAll2.hitModifier.add(editTextSigned)

            AllinAll2.numDice1.add(spinAt1.selectedItem.toString())
            AllinAll2.numDice2.add(spinAt2.selectedItem.toString())
            AllinAll2.numDice3.add(spinAt3.selectedItem.toString())

            AllinAll2.bonus1.add(bonus1Signed)
            AllinAll2.bonus2.add(bonus2Signed)
            AllinAll2.bonus3.add(bonus3Signed)

            AllinAll2.img1.add((spinnerImg1.selectedItemId).toString())
            AllinAll2.img2.add((spinnerImg2.selectedItemId).toString())
            AllinAll2.img3.add((spinnerImg3.selectedItemId).toString())

            AllinAll2.at2Enable.add(Enable_attacks[0].toString())
            AllinAll2.at3Enable.add(Enable_attacks[1].toString())
            AllinAll2.attackName.add(Attack_name.text.toString())


            listView.adapter =  SettingsAdapter(context as Activity, AllinAll2,listView)
            writeToFile(fileInfo.fileMain, AllinAll2)
        }

        var Switch2nd = view.findViewById(R.id.switchscnd) as Switch
        Switch2nd.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Enable_attacks[0] =1
            } else {
                Enable_attacks[0]= 0
            }
        }

        var Switch3d = view.findViewById(R.id.switchthird) as Switch
        Switch3d.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
               Enable_attacks[1] = 1
            } else {
               Enable_attacks[1]= 0
            }
        }

        //Если нажать на список
        listView.onItemClickListener = OnItemClickListener { _, _, position, _ ->
            CurrentPositon = position

            EditButton.text = "Edit ".plus((position+1).toString())
            Attack_name.text = AllinAll2.attackName[position]

            bonus1.setText(AllinAll2.bonus1[position].toInt().toString())
            spinnerImg1.setSelection(AllinAll2.img1[position].toInt(),true)
            spinAt1.setSelection(AllinAll2.numDice1[position].toInt(),true)

            if (AllinAll2.at2Enable[position].toInt() == 1) {
                Switch2nd.isChecked = TRUE
                bonus2.setText(AllinAll2.bonus1[position].toInt().toString())
                spinnerImg2.setSelection(AllinAll2.img2[position].toInt(),true)
                spinAt2.setSelection(AllinAll2.numDice2[position].toInt(),true)
            }
            else
                Switch2nd.isChecked = FALSE

            if (AllinAll2.at3Enable[position].toInt() == 1) {
                Switch3d.isChecked = TRUE
                bonus3.setText(AllinAll2.bonus1[position].toInt().toString())
                spinnerImg3.setSelection(AllinAll2.img3[position].toInt(),true)
                spinAt3.setSelection(AllinAll2.numDice3[position].toInt(),true)
            }
            else
                Switch3d.isChecked = FALSE

        }

        val SneakEdit = view.findViewById(R.id.sneak_edit) as EditText
        var SneakSwich = view.findViewById(R.id.sneak_switch) as Switch
        SneakSwich.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked)
                AllinAll2.sneakEnable = 1
            else
                AllinAll2.sneakEnable = 0

            AllinAll2.sneakDicetype = Spinner_Sneak.selectedItemId.toInt()
            AllinAll2.sneakNum = SneakEdit.text.toString().toInt()
            val f2 = FileOutputStream(fileInfo.fileMain)
            val o2 = ObjectOutputStream(f2)
            o2.writeObject(AllinAll2)
            o2.close()
            f2.close()
        }
        //перезаписываем данные при изменении кубика сник атаки
        Spinner_Sneak.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?,itemSelected: View, selectedItemPosition: Int, selectedId: Long) {
                AllinAll2.sneakDicetype = Spinner_Sneak.selectedItemId.toInt()
                AllinAll2.sneakNum = SneakEdit.text.toString().toInt()
                val f2 = FileOutputStream(fileInfo.fileMain)
                val o2 = ObjectOutputStream(f2)
                o2.writeObject(AllinAll2)
                o2.close()
                f2.close()
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
                AllinAll2.sneakDicetype = Spinner_Sneak.selectedItemId.toInt()
                AllinAll2.sneakNum= SneakEdit.text.toString().toInt()
                val f2 = FileOutputStream(fileInfo.fileMain)
                val o2 = ObjectOutputStream(f2)
                o2.writeObject(AllinAll2)
                o2.close()
                f2.close()
            }
        })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}
