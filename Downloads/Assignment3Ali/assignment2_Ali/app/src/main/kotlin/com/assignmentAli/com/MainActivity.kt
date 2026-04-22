package com.assignmentAli.com

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.assignmentAli.com.auth.LoginFragment
import com.assignmentAli.com.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        auth.addAuthStateListener { firebaseAuth ->
            val fm = supportFragmentManager
            val current = fm.findFragmentById(R.id.fragmentContainer)
            if (firebaseAuth.currentUser == null) {
                if (current !is LoginFragment) {
                    fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    fm.beginTransaction()
                        .replace(R.id.fragmentContainer, LoginFragment())
                        .commit()
                }
            } else {
                if (current is LoginFragment || current == null) {
                    fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    fm.beginTransaction()
                        .replace(R.id.fragmentContainer, MainShellFragment())
                        .commit()
                }
            }
        }
    }
}
