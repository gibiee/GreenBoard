package com.gibisoft.kwton_environment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.login.view.*

class Login : Fragment() {
    private lateinit var mainActivity : MainActivity

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.login, null)

        view.loginButton.setOnClickListener {
            val db = FirebaseFirestore.getInstance()
            db.collection("users")
                .whereEqualTo("id", view.loginId.text.toString())
                .whereEqualTo("password", view.loginPassword.text.toString())
                .get()
                .addOnSuccessListener { result ->
                    if (result.size() == 0) {
                        Toast.makeText(context, "로그인 실패", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "로그인 성공", Toast.LENGTH_SHORT).show()
                        mainActivity.id = view.loginId.text.toString()
                        mainActivity.onFragmentChange("타임라인")
                    }
                }
        }

        view.loginRegisterButton.setOnClickListener {
            mainActivity.onFragmentChange("회원가입")
        }
        return view
    }
}