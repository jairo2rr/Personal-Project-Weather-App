package com.example.customweatherapp.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.customweatherapp.databinding.FragmentPlanningBinding
import com.example.customweatherapp.data.model.plan.ListPlans
import com.example.customweatherapp.preferences.CustomWeatherApplication.Companion.prefers
import com.example.customweatherapp.recycler.PlansAdapter
import com.example.customweatherapp.ui.viewmodel.PlanViewModel
import com.google.android.material.snackbar.Snackbar


class PlanningFragment : Fragment() {

    private val planViewModel: PlanViewModel by activityViewModels()
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
        binding.recyclerView.adapter = adapterPlan
        val itemTouchHelper =
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val itemDeleted = adapterPlan.getListPlan()[position]
                    planViewModel.setItemDeleted(itemDeleted,position)
                    adapterPlan.deleteItem(position)
                }
            })
        planViewModel.itemDeleted.observe(viewLifecycleOwner) { plan ->
            if (plan != null && planViewModel.positionDeleted.value!! >= 0)
                Snackbar.make(
                binding.root,
                "Desea revertir los cambios?",
                Snackbar.LENGTH_SHORT)
                    .setAction("Revertir") { adapterPlan.restoreItem(plan,planViewModel.positionDeleted.value!!) }
                    .show()
        }
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
        planViewModel.extraList.observe(viewLifecycleOwner) { listPlans ->
            if (listPlans == null || listPlans.isEmpty()) {
                binding.recyclerView.isVisible = false
            } else {
                binding.recyclerView.isVisible = true
                adapterPlan.setListPlan(listPlans)
                binding.tvNoPlans.isGone = true
                binding.imgNoPlans.isGone = true
            }
        }
    }

    private fun initActivityAddPlan() {
        val intent = Intent(context, AddPlanActivity::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        planViewModel.updateListPlans()
        planViewModel.listPlans.observe(viewLifecycleOwner) { listPlans ->
            if (listPlans != null) {
                adapterPlan.setListPlan(listPlans)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        prefers.saveListPlans(adapterPlan.getListPlan())
        planViewModel.restoredPositionDeleted()
    }
}