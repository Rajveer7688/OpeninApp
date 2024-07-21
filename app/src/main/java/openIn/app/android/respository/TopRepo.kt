package openIn.app.android.respository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import openIn.app.android.helper.Response
import openIn.app.android.network.InterfaceAPI
import openIn.app.android.network.ServiceBuilder
import retrofit2.await

class TopRepo(token: String?) {
    private val apiService = ServiceBuilder.buildService(InterfaceAPI::class.java, token)
    suspend fun getResponse(): Response? {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getResponse().await()
                response
            } catch (e: Exception) {
                null
            }
        }
    }
}