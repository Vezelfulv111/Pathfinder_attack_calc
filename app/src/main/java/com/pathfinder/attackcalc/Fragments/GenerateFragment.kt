package com.pathfinder.attackcalc.fragments


import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.pathfinder.attackcalc.*
import com.pathfinder.attackcalc.adapters.GenerateAdapter
import com.pathfinder.attackcalc.presenters.PresenterGenerateFragment
import java.io.FileInputStream
import java.io.ObjectInputStream


class GenerateFragment : Fragment(), Contract.View {

    private lateinit var listView: ListView
    private lateinit var GenButton: Button
    private lateinit var RefreshButton: Button

    private lateinit var Plus1: ImageButton
    private lateinit var Minus1: ImageButton
    private lateinit var Plus2: ImageButton
    private lateinit var Minus2: ImageButton

    private lateinit var sneakySwitch: Switch


    var Temporary_modifers= intArrayOf(0, 0)
    private var AllinAll2 = DataClass();
    var fileInfo = FileInfo()

    @SuppressLint("ResourceType")
    var presenterGen: PresenterGenerateFragment? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view: View = inflater!!.inflate(R.layout.generate_fragment, container, false)
        listView =view.findViewById(R.id.result_list)


        presenterGen = PresenterGenerateFragment(this)


        if(fileInfo.fileMain.exists()) {
            val ois = ObjectInputStream(FileInputStream(fileInfo.fileMain))
            AllinAll2 =  ois.readObject() as DataClass
            ois.close()
        }

        sneakySwitch = view.findViewById(R.id.snky_switch)
        sneakySwitch.setOnClickListener() {
            presenterGen?.sneakySwitch(AllinAll2)
        }


        listView.adapter =  GenerateAdapter(context as Activity,AllinAll2,0,Temporary_modifers,sneakySwitch.isChecked)
        GenButton = view.findViewById(R.id.gen_but);

        GenButton.setOnClickListener {
           listView.adapter = GenerateAdapter(context as Activity,AllinAll2,1,Temporary_modifers,sneakySwitch.isChecked)
        }

        RefreshButton = view.findViewById(R.id.refresh)
        RefreshButton.setOnClickListener {
            if(fileInfo.fileMain.exists()) {
                val ois = ObjectInputStream(FileInputStream(fileInfo.fileMain))
                AllinAll2 = ois.readObject() as DataClass
                ois.close()
            }
            listView.adapter = GenerateAdapter(context as Activity,AllinAll2,0,Temporary_modifers, sneakySwitch.isChecked)
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

            val throwData = presenterGen!!.throwComputation(AllinAll2,
                                                            position,
                                                            Temporary_modifers,
                                                            sneakySwitch.isChecked)

            d20.text  = throwData.d20Throw.toString()
            rezD20.text = throwData.d20Total.toString()

            val gen1 = view.findViewById(R.id.answer1) as TextView
            val gen2 = view.findViewById(R.id.answer2) as TextView
            val gen3 = view.findViewById(R.id.answer3) as TextView

            gen1.text =  throwData.dmgRoll1.toString()
            gen2.text =  throwData.dmgRoll2.toString()
            gen3.text =  throwData.dmgRoll3.toString()

            val sneak1 = view.findViewById(R.id.sneakky) as TextView
            sneak1.text = throwData.sneakDmg.toString()

            val sum = view.findViewById(R.id.total_result) as TextView
            sum.text = throwData.totalDamageWithSneak.toString()
        }
        return view

        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    override fun showToastMsg(msg: String) {
        Toast.makeText(context as Activity, msg, Toast.LENGTH_SHORT).show()
    }

    fun enableSneakAttackSwitch(flag: Boolean) {
        sneakySwitch.isEnabled = flag
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