package com.assignmentAli.com

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.assignmentAli.com.databinding.FragmentMainShellBinding
import com.assignmentAli.com.saved.SavedCitiesFragment
import com.assignmentAli.com.settings.SettingsFragment
import com.assignmentAli.com.ui.search.CitySearchFragment

class MainShellFragment : Fragment() {

    private var _binding: FragmentMainShellBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainShellBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bottomNav.setOnItemSelectedListener { item ->
            val fragment: Fragment = when (item.itemId) {
                R.id.nav_search -> CitySearchFragment()
                R.id.nav_saved -> SavedCitiesFragment()
                R.id.nav_settings -> SettingsFragment()
                else -> return@setOnItemSelectedListener false
            }
            childFragmentManager.beginTransaction()
                .replace(R.id.tabContainer, fragment)
                .commit()
            true
        }

        if (savedInstanceState == null) {
            binding.bottomNav.selectedItemId = R.id.nav_search
            childFragmentManager.beginTransaction()
                .replace(R.id.tabContainer, CitySearchFragment())
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
