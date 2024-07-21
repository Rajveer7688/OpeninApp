package openIn.app.android.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import openIn.app.android.helper.RecentLink
import openin.app.android.R

class RecentAdapter(private val context: Context, private val list: MutableList<RecentLink>) : RecyclerView.Adapter<RecentAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_links_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.response = list[position]
        holder.title.text = list[position].title
        holder.dateText.text = list[position].times_ago
        holder.clicks.text = list[position].total_clicks.toString()
        holder.linkText.text = list[position].web_link
        Glide.with(context).load(list[position].original_image).into(holder.imageView)

        val text = list[position].web_link
        holder.copyButton.setOnClickListener {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context, "link copied to clipboard", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var response: RecentLink? = null
        var imageView: ImageView = itemView.findViewById(R.id.imageView)
        var title: TextView = itemView.findViewById(R.id.title)
        var dateText: TextView = itemView.findViewById(R.id.dateText)
        var clicks: TextView = itemView.findViewById(R.id.clickNumber)
        var linkText: TextView = itemView.findViewById(R.id.linkText)
        var copyButton: ImageView = itemView.findViewById(R.id.copyButton)
    }
}