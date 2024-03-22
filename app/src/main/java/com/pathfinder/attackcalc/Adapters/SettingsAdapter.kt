package com.pathfinder.attackcalc.Adapters

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.*

import android.widget.TextView
import com.pathfinder.attackcalc.R
import com.pathfinder.attackcalc.presenters.PresenterSettingsFragment


class SettingsAdapter(private val context: Activity, private var presenter: PresenterSettingsFragment,
                      private val listview: ListView
)
    : ArrayAdapter<Any>(context, R.layout.attac_st_listview, presenter.AllinAll.numDice1.toArray()) {


    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = view ?: inflater.inflate(R.layout.attac_st_listview, null, true)
        //делаем список полосатым
        rowView.setBackgroundColor(if (position and 1 == 1) {
            Color.DKGRAY
        } else Color.GRAY)

        val header = rowView.findViewById(R.id.settings_header) as TableRow
        if (position != 0) {
            header.visibility = View.GONE
        }

        val Allinall = presenter.AllinAll

        val Attac_num = rowView.findViewById(R.id.cur_num) as TextView
        Attac_num.text = (position+1).toString()

        val bonus1 = rowView.findViewById(R.id.bonus1) as TextView
        val bonus2 = rowView.findViewById(R.id.bonus2) as TextView
        val bonus3 = rowView.findViewById(R.id.bonus3) as TextView
            val img1 = rowView.findViewById(R.id.img1) as ImageView
            val img2 = rowView.findViewById(R.id.img2) as ImageView
            val img3 = rowView.findViewById(R.id.img3) as ImageView
        val plus1 = rowView.findViewById(R.id.attack_num1) as TextView
        val plus2 = rowView.findViewById(R.id.attack_num2) as TextView
        val plus3 = rowView.findViewById(R.id.attack_num3) as TextView

        val hit_modifier = rowView.findViewById(R.id.hit_modifier) as TextView

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
        val attack_name = rowView.findViewById(R.id.attack_name) as TextView
        attack_name.text = Allinall.attackName[position]

        //условия по не отображению элементов
        val Table2 = rowView.findViewById(R.id.table2) as TableRow
        val Table3 = rowView.findViewById(R.id.table3) as TableRow

        hit_modifier.text = Allinall.hitModifier[position]
        plus1.text = Allinall.numDice1[position]
        plus2.text = Allinall.numDice2[position]
        plus3.text = Allinall.numDice3[position]

        bonus1.text = Allinall.bonus1[position]
        bonus2.text = Allinall.bonus2[position]
        bonus3.text = Allinall.bonus3[position]

        img1.setImageResource(images[Allinall.img1[position].toInt()])
        img2.setImageResource(images[Allinall.img2[position].toInt()])
        img3.setImageResource(images[Allinall.img3[position].toInt()])

        if (Allinall.at2Enable[position].toInt() == 0)
            Table2.visibility = View.GONE
        if (Allinall.at3Enable[position].toInt() == 0)
            Table3.visibility = View.GONE

        val DelBut = rowView.findViewById(R.id.Delbut) as Button

        DelBut.setOnClickListener {
            presenter.AllinAll.removeAt(position)
            presenter.writeData()
            listview.adapter = SettingsAdapter(context, presenter, listview)
        }

        return rowView
    }

}