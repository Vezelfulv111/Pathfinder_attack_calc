package com.pathfinder.attackcalc

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.*

import android.widget.TextView


class ResultAdapter(
    private val context: Activity, private var Allinall: DataClass,
    private var Condition: Int,private var Temp_modif: IntArray,private var Sneak: IntArray,
    private var switch_on_of: Boolean
    )
    : ArrayAdapter<Any>(context, R.layout.result_view) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.result_view, null, true)
        //делаем список полосатым
        rowView.setBackgroundColor(if (position and 1 === 1) Color.DKGRAY else Color.GRAY)
        val header = rowView.findViewById(R.id.header) as TableRow
        if (position !=0) {
            header.visibility = GONE
        }
        val attacNum = rowView.findViewById(R.id.current_number) as TextView
        attacNum.text = (position+1).toString()

        val bonus1 = rowView.findViewById(R.id.bonus1) as TextView
        val bonus2 = rowView.findViewById(R.id.bonus2) as TextView
        val bonus3 = rowView.findViewById(R.id.bonus3) as TextView
            val img1 = rowView.findViewById(R.id.img1) as ImageView
            val img2 = rowView.findViewById(R.id.img2) as ImageView
            val img3 = rowView.findViewById(R.id.img3) as ImageView
        val diceAmount1 = rowView.findViewById(R.id.attack_num1) as TextView
        val diceAmount2 = rowView.findViewById(R.id.attack_num2) as TextView
        val diceAmount3 = rowView.findViewById(R.id.attack_num3) as TextView

        val hitModifier = rowView.findViewById(R.id.hit_modifier) as TextView

        val allElements = arrayOf(diceAmount1,diceAmount2 ,diceAmount3 ,bonus1,bonus2,bonus3)
        val allImgs = arrayOf(img1,img2,img3)

        //меняем картинки
        val images = intArrayOf(
            R.drawable.d3,
            R.drawable.d4,
            R.drawable.d6,
            R.drawable.d8,
            R.drawable.d10,
            R.drawable.d12,
        )
        //имя атаки
        val attackName = rowView.findViewById(R.id.attack_name_gen) as TextView
        attackName.text = Allinall.Attack_names[position]

        if (Allinall.Attack_names.isEmpty()) {//если строка пустая то убираем подпись
            attackName.visibility = GONE
        }


//        //тк по 3 компонента цикл по 3м
//        for (i in 0..2) {
//          allElements[i].text = (Allinall[i][position])
//          allElements[i+3].text = (Allinall[i+3][position])
//          allImgs[i].setImageResource( images[Allinall[i+6][position].toString().toInt()])
//        }


        allImgs[0].setImageResource( images[Allinall.im1_At2[position].toInt()])
        allImgs[1].setImageResource( images[Allinall.im2_At2[position].toInt()])
        allImgs[2].setImageResource( images[Allinall.im3_At2[position].toInt()])


        //условия по не отображению элементов
        val table2 = rowView.findViewById(R.id.table2) as TableRow
        val table3 = rowView.findViewById(R.id.table3) as TableRow

             if (Allinall.At2_enable[position].toInt() == 0) {
                table2.visibility = GONE
            }
            if (Allinall.At3_enable[position].toInt() == 0) {
                table3.visibility = GONE
            }

        //Генерация
        val gen1 = rowView.findViewById(R.id.answer1) as TextView
        val gen2 = rowView.findViewById(R.id.answer2) as TextView
        val gen3 = rowView.findViewById(R.id.answer3) as TextView
        if (Condition==1) {
            //бросок эн кубов
            val fstAtDice = DiceThrow(Allinall.im1_At2[position].toInt(),diceAmount1.text.toString().toInt())
            val sndAtDice = DiceThrow(Allinall.im2_At2[position].toInt(),diceAmount2.text.toString().toInt())
            val thirdDice = DiceThrow(Allinall.im3_At2[position].toInt(),diceAmount3.text.toString().toInt())

            //результат с бонусом
            val result1 =  fstAtDice + bonus1.text.toString().toInt()
            var result2 =  sndAtDice + bonus2.text.toString().toInt()
            var result3 =  thirdDice + bonus3.text.toString().toInt()

            gen1.text =  result1.toString()
            gen2.text =  result2.toString()
            gen3.text =  result3.toString()

            //теперь разбираемя с 20ткой
            val d20resust = rowView.findViewById(R.id.d20throw) as TextView
            val d20throw = (1..20).random()
            val toHit = hitModifier.text.toString().toInt()
            d20resust.text = "${toHit+d20throw+Temp_modif[0]}"
            //прикрепили результат броска к текствью
            val d20Kinuli = rowView.findViewById(R.id.d20_kinuli) as TextView
            d20Kinuli.text = d20throw.toString()

            //теперь разбираемя с cуммарным уроном
            val summ = rowView.findViewById(R.id.total_result) as TextView
            if (Allinall.At2_enable[position].toInt() == 0) {
                result2 = 0
            }
            if (Allinall.At3_enable[position].toInt() == 0) {
                result3 = 0
            }

            val sneak1 = rowView.findViewById(R.id.sneakky) as TextView
            var sneakThrow = 0
            if (Sneak[0]==1 && switch_on_of)
            {
             sneakThrow = DiceThrow(Sneak[1],Sneak[2])
            }

            summ.text = "${result1+result2+result3+Temp_modif[1]+sneakThrow}"
            sneak1.text = sneakThrow.toString()
        }
         return rowView
    }

    fun DiceThrow(inputdicenum:Int,numberofThrows:Int): Int
    {
        val dices = intArrayOf(
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