package com.example.dynamicfeaturesampleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.dynamicfeatures.DynamicExtras
import androidx.navigation.dynamicfeatures.DynamicInstallMonitor
import androidx.navigation.fragment.NavHostFragment
import com.google.android.play.core.splitinstall.SplitInstallSessionState
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus


class DynamicFeatureHolderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic_feature_holder)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.container) as NavHostFragment?

        // Get the NavController

        // Get the NavController
        val navController = navHostFragment?.navController
        val installMonitor = DynamicInstallMonitor()
        navController?.navigate(
            R.id.navigate_to_dynamic_feature,
            null,
            null,
            DynamicExtras(installMonitor)
        )

        if (installMonitor.isInstallRequired) {
            installMonitor.status.observe(this, object : Observer<SplitInstallSessionState> {
                override fun onChanged(sessionState: SplitInstallSessionState) {
                    when (sessionState.status()) {
                        SplitInstallSessionStatus.INSTALLED -> {
                            navController?.navigate(
                                R.id.navigate_to_dynamic_feature,
                                null,
                                null,
                                null
                            )
                            // Call navigate again here or after user taps again in the UI:
                            // navController.navigate(destinationId, destinationArgs, null, null)
                        }
                        SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                        }

                        // Handle all remaining states:
                        SplitInstallSessionStatus.FAILED -> {}
                        SplitInstallSessionStatus.CANCELED -> {}
                    }

                    if (sessionState.hasTerminalStatus()) {
                        installMonitor.status.removeObserver(this);
                    }
                }
            });
    }
}}