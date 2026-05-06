package com.ekodex.manpowerhrms

import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.ekodex.manpowerhrms.Others.Internet
import com.ekodex.manpowerhrms.Others.SharedPrefManager
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.databinding.FragmentAddNewBankBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.yalantis.ucrop.UCrop
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File

class addNewBankFragment : Fragment() {

    lateinit var binding: FragmentAddNewBankBinding
    private var progressDialog: AlertDialog? = null
    //image list
    private val chequeImages = mutableListOf<Uri>()
    private lateinit var proofadapter: SelectedImagesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_add_new_bank,
            container,
            false
        )

        proofadapter = SelectedImagesAdapter(chequeImages,"addbank",requireActivity()) { removeImage(it,1) }
        binding.chequeList.adapter = proofadapter

        var i1 = Internet()

        binding.button23.setOnClickListener {
            if(i1.checkConnection(requireContext()))
            {
                addBankDetails()
            }
            else
            {
                Toast.makeText(requireContext(),"Please Check internet connection!!", Toast.LENGTH_LONG).show()
            }
        }

        binding.addDoc.setOnClickListener {
            selectImages(100)
        }

        return binding.root
    }

    private fun removeImage(imageUri: Uri,code:Int) {
        when(code)
        {
            1->{ chequeImages.remove(imageUri)
                proofadapter.notifyDataSetChanged()}
        }
    }

    private fun selectImages(code:Int) {
//        ImagePicker.with(this)
//            .crop()	    			//Crop image(Optional), Check Customization for more option
//            .compress(1024)			//Final image size will be less than 1 MB(Optional)
//            //.maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
//            .start(code)

        ImagePicker.with(this@addNewBankFragment)
            .galleryMimeTypes(arrayOf("image/png", "image/jpg", "image/jpeg"))
            .start(code)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // ------------------ Handle UCrop Results ------------------
        if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK && data != null) {
            val resultUri = UCrop.getOutput(data)
            if (resultUri != null) {

                // Get bitmap from cropped image
                val bitmap = MediaStore.Images.Media.getBitmap(
                    requireActivity().contentResolver,
                    resultUri
                )

                // Convert bitmap to Base64
                val baos = ByteArrayOutputStream()
                // bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val imageBytes = baos.toByteArray()
                val selectedPicture = android.util.Base64.encodeToString(
                    imageBytes,
                    android.util.Base64.DEFAULT
                )

                when (currentImageType) { // <- helper variable to know which list to update
                    100 -> {
                        chequeImages.add(resultUri)
                        proofadapter.notifyDataSetChanged()
                    }
                }
            }
        } else if (requestCode == UCrop.REQUEST_CROP && resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(data!!)
            Toast.makeText(requireContext(), "Crop error: ${cropError?.message}", Toast.LENGTH_SHORT).show()
        }

        // ------------------ Handle Image Picking ------------------
        if ((requestCode in 100..105) && resultCode == RESULT_OK && data != null) {
            data.let {
                if (it.clipData != null) {
                    for (i in 0 until it.clipData!!.itemCount) {
                        val imgUri = it.clipData!!.getItemAt(i).uri
                        launchCrop(imgUri, requestCode) // 👉 send for cropping
                    }
                } else {
                    it.data?.let { uri -> launchCrop(uri, requestCode) } // 👉 send for cropping
                }
            }
        }
    }

    private fun launchCrop(sourceUri: Uri, type: Int) {
        currentImageType = type

        val destinationUri = Uri.fromFile(
            File(requireActivity().cacheDir, "cropped_${System.currentTimeMillis()}.jpg")
        )

        val options = UCrop.Options().apply {
            setFreeStyleCropEnabled(true)
            setToolbarTitle("Crop Image")
            setCompressionQuality(90)
            setHideBottomControls(false)
        }

        UCrop.of(sourceUri, destinationUri)
            .withOptions(options)
            .start(requireContext(),this)
    }


    /**
     * Launch UCrop cropping activity
     */
    private var currentImageType: Int = -1


    private fun addBankDetails() {
        var bank = binding.editTextTextPersonName81.text.toString()
        var holder =  binding.editTextTextPersonName83.text.toString()
        var acno = binding.editTextTextPersonName84.text.toString()
        var ifsc =  binding.editTextTextPersonName85.text.toString()
        var address = binding.editTextTextPersonName86.text.toString()
        var state =  binding.editTextTextPersonName87.text.toString()
        var city = binding.editTextTextPersonName88.text.toString()
        var micr =  binding.editTextTextPersonName89.text.toString()
        var card = binding.editTextTextPersonName90.text.toString()

        if (TextUtils.isEmpty(bank)) {
            binding.editTextTextPersonName81.error = "Please enter bank name!!"
            binding.editTextTextPersonName81.requestFocus()
            return
        }

        if (TextUtils.isEmpty(holder)) {
            binding.editTextTextPersonName83.error = "Please enter account holder name!!"
            binding.editTextTextPersonName83.requestFocus()
            return
        }

        if (TextUtils.isEmpty(acno)) {
            binding.editTextTextPersonName84.error = "Please enter account number!!"
            binding.editTextTextPersonName84.requestFocus()
            return
        }

        if (TextUtils.isEmpty(ifsc)) {
            binding.editTextTextPersonName85.error = "Please enter ifsc code"
            binding.editTextTextPersonName85.requestFocus()
            return
        }
//        if (TextUtils.isEmpty(address)) {
//            binding.editTextTextPersonName86.error = "Please enter bank address!!"
//            binding.editTextTextPersonName86.requestFocus()
//            return
//        }
//
//        if (TextUtils.isEmpty(state)) {
//            binding.editTextTextPersonName87.error = "Please enter bank state!!"
//            binding.editTextTextPersonName87.requestFocus()
//            return
//        }
//        if (TextUtils.isEmpty(city)) {
//            binding.editTextTextPersonName88.error = "Please enter bank city!!"
//            binding.editTextTextPersonName88.requestFocus()
//            return
//        }
//
//        if (TextUtils.isEmpty(micr)) {
//            binding.editTextTextPersonName89.error = "Please enter bank micr!!"
//            binding.editTextTextPersonName89.requestFocus()
//            return
//        }
//        if (TextUtils.isEmpty(card)) {
//            binding.editTextTextPersonName90.error = "Please enter card number!!"
//            binding.editTextTextPersonName90.requestFocus()
//            return
//        }

        showProgressDialog()

        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_ADD_NEW_BANK_DETAIL,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        hideProgressDialog()
                        Toast.makeText(
                            requireActivity().applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()

                        findNavController().navigate(addNewBankFragmentDirections.actionAddNewBanksFragmentToMyBanksFragment())

                    } else {
                        Toast.makeText(
                            requireActivity().applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                        hideProgressDialog()

                    }

                } catch (e: JSONException) {
                    
                    hideProgressDialog()
                }

            },
            Response.ErrorListener { error ->
                
                hideProgressDialog()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

                params["user_id"] = SharedPrefManager.getInstance(requireContext()).user.id
                params["bank"] = bank
                params["acno"] = acno
                params["ifsc"] = ifsc
                params["holder"] = holder
                params["address"] = address
                params["state"] = state
                params["city"] = city
                params["micr"] = micr
                params["card"] = card
                params["chequeCount"] = chequeImages.size.toString()
                if(chequeImages.size>0)
                {
                    for(i in 0..chequeImages.size-1)
                    {
                        var num = (i+1).toString()
                        params["cheque$num"] = imageUriToBase64Max100KB(requireActivity().contentResolver,chequeImages.get(i))
                    }
                }

                return params

            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun showProgressDialog() {
        if (progressDialog == null) {
            val progressBar = ProgressBar(requireContext())
            progressBar.isIndeterminate = true
            val padding = 50
            progressBar.setPadding(padding, padding, padding, padding)

            progressDialog = AlertDialog.Builder(requireContext())
                .setTitle("Adding Bank")
                .setMessage("Please wait...")
                .setView(progressBar)
                .setCancelable(false)
                .create()
        }
        progressDialog?.show()
    }

    private fun hideProgressDialog() {
        progressDialog?.dismiss()
    }

    //to store images under 100kb
    fun imageUriToBase64Max100KB(
        contentResolver: ContentResolver,
        imageUri: Uri
    ): String {

        val originalBitmap =
            MediaStore.Images.Media.getBitmap(contentResolver, imageUri)

        // 🔹 Resize first (VERY IMPORTANT)
        val maxWidth = 1080
        val ratio = originalBitmap.height.toFloat() / originalBitmap.width.toFloat()
        val resizedBitmap = Bitmap.createScaledBitmap(
            originalBitmap,
            maxWidth,
            (maxWidth * ratio).toInt(),
            true
        )

        var quality = 90
        var base64String: String

        do {
            val baos = ByteArrayOutputStream()
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos)
            val bytes = baos.toByteArray()
            base64String = Base64.encodeToString(bytes, Base64.NO_WRAP)

            quality -= 5
        } while (bytes.size > 100 * 1024 && quality > 20)

        return base64String
    }


}