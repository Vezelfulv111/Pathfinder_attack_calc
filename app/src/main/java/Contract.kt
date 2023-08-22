import com.pathfinder.attackcalc.AttackInfo

interface Contract {

    interface Presenter {
            fun onDestroy()

            fun readData()
    }

    interface View {
        fun showToastMsg(msg: String) {
        }
    }

    interface Model {
         fun readAttacInfo(): AttackInfo?
         fun writeAttacInfo(data :AttackInfo)
    }

}