package com.pathfinder.attackcalc.Adapters

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.*

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pathfinder.attackcalc.R
import com.pathfinder.attackcalc.fragments.GenerateFragment
import com.pathfinder.attackcalc.fragments.SettingsFragment
import com.pathfinder.attackcalc.presenters.PresenterGenerateFragment


class GenerateAdapter(
    private var Condition: Int,
    private var switch_on_of: Boolean,
    private var presenter: PresenterGenerateFragment,
    private val settingsFragment: GenerateFragment
    )
    : RecyclerView.Adapter<GenerateAdapter.ViewHolder>()

{
    val images = intArrayOf(
        R.drawable.d3,
        R.drawable.d4,
        R.drawable.d6,
        R.drawable.d8,
        R.drawable.d10,
        R.drawable.d12,
    )

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val attacNum: TextView = itemView.findViewById(R.id.current_number)
            val bonus1: TextView = itemView.findViewById(R.id.bonus1)
            val bonus2: TextView = itemView.findViewById(R.id.bonus2)
            val bonus3: TextView = itemView.findViewById(R.id.bonus3)
            val img1: ImageView = itemView.findViewById(R.id.img1)
            val img2: ImageView = itemView.findViewById(R.id.img2)
            val img3: ImageView = itemView.findViewById(R.id.img3)
            val diceAmount1: TextView = itemView.findViewById(R.id.attack_num1)
            val diceAmount2: TextView = itemView.findViewById(R.id.attack_num2)
            val diceAmount3: TextView = itemView.findViewById(R.id.attack_num3)
            val hitModifier: TextView = itemView.findViewById(R.id.hit_modifier)
            val attackName: TextView = itemView.findViewById(R.id.attack_name_gen)
            val table2: TableRow = itemView.findViewById(R.id.table2)
            val table3: TableRow = itemView.findViewById(R.id.table3)

            val d20result: TextView = itemView.findViewById(R.id.d20throw)
            val d20Kinuli: TextView = itemView.findViewById(R.id.d20_kinuli)
            val summ: TextView = itemView.findViewById(R.id.total_result)
            val sneakky: TextView = itemView.findViewById(R.id.sneakky)

            val gen1: TextView = itemView.findViewById(R.id.answer1)
            val gen2: TextView = itemView.findViewById(R.id.answer2)
            val gen3: TextView = itemView.findViewById(R.id.answer3)

            val header: TableRow = itemView.findViewById(R.id.generate_header)

            val numberTemp1: TextView = itemView.findViewById(R.id.tmp_current_number1)
            val numberTemp2: TextView = itemView.findViewById(R.id.tmp_current_number2)

            val sneakTemp1: TextView = itemView.findViewById(R.id.tmp1_sneakky)
            val sneakTemp2: TextView = itemView.findViewById(R.id.tmp2_sneakky)

            val totalTemp1: TextView = itemView.findViewById(R.id.tmp1_total_result)
            val totalTemp2: TextView = itemView.findViewById(R.id.tmp2_total_result)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Allinall = presenter.AllinAll

        holder.attacNum.text = (position + 1).toString()
        holder.bonus1.text = Allinall.bonus1[position]
        holder.bonus2.text = Allinall.bonus2[position]
        holder.bonus3.text = Allinall.bonus3[position]
        holder.img1.setImageResource(images[Allinall.img1[position].toInt()])
        holder.img2.setImageResource(images[Allinall.img2[position].toInt()])
        holder.img3.setImageResource(images[Allinall.img3[position].toInt()])
        holder.diceAmount1.text = Allinall.numDice1[position]
        holder.diceAmount2.text = Allinall.numDice2[position]
        holder.diceAmount3.text = Allinall.numDice3[position]
        holder.hitModifier.text = Allinall.hitModifier[position]
        holder.attackName.text = Allinall.attackName[position]


        if (position != 0)
            holder.header.visibility = GONE
        if (position % 2 == 1)
            holder.itemView.setBackgroundColor(Color.DKGRAY)
        else
            holder.itemView.setBackgroundColor(Color.GRAY)

        if (Allinall.attackName.isEmpty()) {
            holder.attackName.visibility = GONE
        }

        val nameTemp1: TextView = holder.numberTemp1
        val nameTemp2: TextView = holder.numberTemp2
        val totalTemp1: TextView = holder.totalTemp1
        val totalTemp2: TextView = holder.totalTemp2
        val sneakTemp1: TextView = holder.sneakTemp1
        val sneakTemp2: TextView = holder.sneakTemp2
        if (Allinall.at2Enable[position].toInt() == 0 && Allinall.at3Enable[position].toInt() == 0) {
            val param = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                7f
            )
            nameTemp1.layoutParams = param
            nameTemp2.layoutParams =param
            totalTemp1.layoutParams = param
            totalTemp2.layoutParams =param
            sneakTemp1.layoutParams = param
            sneakTemp2.layoutParams =param
        }
        else if (Allinall.at2Enable[position].toInt() != 0 && Allinall.at3Enable[position].toInt() != 0) {
            val param = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                13.0f
            )
            nameTemp1.layoutParams = param
            nameTemp2.layoutParams =param
            totalTemp1.layoutParams = param
            totalTemp2.layoutParams =param
            sneakTemp1.layoutParams = param
            sneakTemp2.layoutParams =param
        }
        if (Allinall.at2Enable[position].toInt() == 0) {
            holder.table2.visibility = GONE
        }
        if (Allinall.at3Enable[position].toInt() == 0) {
            holder.table3.visibility = GONE
        }

        if (Condition==1) {
            //бросок эн кубов
            val throwData = presenter.throwComputation(position, switch_on_of)

            holder.gen1.text =  throwData.dmgRoll1.toString()
            holder.gen2.text =  throwData.dmgRoll2.toString()
            holder.gen3.text =  throwData.dmgRoll3.toString()
            //теперь разбираемcя с 20ткой
            holder.d20result.text = throwData.d20Total.toString()
            holder.d20Kinuli.text = throwData.d20Throw.toString()
            holder.summ.text = throwData.totalDamageWithSneak.toString()
            holder.sneakky.text  = throwData.sneakDmg.toString()
        }

        holder.itemView.setOnClickListener {
            val sneakySwitch: Switch = settingsFragment.requireView().findViewById(R.id.snky_switch)
            val throwData = presenter.throwComputation(position,sneakySwitch.isChecked)

            holder.d20Kinuli.text  = throwData.d20Throw.toString()
            holder.d20result.text = throwData.d20Total.toString()

            holder.gen1.text =  throwData.dmgRoll1.toString()
            holder.gen2.text =  throwData.dmgRoll2.toString()
            holder.gen3.text =  throwData.dmgRoll3.toString()

            holder.sneakky.text = throwData.sneakDmg.toString()

            if (throwData.totalDamageWithSneak < 0)
                holder.summ.text = "0"
            else
                holder.summ.text = throwData.totalDamageWithSneak.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.result_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return presenter.AllinAll.numDice1.size
    }

}