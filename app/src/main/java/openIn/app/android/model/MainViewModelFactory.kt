package openIn.app.android.model

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import openin.app.android.databinding.ActivityMainBinding

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val resources: Resources, private val binding: ActivityMainBinding): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(resources, binding) as T
    }
}