package org.android.turnaround.presentation.main

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.ActivityMainBinding
import org.android.turnaround.util.binding.BindingActivity

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBottomNavigationView()
        getExtras()
    }

    private fun getExtras() {
        if (intent.getBooleanExtra(MOVE_TO_ACTIVITY_TAB, false)) moveToActivityFragment()
    }

    private fun initBottomNavigationView() {
        val navController = findNavController()
        binding.botNavMain.setupWithNavController(navController)
    }

    private fun findNavController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fc_main) as NavHostFragment
        return navHostFragment.navController
    }

    private fun moveToActivityFragment() {
        binding.botNavMain.selectedItemId = R.id.activityFragment
    }

    companion object {
        const val MOVE_TO_ACTIVITY_TAB = "moveToActivityTab"
    }
}
