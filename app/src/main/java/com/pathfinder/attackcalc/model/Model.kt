package com.pathfinder.attackcalc.model

import Contract
import com.pathfinder.attackcalc.AttackInfo
import java.io.*

class Model: Contract.Model {
    private val fileName = "AttacInfo.txt"
    private val fileMain = File("/data/data/com.pathfinder.attackcalc/" + File.separator + fileName)
    var AllinAll = AttackInfo()

    override fun readAttacInfo(): AttackInfo?{
        return if(fileMain.exists()) {
            try {
                val ois = ObjectInputStream(FileInputStream(fileMain))
                val data : AttackInfo? = ois.readObject() as? AttackInfo?
                ois.close()
                data
            }
            catch(e: Exception) {
                null
            }
        } else {
            null
        }
     }

    override fun writeAttacInfo(data :AttackInfo) {
        try {
            val f = FileOutputStream(fileMain)
            val o = ObjectOutputStream(f)
            o.writeObject(data)
            o.close()
            f.close()
        } catch (e: Exception) {
            System.err.println("Error opening file.")
        }
    }


    val dices = intArrayOf(
            3,
            4,
            6,
            8,
            10,
            12,
    )
}

