import android.widget.Toast

interface Contract {

    interface Presenter {
            fun onDestroy()

            fun readData() = null
    }

    interface View {
        fun showToastMsg(msg: String) {
        }
    }

}