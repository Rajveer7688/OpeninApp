package openIn.app.android.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import openIn.app.android.adapter.LinksAdapter
import openIn.app.android.helper.TopLink
import openIn.app.android.model.TopViewModel
import openIn.app.android.model.TopViewModelFactory
import openin.app.android.R
import openin.app.android.databinding.FragmentTopLinkBinding

class TopLinkFragment : Fragment() {
    private lateinit var binding: FragmentTopLinkBinding
    private lateinit var adapter: LinksAdapter
    private lateinit var list: MutableList<TopLink>
    private lateinit var viewModel: TopViewModel
    private lateinit var token: String
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTopLinkBinding.bind(view)
        list = mutableListOf()
        adapter = LinksAdapter(requireContext(), list)
        binding.topRecyclerView.adapter = adapter
        binding.topRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.topRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.topRecyclerView.setHasFixedSize(true)
        binding.progressBar.isVisible = true

        // Set the token
        token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjU5MjcsImlhdCI6MTY3NDU1MDQ1MH0.dCkW0ox8tbjJA2GgUx2UEwNlbTZ7Rr38PVFJevYcXFI"

        // Fetch data from the API
        val topViewModelFactory = TopViewModelFactory(token)
        viewModel = ViewModelProvider(this, topViewModelFactory)[TopViewModel::class.java]
        viewModel.fetchTopLinks()

        // Observe the LiveData
        viewModel.topLinks.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                list.clear()
                list.addAll(response.data.top_links)
                adapter.notifyDataSetChanged()
                binding.progressBar.isVisible = false
            } else {
                Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show()
                binding.progressBar.isVisible = false
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_top_link, container, false)
    }
}