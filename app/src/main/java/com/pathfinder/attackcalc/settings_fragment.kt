package com.pathfinder.attackcalc


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
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

import android.widget.Toast










class settings_fragment : Fragment() {

    val All = "AllinAll2.txt"
    val file = File("/data/data/com.pathfinder.attackcalc/" + File.separator + All)

    val Sneak = "Sneak2.txt"
    val file_sneak = File("/data/data/com.pathfinder.attackcalc/" + File.separator + Sneak)

    private lateinit var adapter: spinadapter
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

    //проверка два
    var Hit_modifier2 = arrayOf("+9", "+20", "+0")
    var X12 = arrayOf("1", "2", "3")
    var X22 = arrayOf("1", "2", "3")
    var X32 = arrayOf("1", "2", "3")

    var plus12 = arrayOf("-1", "+2", "-3")
    var plus22 = arrayOf("-1", "+2", "+0")
    var plus32 = arrayOf("-1", "+2", "-3")

    var im1_At2 = arrayOf("1", "2", "3")
    var im2_At2 = arrayOf("1", "2", "3")
    var im3_At2 = arrayOf("4", "4", "4")


    var At2_enable = arrayOf("0", "1", "0")
    var At3_enable = arrayOf("1", "1", "0")


    var Dices = arrayOf("1", "2","3","4","5","6")

    var Attack_names = arrayOf("sai", "saber +1","kukri")

    var AllinAll = arrayOf(X12,X22,X32,plus12,plus22,plus32,im1_At2,im2_At2,im3_At2,Hit_modifier2,At2_enable,At3_enable,Attack_names)




    @SuppressLint("ResourceType")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? {
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.fragment_settings, container, false)
        Spinner_1 = view.findViewById<Spinner>(R.id.Dice_spinner1)
        Spinner_2 = view.findViewById<Spinner>(R.id.Dice_spinner2)
        Spinner_3 = view.findViewById<Spinner>(R.id.Dice_spinner3)

        var SpinAt1 = view.findViewById<Spinner>(R.id.Spin_n1) as Spinner
        var SpinAt2 = view.findViewById<Spinner>(R.id.Spin_n2) as Spinner
        var SpinAt3 = view.findViewById<Spinner>(R.id.Spin_n3) as Spinner

        val arrayAdapter: ArrayAdapter<Any> = ArrayAdapter<Any>(context as Activity,
            R.layout.spiner_123, resources.getStringArray(R.array.SpinNumbers))
        SpinAt1.adapter = arrayAdapter;
        SpinAt2.adapter = arrayAdapter;
        SpinAt3.adapter = arrayAdapter;

        if (file.exists())
        {
            val ois = ObjectInputStream(FileInputStream(file))
            AllinAll =  ois.readObject() as Array<Array<String>>
            ois.close()
        }

        var Attack_name = view.findViewById<TextView>(R.id.enter_at_name)

        Spinner_Sneak = view.findViewById<Spinner>(R.id.sneak_spinner)
        listView =view.findViewById(R.id.listView)

        //Добавили картинки к спинерам
        adapter =  spinadapter(context,Dices,images)

        Spinner_1.adapter = adapter
        Spinner_2.adapter = adapter
        Spinner_3.adapter = adapter
        Spinner_Sneak.adapter = adapter

            val Bonus1 = view.findViewById(R.id.Edittext1) as EditText
            val Bonus2 = view.findViewById(R.id.Edittext2) as EditText
            val Bonus3 = view.findViewById(R.id.Edittext3) as EditText
            var EditModifer = view.findViewById(R.id.editHit) as EditText

        EditButton = view.findViewById(R.id.EditButton)
        AddButton = view.findViewById(R.id.Addbutton)

        var myListAdapter = ArrayList_Adapter(context as Activity,AllinAll,listView)
        listView.adapter = myListAdapter

        EditButton.setOnClickListener {


            if (file.exists())
            {
              val ois = ObjectInputStream(FileInputStream(file))
              AllinAll =  ois.readObject() as Array<Array<String>>
              ois.close()
            }

            var EditText_signed=EditModifer.text.toString();
            if (EditModifer.text.toString().toInt()>0)
            {EditText_signed = "+" +EditModifer.text.toString().toInt().toString();
            }
            var Bonus1_signed=Bonus1.text.toString();
            var Bonus2_signed=Bonus2.text.toString();
            var Bonus3_signed=Bonus3.text.toString();
            if (Bonus1.text.toString().toInt()>0)
            {Bonus1_signed = "+" +Bonus1.text.toString().toInt().toString();
            }
            if (Bonus2.text.toString().toInt()>0)
            {Bonus2_signed = "+" +Bonus2.text.toString().toInt().toString();//для красоты;//для красоты
            }
            if (Bonus3.text.toString().toInt()>0)
            {Bonus3_signed = "+" +Bonus3.text.toString().toInt().toString();//для красоты;//для красоты
            }

            var SCHITKA = arrayOf(
                (SpinAt1.selectedItem.toString()),
                (SpinAt2.selectedItem.toString()),
                (SpinAt3.selectedItem.toString()),
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


            if ( CurrentPositon <(AllinAll[0].size))
            {
                for (i in 0..12)
                { AllinAll[i][CurrentPositon] = SCHITKA[i];}
            }

            var myListAdapter = ArrayList_Adapter(context as Activity, AllinAll,listView)
            listView.adapter = myListAdapter
            }

        AddButton.setOnClickListener {
            if (file.exists())
            {
                val ois = ObjectInputStream(FileInputStream(file))
                AllinAll =  ois.readObject() as Array<Array<String>>
                ois.close()
            }

            //Создали пустой массив
            var copyArray =  arrayOf(
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

            var EditText_signed=EditModifer.text.toString();
            if (EditModifer.text.toString().toInt()>0)
            {EditText_signed = "+" +EditModifer.text.toString().toInt().toString();//для красоты;//для красоты
            }

            var Bonus1_signed=Bonus1.text.toString();
            var Bonus2_signed=Bonus2.text.toString();
            var Bonus3_signed=Bonus3.text.toString();
            if (Bonus1.text.toString().toInt()>0)
            {Bonus1_signed = "+" +Bonus1.text.toString().toInt().toString();//для красоты
            }
            if (Bonus2.text.toString().toInt()>0)
            {Bonus2_signed = "+" +Bonus2.text.toString().toInt().toString();//для красоты
            }
            if (Bonus3.text.toString().toInt()>0)
            {Bonus3_signed = "+" +Bonus3.text.toString().toInt().toString();//для красоты
            }


            var SCHITKA = arrayOf(
                (SpinAt1.selectedItem.toString()),
                (SpinAt2.selectedItem.toString()),
                (SpinAt3.selectedItem.toString()),
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


            for (i in 0..12)
            {
                 copyArray[i] = AllinAll[i]+SCHITKA[i]
            }
            listView.adapter =  ArrayList_Adapter(context as Activity, copyArray,listView)
                val f = FileOutputStream(file)
                val o = ObjectOutputStream(f)
                o.writeObject(copyArray)
                o.close()
                f.close()
            }




        var Switch_2nd = view.findViewById(R.id.switchscnd) as Switch
        Switch_2nd.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Enable_attacks[0] =1;
            } else {
                Enable_attacks[0]=0
            }
        }



        var Switch_3d = view.findViewById(R.id.switchthird) as Switch
        Switch_3d.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
               Enable_attacks[1] =1;
            } else {
               Enable_attacks[1]=0
            }
        }



        //Если нажать на список
        listView.onItemClickListener = OnItemClickListener { _, _, position, _ ->

            var CopyArray = AllinAll
            if (file.exists())
            {
                val ois = ObjectInputStream(FileInputStream(file))
                CopyArray =  ois.readObject() as Array<Array<String>>
                ois.close()
            }



              SpinAt1.setSelection(CopyArray[0][position].toInt()-1,true)
              SpinAt2.setSelection(CopyArray[1][position].toInt()-1,true)
              SpinAt3.setSelection(CopyArray[2][position].toInt()-1,true)
           Bonus1.setText(CopyArray[3][position].toInt().toString())
           Bonus2.setText(CopyArray[4][position].toInt().toString())
           Bonus3.setText(CopyArray[5][position].toInt().toString())
             Spinner_1.setSelection(CopyArray[6][position].toInt() ,true)
             Spinner_2.setSelection(CopyArray[7][position].toInt(),true)
             Spinner_3.setSelection(CopyArray[8][position].toInt(),true)
            EditModifer.setText(CopyArray[9][position].toInt().toString())

            if (CopyArray[10][position].toInt() ==1)
            { Switch_2nd.isChecked = TRUE}
            else{ Switch_2nd.isChecked = FALSE}

           if (CopyArray[11][position].toInt() ==1)
            {Switch_3d.isChecked = TRUE}
            else{Switch_3d.isChecked = FALSE}


            EditButton.text = "Edit ".plus((position+1).toString())
            CurrentPositon = position.toInt();
            Attack_name.setText(CopyArray[12][position].toString())
        }

        val SneakEdit = view.findViewById(R.id.sneak_edit) as EditText
        var Sneak_swich = view.findViewById(R.id.sneak_switch) as Switch
        Sneak_swich.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Sneak_attacks[0] =1;

            } else {
                Sneak_attacks[0]=0
            }
            Sneak_attacks[1]= Spinner_Sneak.selectedItemId.toInt()
            Sneak_attacks[2]= SneakEdit.text.toString().toInt()
            val f2 = FileOutputStream(file_sneak)
            val o2 = ObjectOutputStream(f2)
            o2.writeObject(Sneak_attacks)
            o2.close()
            f2.close()
        }

        //перезаписываем данные при изменении кубика сник атаки
        Spinner_Sneak.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?,itemSelected: View, selectedItemPosition: Int, selectedId: Long)
            {
                Sneak_attacks[1]= Spinner_Sneak.selectedItemId.toInt()
                Sneak_attacks[2]= SneakEdit.text.toString().toInt()
                val f2 = FileOutputStream(file_sneak)
                val o2 = ObjectOutputStream(f2)
                o2.writeObject(Sneak_attacks)
                o2.close()
                f2.close()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        //перезаписываем при изменении числа кубиков
        SneakEdit.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,before: Int, count: Int) {
                Sneak_attacks[1]= Spinner_Sneak.selectedItemId.toInt()
                Sneak_attacks[2]= SneakEdit.text.toString().toInt()
                val f2 = FileOutputStream(file_sneak)
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
