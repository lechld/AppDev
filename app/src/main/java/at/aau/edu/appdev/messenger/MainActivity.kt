package at.aau.edu.appdev.messenger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import at.aau.edu.appdev.messenger.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}