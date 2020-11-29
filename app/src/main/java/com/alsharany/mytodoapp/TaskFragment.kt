package com.alsharany.mytodoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


class CategoryFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_task, container, false)
    }

    companion object {


        @JvmStatic
        fun newInstance(id: Int, name: String?) =
            CategoryFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}