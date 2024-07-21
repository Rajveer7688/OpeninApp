package openIn.app.android.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TopViewModelFactory(private val token: String?): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TopViewModel::class.java)) {
            return TopViewModel(token) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}