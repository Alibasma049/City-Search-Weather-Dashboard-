package com.assignmentAli.com.ui.search;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.assignmentAli.com.databinding.ItemCityBinding;
import com.assignmentAli.com.model.City;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    public interface OnCityClickListener {
        void onCityClick(City city);
    }

    private List<City> cities;
    private final OnCityClickListener listener;

    public CityAdapter(List<City> cities, OnCityClickListener listener) {
        this.cities = cities;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCityBinding binding = ItemCityBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new CityViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        City city = cities.get(position);
        holder.bind(city, listener);
    }

    @Override
    public int getItemCount() {
        return cities == null ? 0 : cities.size();
    }

    public void updateCities(List<City> newCities) {
        List<City> next = newCities != null ? newCities : new java.util.ArrayList<>();
        DiffUtil.DiffResult diffResult =
                DiffUtil.calculateDiff(new CityDiffCallback(this.cities, next));
        this.cities = next;
        diffResult.dispatchUpdatesTo(this);
    }

    private static class CityDiffCallback extends DiffUtil.Callback {

        private final List<City> oldList;
        private final List<City> newList;

        CityDiffCallback(List<City> oldList, List<City> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList == null ? 0 : oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList == null ? 0 : newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            City oldCity = oldList.get(oldItemPosition);
            City newCity = newList.get(newItemPosition);
            if (!oldCity.getId().isEmpty() && !newCity.getId().isEmpty()) {
                return oldCity.getId().equals(newCity.getId());
            }
            return oldCity.getName().equals(newCity.getName())
                    && oldCity.getCountry().equals(newCity.getCountry());
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            City a = oldList.get(oldItemPosition);
            City b = newList.get(newItemPosition);
            return a.getName().equals(b.getName())
                    && a.getRegion().equals(b.getRegion())
                    && a.getCountry().equals(b.getCountry())
                    && a.getId().equals(b.getId());
        }
    }

    static class CityViewHolder extends RecyclerView.ViewHolder {

        private final ItemCityBinding binding;

        CityViewHolder(ItemCityBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(City city, OnCityClickListener listener) {
            binding.cityName.setText(city.getName());
            binding.regionCountry.setText(
                    binding.getRoot().getContext().getString(
                            com.assignmentAli.com.R.string.city_region_country,
                            city.getRegion(),
                            city.getCountry()
                    )
            );
            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) {
                    listener.onCityClick(city);
                }
            });
        }
    }
}
