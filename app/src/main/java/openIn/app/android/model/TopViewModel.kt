package openIn.app.android.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import openIn.app.android.helper.Response
import openIn.app.android.respository.TopRepo

class TopViewModel(token: String?): ViewModel() {
    private val repo = TopRepo(token)
    private val topLinkData = MutableLiveData<Response?>()
    val topLinks: MutableLiveData<Response?>
        get() = topLinkData

    fun fetchTopLinks() {
        viewModelScope.launch {
            val result = repo.getResponse()
            topLinkData.postValue(result)
        }
    }
}
