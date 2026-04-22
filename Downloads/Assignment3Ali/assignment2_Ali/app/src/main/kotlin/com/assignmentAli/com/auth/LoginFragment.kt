package com.assignmentAli.com.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.assignmentAli.com.R
import com.assignmentAli.com.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.errorKey.observe(viewLifecycleOwner) { key ->
            if (key == null) {
                binding.errorText.visibility = View.GONE
            } else {
                val resId = when (key) {
                    "invalid_credentials" -> R.string.auth_error_invalid_credentials
                    "signup_failed" -> R.string.auth_error_signup_failed
                    "invalid_email" -> R.string.auth_error_invalid_email
                    "email_in_use" -> R.string.auth_error_email_in_use
                    "weak_password" -> R.string.auth_error_weak_password
                    "network_error" -> R.string.auth_error_network
                    "auth_not_configured" -> R.string.auth_error_auth_not_configured
                    "too_many_requests" -> R.string.auth_error_too_many_requests
                    "empty_fields" -> R.string.auth_error_empty_fields
                    "unknown_error" -> R.string.auth_error_unknown
                    else -> R.string.auth_error_unknown
                }
                binding.errorText.setText(resId)
                binding.errorText.visibility = View.VISIBLE
            }
        }

        binding.signInButton.setOnClickListener {
            viewModel.clearError()
            val email = binding.emailInput.text?.toString().orEmpty()
            val password = binding.passwordInput.text?.toString().orEmpty()
            viewModel.signIn(email, password)
        }

        binding.signUpButton.setOnClickListener {
            viewModel.clearError()
            val email = binding.emailInput.text?.toString().orEmpty()
            val password = binding.passwordInput.text?.toString().orEmpty()
            viewModel.signUp(email, password)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
