package com.example.customweatherapp.main

import android.content.ClipData.Item
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.customweatherapp.R
import com.example.customweatherapp.databinding.FragmentPlanningBinding
import com.example.customweatherapp.databinding.ItemPlannersBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PlanningFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding:FragmentPlanningBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlanningBinding.inflate(inflater)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PlanningFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}