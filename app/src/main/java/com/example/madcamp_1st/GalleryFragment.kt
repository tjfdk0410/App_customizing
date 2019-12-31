package com.example.madcamp_1st

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Build.*
import android.os.Bundle
import android.provider.ContactsContract
import android.view.AbsSavedState
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.gallery.*


class GalleryFragment: Fragment() {

    var imgList = arrayListOf<Image>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(
            R.layout.gallery,
            container,
            false
        ) //fragement 생성 위한 view를 gallery에서 띄우고 반환

//        var rv: RecyclerView = view.findViewById(R.id.gallRecyclerView)
////        var galleryAdapter = GalleryRVAdapter(requireContext(), imgList)
////        println("11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111")
////
////        var SPAN_COUNT = 2
////        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
////            SPAN_COUNT = 3
////        }
////
////        //가로모드인 경우 col수 3개로 바꿈
////
////        val layoutManager = GridLayoutMana ger(requireContext(), SPAN_COUNT)
////        rv.layoutManager = layoutManager
////        rv.setHasFixedSize(true)
////
////        rv.adapter = galleryAdapter
        imgList.add(Image(Uri.parse("file://https://cdn.pixabay.com/photo/2019/12/23/15/07/freiburg-4714770_960_720.jpg")))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //var rv: RecyclerView = view.findViewById(R.id.gallRecyclerView)
        var galleryAdapter = GalleryRVAdapter(context, imgList)
        gallRecyclerView.adapter = galleryAdapter

        var SPAN_COUNT = 2
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            SPAN_COUNT = 3
        }
        //가로모드인 경우 col수 3개로 바꿈

        val layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
//        val layoutManager = LinearLayoutManager(requireContext())
        gallRecyclerView.layoutManager = layoutManager
        gallRecyclerView.setHasFixedSize(true)
        getgallerypermission()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        getgallerypermission()
    }


    /**
     *  pickImageFromGallery()
     *  get permission and call requestpermissions
     *  if don't have to, call pickImageFromGallery(already get or less OS level)
     */
    private fun getgallerypermission() {
//        print("pickkkkkkkkkkkk")
        img_pick_btn.setOnClickListener {
            //check runtime permission
            if (VERSION.SDK_INT >= VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                } else {
                    //permission already granted
                    pickImageFromGallery()
                }
            } else {
                //system OS is < Marshmallow
                pickImageFromGallery()
            }
        }
    }


    /**
     *  pickImageFromGallery()
     *  through intent, call gallery activity
     *  go to gallery activity, and get some image
     */
    private fun pickImageFromGallery() {
//        println("ffff")
        //Intent to pick image
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = "image/*"
//        startActivityForResult(intent, IMAGE_PICK_CODE)
        val intent = Intent(Intent.ACTION_PICK)
        //action_pick이면 구글 드라이브로 가는데?????? action_content하면 안뜨고ㅜㅜ
        intent.setType("image/*")
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true) //activity 간의 인수와 리턴값을 전달(저장)
        startActivityForResult(Intent.createChooser(intent, "Select picture"), IMAGE_PICK_CODE)

    }

    companion object {
        //
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
    }

    /**
     *  onRequestPermissionResult()
     *  handle requested permission result
     *  if user choose OK, call pickImageFromGallery
     */
    //handle requested permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    //permission from popup granted
                    pickImageFromGallery()
                } else {
                    //permission from popup denied
                    Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
                    //context ** this => requireContext()
                }
            }
        }
    }


    /**
     *  onActivityResult()
     *  get result of gallery activity
     *  handle result of picked image
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
//            image_view.setImageURI(data?.data)
            if (data == null) {
                //something is wrong
            }
//            else {
//                val uri = data.data
//                if (uri != null) {
//                    imgList.add(uri)
//                }
//            }
            val clipData = data?.clipData
//            val a = clipData?.getItemAt(0)
//            imgList = imgList
            if (clipData != null) {
                for (i in 0 until clipData.itemCount) {
                    val str = clipData.getItemAt(i).uri
                    imgList.add(Image(str))
                }
            }

        }

    }
}

//            Toast.makeText(requireContext(), "$clipData", Toast.LENGTH_LONG)
//            if (clipData != null){
//                for (i in 0 until clipData.itemCount-1){
//                    val uri = clipData.getItemAt(i).uri
//                    importPhoto(uri)
//                }
//            } else {
//                val uri = data?.data
//                if (uri != null) {
//                    importPhoto(uri)
//                }
//            }





//
//    /**
//     *  importPhoto()
//     *  not image, false
//     *  try make file, get URi
//     */
//    fun importPhoto(uri: Uri): Boolean {
//        if (!isImage(requireContext(), uri)) {
//            // not image
//            return false
//        }
//
//        return try {
//            val photoFile = createImageFile(requireContext().cacheDir)
//            //cacheDir: provide a directory where the OS want to store cached files
//            copyUriToFile(requireContext(), uri, photoFile)
//
//            // addImageToGallery(photoFile)
//            true
//        }
//        catch (e: IOException) {
//            e.printStackTrace()
//            // handle error
//            false
//        }
//    }
//
//
//    /**
//     *  isImage()
//     *  와 씨 진짜 모르겠다ㅜ
//     *
//     */
//    fun isImage(context: Context, uri: Uri): Boolean {
//        val mimeType = context.contentResolver.getType(uri) ?: return true
//        //MIME : Multiple purpose internet mail extension => 여러 형태의 파일 전달 위해 사용
//        //인코딩 방식을 말한다 텍스트로 변환 인코딩, 바이너리로 전환 디코딩
//        return mimeType.startsWith("image/")
//    }
//    //서버 대신하는 안드로이드 4대 컴포넌트 중 하나인 content provider 가서 가져오는 듯
//
//    /**
//     *  createImageFile()
//     *  make file for pattern("yyyyMMdd-kkmmss")
//     *  if
//     */
//    private fun createImageFile(dir: File, fileName: String? = null): File {
//        if (fileName == null) {
//            val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-kkmmss"))
//            return File.createTempFile("IMG_$timestamp", ".jpg", dir)
//            // 임시파일 생성
//        }
//        return File(dir, fileName)
//    }
//
//    /**
//     *  copyUriToFile()
//     *
//     *
//     */
//    fun copyUriToFile(context: Context, uri: Uri, outputFile: File) {
//        val inputStream = context.contentResolver.openInputStream(uri)
//
//        // copy inputStream to file using okio
//        /*
//        val source = Okio.buffer(Okio.source(inputStream))
//        val sink = Okio.buffer(Okio.sink(outputFile))
//
//        sink.writeAll(source)
//
//        sink.close()
//        source.close()
//         */
//
//        val outputStream = FileOutputStream(outputFile)
//        inputStream.use { input ->
//            outputStream.use { output ->
//                input?.copyTo(output)
//            }
//        }
//    }


