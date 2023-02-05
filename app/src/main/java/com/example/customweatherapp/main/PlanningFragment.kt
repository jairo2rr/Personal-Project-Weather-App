package com.example.customweatherapp.main

import android.annotation.SuppressLint
import android.content.ClipData.Item
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.example.customweatherapp.R
import com.example.customweatherapp.databinding.FragmentPlanningBinding
import com.example.customweatherapp.databinding.ItemPlannersBinding
import com.example.customweatherapp.model.plan.ListPlans
import com.example.customweatherapp.preferences.CustomWeatherApplication.Companion.prefers
import com.example.customweatherapp.recycler.PlansAdapter
import com.example.customweatherapp.viewmodel.PlanViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PlanningFragment : Fragment() {

    private val planViewModel:PlanViewModel by activityViewModels()
    private lateinit var binding: FragmentPlanningBinding
    private var adapterPlan = PlansAdapter(ListPlans())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlanningBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabAddPlan.setOnClickListener {
            initActivityAddPlan()
        }
        planViewModel.listPlans.observe(viewLifecycleOwner) { listPlans ->
            if (listPlans == null || listPlans.isEmpty()) {
                binding.recyclerView.isVisible = false
            } else {
                binding.recyclerView.isVisible = true
                adapterPlan.listPlans = listPlans
                binding.tvNoPlans.isGone = true
                binding.imgNoPlans.isGone = true
                binding.recyclerView.adapter = adapterPlan
            }
        }


    }

    private fun initActivityAddPlan() {
        val intent = Intent(context, AddPlanActivity::class.java)
        startActivity(intent)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PlanningFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onStart() {
        super.onStart()
        planViewModel.updateListPlans()
        planViewModel.listPlans.observe(viewLifecycleOwner) { listPlans ->
            if (listPlans != null) {
                adapterPlan.notifyDataSetChanged()
                adapterPlan.listPlans = listPlans
            }
        }
    }
}