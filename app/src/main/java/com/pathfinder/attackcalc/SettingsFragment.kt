package com.pathfinder.attackcalc


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
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE
import android.widget.ArrayAdapter
import android.widget.AdapterView

class SettingsFragment : Fragment() {

     private lateinit var adapter: SpinAdapter
    private lateinit var Spinner_1: Spinner
    private lateinit var Spinner_2: Spinner
    private lateinit var Spinner_3: Spinner
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
    var Sneak_attacks= intArrayOf(0, 0,0)



    var Dices = arrayOf("1", "2","3","4","5","6")
    var AllinAll2 = DataClass()
    var fileInfo = FileInfo()



    @SuppressLint("ResourceType")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_settings, container, false)
        Spinner_1 = view.findViewById<Spinner>(R.id.Dice_spinner1)
        Spinner_2 = view.findViewById<Spinner>(R.id.Dice_spinner2)
        Spinner_3 = view.findViewById<Spinner>(R.id.Dice_spinner3)

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

        Spinner_1.adapter = adapter
        Spinner_2.adapter = adapter
        Spinner_3.adapter = adapter
        Spinner_Sneak.adapter = adapter

            val bonus1 = view.findViewById(R.id.Edittext1) as EditText
            val bonus2 = view.findViewById(R.id.Edittext2) as EditText
            val bonus3 = view.findViewById(R.id.Edittext3) as EditText
            val EditModifer = view.findViewById(R.id.editHit) as EditText

        EditButton = view.findViewById(R.id.EditButton)
        AddButton = view.findViewById(R.id.Addbutton)

        val myListAdapter = ArrayList_Adapter(context as Activity,AllinAll2,listView)
        listView.adapter = myListAdapter

        EditButton.setOnClickListener {

            if (fileInfo.fileMain.exists()) {
              val ois = ObjectInputStream(FileInputStream(fileInfo.fileMain))
              AllinAll2 =  ois.readObject() as DataClass
              ois.close()
            }

            var EditText_signed=EditModifer.text.toString()
            if (EditModifer.text.toString().toInt()>0)
            {EditText_signed = "+" +EditModifer.text.toString().toInt().toString()
            }
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

            var SCHITKA = arrayOf(
                (spinAt1.selectedItem.toString()),
                (spinAt2.selectedItem.toString()),
                (spinAt3.selectedItem.toString()),
                Bonus1_signed,
                Bonus2_signed,
                Bonus3_signed,
                (Spinner_1.selectedItemId).toString(),
                (Spinner_2.selectedItemId).toString(),
                (Spinner_3.selectedItemId).toString(),
                EditText_signed,
                Enable_attacks[0].toString(),
                Enable_attacks[1].toString(),
                Attack_name.text.toString()
            )

//            if ( CurrentPositon <(AllinAll[0].size)) {
//                for (i in 0..12) {
//                    AllinAll[i][CurrentPositon] = SCHITKA[i]
//                }
//            }

            val myListAdapter = ArrayList_Adapter(context as Activity, AllinAll2,listView)
            listView.adapter = myListAdapter
            }

        AddButton.setOnClickListener {
            if (fileInfo.fileMain.exists()) {
                val ois = ObjectInputStream(FileInputStream(fileInfo.fileMain))
                AllinAll2 =  ois.readObject() as DataClass
                ois.close()
            }

            //Создали пустой массив
            val copyArray =  arrayOf(
             arrayOf<String>(),
             arrayOf<String>(),
             arrayOf<String>(),
                    arrayOf<String>(),
                    arrayOf<String>(),
                    arrayOf<String>(),
            arrayOf<String>(),
            arrayOf<String>(),
            arrayOf<String>(),
                arrayOf<String>(),
                arrayOf<String>(),
                arrayOf<String>(),
            arrayOf<String>(),
            )

            var EditTextSigned=EditModifer.text.toString()
            if (EditModifer.text.toString().toInt()>0) {
                EditTextSigned = "+" +EditModifer.text.toString().toInt().toString()
            }

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

            val SCHITKA = arrayOf(
                (spinAt1.selectedItem.toString()),
                (spinAt2.selectedItem.toString()),
                (spinAt3.selectedItem.toString()),
                Bonus1_signed,
                Bonus2_signed,
                Bonus3_signed,
                (Spinner_1.selectedItemId).toString(),
                (Spinner_2.selectedItemId).toString(),
                (Spinner_3.selectedItemId).toString(),
                EditTextSigned,
                Enable_attacks[0].toString(),
                Enable_attacks[1].toString(),
                Attack_name.text.toString()
            )

//            listView.adapter =  ArrayList_Adapter(context as Activity, AllinAll2,listView)
//                val f = FileOutputStream(fileInfo.fileMain)
//                val o = ObjectOutputStream(f)
//                //o.writeObject(AllinAll2)
//                o.close()
//                f.close()
            }

        var Switch2nd = view.findViewById(R.id.switchscnd) as Switch
        Switch2nd.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Enable_attacks[0] =1;
            } else {
                Enable_attacks[0]=0
            }
        }

        var Switch3d = view.findViewById(R.id.switchthird) as Switch
        Switch3d.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
               Enable_attacks[1] =1;
            } else {
               Enable_attacks[1]=0
            }
        }

        //Если нажать на список
        listView.onItemClickListener = OnItemClickListener { _, _, position, _ ->

//            var CopyArray = AllinAll2
//            if (file.exists())
//            {
//                val ois = ObjectInputStream(FileInputStream(file))
//                CopyArray =  ois.readObject() as Array<Array<String>>
//                ois.close()
//            }
//              spinAt1.setSelection(CopyArray[0][position].toInt()-1,true)
//              spinAt2.setSelection(CopyArray[1][position].toInt()-1,true)
//              spinAt3.setSelection(CopyArray[2][position].toInt()-1,true)
//           bonus1.setText(CopyArray[3][position].toInt().toString())
//           bonus2.setText(CopyArray[4][position].toInt().toString())
//           bonus3.setText(CopyArray[5][position].toInt().toString())
//             Spinner_1.setSelection(CopyArray[6][position].toInt() ,true)
//             Spinner_2.setSelection(CopyArray[7][position].toInt(),true)
//             Spinner_3.setSelection(CopyArray[8][position].toInt(),true)
//            EditModifer.setText(CopyArray[9][position].toInt().toString())
//
//            if (CopyArray[10][position].toInt() ==1) {
//                Switch2nd.isChecked = TRUE
//            }
//            else {
//                Switch2nd.isChecked = FALSE
//            }
//
//           if (CopyArray[11][position].toInt() ==1) {
//               Switch3d.isChecked = TRUE}
//            else{
//                Switch3d.isChecked = FALSE
//            }
//
//            EditButton.text = "Edit ".plus((position+1).toString())
//            CurrentPositon = position
//            Attack_name.text = CopyArray[12][position]
        }

        val SneakEdit = view.findViewById(R.id.sneak_edit) as EditText
        var SneakSwich = view.findViewById(R.id.sneak_switch) as Switch
        SneakSwich.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Sneak_attacks[0] =1;
            }
            else {
                Sneak_attacks[0]=0
            }
            Sneak_attacks[1]= Spinner_Sneak.selectedItemId.toInt()
            Sneak_attacks[2]= SneakEdit.text.toString().toInt()
            val f2 = FileOutputStream(fileInfo.fileSneak)
            val o2 = ObjectOutputStream(f2)
            o2.writeObject(Sneak_attacks)
            o2.close()
            f2.close()
        }
        //перезаписываем данные при изменении кубика сник атаки
        Spinner_Sneak.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?,itemSelected: View, selectedItemPosition: Int, selectedId: Long) {
                Sneak_attacks[1]= Spinner_Sneak.selectedItemId.toInt()
                Sneak_attacks[2]= SneakEdit.text.toString().toInt()
                val f2 = FileOutputStream(fileInfo.fileSneak)
                val o2 = ObjectOutputStream(f2)
                o2.writeObject(Sneak_attacks)
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
                Sneak_attacks[1]= Spinner_Sneak.selectedItemId.toInt()
                Sneak_attacks[2]= SneakEdit.text.toString().toInt()
                val f2 = FileOutputStream(fileInfo.fileSneak)
                val o2 = ObjectOutputStream(f2)
                o2.writeObject(Sneak_attacks)
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
