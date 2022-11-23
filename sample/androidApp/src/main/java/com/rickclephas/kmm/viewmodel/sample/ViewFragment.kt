package com.rickclephas.kmm.viewmodel.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.rickclephas.kmm.viewmodel.sample.shared.TimeTravelViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ViewFragment: Fragment(R.layout.fragment_time_travel) {

    private val viewModel: TimeTravelViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null

        view.findViewById<TextView>(R.id.actualTimeTextView).apply {
            viewModel.actualTime.flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .onEach(::setText)
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }

        view.findViewById<TextView>(R.id.travelEffectTextView).apply {
            viewModel.travelEffect.flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .onEach { text = it.toString() }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }

        view.findViewById<TextView>(R.id.currentTimeTextView).apply {
            viewModel.currentTime.flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .onEach(::setText)
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }

        view.findViewById<CheckBox>(R.id.fixedTimeCheckBox).apply {
            viewModel.isFixedTime.flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .onEach(::setChecked)
                .launchIn(viewLifecycleOwner.lifecycleScope)
            setOnCheckedChangeListener { _, isChecked ->
                when (isChecked) {
                    true -> viewModel.stopTime()
                    false -> viewModel.startTime()
                }
            }
        }

        view.findViewById<Button>(R.id.timeTravelButton).setOnClickListener {
            viewModel.timeTravel()
        }

        view.findViewById<Button>(R.id.resetButton).setOnClickListener {
            viewModel.resetTime()
        }

        return view
    }
}
