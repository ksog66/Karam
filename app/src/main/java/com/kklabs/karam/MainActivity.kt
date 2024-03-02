package com.kklabs.karam

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.kklabs.karam.presentation.KaramApp
import com.kklabs.karam.ui.theme.KaramTheme
import dagger.hilt.android.AndroidEntryPoint

@RequiresApi(Build.VERSION_CODES.O)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KaramTheme {
                KaramApp(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}