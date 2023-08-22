import android.widget.Toast
import com.pathfinder.attackcalc.DataClass

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
         fun readAttacInfo(): DataClass?
         fun writeAttacInfo(data :DataClass)
    }

}