package com.pathfinder.attackcalc.model

import Contract
import com.pathfinder.attackcalc.DataClass
import java.io.*

class Model: Contract.Model {
    private val fileName = "Data2.txt"
    private val fileMain = File("/data/data/com.pathfinder.attackcalc/" + File.separator + fileName)

    override fun readAttacInfo():DataClass?{
        return if(fileMain.exists()) {
            val ois = ObjectInputStream(FileInputStream(fileMain))
            val data =  ois.readObject() as DataClass
            ois.close()
            data
        } else {
            null
        }
     }

    override fun writeAttacInfo(data :DataClass) {
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

}