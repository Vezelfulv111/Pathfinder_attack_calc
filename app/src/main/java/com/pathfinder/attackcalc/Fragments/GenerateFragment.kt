package com.pathfinder.attackcalc.Fragments


import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.pathfinder.attackcalc.Adapters.GenerateAdapter
import com.pathfinder.attackcalc.DataClass
import com.pathfinder.attackcalc.Dices
import com.pathfinder.attackcalc.FileInfo
import com.pathfinder.attackcalc.R
import java.io.FileInputStream
import java.io.ObjectInputStream


class GenerateFragment : Fragment() {

    private var CONDITION = 0;

    private lateinit var listView: ListView
    private lateinit var GenButton: Button
    private lateinit var RefreshButton: Button

    private lateinit var Plus1: ImageButton
    private lateinit var Minus1: ImageButton
    private lateinit var Plus2: ImageButton
    private lateinit var Minus2: ImageButton

    var Temporary_modifers= intArrayOf(0, 0)
    private var AllinAll2 = DataClass();
    var fileInfo = FileInfo()

    @SuppressLint("ResourceType")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? {

        val view: View = inflater!!.inflate(R.layout.generate_fragment, container, false)
        listView =view.findViewById(R.id.result_list)


        if(fileInfo.fileMain.exists()) {
            val ois = ObjectInputStream(FileInputStream(fileInfo.fileMain))
            AllinAll2 =  ois.readObject() as DataClass
            ois.close()
        }

        val  snky_switch = view.findViewById(R.id.snky_switch) as Switch

           snky_switch.setOnClickListener() {
           if (AllinAll2.sneakEnable == 0) {
            Toast.makeText(context as Activity,"Set in settings window",Toast.LENGTH_SHORT).show()
            snky_switch.isChecked = false
           }
           else {
               snky_switch.isEnabled = true
           }
       }
        var ListAdapter = GenerateAdapter(context as Activity,AllinAll2,CONDITION,Temporary_modifers,snky_switch.isChecked)
        listView.adapter = ListAdapter
        GenButton = view.findViewById(R.id.gen_but);
        GenButton.setOnClickListener {
           CONDITION =1
           var ListAdapter = GenerateAdapter(context as Activity,AllinAll2,CONDITION,Temporary_modifers,snky_switch.isChecked)
           listView.adapter = ListAdapter
           CONDITION =0
        }

        RefreshButton = view.findViewById(R.id.refresh)
        RefreshButton.setOnClickListener {
            if(fileInfo.fileMain.exists()) {
                val ois = ObjectInputStream(FileInputStream(fileInfo.fileMain))
                AllinAll2 = ois.readObject() as DataClass
                ois.close()
                ListAdapter = GenerateAdapter(
                    context as Activity,
                    AllinAll2,
                    CONDITION,
                    Temporary_modifers,
                    snky_switch.isChecked
                )
                ois.close()
            }
            listView.adapter = ListAdapter
        }

        Plus1 = view.findViewById(R.id.Fstplus);
        Minus1 = view.findViewById(R.id.Fstminus);
        Plus2 = view.findViewById(R.id.Fstplus2);
        Minus2 = view.findViewById(R.id.Fstminus2);
        val hitbonus = view.findViewById(R.id.hitbonus) as TextView
        val hitbonus2 = view.findViewById(R.id.hitbonus2) as TextView

        Plus1.setOnClickListener {
            Temporary_modifers[0] = Temporary_modifers[0]+1
            hitbonus.text = Temporary_modifers[0].toString()
        }
        Minus1.setOnClickListener {
            Temporary_modifers[0] = Temporary_modifers[0]-1
            hitbonus.text =  Temporary_modifers[0].toString()
        }
        Plus2.setOnClickListener {
            Temporary_modifers[1] = Temporary_modifers[1]+1
            hitbonus2.text = Temporary_modifers[1].toString()
        }
        Minus2.setOnClickListener {
            Temporary_modifers[1] = Temporary_modifers[1]-1
            hitbonus2.text =  Temporary_modifers[1].toString()
        }


        listView.setOnItemClickListener { parent, view, position, idd ->
            val RezD20 = view.findViewById(R.id.d20throw) as TextView
            val D20 = view.findViewById(R.id.d20_kinuli) as TextView
            val modd20 = view.findViewById(R.id.hit_modifier) as TextView

            val A = (1..20).random()
            val B = modd20.text.toString().toInt()
            D20.text  =A.toString()
            RezD20.text = (A+B+Temporary_modifers[0]).toString()

            //теперь урон
            val diceamount1 = view.findViewById(R.id.attack_num1) as TextView
            val diceamount2 = view.findViewById(R.id.attack_num2) as TextView
            val diceamount3 = view.findViewById(R.id.attack_num3) as TextView
            val FstAtDice = DiceThrow(AllinAll2.img1[position].toInt(),diceamount1.text.toString().toInt());
            val SndAtDice = DiceThrow(AllinAll2.img2[position].toInt(),diceamount2.text.toString().toInt() );
            val ThirdDice = DiceThrow(AllinAll2.img3[position].toInt(),diceamount3.text.toString().toInt()  );

            val Bonus1 = view.findViewById(R.id.bonus1) as TextView
            val Bonus2 = view.findViewById(R.id.bonus2) as TextView
            val Bonus3 = view.findViewById(R.id.bonus3) as TextView
            //результат с бонусом
            val Result1 =  FstAtDice + Bonus1.text.toString().toInt()
            var Result2 =  SndAtDice + Bonus2.text.toString().toInt()
            var Result3 =  ThirdDice + Bonus3.text.toString().toInt()

            val Gen1 = view.findViewById(R.id.answer1) as TextView
            val Gen2 = view.findViewById(R.id.answer2) as TextView
            val Gen3 = view.findViewById(R.id.answer3) as TextView

            Gen1.text =  Result1.toString()
            Gen2.text =  Result2.toString()
            Gen3.text =  Result3.toString()

            val Summ = view.findViewById(R.id.total_result) as TextView
            if (AllinAll2.at2Enable[position].toInt() == 0)
                Result2 = 0

            if (AllinAll2.at3Enable[position].toInt() == 0)
                Result3 = 0

            Summ.text = (Result1+Result2+Result3+Temporary_modifers[1]).toString()
            var SneakThrow = 0

            val sneak1 = view.findViewById(R.id.sneakky) as TextView
            if (AllinAll2.sneakEnable == 1 && snky_switch.isChecked) {
                SneakThrow = DiceThrow(AllinAll2.sneakDicetype, AllinAll2.sneakNum)
                sneak1.text = SneakThrow.toString()
            }
            else {
                sneak1.text = "0"
            }
            Summ.text = (Result1+Result2+Result3+Temporary_modifers[1]+SneakThrow).toString()

        }
        return view

        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun DiceThrow(inputdicenum:Int, numberofThrows:Int): Int
    {
        var rez = 0;
        for (i in 1..numberofThrows)
        {
            rez += (1..Dices.dices[inputdicenum]).random()
        }
        return rez
    }

    override fun onResume() {
        super.onResume()

        var  snky_switch = view?.findViewById(R.id.snky_switch) as Switch
        if(fileInfo.fileMain.exists()) {
            val ois = ObjectInputStream(FileInputStream(fileInfo.fileMain))
            AllinAll2 =  ois.readObject() as DataClass
            ois.close()
        }
        listView = view?.findViewById(R.id.result_list)!!
        val ListAdapter = GenerateAdapter(context as Activity,AllinAll2,CONDITION,Temporary_modifers,snky_switch.isChecked)
        listView.adapter = ListAdapter

            if (AllinAll2.sneakEnable == 1) {
                val str = AllinAll2.sneakNum.toString() +"d"+ Dices.dices[AllinAll2.sneakDicetype].toString()
                snky_switch.text = str
            }
            else {
                snky_switch.text="None"
                snky_switch.isChecked = false
            }



    }
}