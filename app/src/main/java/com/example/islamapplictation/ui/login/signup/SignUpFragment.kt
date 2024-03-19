package com.example.islamapplictation.ui.login.signup

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.islamapplictation.data.pojo.user.AuthType
import com.example.islamapplictation.data.pojo.user.User
import com.example.islamapplictation.databinding.FragmentSignUpBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private val redColor = ColorStateList.valueOf(Color.RED)
    private val mFireAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val mDatabase: DatabaseReference by lazy {
        FirebaseDatabase.getInstance()
            .getReference("Users")
    }
    private val TAG = "SignUpFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signupBtn.setOnClickListener { registerUserWithEmailAndPassword() }

    }

    private fun checkFullName(): Boolean {
        val fullName = binding.signupEdFullName.text
        val name = fullName?.split(" ")
        if (fullName == null || name == null) {
            binding.signupFullNameInputLayout.setErrorTextColor(ColorStateList.valueOf(Color.RED))
            binding.signupFullNameInputLayout.error = "your is empty "
            return false
        } else {
            return if (fullName.isEmpty()) {
                binding.signupFullNameInputLayout.setErrorTextColor(redColor)
                binding.signupFullNameInputLayout.error = "user name is empty"
                false
            } else if (name.size < 2) {
                binding.signupFullNameInputLayout.setErrorTextColor(ColorStateList.valueOf(Color.RED))
                binding.signupFullNameInputLayout.error = "Please write your full name right"
                false
            } else if (fullName.length < 6) {
                binding.signupFullNameInputLayout.setErrorTextColor(ColorStateList.valueOf(Color.RED))
                binding.signupFullNameInputLayout.error = "your user name is less than 6 char "
                false
            } else {
                binding.signupFullNameInputLayout.error = null
                binding.signupFullNameInputLayout.isErrorEnabled = false
                true
            }
        }

    }

    private fun checkEmail(): Boolean {
        val email = binding.signupEdEmail.text
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        if (email != null) {
            return if (email.isEmpty()) {
                binding.signupEmailInputLayout.setErrorTextColor(ColorStateList.valueOf(Color.RED))
                binding.signupEmailInputLayout.error = "your email is empty"
                false
            } else if (!email.contains("@") || !email.contains(".com")) {
                binding.signupEmailInputLayout.setErrorTextColor(ColorStateList.valueOf(Color.RED))
                binding.signupEmailInputLayout.error = "Email is Invalid"
                false
            } else {
                if (!emailRegex.toRegex().matches(email)) {
                    binding.signupEmailInputLayout.setErrorTextColor(ColorStateList.valueOf(Color.RED))
                    binding.signupEmailInputLayout.error = "Email is Invalid"
                    return false
                } else {
                    binding.signupEmailInputLayout.error = null
                    binding.signupEmailInputLayout.isErrorEnabled = false
                    true
                }
            }
        } else {
            binding.signupEmailInputLayout.setErrorTextColor(ColorStateList.valueOf(Color.RED))
            binding.signupEmailInputLayout.error = "your email is empty"
            return false
        }

    }

    private fun checkPassword(): Boolean {
        val password = binding.signupEdPassword.text.toString()
        val requirements = listOf(
            isLongEnough(password),
            hasEnoughDigits(password),
            isMixedCase(password),
            hasSpecialChar(password)
        )
        val meetsRequirements = requirements.all { it }
        return if (!meetsRequirements) {
            binding.signupPasswordInputLayout.setErrorTextColor(ColorStateList.valueOf(Color.RED))
            binding.signupPasswordInputLayout.error = "your password is not strong"
            false
        } else {
            binding.signupPasswordInputLayout.error = null
            binding.signupPasswordInputLayout.isErrorEnabled = false
            true
        }
    }

    private fun checkConfirmPassword(): Boolean {
        val password = binding.signupEdPassword.text.toString()
        val confirmPassword = binding.signupEdConfirmPassword.text.toString()
        return if (password != confirmPassword) {
            binding.signupConfirmPasswordInputLayout.setErrorTextColor(ColorStateList.valueOf(Color.RED))
            binding.signupConfirmPasswordInputLayout.error = "your password is not match"
            false
        } else {
            binding.signupConfirmPasswordInputLayout.error = null
            binding.signupConfirmPasswordInputLayout.isErrorEnabled = false
            true
        }
    }

    private fun allCheck(): Boolean {
        return checkFullName() && checkEmail()
//                && checkPassword()
                && checkConfirmPassword()
    }

    private fun registerUserWithEmailAndPassword() {
        if (allCheck()) {
            val email = binding.signupEdEmail.text
            val password = binding.signupEdPassword.text
            val fullName = binding.signupEdFullName.text
            mFireAuth.createUserWithEmailAndPassword(email.toString(), password.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val uid = mFireAuth.currentUser?.uid
                        val mUser = User(
                            email.toString(),
                            fullName.toString(),
                            password.toString(),
                            AuthType.FireBaseEmailPassword.name
                        )
                        if (uid != null) {
                            mDatabase.child(uid).setValue(mUser).addOnCompleteListener {
                                if (it.isSuccessful) {
                                    binding.signupBtn.isClickable = false
                                    Snackbar.make(
                                        requireView(),
                                        "You register successfully",
                                        Snackbar.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "${it.exception?.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
//

                    } else {
                        binding.signupBtn.visibility = View.VISIBLE
                        Snackbar.make(
                            requireView(),
                            task.exception?.message.toString(),
                            Snackbar.LENGTH_SHORT
                        ).show()
                        Log.d(TAG, "registerUserWithEmailAndPassword: " + task.exception?.message)
                    }

                }

        }
    }

    private fun isLongEnough(password: String) = password.length >= 8
    private fun hasEnoughDigits(password: String) = password.isNotEmpty()
    private fun isMixedCase(password: String) =
        password.any(Char::isLowerCase) && password.any(Char::isUpperCase)

    private fun hasSpecialChar(password: String) = password.any { it in "!,+^@#&*()_=-!$" }

}