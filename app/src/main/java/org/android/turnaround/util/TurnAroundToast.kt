package org.android.turnaround.util

import android.content.Context
import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import org.android.turnaround.R
import org.android.turnaround.databinding.ToastTurnaroundPurpleBinding

object TurnAroundToast {
    private var toast: Toast? = null

    fun showToast(
        context: Context,
        content: String,
        textColor: Int = R.color.turnaround_white,
        gravity: Int = Gravity.FILL_HORIZONTAL
    ) {
        toast = Toast(context)
        val inflater = LayoutInflater.from(context)
        val binding: ToastTurnaroundPurpleBinding =
            DataBindingUtil.inflate(inflater, R.layout.toast_turnaround_purple, null, false)
        binding.content = content
        binding.textColor = textColor
        if (toast != null) {
            requireNotNull(toast).apply {
                when (gravity) {
                    Gravity.TOP -> setGravity(Gravity.TOP + Gravity.FILL_HORIZONTAL, 0, 28.toPx())
                    Gravity.FILL_HORIZONTAL -> setGravity(Gravity.FILL_HORIZONTAL, 0, 0)
                }
                duration = Toast.LENGTH_SHORT
                view = binding.root
                show()
            }
        }
    }

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}
