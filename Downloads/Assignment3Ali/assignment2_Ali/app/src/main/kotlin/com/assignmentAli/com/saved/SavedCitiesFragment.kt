package com.assignmentAli.com.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assignmentAli.com.R
import com.assignmentAli.com.databinding.FragmentSavedCitiesBinding
import com.assignmentAli.com.ui.dashboard.WeatherDashboardFragment

class SavedCitiesFragment : Fragment() {

    private var _binding: FragmentSavedCitiesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SavedCitiesViewModel by viewModels()

    private lateinit var adapter: SavedCityAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedCitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SavedCityAdapter(
            onCityClick = { city ->
                val fragment = WeatherDashboardFragment.newInstance(
                    city.cityId,
                    city.name,
                    city.region,
                    city.country
                )
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit()
            },
            onDeleteClick = { city ->
                viewModel.deleteCity(city.cityId)
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        val swipeCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                val pos = viewHolder.bindingAdapterPosition
                if (pos == RecyclerView.NO_POSITION) return
                val item = adapter.currentList.getOrNull(pos) ?: return
                viewModel.deleteCity(item.cityId)
            }
        }
        ItemTouchHelper(swipeCallback).attachToRecyclerView(binding.recyclerView)

        viewModel.savedCities.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            if (list.isNullOrEmpty()) {
                binding.emptyState.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.emptyState.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
