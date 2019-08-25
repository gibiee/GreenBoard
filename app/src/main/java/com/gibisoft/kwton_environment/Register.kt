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
import kotlinx.android.synthetic.main.register.*
import kotlinx.android.synthetic.main.register.view.*
import kotlinx.android.synthetic.main.register.view.registerPassword

class Register : Fragment() {
    private lateinit var mainActivity : MainActivity

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.register, null)

        view.registerButton.setOnClickListener {

            if(registerPassword.text.toString() == registerPasswordConfirm.text.toString()) {
                val db = FirebaseFirestore.getInstance()
                val user = hashMapOf(
                    "id" to registerId.text.toString(),
                    "password" to registerPassword.text.toString(),
                    "score" to 0
                )

                db.collection("users")
                    .add(user)
                    .addOnSuccessListener {
                        Toast.makeText(mainActivity, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(mainActivity, "오류 발생", Toast.LENGTH_SHORT).show()
                    }
                mainActivity.onFragmentChange("로그인")
            }
            else {
                Toast.makeText(context, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        view.backButton.setOnClickListener {
            mainActivity.onFragmentChange("로그인")
        }
        return view
    }
}