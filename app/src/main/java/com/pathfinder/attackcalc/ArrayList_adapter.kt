package com.pathfinder.attackcalc

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.*

import android.widget.TextView
import java.io.*


class ArrayList_Adapter(private val context: Activity, private var Allinall: Array< Array<String>>,
                        private val listview: ListView
)
    : ArrayAdapter<Any>(context, R.layout.attac_st_listview, Allinall[1]) {




    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.attac_st_listview, null, true)
        //делаем список полосатым
        rowView.setBackgroundColor(if (position and 1 === 1) Color.DKGRAY else Color.GRAY)

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


        val All = "AllinAll.txt"
        val file = File("/data/data/com.pathfinder.attackcalc/" + File.separator + All)

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




        val DelBut = rowView.findViewById(R.id.Delbut) as Button
        var listview = listview;


        val A =  arrayListOf(
            Allinall[0].toMutableList(),
            Allinall[1].toMutableList(),
            Allinall[2].toMutableList(),
            Allinall[3].toMutableList(),
            Allinall[4].toMutableList(),
            Allinall[5].toMutableList(),
            Allinall[6].toMutableList(),
            Allinall[7].toMutableList(),
            Allinall[8].toMutableList(),
            Allinall[9].toMutableList(),
            Allinall[10].toMutableList(),
            Allinall[11].toMutableList(),
        )

        try {
            val f = FileOutputStream(file)
            val o = ObjectOutputStream(f)
            o.writeObject(Allinall)
            o.close()
            f.close()
        } catch (e: FileNotFoundException) {
            println("File not found")
        } catch (e: IOException) {
            println("Error initializing stream")
        } catch (e: ClassNotFoundException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        //доп запись в файл
        val ois = ObjectInputStream(FileInputStream(file))
        Allinall =  ois.readObject() as Array<Array<String>>
        ois.close()
        //доп запись в файл


        DelBut.setOnClickListener(View.OnClickListener {
          //  Toast.makeText(context, "gf", Toast.LENGTH_SHORT).show()

            for (i in 0..11)
            {
               A[i].removeAt(position)
            }

             Allinall =  arrayOf(
                A[0].toTypedArray(),
                A[1].toTypedArray(),
                A[2].toTypedArray(),
                A[3].toTypedArray(),
                A[4].toTypedArray(),
                A[5].toTypedArray(),
                A[6].toTypedArray(),
                A[7].toTypedArray(),
                A[8].toTypedArray(),
                A[9].toTypedArray(),
                A[10].toTypedArray(),
                A[11].toTypedArray(),
                )



            try {
                val f = FileOutputStream(file)
                val o = ObjectOutputStream(f)
                o.writeObject(Allinall)
                o.close()
                f.close()
            } catch (e: FileNotFoundException) {
                println("File not found")
            } catch (e: IOException) {
                println("Error initializing stream")
            } catch (e: ClassNotFoundException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }

            val myListAdapter2 = ArrayList_Adapter(context as Activity,  Allinall,listview)
            listview.adapter= myListAdapter2;

        })



        return rowView



    }



}