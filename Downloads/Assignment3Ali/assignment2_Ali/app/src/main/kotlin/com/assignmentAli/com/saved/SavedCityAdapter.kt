package com.assignmentAli.com.saved

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.assignmentAli.com.data.SavedCityUi
import com.assignmentAli.com.databinding.ItemSavedCityBinding
import java.text.DateFormat

class SavedCityAdapter(
    private val onCityClick: (SavedCityUi) -> Unit,
    private val onDeleteClick: (SavedCityUi) -> Unit
) : ListAdapter<SavedCityUi, SavedCityAdapter.VH>(DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemSavedCityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position), onCityClick, onDeleteClick)
    }

    class VH(private val binding: ItemSavedCityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: SavedCityUi,
            onCityClick: (SavedCityUi) -> Unit,
            onDeleteClick: (SavedCityUi) -> Unit
        ) {
            binding.cityName.text = item.name
            binding.regionCountry.text = binding.root.context.getString(
                com.assignmentAli.com.R.string.city_region_country,
                item.region,
                item.country
            )
            val ts = item.lastSearched
            binding.lastSearched.text = if (ts != null) {
                val formatted = DateFormat.getDateTimeInstance(
                    DateFormat.MEDIUM,
                    DateFormat.SHORT
                ).format(ts.toDate())
                binding.root.context.getString(
                    com.assignmentAli.com.R.string.saved_last_searched,
                    formatted
                )
            } else {
                ""
            }
            binding.root.setOnClickListener { onCityClick(item) }
            binding.deleteButton.setOnClickListener { onDeleteClick(item) }
        }
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<SavedCityUi>() {
            override fun areItemsTheSame(a: SavedCityUi, b: SavedCityUi) =
                a.cityId == b.cityId

            override fun areContentsTheSame(a: SavedCityUi, b: SavedCityUi) = a == b
        }
    }
}
