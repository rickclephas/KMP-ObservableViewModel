package com.rickclephas.kmp.observableviewmodel.sample

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
            navigate<ViewFragment>()
        }

        view.findViewById<Button>(R.id.composeButton).setOnClickListener {
            navigate<ComposeFragment>()
        }

        view.findViewById<Button>(R.id.composeMPButton).setOnClickListener {
            navigate<ComposeMPFragment>()
        }

        return view
    }

    private inline fun <reified T: Fragment> navigate() {
        parentFragmentManager.commit {
            replace<T>(R.id.fragmentContainerView)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }
}
