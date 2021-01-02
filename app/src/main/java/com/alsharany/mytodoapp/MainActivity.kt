package com.alsharany.mytodoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*

class MainActivity : AppCompatActivity(), TaskListFragment.ListTaskCallBacks {
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager2
    lateinit var adabter: PagerAdabter

    //  lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // toolbar=findViewById(R.id.toolbar)
        tabLayout = findViewById(R.id.main_tab_layout)
        viewPager = findViewById(R.id.main_view_Pager)
        adabter = PagerAdabter(this)
        adabter.addTab(MyTab(TaskState(0, "To Do"), TaskListFragment.newInstance(0)))
        adabter.addTab(MyTab(TaskState(1, "in progress"), TaskListFragment.newInstance(1)))
        adabter.addTab(MyTab(TaskState(2, "Done"), TaskListFragment.newInstance(2)))

        viewPager.adapter = adabter

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

    override fun onTaskSelected(taskId: UUID) {
        val fragment = TaskDetailsFragment.newInstance(taskId)

        val fragmentManager = supportFragmentManager
        val ft = fragmentManager.beginTransaction()
        ft.replace(R.id.main_view_Pager, fragment).addToBackStack(null).commit()
    }
}