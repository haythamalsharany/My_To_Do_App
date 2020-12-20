package com.alsharany.mytodoapp

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*


var TAP_INDEX = "tab index"

class TaskListFragment : Fragment(), InputDialogFragment.InputCallbacks {


    private lateinit var taskRecyclerView: RecyclerView
    private val taskViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(TaskViewModel::class.java)
    }
    private var adapter: TaskAdapter? = TaskAdapter(emptyList())
    private var tabIndex: Int = 0

    // private lateinit var noDataTextView: TextView
    //0 private lateinit var addCrimeButton: Button
    var taskList: List<Task> = emptyList()
    // private var listener: Listener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments.let {
            tabIndex = it!!.getInt(TAP_INDEX)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_task, container, false)
        taskRecyclerView = view.findViewById(R.id.task_recycler) as RecyclerView

        taskRecyclerView.layoutManager = LinearLayoutManager(context)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskViewModel.apply {
            if (tabIndex == 0)
                toDoTaskLiveDataList?.observe(

                    viewLifecycleOwner,
                    { tasks ->
                        taskList = tasks

                        updateUI(taskList)
                    }
                )
            else if (tabIndex == 1)
                inprogressTaskLiveDataList?.observe(

                    viewLifecycleOwner,
                    { tasks ->

                        taskList = tasks
                        updateUI(taskList)
                    }
                )
            else if (tabIndex == 2)
                doneTaskLiveDataList?.observe(

                    viewLifecycleOwner,
                    { tasks ->
                        taskList = tasks


                        updateUI(taskList)
                    }
                )

        }


    }

    private inner class TaskHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        lateinit var task: Task
        val downListStateButton = itemView.findViewById(R.id.down_state_btn) as Button
        val upgradeListStateButton = itemView.findViewById(R.id.upgrade_state) as Button
        val taskTitleTextView = itemView.findViewById(R.id.task_title) as TextView


        init {

            downListStateButton.setOnClickListener(this)


            upgradeListStateButton.setOnClickListener {
                if (tabIndex == 0) {
                    this.task.taskState = 1

                } else if (tabIndex == 1) {
                    this.task.taskState = 2
                }
                taskViewModel.updateTaskState(this.task)
                updateUI(taskList)

            }

        }


        fun bind(item: Task) {
            this.task = item
            if (tabIndex == 0) {
                downListStateButton.visibility = View.GONE
                upgradeListStateButton.visibility = View.VISIBLE
                upgradeListStateButton.text = "inprogress"
            } else if (tabIndex == 1) {

                upgradeListStateButton.text = "Done"
                downListStateButton.text = "To Do"
                downListStateButton.visibility = View.VISIBLE
                upgradeListStateButton.visibility = View.VISIBLE
            } else {
                downListStateButton.visibility = View.VISIBLE
                downListStateButton.text = "inprogress"
                upgradeListStateButton.visibility = View.GONE
            }
            taskTitleTextView.text = task.title
            val calander = Calendar.getInstance()
            val calanderNow = Calendar.getInstance()
            calander.time = task.endDate
            calanderNow.time = Date()

            if (calander.get(Calendar.DAY_OF_WEEK) - 3 == calanderNow.get(Calendar.DAY_OF_WEEK))
                itemView.setBackgroundColor(Color.RED)
        }

        override fun onClick(v: View?) {
            // if (v == downListStateButton) {
            if (tabIndex == 1) {
                this.task.taskState = 0

            } else if (tabIndex == 2) {
                this.task.taskState = 1
            }
            taskViewModel.updateTaskState(this.task)
            updateUI(taskList)
            /*else if (v == upgradeListStateButton) {
                   */

        }


    }

    private inner class TaskAdapter(var tasks: List<Task>) :
        RecyclerView.Adapter<TaskHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
            val view = layoutInflater.inflate(R.layout.list_item, parent, false)
            return TaskHolder(view)
        }

        override fun onBindViewHolder(holder: TaskHolder, position: Int) {
            val task = tasks[position]
            holder.bind(task)

        }

        override fun getItemCount(): Int {
            /*if (tasks.isNotEmpty()) {
                noDataTextView.visibility = View.GONE
                addCrimeButton.visibility = View.GONE
            } else {

                noDataTextView.visibility = View.VISIBLE
                addCrimeButton.visibility = View.VISIBLE
            }
*/
            return tasks.size
        }


    }

    private fun updateUI(tasks: List<Task>) {


        adapter = TaskAdapter(tasks)
        taskRecyclerView.adapter = adapter


    }

    companion object {

        @JvmStatic
        fun newInstance(tabIndex: Int) =
            TaskListFragment().apply {
                arguments = Bundle().apply {
                    putInt(TAP_INDEX, tabIndex)
                }
            }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.task_menue, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_task -> {
                InputDialogFragment.newInstance().apply {
                    setTargetFragment(this@TaskListFragment, 0)
                    show(this@TaskListFragment.requireFragmentManager(), "input")
                }
                true
            }
            else ->
                return super.onOptionsItemSelected(item)


        }


    }


    override fun onTaskAdd(task: Task) {
        try {
            taskViewModel.addTask(task)
        } catch (e: Exception) {

            Log.d("notAdd", e.message)
        }


    }
}
