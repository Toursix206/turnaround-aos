package org.android.turnaround.presentation.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.ActivitySignUpBinding
import org.android.turnaround.presentation.main.MainActivity
import org.android.turnaround.util.KeyBoardUtil
import org.android.turnaround.util.binding.BindingActivity
import org.android.turnaround.util.dialog.DialogBtnClickListener
import org.android.turnaround.util.dialog.WarningDialogFragment
import org.android.turnaround.util.dialog.WarningType
import org.android.turnaround.util.extension.repeatOnStarted

@AndroidEntryPoint
class SignUpActivity : BindingActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        initEditTextClearFocus()
        initNicknameChangeListener()
        initIsSuccessSignUpCollector()
        initIsSuccessPutSettingCollector()
    }

    private fun initEditTextClearFocus() {
        binding.layoutSignUp.setOnClickListener {
            KeyBoardUtil.hide(this)
        }
    }

    private fun initNicknameChangeListener() {
        binding.etSignUpNickname.addTextChangedListener {
            if (viewModel.isNicknameValid.value) {
                viewModel.resetIsNicknameValid()
            }
            if (viewModel.isNicknameDuplicate.value) {
                viewModel.resetIsNicknameDuplicate()
            }
        }
    }

    private fun initIsSuccessSignUpCollector() {
        repeatOnStarted {
            viewModel.isSuccessSignUp.collect { isSuccess ->
                if (isSuccess) {
                    WarningDialogFragment().apply {
                        arguments = Bundle().apply {
                            putSerializable(
                                WarningDialogFragment.WARNING_TYPE,
                                WarningType.WARNING_NOTIFICATION
                            )
                            putParcelable(
                                WarningDialogFragment.CONFIRM_ACTION,
                                DialogBtnClickListener(clickAction = { viewModel.putSetting(true) })
                            )
                            putParcelable(
                                WarningDialogFragment.CANCEL_ACTION,
                                DialogBtnClickListener(clickAction = { viewModel.putSetting(false) })
                            )
                        }
                    }.show(supportFragmentManager, WarningDialogFragment.DIALOG_WARNING)
                }
            }
        }
    }

    private fun initIsSuccessPutSettingCollector() {
        repeatOnStarted {
            viewModel.isSuccessPutSetting.collect { isSuccess ->
                if (isSuccess) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }
    }
}
