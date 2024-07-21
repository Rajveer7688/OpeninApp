package openIn.app.android.network

import openIn.app.android.helper.Response
import retrofit2.Call
import retrofit2.http.GET

interface InterfaceAPI {
    @GET("/api/v1/dashboardNew/")
    fun getResponse(): Call<Response>
}