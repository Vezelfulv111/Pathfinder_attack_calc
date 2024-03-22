package com.pathfinder.attackcalc.Adapters

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.pathfinder.attackcalc.R
import com.pathfinder.attackcalc.fragments.SettingsFragment
import com.pathfinder.attackcalc.presenters.PresenterSettingsFragment


class SettingsAdapter(private val context: Activity, private var presenter: PresenterSettingsFragment,
                      private val listview: RecyclerView , private val settingsFragment: SettingsFragment
)
    : RecyclerView.Adapter<SettingsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val header: TableRow = itemView.findViewById(R.id.settings_header)
        val Attac_num: TextView = itemView.findViewById(R.id.cur_num)

        val bonus1: TextView = itemView.findViewById(R.id.bonus1)
        val bonus2: TextView = itemView.findViewById(R.id.bonus2)
        val bonus3: TextView = itemView.findViewById(R.id.bonus3)
        val img1: ImageView = itemView.findViewById(R.id.img1)
        val img2: ImageView = itemView.findViewById(R.id.img2)
        val img3: ImageView = itemView.findViewById(R.id.img3)
        val plus1: TextView = itemView.findViewById(R.id.attack_num1)
        val plus2: TextView = itemView.findViewById(R.id.attack_num2)
        val plus3: TextView = itemView.findViewById(R.id.attack_num3)

        val hit_modifier: TextView = itemView.findViewById(R.id.hit_modifier)

        val attackName: TextView = itemView.findViewById(R.id.attack_name)
        //условия по не отображению элементов
        val Table2: TableRow = itemView.findViewById(R.id.table2)
        val Table3: TableRow = itemView.findViewById(R.id.table3)
        val DelBut: Button = itemView.findViewById(R.id.Delbut)

        val textViewTemp1: TextView = itemView.findViewById(R.id.cur_tmp2)
        val textViewTemp2: TextView = itemView.findViewById(R.id.cur_tmp3)
        val hitTemp1: TextView = itemView.findViewById(R.id.tmp_hit_modifier1)
        val hitTemp2: TextView = itemView.findViewById(R.id.tmp_hit_modifier2)
        val nameTemp1: TextView = itemView.findViewById(R.id.tmp_attack_name1)
        val nameTemp2: TextView = itemView.findViewById(R.id.tmp_attack_name2)
    }

    val images = intArrayOf(
        R.drawable.d3,
        R.drawable.d4,
        R.drawable.d6,
        R.drawable.d8,
        R.drawable.d10,
        R.drawable.d12,
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.attac_st_listview, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return presenter.AllinAll.numDice1.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Allinall = presenter.AllinAll

        if (position % 2 == 1)
            holder.itemView.setBackgroundColor(Color.DKGRAY)
         else
            holder.itemView.setBackgroundColor(Color.GRAY)


        val header = holder.header
        if (position != 0) {
            header.visibility = View.GONE
        }

        //атака
        holder.Attac_num.text = (position+1).toString()
        val attack_name = holder.attackName
        attack_name.text = Allinall.attackName[position]

        //условия по не отображению элементов
        val Table2 = holder.Table2
        val Table3 =  holder.Table3

        holder.hit_modifier.text = Allinall.hitModifier[position]
        holder.plus1.text = Allinall.numDice1[position]
        holder.plus2.text = Allinall.numDice2[position]
        holder.plus3.text = Allinall.numDice3[position]

        holder.bonus1.text = Allinall.bonus1[position]
        holder.bonus2.text = Allinall.bonus2[position]
        holder.bonus3.text = Allinall.bonus3[position]

        holder.img1.setImageResource(images[Allinall.img1[position].toInt()])
        holder.img2.setImageResource(images[Allinall.img2[position].toInt()])
        holder.img3.setImageResource(images[Allinall.img3[position].toInt()])

        val textViewTemp1: TextView = holder.textViewTemp1
        val textViewTemp2: TextView = holder.textViewTemp2
        val hitTemp1: TextView = holder.hitTemp1
        val hitTemp2: TextView = holder.hitTemp2
        val nameTemp1: TextView = holder.nameTemp1
        val nameTemp2: TextView = holder.nameTemp2

        if (Allinall.at2Enable[position].toInt() == 0 && Allinall.at3Enable[position].toInt() == 0) {
            val param = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
            textViewTemp1.layoutParams = param
            textViewTemp2.layoutParams =param
            hitTemp1.layoutParams = param
            hitTemp2.layoutParams =param
            nameTemp1.layoutParams = param
            nameTemp2.layoutParams =param
        }
        else if (Allinall.at2Enable[position].toInt() != 0 && Allinall.at3Enable[position].toInt() != 0) {
            val param = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                13.0f
            )
            textViewTemp1.layoutParams = param
            textViewTemp2.layoutParams =param
            hitTemp1.layoutParams = param
            hitTemp2.layoutParams =param
            nameTemp1.layoutParams = param
            nameTemp2.layoutParams =param
        }

        if (Allinall.at2Enable[position].toInt() == 0) {
            Table2.visibility = View.GONE
        }

        if (Allinall.at3Enable[position].toInt() == 0) {
            Table3.visibility = View.GONE
        }

        val DelBut = holder.DelBut
        DelBut.setOnClickListener {
            presenter.AllinAll.removeAt(position)
            presenter.writeData()
            listview.adapter = SettingsAdapter(context, presenter, listview, settingsFragment)
        }


        holder.itemView.setOnClickListener {
            val currentPosition = holder.adapterPosition
            presenter.CurrentPositon = currentPosition

            val EditButton: Button = settingsFragment.requireView().findViewById(R.id.EditButton)
            EditButton.text = "Edit ".plus((position+1).toString())

            val Attack_name: TextView = settingsFragment.requireView().findViewById(R.id.enter_at_name)
            Attack_name.text = presenter.AllinAll.attackName[position]

            val bonus1: TextView = settingsFragment.requireView().findViewById(R.id.Edittext1)
            bonus1.text = presenter.AllinAll.bonus1[position].toInt().toString()

            val spinnerImg1: Spinner = settingsFragment.requireView().findViewById(R.id.Dice_spinner1)
            spinnerImg1.setSelection(presenter.AllinAll.img1[position].toInt(),true)

            val spinAt1: Spinner = settingsFragment.requireView().findViewById(R.id.Spin_n1)
            spinAt1.setSelection(presenter.AllinAll.numDice1[position].toInt()-1,true)

            val Switch2nd: Switch = settingsFragment.requireView().findViewById(R.id.switchscnd)
            val Switch3d: Switch = settingsFragment.requireView().findViewById(R.id.switchthird)

            Switch2nd.isChecked = presenter.checkAttack2Enable(position)
            Switch3d.isChecked = presenter.checkAttack3Enable(position)
        }

    }

}