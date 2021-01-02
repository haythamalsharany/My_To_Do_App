package com.alsharany.mytodoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_TASK_ID = "taskId"


/**
 * A simple [Fragment] subclass.
 * Use the [TaskDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaskDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var taskId: UUID


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            taskId = it.getSerializable(ARG_TASK_ID) as UUID

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_details, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TaskDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(taskId: UUID) =
            TaskDetailsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_TASK_ID, taskId)

                }
            }
    }
}