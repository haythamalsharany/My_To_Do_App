package com.alsharany.mytodoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager2
    lateinit var adabter: PagerAdabter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tabLayout = findViewById(R.id.main_tab_layout)
        viewPager = findViewById(R.id.main_view_Pager)
        adabter = PagerAdabter(this)
        adabter.addTab(MyTab(TaskState(1, "To Do"), TaskListFragment.newInstance(0)))
        adabter.addTab(MyTab(TaskState(2, "in progress"), TaskListFragment.newInstance(1)))
        adabter.addTab(MyTab(TaskState(3, "Done"), TaskListFragment.newInstance(2)))

        viewPager.adapter = adabter


        /* viewPager.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
             override fun onPageSelected(position: Int) {
                 super.onPageSelected(position)
                 Toast.makeText(this@MainActivity,"page ${position+1}", Toast.LENGTH_LONG).show()
             }
         })*/


        TabLayoutMediator(tabLayout, viewPager) { tap, postion ->
            when (postion) {
                0 -> {

                    tap.text = "TO Do "


                }
                1 -> {
                    tap.text = "Inprogress"

                }

                2 -> {
                    tap.text = "Done "


                }


                else -> tap.text = "other"
            }


        }.attach()
    }
}