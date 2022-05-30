package com.pathfinder.attackcalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout


import com.google.android.material.tabs.TabLayoutMediator
import java.io.File


class MainActivity : AppCompatActivity()  {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout


    val All = "AllinAll2.txt"
    val file = File("/data/data/com.pathfinder.attackcalc/" + File.separator + All)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (getSupportActionBar() != null) {
            getSupportActionBar()?.hide();
        }

        viewPager = findViewById(R.id.viewpager)

        val fragments =
            mutableListOf(
                settings_fragment(),
                generate_fragment(),
                )
        val adapter = TbAdapt(this)
        viewPager.adapter = adapter



        tabLayout = findViewById(R.id.Tabs)
        TabLayoutMediator(tabLayout, viewPager) {
                tab, position ->
            if (position == 0)
                tab.text = "Generate"
            if (position == 1)
                tab.text = "Settings"
        }.attach()




        var myPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
            //  Toast.makeText(this@MainActivity, "Selected position:2", Toast.LENGTH_SHORT).show()
            }
        }
        viewPager.registerOnPageChangeCallback(myPageChangeCallback);




    }




}
