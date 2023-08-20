package com.pathfinder.attackcalc.fragments


import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.pathfinder.attackcalc.*
import com.pathfinder.attackcalc.adapters.GenerateAdapter
import java.io.FileInputStream
import java.io.ObjectInputStream


class GenerateFragment : Fragment() {

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
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view: View = inflater!!.inflate(R.layout.generate_fragment, container, false)
        listView =view.findViewById(R.id.result_list)


        if(fileInfo.fileMain.exists()) {
            val ois = ObjectInputStream(FileInputStream(fileInfo.fileMain))
            AllinAll2 =  ois.readObject() as DataClass
            ois.close()
        }

        var snky_switch = view.findViewById(R.id.snky_switch) as Switch

           snky_switch.setOnClickListener() {
           if (AllinAll2.sneakEnable == 0) {
            Toast.makeText(context as Activity,"Set it in settings window!",Toast.LENGTH_SHORT).show()
            snky_switch.isChecked = false
           }
           else {
               snky_switch.isEnabled = true
           }
       }
        listView.adapter =  GenerateAdapter(context as Activity,AllinAll2,0,Temporary_modifers,snky_switch.isChecked)
        GenButton = view.findViewById(R.id.gen_but);
        GenButton.setOnClickListener {
           listView.adapter = GenerateAdapter(context as Activity,AllinAll2,1,Temporary_modifers,snky_switch.isChecked)
        }

        RefreshButton = view.findViewById(R.id.refresh)
        RefreshButton.setOnClickListener {
            if(fileInfo.fileMain.exists()) {
                val ois = ObjectInputStream(FileInputStream(fileInfo.fileMain))
                AllinAll2 = ois.readObject() as DataClass
                ois.close()
            }
            listView.adapter = GenerateAdapter(context as Activity,AllinAll2,0,Temporary_modifers, snky_switch.isChecked)
        }

        Plus1 = view.findViewById(R.id.Fstplus)
        Minus1 = view.findViewById(R.id.Fstminus)
        Plus2 = view.findViewById(R.id.Fstplus2)
        Minus2 = view.findViewById(R.id.Fstminus2)
        val hitbonus = view.findViewById(R.id.hitbonus) as TextView
        val hitbonus2 = view.findViewById(R.id.hitbonus2) as TextView

        Plus1.setOnClickListener {
            Temporary_modifers[0] += 1
            hitbonus.text = Temporary_modifers[0].toString()
        }
        Minus1.setOnClickListener {
            Temporary_modifers[0] -= 1
            hitbonus.text =  Temporary_modifers[0].toString()
        }
        Plus2.setOnClickListener {
            Temporary_modifers[1]  += 1
            hitbonus2.text = Temporary_modifers[1].toString()
        }
        Minus2.setOnClickListener {
            Temporary_modifers[1] -= 1
            hitbonus2.text =  Temporary_modifers[1].toString()
        }


        listView.setOnItemClickListener { _, view, position, _ ->
            val rezD20 = view.findViewById(R.id.d20throw) as TextView
            val d20 = view.findViewById(R.id.d20_kinuli) as TextView
            val modd20 = view.findViewById(R.id.hit_modifier) as TextView

            val b = (1..20).random()
            val a = modd20.text.toString().toInt()
            d20.text  = a.toString()
            val rezVal = (a+b+Temporary_modifers[0]).toString()
            rezD20.text = rezVal

            //теперь урон
            val diceAmount1 = view.findViewById(R.id.attack_num1) as TextView
            val diceAmount2 = view.findViewById(R.id.attack_num2) as TextView
            val diceAmount3 = view.findViewById(R.id.attack_num3) as TextView
            val fstAtDice = diceThrow(AllinAll2.img1[position].toInt(),diceAmount1.text.toString().toInt())
            val sndAtDice = diceThrow(AllinAll2.img2[position].toInt(),diceAmount2.text.toString().toInt())
            val thirdDice = diceThrow(AllinAll2.img3[position].toInt(),diceAmount3.text.toString().toInt())

            val bonus1 = view.findViewById(R.id.bonus1) as TextView
            val bonus2 = view.findViewById(R.id.bonus2) as TextView
            val bonus3 = view.findViewById(R.id.bonus3) as TextView

            //результат с бонусом
            val result1 =  fstAtDice + bonus1.text.toString().toInt()
            var result2 =  sndAtDice + bonus2.text.toString().toInt()
            var result3 =  thirdDice + bonus3.text.toString().toInt()

            val gen1 = view.findViewById(R.id.answer1) as TextView
            val gen2 = view.findViewById(R.id.answer2) as TextView
            val gen3 = view.findViewById(R.id.answer3) as TextView

            gen1.text =  result1.toString()
            gen2.text =  result2.toString()
            gen3.text =  result3.toString()

            val summ = view.findViewById(R.id.total_result) as TextView
            if (AllinAll2.at2Enable[position].toInt() == 0)
                result2 = 0

            if (AllinAll2.at3Enable[position].toInt() == 0)
                result3 = 0


            var sneakThrow = 0
            val sneak1 = view.findViewById(R.id.sneakky) as TextView
            if (AllinAll2.sneakEnable == 1 && snky_switch.isChecked) {
                sneakThrow = diceThrow(AllinAll2.sneakDicetype, AllinAll2.sneakNum)
                sneak1.text = sneakThrow.toString()
            }
            else {
                sneak1.text = "0"
            }

            val sumValue = (result1+result2+result3+Temporary_modifers[1]+sneakThrow).toString()
            summ.text = sumValue

        }
        return view

        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        var  snky_switch = requireView().findViewById(R.id.snky_switch) as Switch
        if(fileInfo.fileMain.exists()) {
            val ois = ObjectInputStream(FileInputStream(fileInfo.fileMain))
            AllinAll2 =  ois.readObject() as DataClass
            ois.close()
        }
        listView = requireView().findViewById(R.id.result_list)
        listView.adapter = GenerateAdapter(context as Activity,AllinAll2,0,Temporary_modifers,snky_switch.isChecked)

            if (AllinAll2.sneakEnable == 1) {
                val str = AllinAll2.sneakNum.toString() +"d"+ Dices.dices[AllinAll2.sneakDicetype].toString()
                snky_switch.text = str
            }
            else {
                snky_switch.text=getString(R.string.SneakSwitchStrNone)
                snky_switch.isChecked = false
            }
    }
}