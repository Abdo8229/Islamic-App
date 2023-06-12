package com.example.islamapplictation.ui.login.signin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.islamapplictation.IslamApplication
import com.example.islamapplictation.MainActivity
import com.example.islamapplictation.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signinBtn.setOnClickListener {
            val email = binding.signinEdEmail.editText?.text.toString()
            val password = binding.signinEdPassword.editText?.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                binding.signinEdEmail.error = "email is empty"
                binding.signinEdPassword.error = "password is empty"
            } else {
                signInWithEmailAndPassword(email, password)
            }
        }

    }

    private fun signInWithEmailAndPassword(email: String, password: String) {
        val intent = Intent(requireContext(), MainActivity::class.java).apply {
            flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        mAuth.signOut()
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { authResultTask ->
            if (authResultTask.isSuccessful && authResultTask.isComplete) {
//               if (mAuth.currentUser?.isEmailVerified == false) {
//                   binding.signinEdEmail.error = "please verify your email"
//                   binding.signinEdPassword.error = "please verify your email"
//                    mAuth.currentUser!!.sendEmailVerification().addOnCompleteListener {
//                        if (it.isSuccessful) {
//                            Toast.makeText(requireContext(), "please verify your email", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                   return@addOnCompleteListener
//               }else if (mAuth.currentUser?.isEmailVerified == true) {

                Toast.makeText(requireContext(), "Kolo tam-am ya prince ", Toast.LENGTH_SHORT)
                    .show()
                startActivity(intent)
                requireActivity().finish()


//                   }
            } else {
                binding.signinEdEmail.error = "email is not correct"
                binding.signinEdPassword.error = "password is not correct"
            }
        }

    }
}