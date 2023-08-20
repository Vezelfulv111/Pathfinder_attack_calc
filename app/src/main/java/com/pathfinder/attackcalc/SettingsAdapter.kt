package com.pathfinder.attackcalc

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.*

import android.widget.TextView


class SettingsAdapter(private val context: Activity, private var Allinall: DataClass,
                      private val listview: ListView
)
    : ArrayAdapter<Any>(context, R.layout.attac_st_listview, Allinall.X12.toArray()) {


    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.attac_st_listview, null, true)
        //делаем список полосатым
        rowView.setBackgroundColor(if (position and 1 == 1) {
            Color.DKGRAY
        } else Color.GRAY)

        val Attac_num = rowView.findViewById(R.id.cur_num) as TextView
        Attac_num.text = (position+1).toString()

        val Bonus1 = rowView.findViewById(R.id.bonus1) as TextView
        val Bonus2 = rowView.findViewById(R.id.bonus2) as TextView
        val Bonus3 = rowView.findViewById(R.id.bonus3) as TextView
            val img1 = rowView.findViewById(R.id.img1) as ImageView
            val img2 = rowView.findViewById(R.id.img2) as ImageView
            val img3 = rowView.findViewById(R.id.img3) as ImageView
        val plus1 = rowView.findViewById(R.id.attack_num1) as TextView
        val plus2 = rowView.findViewById(R.id.attack_num2) as TextView
        val plus3 = rowView.findViewById(R.id.attack_num3) as TextView

        var hit_modifier = rowView.findViewById(R.id.hit_modifier) as TextView

        var AllElements = arrayOf(plus1,plus2,plus3,Bonus1,Bonus2,Bonus3)
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


        //имя атаки
        val attack_name = rowView.findViewById(R.id.attack_name) as TextView
        attack_name.text = Allinall.attackName[position]

        //условия по не отображению элементов
        val Table2 = rowView.findViewById(R.id.table2) as TableRow
        val Table3 = rowView.findViewById(R.id.table3) as TableRow

        if (Allinall.at2Enable[position].toInt() == 0)
            Table2.visibility = View.GONE
        if (Allinall.at3Enable[position].toInt() == 0)
            Table3.visibility = View.GONE

        val DelBut = rowView.findViewById(R.id.Delbut) as Button

        DelBut.setOnClickListener {
            //  Toast.makeText(context, "gf", Toast.LENGTH_SHORT).show()
            Allinall.removeAt(position)

            val file = FileInfo()
            file.writeToFile(file.fileMain, Allinall)

            val listview = listview
            val myListAdapter2 = SettingsAdapter(context, Allinall, listview)
            listview.adapter = myListAdapter2
        }

        return rowView
    }

}