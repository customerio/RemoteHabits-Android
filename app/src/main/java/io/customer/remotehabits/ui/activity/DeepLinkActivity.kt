package io.customer.remotehabits.ui.activity

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.customer.remotehabits.databinding.ActivityDeepLinkBinding

class DeepLinkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeepLinkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeepLinkBinding.inflate(layoutInflater).apply {
//            setContentView(root)
        }

        val data: Uri? = intent?.data
        binding.dataTv.text = data.toString()
    }
}
