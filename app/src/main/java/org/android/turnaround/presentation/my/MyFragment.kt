package org.android.turnaround.presentation.my

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentMyBinding
import org.android.turnaround.util.binding.BindingFragment

@AndroidEntryPoint
class MyFragment : BindingFragment<FragmentMyBinding>(R.layout.fragment_my) {
    private val viewModel by viewModels<MyViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMyObserver()
    }

    private fun initMyObserver() {
        viewModel.my.observe(viewLifecycleOwner) {
            binding.my = it
//            when (it.profileType) {
//                ProfileType.ONE -> binding.ivMyProfile.setImageResource(R.drawable.img_profile_character_1)
//                ProfileType.TWO -> binding.ivMyProfile.setImageResource(R.drawable.img_profile_character_2)
//                ProfileType.THREE -> binding.ivMyProfile.setImageResource(R.drawable.img_profile_character_3)
//            }
        }
    }
}
