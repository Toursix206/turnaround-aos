package org.android.turnaround.presentation.setting

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.ActivitySettingBinding
import org.android.turnaround.util.binding.BindingActivity

@AndroidEntryPoint
class SettingActivity : BindingActivity<ActivitySettingBinding>(R.layout.activity_setting) {
    private val viewModel by viewModels<SettingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUserSettingObserver()
        initSwitchBtnCheckListener()
    }

    private fun initUserSettingObserver() {
        viewModel.setting.observe(this) {
            binding.setting = it
        }
    }

    private fun initSwitchBtnCheckListener() {
        binding.switchBtnSetting.setOnCheckedChangeListener { view, isChecked ->
            viewModel.putUserSetting(isChecked)
        }
    }
}
