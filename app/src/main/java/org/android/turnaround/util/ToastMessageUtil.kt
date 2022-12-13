package org.android.turnaround.util

import android.content.Context
import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import org.android.turnaround.R
import org.android.turnaround.databinding.ToastPurpleBinding

object ToastMessageUtil {
    private var toast: Toast? = null

    fun showToast(context: Context, msg: String) {
        toast?.cancel()
        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        requireNotNull(toast).show()
    }

    fun showTopPurpleToast(context: Context, msg: String, isWarning: Boolean) {
        toast?.cancel()

        val inflater = LayoutInflater.from(context)
        val binding: ToastPurpleBinding =
            DataBindingUtil.inflate(inflater, R.layout.toast_purple, null, false)
        binding.tvToastPurple.text = msg
        binding.tvToastPurple.setTextColor(
            if (isWarning) context.getColor(R.color.turnaround_toast_red) else context.getColor(R.color.turnaround_white)
        )

        toast = Toast(context).apply {
            setGravity(Gravity.TOP or Gravity.CENTER, 0, 20.toPx())
            duration = Toast.LENGTH_SHORT
            view = binding.root
        }
        requireNotNull(toast).show()
    }

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}
