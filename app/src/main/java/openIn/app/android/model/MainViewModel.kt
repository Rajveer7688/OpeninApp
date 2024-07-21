package openIn.app.android.model

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import openIn.app.android.helper.Response
import openIn.app.android.network.InterfaceAPI
import openIn.app.android.network.ServiceBuilder
import openin.app.android.R
import openin.app.android.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback

class MainViewModel(res: Resources, bind: ActivityMainBinding) : ViewModel() {
    private val resources = res
    private val binding = bind
    private val mTAG = "MainViewModel"
    private lateinit var number: String
    private val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjU5MjcsImlhdCI6MTY3NDU1MDQ1MH0.dCkW0ox8tbjJA2GgUx2UEwNlbTZ7Rr38PVFJevYcXFI"

    fun setupLineChart(lineChart: LineChart) {
        val entries = listOf(
            Entry(0f, 20f),
            Entry(1f, 40f),
            Entry(2f, 60f),
            Entry(3f, 80f),
            Entry(4f, 100f),
            Entry(5f, 60f),
            Entry(6f, 40f),
            Entry(7f, 20f),
            Entry(8f, 100f),
            Entry(9f, 60f),
            Entry(10f, 20f),
            Entry(11f, 80f)
        )

        val dataSet = LineDataSet(entries, "").apply {
            color = ResourcesCompat.getColor(resources, R.color.blue, null)
            lineWidth = 2.5f
            setDrawFilled(true)
            setDrawCircles(false)
            label.isEmpty()
            valueTextSize = 0f
            fillFormatter = IFillFormatter { _, _ -> lineChart.axisLeft.axisMinimum }
            fillDrawable = getGradientDrawable()
            valueTypeface = Typeface.DEFAULT_BOLD
            mode = LineDataSet.Mode.LINEAR
        }

        val lineData = LineData(dataSet)
        lineChart.data = lineData
        lineChart.apply {
            description = Description().apply { text = "" }
            xAxis.apply {
                valueFormatter = IndexAxisValueFormatter(arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"))
                textSize = 10f
                textColor = ResourcesCompat.getColor(resources, R.color.dark_text, null)
                setDrawGridLines(true)
                gridColor = ResourcesCompat.getColor(resources, R.color.grid_line_color, null)
                gridLineWidth = 1f
                position = XAxis.XAxisPosition.BOTTOM
            }

            axisLeft.apply {
                textSize = 10f
                textColor = ResourcesCompat.getColor(resources, R.color.dark_text, null)
                setDrawGridLines(true)
                gridColor = ResourcesCompat.getColor(resources, R.color.grid_line_color, null)
                gridLineWidth = 1f
                axisMinimum = 0f
                setLabelCount(5, true)
            }

            axisRight.isEnabled = false
            setDrawGridBackground(false)
            legend.isEnabled = false
            setDrawBorders(false)
            setBorderWidth(0f)
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(true)
            setPinchZoom(true)
            animateXY(800, 800, Easing.EaseInOutBounce, Easing.EaseInExpo)
        }
        lineChart.invalidate()
    }

    private fun getGradientDrawable(): Drawable {
        val gradient = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(Color.parseColor("#B3C6FF"), Color.TRANSPARENT)
        )
        gradient.cornerRadius = 0f
        gradient.gradientType = GradientDrawable.LINEAR_GRADIENT
        return gradient
    }

    fun setUpHorizontalRecyclerView(context: Context) {
        val service = ServiceBuilder.buildService(InterfaceAPI::class.java, token).getResponse()
        service.enqueue(object : Callback<Response?> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<Response?>, response: retrofit2.Response<Response?>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        binding.clickTitle.text = responseBody.today_clicks.toString().trim()
                        binding.locationTitle.text = responseBody.top_location
                        binding.sourceTitle.text = responseBody.top_source
                        binding.linkTitle.text = responseBody.total_links.toString().trim()
                        binding.incomeTitle.text = responseBody.extra_income.toString().trim()
                        binding.totalClickTitle.text = responseBody.total_clicks.toString().trim()
                        number = responseBody.support_whatsapp_number.trim()
                    }
                }
            }

            override fun onFailure(call: Call<Response?>, t: Throwable) {
                Log.d(mTAG, "onFailure: ${t.message}")
                showToastMessage("Something went wrong, please try again later.", context)
            }
        })
    }

    private fun showToastMessage(s: String, context: Context) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
    }

    fun openWhatsAppService(context: Context) {
        if (number.isNotEmpty()) {
            try {
                val uri = Uri.parse("https://api.whatsapp.com/send?phone=$number")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.setPackage("com.whatsapp")
                if (intent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(intent)
                } else showToastMessage("WhatsApp is not installed on your device", context)
            } catch (e: Exception) {
                e.printStackTrace()
                showToastMessage("Error opening WhatsApp", context)
            }
        } else showToastMessage("Now, This service is not available.", context)
    }

}