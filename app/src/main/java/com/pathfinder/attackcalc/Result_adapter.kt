package com.pathfinder.attackcalc

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.*

import android.widget.TextView


class Result_adapter(
    private val context: Activity, private var Allinall: Array<Array<String>>,
    private var Condition: Int,private var Temp_modif: IntArray,private var Sneak: IntArray

    )
    : ArrayAdapter<Any>(context, R.layout.result_view, Allinall[1]) {




    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.result_view, null, true)
        //делаем список полосатым
        rowView.setBackgroundColor(if (position and 1 === 1) Color.DKGRAY else Color.GRAY)


        val Header = rowView.findViewById(R.id.header) as TableRow
        if (position !=0)
        {Header.visibility = View.GONE}

        val Attac_num = rowView.findViewById(R.id.current_number) as TextView
        Attac_num.text = (position+1).toString()




        val Bonus1 = rowView.findViewById(R.id.bonus1) as TextView
        val Bonus2 = rowView.findViewById(R.id.bonus2) as TextView
        val Bonus3 = rowView.findViewById(R.id.bonus3) as TextView
            val img1 = rowView.findViewById(R.id.img1) as ImageView
            val img2 = rowView.findViewById(R.id.img2) as ImageView
            val img3 = rowView.findViewById(R.id.img3) as ImageView
        val diceamount1 = rowView.findViewById(R.id.attack_num1) as TextView
        val diceamount2 = rowView.findViewById(R.id.attack_num2) as TextView
        val diceamount3 = rowView.findViewById(R.id.attack_num3) as TextView

        var hit_modifier = rowView.findViewById(R.id.hit_modifier) as TextView




        var AllElements = arrayOf(diceamount1,diceamount2 ,diceamount3 ,Bonus1,Bonus2,Bonus3)
        var AllImgs = arrayOf(img1,img2,img3)

        //меняем картинки
        var images = intArrayOf(
            R.drawable.d3,
            R.drawable.d4,
            R.drawable.d6,
            R.drawable.d8,
            R.drawable.d10,
            R.drawable.d12,

        )

        //тк по 3 компонента цикл по 3м
        for (i in 0..2)
        {
          AllElements[i].text = (Allinall[i][position] as String)
          AllElements[i+3].text = (Allinall[i+3][position] as String)
          AllImgs[i].setImageResource( images[Allinall[i+6][position].toString().toInt() as Int])
        }
        hit_modifier.text = Allinall[9][position] as String

        //условия по не отображению элементов
        val Table2 = rowView.findViewById(R.id.table2) as TableRow
        val Table3 = rowView.findViewById(R.id.table3) as TableRow
            if (Allinall[10][position].toInt() == 0)
            {Table2.visibility = View.GONE}
            if (Allinall[11][position].toInt() == 0)
            {Table3.visibility = View.GONE}

        //Генерация
        val Gen1 = rowView.findViewById(R.id.answer1) as TextView
        val Gen2 = rowView.findViewById(R.id.answer2) as TextView
        val Gen3 = rowView.findViewById(R.id.answer3) as TextView
        if (Condition==1)
        {

            //бросок эн кубов
            var FstAtDice = DiceThrow(Allinall[6][position].toInt(),diceamount1.text.toString().toInt());
            var SndAtDice = DiceThrow(Allinall[7][position].toInt(),diceamount2.text.toString().toInt() );
            var ThirdDice = DiceThrow(Allinall[8][position].toInt(),diceamount3.text.toString().toInt()  );

            //результат с бонусом
            var Result1 =  FstAtDice + Bonus1.text.toString().toInt()
            var Result2 =  SndAtDice + Bonus2.text.toString().toInt()
            var Result3 =  ThirdDice + Bonus3.text.toString().toInt()


            Gen1.text =  Result1.toString()
            Gen2.text =  Result2.toString()
            Gen3.text =  Result3.toString()

            //теперь разбираемя с 20ткой
            val D20resust = rowView.findViewById(R.id.d20throw) as TextView
            val d20throw = (1..20).random()
            var toHit = hit_modifier.text.toString().toInt()
            D20resust.text = (toHit+d20throw+Temp_modif[0]).toString()
            //прикрепили результат броска к текствью
            val d20_kinuli = rowView.findViewById(R.id.d20_kinuli) as TextView
            d20_kinuli.text = d20throw.toString()

            //теперь разбираемя с cуммарным уроном
            val Summ = rowView.findViewById(R.id.total_result) as TextView
            if (Allinall[10][position].toInt() == 0)
            {Result2 = 0}
            if (Allinall[11][position].toInt() == 0)
            {Result3 = 0}

            val sneak1 = rowView.findViewById(R.id.sneakky) as TextView
            var SneakThrow = 0
            if (Sneak[0]==1)
            {
             SneakThrow = DiceThrow(Sneak[1],Sneak[2])
            }

            Summ.text = (Result1+Result2+Result3+Temp_modif[1]+SneakThrow).toString()
            sneak1.text = SneakThrow.toString()
        }





         return rowView



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



}