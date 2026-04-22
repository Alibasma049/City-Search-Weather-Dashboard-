package com.assignmentAli.com.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.assignmentAli.com.R;
import com.assignmentAli.com.dashboard.WeatherDashboardViewModel;
import com.assignmentAli.com.databinding.FragmentWeatherDashboardBinding;
import com.assignmentAli.com.viewmodel.WeatherViewModel;

public class WeatherDashboardFragment extends Fragment {

    private FragmentWeatherDashboardBinding binding;
    private WeatherDashboardViewModel bookmarkViewModel;

    public static WeatherDashboardFragment newInstance(
            String cityId,
            String cityName,
            String region,
            String country) {

        WeatherDashboardFragment fragment = new WeatherDashboardFragment();
        Bundle bundle = new Bundle();
        bundle.putString("cityId", cityId);
        bundle.putString("city", cityName);
        bundle.putString("region", region);
        bundle.putString("country", country);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentWeatherDashboardBinding.inflate(inflater, container, false);

        WeatherViewModel weatherViewModel =
                new ViewModelProvider(this).get(WeatherViewModel.class);

        bookmarkViewModel = new ViewModelProvider(this).get(WeatherDashboardViewModel.class);

        String cityName = null;
        String region = "";
        String countryArg = "";
        String cityId = "";

        if (getArguments() != null) {
            cityName = getArguments().getString("city");
            region = getArguments().getString("region", "");
            countryArg = getArguments().getString("country", "");
            cityId = getArguments().getString("cityId", "");
        }

        final String finalCityId = cityId;
        final String finalCityName = cityName != null ? cityName : "";
        final String finalRegion = region;
        final String finalCountryArg = countryArg;

        binding.toolbar.setNavigationOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack());

        binding.toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_bookmark) {
                bookmarkViewModel.saveOrUpdateBookmark(
                        finalCityId,
                        finalCityName,
                        finalRegion,
                        finalCountryArg
                );
                return true;
            }
            return false;
        });

        weatherViewModel.getWeather().observe(getViewLifecycleOwner(), weather -> {
            if (weather == null) {
                return;
            }

            binding.toolbar.setTitle(weather.getCity());
            binding.cityName.setText(weather.getCity());
            if (weather.getCountry() != null && !weather.getCountry().isEmpty()) {
                binding.country.setText(weather.getCountry());
                binding.country.setVisibility(View.VISIBLE);
            } else if (!finalCountryArg.isEmpty()) {
                binding.country.setText(finalCountryArg);
                binding.country.setVisibility(View.VISIBLE);
            } else {
                binding.country.setVisibility(View.GONE);
            }

            binding.temp.setText(
                    getString(R.string.temperature,
                            weather.getTempC(),
                            weather.getTempF())
            );

            if (weather.getConditionText() != null && !weather.getConditionText().isEmpty()) {
                binding.condition.setText(weather.getConditionText());
                binding.condition.setVisibility(View.VISIBLE);
            } else {
                binding.condition.setVisibility(View.GONE);
            }

            binding.feelsLike.setText(
                    getString(R.string.feels_Like,
                            weather.getFeelsLike())
            );

            binding.humidity.setText(
                    getString(R.string.humidity,
                            weather.getHumidity())
            );

            binding.windSpeed.setText(
                    getString(R.string.wind_speed,
                            weather.getWindSpeed())
            );

            binding.windDirection.setText(
                    getString(R.string.wind_direction,
                            weather.getWindDirection())
            );

            if (weather.getIcon() != null && !weather.getIcon().isEmpty()) {
                Glide.with(this)
                        .load(weather.getIcon())
                        .into(binding.weatherIcon);
            }
        });

        bookmarkViewModel.getBookmarkState().observe(getViewLifecycleOwner(), saved -> {
            if (saved == null) {
                return;
            }
            android.view.MenuItem bookmarkItem = binding.toolbar.getMenu().findItem(R.id.action_bookmark);
            if (bookmarkItem != null) {
                bookmarkItem.setIcon(
                        saved ? R.drawable.ic_bookmark_filled : R.drawable.ic_bookmark_border
                );
            }
        });

        if (cityName != null) {
            weatherViewModel.loadWeather(cityName);
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String cityId = "";
        if (getArguments() != null) {
            cityId = getArguments().getString("cityId", "");
        }
        bookmarkViewModel.refreshBookmarkState(cityId);
    }
}
