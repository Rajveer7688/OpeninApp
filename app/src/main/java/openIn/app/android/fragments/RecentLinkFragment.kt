package openIn.app.android.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import openIn.app.android.adapter.RecentAdapter
import openIn.app.android.helper.RecentLink
import openIn.app.android.model.TopViewModel
import openIn.app.android.model.TopViewModelFactory
import openin.app.android.R
import openin.app.android.databinding.FragmentRecentLinkBinding

class RecentLinkFragment : Fragment() {
    private lateinit var binding: FragmentRecentLinkBinding
    private lateinit var adapter: RecentAdapter
    private lateinit var list: MutableList<RecentLink>
    private lateinit var token: String
    private lateinit var viewModel: TopViewModel
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRecentLinkBinding.bind(view)
        list = mutableListOf()
        adapter = RecentAdapter(requireContext(), list)
        binding.recentRecyclerView.adapter = adapter
        binding.recentRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recentRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recentRecyclerView.setHasFixedSize(true)
        binding.progressBar.isVisible = true
        token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjU5MjcsImlhdCI6MTY3NDU1MDQ1MH0.dCkW0ox8tbjJA2GgUx2UEwNlbTZ7Rr38PVFJevYcXFI"

        val topViewModelFactory = TopViewModelFactory(token)
        viewModel = ViewModelProvider(this, topViewModelFactory)[TopViewModel::class.java]
        viewModel.fetchTopLinks()
        viewModel.topLinks.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                list.clear()
                list.addAll(response.data.recent_links)
                adapter.notifyDataSetChanged()
                binding.progressBar.isVisible = false
            } else {
                Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show()
                binding.progressBar.isVisible = false
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recent_link, container, false)
    }
}