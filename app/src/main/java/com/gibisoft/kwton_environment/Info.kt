package com.gibisoft.kwton_environment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.info.view.*
import kotlinx.android.synthetic.main.login.view.*
import kotlinx.android.synthetic.main.write.view.*
import java.io.ByteArrayOutputStream

class Info : Fragment() {
    private lateinit var mainActivity : MainActivity
    var bitmap: Bitmap? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.info, null)

        view.info_id.text = "${mainActivity.id}"

        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .whereEqualTo("id", mainActivity.id)
            .get()
            .addOnSuccessListener { result ->
                for (data in result) {
                    view.info_score.text = "나의 환경 점수 : ${ data.get("score") }"
                }
            }

        Glide.with(mainActivity)
            .load("https://storage.googleapis.com/kwton-c336f.appspot.com/profile_image/${mainActivity.id}.jpg")
            .into(view.profile_image)

        view.profile_image.setOnClickListener {
            var intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1)
        }

        view.timelineButton.setOnClickListener {
            mainActivity.onFragmentChange("타임라인")
        }
        view.rankButton.setOnClickListener {
            mainActivity.onFragmentChange("랭크")
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            //이미지를 하나 골랐을때
            if (requestCode == 1 && resultCode == Activity.RESULT_OK && null != data) {
                val returnUri = data.data
                val bitmapImage = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, returnUri)
                view?.writeImage?.setImageBitmap(bitmapImage)
                bitmap = bitmapImage

                val storage = FirebaseStorage.getInstance()
                val storageRef = storage.reference
                val imagesRef = storageRef.child("profile_image/${mainActivity.id}.jpg")

                val baos = ByteArrayOutputStream()
                bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                var uploadTask = imagesRef.putBytes(data)
                uploadTask.addOnFailureListener {
                    // Handle unsuccessful uploads
                    Toast.makeText(context, "이미지 업로드 실패", Toast.LENGTH_SHORT).show()
                }.addOnSuccessListener {
                    // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                    // ...
                }
                Toast.makeText(context, "프로필 작성 완료", Toast.LENGTH_SHORT).show()
                Glide.with(mainActivity)
                    .load("https://storage.googleapis.com/kwton-c336f.appspot.com/profile_image/${mainActivity.id}.jpg")
                    .into(view!!.profile_image)

            } else {
                Toast.makeText(context, "취소 되었습니다.", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Oops! 로딩에 오류가 있습니다.", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }
}