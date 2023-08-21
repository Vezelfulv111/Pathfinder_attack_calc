interface Contract {

    interface Presenter {
            fun onViewPagerClick(position: Int)

            fun onDestroy()
    }

    interface View {
        fun showToastMsg(msg: String)
    }

}