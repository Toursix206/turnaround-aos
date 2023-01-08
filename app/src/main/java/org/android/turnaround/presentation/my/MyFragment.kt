package org.android.turnaround.presentation.my

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentMyBinding
import org.android.turnaround.presentation.setting.SettingActivity
import org.android.turnaround.util.binding.BindingFragment

@AndroidEntryPoint
class MyFragment : BindingFragment<FragmentMyBinding>(R.layout.fragment_my) {
    private val viewModel by viewModels<MyViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        initStartSettingBtnClickListener()
        initReviewClickListener()
    }

    private fun initStartSettingBtnClickListener() {
        binding.tvMySetting.setOnClickListener {
            startActivity(Intent(requireActivity(), SettingActivity::class.java))
        }
    }

    private fun initReviewClickListener() {
        binding.tvMyReview.setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.market_url)))
            )
        }
    }
}
