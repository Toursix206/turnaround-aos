package org.android.turnaround.presentation.signup

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.ActivitySignUpBinding
import org.android.turnaround.util.binding.BindingActivity

@AndroidEntryPoint
class SignUpActivity : BindingActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        initNicknameChangeListener()
    }

    private fun initNicknameChangeListener() {
        binding.etSignUpNickname.addTextChangedListener {
            if (viewModel.isNicknameValid.value) {
                viewModel.resetIsNicknameValid()
            }
        }
    }
}
