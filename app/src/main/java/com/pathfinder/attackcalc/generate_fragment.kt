package com.pathfinder.attackcalc


import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.io.File
import java.io.FileInputStream
import java.io.ObjectInputStream


class generate_fragment : Fragment() {

    var CONDITION = 0;

    private lateinit var listView: ListView
    var Sneak_attacks= intArrayOf(0, 0,0)

    val All = "AllinAll2.txt"
    val file = File("/data/data/com.pathfinder.attackcalc/" + File.separator + All)

    val Sneak = "Sneak2.txt"
    val file_sneak = File("/data/data/com.pathfinder.attackcalc/" + File.separator + Sneak)

    private lateinit var GenButton: Button
    private lateinit var RefreshButton: Button

    private lateinit var Plus1: ImageButton
    private lateinit var Minus1: ImageButton
    private lateinit var Plus2: ImageButton
    private lateinit var Minus2: ImageButton
    var Temporary_modifers= intArrayOf(0, 0)

    var Hit_modifier2 = arrayOf("+9", "+20", "-2")
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
    var Attack_names = arrayOf("sai", "saber +1","kukri")

    var AllinAll = arrayOf(X12,X22,X32,plus12,plus22,plus32,im1_At2,im2_At2,im3_At2,Hit_modifier2,At2_enable,At3_enable,Attack_names)

    var dices = intArrayOf(
        3,
        4,
        6,
        8,
        10,
        12,
    )



    @SuppressLint("ResourceType")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? {

        val view: View = inflater!!.inflate(R.layout.generate_fragment, container, false)
        listView =view.findViewById(R.id.result_list)

        if(file.exists())
        {   val ois = ObjectInputStream(FileInputStream(file))
            AllinAll =  ois.readObject() as Array<Array<String>>
            ois.close()
        }


        var  snky_switch = view.findViewById(R.id.snky_switch) as Switch





        if(file_sneak.exists())
        {   val o2 = ObjectInputStream(FileInputStream(file_sneak))
            Sneak_attacks =  o2.readObject() as IntArray
            o2.close()
            if (Sneak_attacks[0]==1)
            {
             snky_switch.text = Sneak_attacks[2].toString() +"d"+ dices[Sneak_attacks[1]].toString()
            }
            else
            { snky_switch.text ="None"}
        }

       snky_switch.setOnClickListener()
       {


           if (Sneak_attacks[0]==0)
           {
            Toast.makeText(context as Activity,"Set in settings window",Toast.LENGTH_SHORT).show();
            snky_switch.isChecked = false;
           }
           else
           {snky_switch.isEnabled = true;}

       }




        var ListAdapter = Result_adapter(context as Activity,AllinAll,CONDITION,Temporary_modifers,Sneak_attacks,snky_switch.isChecked)
        listView.adapter = ListAdapter


        GenButton = view.findViewById(R.id.gen_but);
        GenButton.setOnClickListener {
           CONDITION =1;
           var ListAdapter = Result_adapter(context as Activity,AllinAll,CONDITION,Temporary_modifers,Sneak_attacks,snky_switch.isChecked)
           listView.adapter = ListAdapter
           CONDITION =0;
        }

        RefreshButton = view.findViewById(R.id.refresh)
        RefreshButton.setOnClickListener {

            if(file.exists()) {
                val ois = ObjectInputStream(FileInputStream(file))
                AllinAll = ois.readObject() as Array<Array<String>>
                ois.close()
                ListAdapter = Result_adapter(
                    context as Activity,
                    AllinAll,
                    CONDITION,
                    Temporary_modifers,
                    Sneak_attacks,
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
            Temporary_modifers[0] = Temporary_modifers[0]+1;
            hitbonus.text = Temporary_modifers[0].toString()
        }
        Minus1.setOnClickListener {
            Temporary_modifers[0] = Temporary_modifers[0]-1;
            hitbonus.text =  Temporary_modifers[0].toString()
        }
        Plus2.setOnClickListener {
            Temporary_modifers[1] = Temporary_modifers[1]+1;
            hitbonus2.text = Temporary_modifers[1].toString()
        }
        Minus2.setOnClickListener {
            Temporary_modifers[1] = Temporary_modifers[1]-1;
            hitbonus2.text =  Temporary_modifers[1].toString()
        }


        listView.setOnItemClickListener { parent, view, position, idd ->
            val RezD20 = view.findViewById(R.id.d20throw) as TextView
            val D20 = view.findViewById(R.id.d20_kinuli) as TextView
            val modd20 = view.findViewById(R.id.hit_modifier) as TextView

            var A = (1..20).random()
            var B = modd20.text.toString().toInt()
            D20.text  =A.toString()
            RezD20.text = (A+B+Temporary_modifers[0]).toString()


            //теперь урон
            val diceamount1 = view.findViewById(R.id.attack_num1) as TextView
            val diceamount2 = view.findViewById(R.id.attack_num2) as TextView
            val diceamount3 = view.findViewById(R.id.attack_num3) as TextView
            var FstAtDice = DiceThrow(AllinAll[6][position].toInt(),diceamount1.text.toString().toInt());
            var SndAtDice = DiceThrow(AllinAll[7][position].toInt(),diceamount2.text.toString().toInt() );
            var ThirdDice = DiceThrow(AllinAll[8][position].toInt(),diceamount3.text.toString().toInt()  );

            val Bonus1 = view.findViewById(R.id.bonus1) as TextView
            val Bonus2 = view.findViewById(R.id.bonus2) as TextView
            val Bonus3 = view.findViewById(R.id.bonus3) as TextView
            //результат с бонусом
            var Result1 =  FstAtDice + Bonus1.text.toString().toInt()
            var Result2 =  SndAtDice + Bonus2.text.toString().toInt()
            var Result3 =  ThirdDice + Bonus3.text.toString().toInt()

            val Gen1 = view.findViewById(R.id.answer1) as TextView
            val Gen2 = view.findViewById(R.id.answer2) as TextView
            val Gen3 = view.findViewById(R.id.answer3) as TextView

            Gen1.text =  Result1.toString()
            Gen2.text =  Result2.toString()
            Gen3.text =  Result3.toString()

            val Summ = view.findViewById(R.id.total_result) as TextView
            if (AllinAll[10][position].toInt() == 0)
            {Result2 = 0}
            if (AllinAll[11][position].toInt() == 0)
            {Result3 = 0}
            Summ.text = (Result1+Result2+Result3+Temporary_modifers[1]).toString()
            var SneakThrow = 0

            val sneak1 = view.findViewById(R.id.sneakky) as TextView
            if (Sneak_attacks[0]==1 && snky_switch.isChecked) {
                SneakThrow = DiceThrow(Sneak_attacks[1], Sneak_attacks[2])
                sneak1.text = SneakThrow.toString()
            }
            else
            { sneak1.text = "0"}
            Summ.text = (Result1+Result2+Result3+Temporary_modifers[1]+SneakThrow).toString()

        }
        return view


        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun DiceThrow(inputdicenum:Int,numberofThrows:Int): Int
    {

        var dices = intArrayOf(
            3,
            4,
            6,
            8,
            10,
            12,
        )

        var rez = 0;
        for (i in 1..numberofThrows)
        {
            rez += (1..dices[inputdicenum]).random()
        }
        return rez
    }

    override fun onResume() {
        super.onResume()

        var  snky_switch = view?.findViewById(R.id.snky_switch) as Switch
        if(file.exists())
        {   val ois = ObjectInputStream(FileInputStream(file))
            AllinAll =  ois.readObject() as Array<Array<String>>
            ois.close()
        }
        listView = view?.findViewById(R.id.result_list)!!
        var ListAdapter = Result_adapter(context as Activity,AllinAll,CONDITION,Temporary_modifers,Sneak_attacks,snky_switch.isChecked)
        listView.adapter = ListAdapter
        if(file_sneak.exists()) {
            val o2 = ObjectInputStream(FileInputStream(file_sneak))
            Sneak_attacks = o2.readObject() as IntArray
            o2.close()
        }


        if(file_sneak.exists())
        {   val o2 = ObjectInputStream(FileInputStream(file_sneak))
            Sneak_attacks =  o2.readObject() as IntArray
            o2.close()
            if (Sneak_attacks[0]==1)
            { snky_switch.text = Sneak_attacks[2].toString() +"d"+ dices[Sneak_attacks[1]].toString()}
            else
            {snky_switch.text="None"
             snky_switch.isChecked = false;}
        }


    }
}