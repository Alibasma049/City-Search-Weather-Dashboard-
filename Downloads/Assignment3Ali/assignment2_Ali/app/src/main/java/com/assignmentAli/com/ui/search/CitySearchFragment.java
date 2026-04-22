package com.assignmentAli.com.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.assignmentAli.com.R;
import com.assignmentAli.com.auth.AuthViewModel;
import com.assignmentAli.com.databinding.FragmentCitySearchBinding;
import com.assignmentAli.com.model.City;
import com.assignmentAli.com.ui.dashboard.WeatherDashboardFragment;
import com.assignmentAli.com.viewmodel.CitySearchViewModel;

import java.util.ArrayList;

public class CitySearchFragment extends Fragment {

    private FragmentCitySearchBinding binding;
    private CitySearchViewModel viewModel;
    private CityAdapter adapter;

    public CitySearchFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCitySearchBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(this).get(CitySearchViewModel.class);

        adapter = new CityAdapter(new ArrayList<>(), this::openWeather);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        binding.toolbar.inflateMenu(R.menu.menu_city_search);
        binding.toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_sign_out) {
                new ViewModelProvider(requireActivity()).get(AuthViewModel.class).signOut();
                return true;
            }
            return false;
        });

        viewModel.getLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading != null) {
                binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            }
        });

        viewModel.getCities().observe(getViewLifecycleOwner(), cities -> {
            binding.progressBar.setVisibility(View.GONE);
            adapter.updateCities(cities);

            if (cities == null || cities.isEmpty()) {
                binding.emptyState.setVisibility(View.VISIBLE);
                binding.recyclerView.setVisibility(View.GONE);
            } else {
                binding.emptyState.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);
            }
        });

        binding.searchButton.setOnClickListener(v -> {
            String query = binding.searchInput.getText().toString().trim();
            if (!query.isEmpty()) {
                viewModel.searchCities(query);
            }
        });

        return binding.getRoot();
    }

    private void openWeather(City city) {
        WeatherDashboardFragment fragment = WeatherDashboardFragment.newInstance(
                city.getId(),
                city.getName(),
                city.getRegion(),
                city.getCountry()
        );

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();
    }
}
