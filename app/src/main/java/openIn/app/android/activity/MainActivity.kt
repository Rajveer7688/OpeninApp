package openIn.app.android.activity

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import openIn.app.android.fragments.RecentLinkFragment
import openIn.app.android.fragments.TopLinkFragment
import openIn.app.android.model.MainViewModel
import openIn.app.android.model.MainViewModelFactory
import openin.app.android.R
import openin.app.android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigation.background = null
        binding.bottomNavigation.menu.getItem(2).isEnabled = false
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(resources, binding))[MainViewModel::class.java]
        mainViewModel.setupLineChart(binding.lineChart)
        mainViewModel.setUpHorizontalRecyclerView(this)
        binding.whatsAppButton.setOnClickListener { mainViewModel.openWhatsAppService(this) }

        selectButton(binding.topLinkButton)
        replaceFragment(TopLinkFragment())
        binding.topLinkButton.setOnClickListener {
            selectButton(binding.topLinkButton)
            replaceFragment(TopLinkFragment())
        }
        binding.recentLinkButton.setOnClickListener {
            selectButton(binding.recentLinkButton)
            replaceFragment(RecentLinkFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun selectButton(selectedButton: Button) {
        val greyColor = ResourcesCompat.getColor(resources, R.color.dark_text, null)
        val buttons = listOf(binding.topLinkButton, binding.recentLinkButton)
        buttons.forEach { button ->
            if (button == selectedButton) {
                button.setBackgroundResource(R.drawable.back_button_blue)
                button.setTextColor(Color.WHITE)
            } else {
                button.setBackgroundResource(android.R.color.transparent)
                button.setTextColor(greyColor)
            }
        }
    }

}