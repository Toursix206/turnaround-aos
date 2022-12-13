package org.android.turnaround.util

import android.content.Context
import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import org.android.turnaround.R
import org.android.turnaround.databinding.ToastTurnaroundPurpleBinding

object ToastMessageUtil {
    private var toast: Toast? = null

    fun showToast(context: Context, msg: String) {
        toast?.cancel()
        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        requireNotNull(toast).show()
    }

    fun showPurpleToast(
        context: Context,
        content: String,
        isWarning: Boolean,
        gravity: Int = Gravity.FILL_HORIZONTAL
    ) {
        toast?.cancel()

        val inflater = LayoutInflater.from(context)
        val binding: ToastTurnaroundPurpleBinding =
            DataBindingUtil.inflate(inflater, R.layout.toast_turnaround_purple, null, false)
        binding.content = content
        binding.tvText.setTextColor(
            if (isWarning) context.getColor(R.color.turnaround_toast_red) else context.getColor(R.color.turnaround_white)
        )

        toast = Toast(context).apply {
            when (gravity) {
                Gravity.TOP -> setGravity(Gravity.TOP + Gravity.FILL_HORIZONTAL, 0, 28.toPx())
                Gravity.FILL_HORIZONTAL -> setGravity(Gravity.FILL_HORIZONTAL, 0, 0)
            }
            duration = Toast.LENGTH_SHORT
            view = binding.root
        }

        requireNotNull(toast).show()
    }

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}
