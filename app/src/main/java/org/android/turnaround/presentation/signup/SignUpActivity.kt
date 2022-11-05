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
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }
    }
}
