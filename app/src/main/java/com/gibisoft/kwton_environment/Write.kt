package com.gibisoft.kwton_environment

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.write.view.*
import java.text.SimpleDateFormat
import java.util.*
import android.provider.MediaStore
import android.R.attr.data
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream


class Write : Fragment() {
    private lateinit var mainActivity : MainActivity
    var bitmap: Bitmap? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.write, null)


        view.writeImage.setOnClickListener {
            var intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1)
        }


        view.write_ok.setOnClickListener {
            Toast.makeText(context,"작성",Toast.LENGTH_SHORT).show()

            var date = Date();
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

            val post = hashMapOf(
                "date" to formatter.format(date),
                "writer" to "${mainActivity.id}",
                "picture" to "${formatter.format(date)}.jpg",
                "text" to view.writeText.text.toString(),
                "tree" to 0,
                "comment" to 0)

            val db = FirebaseFirestore.getInstance()
            db.collection("timeline")
                .add(post)
                .addOnFailureListener {
                    Toast.makeText(context, "작성 에러", Toast.LENGTH_SHORT).show()
                }
                .addOnSuccessListener {
                }

            val storage = FirebaseStorage.getInstance()
            val storageRef = storage.reference
            val imagesRef = storageRef.child("timeline_picture/${formatter.format(date)}.jpg")

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
            Toast.makeText(context, "작성 완료", Toast.LENGTH_SHORT).show()
            mainActivity.onFragmentChange("타임라인")

        }
        view.write_cancel.setOnClickListener {
            mainActivity.onFragmentChange("타임라인")
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            //이미지를 하나 골랐을때
            if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
                val returnUri = data.data
                val bitmapImage = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, returnUri)
                view?.writeImage?.setImageBitmap(bitmapImage)
                bitmap = bitmapImage
            } else {
                Toast.makeText(context, "취소 되었습니다.", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Oops! 로딩에 오류가 있습니다.", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }
}