package com.pathfinder.attackcalc


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout


import com.google.android.material.tabs.TabLayoutMediator
import com.pathfinder.attackcalc.adapters.TbAdapt


class MainActivity : AppCompatActivity(), Contract.View {

    private var presenter: Presenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //presenter init
        presenter = Presenter(this)


        if (supportActionBar != null) {
            supportActionBar?.hide()
        }

        val viewPager: ViewPager2 = findViewById(R.id.viewpager)
        val adapter = TbAdapt(this)
        viewPager.adapter = adapter

        val tabLayout: TabLayout = findViewById(R.id.Tabs)
        TabLayoutMediator(tabLayout, viewPager) {
                tab, position ->
            if (position == 0)
                tab.text = "Generate"
            if (position == 1)
                tab.text = "Settings"
        }.attach()

        val myPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                presenter!!.onViewPagerClick(position)
            }
        }
        viewPager.registerOnPageChangeCallback(myPageChangeCallback)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter!!.onDestroy()
    }

    override fun showToastMsg(msg: String) {
        Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
    }




}
