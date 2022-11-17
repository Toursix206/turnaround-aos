package org.android.turnaround.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentActivityBinding
import org.android.turnaround.util.binding.BindingFragment

@AndroidEntryPoint
class ActivityFragment : BindingFragment<FragmentActivityBinding>(R.layout.fragment_activity) {
    private val viewModel by viewModels<ActivityViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
