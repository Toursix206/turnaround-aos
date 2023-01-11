package org.android.turnaround.presentation.setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.ActivitySettingBinding
import org.android.turnaround.presentation.tutorial.TutorialActivity
import org.android.turnaround.util.ToastMessageUtil
import org.android.turnaround.util.binding.BindingActivity
import org.android.turnaround.util.dialog.DialogBtnClickListener
import org.android.turnaround.util.dialog.WarningDialogFragment
import org.android.turnaround.util.dialog.WarningType
import org.android.turnaround.util.extension.repeatOnStarted

@AndroidEntryPoint
class SettingActivity : BindingActivity<ActivitySettingBinding>(R.layout.activity_setting) {
    private val viewModel by viewModels<SettingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        initSwitchBtnCheckListener()
        initBackBtnClickListener()
        initTvPolicyClickListener()
        initOpenSourceClickListener()
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

    private fun initTvPolicyClickListener() {
        binding.tvSettingPolicy.setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(requireNotNull(viewModel.setting.value).policyUrl))
            )
        }
    }

    private fun initOpenSourceClickListener() {
        binding.tvSettingOpenSource.setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(requireNotNull(viewModel.setting.value).openSourceLicenseUrl.aos))
            )
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
                        WarningDialogFragment.CANCEL_ACTION,
                        DialogBtnClickListener(clickAction = { viewModel.deleteUser() })
                    )
                }
            }.show(supportFragmentManager, WarningDialogFragment.DIALOG_WARNING)
        }
    }

    private fun initIsSuccessLogoutCollector() {
        repeatOnStarted {
            viewModel.isSuccessLogout.collect { isSuccess ->
                if (isSuccess) {
                    ToastMessageUtil.showToast(this@SettingActivity, getString(R.string.settings_logout_toast))
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
                    ToastMessageUtil.showToast(this@SettingActivity, getString(R.string.withdraw_toast))
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
