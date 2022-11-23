package com.rickclephas.kmm.viewmodel.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace

class PickerFragment: Fragment(R.layout.fragment_picker) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState) ?: return null

        view.findViewById<Button>(R.id.viewButton).setOnClickListener {
            parentFragmentManager.commit {
                replace<ViewFragment>(R.id.fragmentContainerView)
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        }

        view.findViewById<Button>(R.id.composeButton).setOnClickListener {
            parentFragmentManager.commit {
                replace<ComposeFragment>(R.id.fragmentContainerView)
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        }

        return view
    }
}
