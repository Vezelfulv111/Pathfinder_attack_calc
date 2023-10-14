package com.pathfinder.attackcalc.adapters

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.*

import android.widget.TextView
import com.pathfinder.attackcalc.R
import com.pathfinder.attackcalc.presenters.PresenterGenerateFragment


class GenerateAdapter(
    private val context: Activity,
    private var Condition: Int,
    private var switch_on_of: Boolean,
    private var presenter: PresenterGenerateFragment
    )
    : ArrayAdapter<Any>(context, R.layout.result_view, presenter.AllinAll.numDice1.toArray()) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = view ?: inflater.inflate(R.layout.result_view, null, true)
        //делаем список полосатым
        rowView.setBackgroundColor(if (position and 1 == 1) Color.DKGRAY else Color.GRAY)
        val header = rowView.findViewById(R.id.header) as TableRow
        if (position != 0) {
            header.visibility = GONE
        }
        val Allinall = presenter.AllinAll

        val attacNum = rowView.findViewById(R.id.current_number) as TextView
        (position+1).toString().also { attacNum.text = it }

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
        attackName.text = Allinall.attackName[position]

        if (Allinall.attackName.isEmpty()) {//если строка пустая то убираем подпись
            attackName.visibility = GONE
        }

        img1.setImageResource( images[Allinall.img1[position].toInt()])
        img2.setImageResource( images[Allinall.img2[position].toInt()])
        img3.setImageResource( images[Allinall.img3[position].toInt()])

        hitModifier.text = Allinall.hitModifier[position]

        bonus1.text = Allinall.bonus1[position]
        bonus2.text = Allinall.bonus2[position]
        bonus3.text = Allinall.bonus3[position]

        diceAmount1.text = Allinall.numDice1[position]
        diceAmount2.text = Allinall.numDice2[position]
        diceAmount3.text = Allinall.numDice3[position]

        //условия по не отображению элементов
        val table2 = rowView.findViewById(R.id.table2) as TableRow
        val table3 = rowView.findViewById(R.id.table3) as TableRow

        if (Allinall.at2Enable[position].toInt() == 0) {
                table2.visibility = GONE
        }
        if (Allinall.at3Enable[position].toInt() == 0) {
                table3.visibility = GONE
        }

        //Генерация
        if (Condition==1) {
            //бросок эн кубов
            val throwData = presenter.throwComputation(position, switch_on_of)

            val gen1 = rowView.findViewById(R.id.answer1) as TextView
            val gen2 = rowView.findViewById(R.id.answer2) as TextView
            val gen3 = rowView.findViewById(R.id.answer3) as TextView
            gen1.text =  throwData.dmgRoll1.toString()
            gen2.text =  throwData.dmgRoll2.toString()
            gen3.text =  throwData.dmgRoll3.toString()

            //теперь разбираемcя с 20ткой
            val d20resust = rowView.findViewById(R.id.d20throw) as TextView
            d20resust.text = throwData.d20Total.toString()

            val d20Kinuli = rowView.findViewById(R.id.d20_kinuli) as TextView
            d20Kinuli.text = throwData.d20Throw.toString()

            //теперь разбираемя с cуммарным уроном
            val summ = rowView.findViewById(R.id.total_result) as TextView
            summ.text = throwData.totalDamageWithSneak.toString()

            val sneak1 = rowView.findViewById(R.id.sneakky) as TextView
            sneak1.text = throwData.sneakDmg.toString()
        }
         return rowView
    }

}