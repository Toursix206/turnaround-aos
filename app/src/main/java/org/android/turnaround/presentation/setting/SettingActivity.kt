package org.android.turnaround.presentation.setting

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.ActivitySettingBinding
import org.android.turnaround.presentation.tutorial.TutorialActivity
import org.android.turnaround.util.binding.BindingActivity
import org.android.turnaround.util.dialog.ConfirmClickListener
import org.android.turnaround.util.dialog.WarningDialogFragment
import org.android.turnaround.util.dialog.WarningType
import org.android.turnaround.util.extension.repeatOnStarted
import org.android.turnaround.util.showToast

@AndroidEntryPoint
class SettingActivity : BindingActivity<ActivitySettingBinding>(R.layout.activity_setting) {
    private val viewModel by viewModels<SettingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        initSwitchBtnCheckListener()
        initBackBtnClickListener()
        initTvWithdrawClickListener()
        initIsSuccessLogoutCollector()
        initSuccessWithdrawCollector()
    }

    private fun initSwitchBtnCheckListener() {
        binding.switchBtnSetting.setOnCheckedChangeListener { _, isChecked ->
            viewModel.putUserSetting(isChecked)
        }
    }

    private fun initBackBtnClickListener() {
        binding.ivSettingBack.setOnClickListener {
            finish()
        }
    }

    private fun initTvWithdrawClickListener() {
        binding.tvSettingWithdrawal.setOnClickListener {
            WarningDialogFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(
                        WarningDialogFragment.WARNING_TYPE,
                        WarningType.WARNING_WITHDRAW
                    )
                    putParcelable(
                        WarningDialogFragment.CONFIRM_ACTION,
                        ConfirmClickListener(confirmAction = { viewModel.deleteUser() })
                    )
                }
            }.show(supportFragmentManager, WarningDialogFragment.DIALOG_WARNING)
        }
    }

    private fun initIsSuccessLogoutCollector() {
        repeatOnStarted {
            viewModel.isSuccessLogout.collect { isSuccess ->
                if (isSuccess) {
                    showToast(getString(R.string.settings_logout_toast))
                    startActivity(
                        Intent(this, TutorialActivity::class.java).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        }
                    )
                }
            }
        }
    }

    private fun initSuccessWithdrawCollector() {
        repeatOnStarted {
            viewModel.isSuccessWithdraw.collect { isSuccess ->
                if (isSuccess) {
                    showToast(getString(R.string.withdraw_toast))
                    startActivity(
                        Intent(this, TutorialActivity::class.java).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        }
                    )
                }
            }
        }
    }
}
