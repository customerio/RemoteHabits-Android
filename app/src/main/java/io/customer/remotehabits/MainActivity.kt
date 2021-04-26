package io.customer.remotehabits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.customer.sdk.ExampleSdkFile

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        ExampleSdkFile().add(1, 1)
    }

}