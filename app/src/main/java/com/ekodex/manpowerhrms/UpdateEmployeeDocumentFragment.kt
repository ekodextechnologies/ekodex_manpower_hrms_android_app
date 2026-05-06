package com.ekodex.manpowerhrms

import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Base64
import android.util.Log
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
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.ekodex.manpowerhrms.Others.Internet
import com.ekodex.manpowerhrms.Others.SharedPrefManager
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.databinding.FragmentUpdateEmployeeDocumentBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.text.TextRecognizer
import com.yalantis.ucrop.UCrop
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class UpdateEmployeeDocumentFragment : Fragment() {

    lateinit var binding: FragmentUpdateEmployeeDocumentBinding

    private var progressDialog: AlertDialog? = null

    private val aadharImages = mutableListOf<Uri>()
    private val panImages = mutableListOf<Uri>()
    private val uanImages = mutableListOf<Uri>()
    private val pfImages = mutableListOf<Uri>()
    private val passportImages = mutableListOf<Uri>()
    private val esisImages = mutableListOf<Uri>()

    private lateinit var aadharadapter: SelectedImagesAdapter
    private lateinit var panadapter: SelectedPanImagesAdapter
    private lateinit var passportadapter: SelectedPassportImagesAdapter
    private lateinit var pfadapter: SelectedPfImagesAdapter
    private lateinit var uanadapter: SelectedUanImagesAdapter
    private lateinit var esisadapter: SelectedEsisImagesAdapter

    //textfields value
    var aadhar_no = ""
    var pan_no = ""
    var uan_no = ""
    var passport_no = ""
    var pf_no = ""
    var esis_no = ""

    //extracted dates
    private var selectedType = ""
    private var uan = ""
    private var esis = ""
    private var passport = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_update_employee_document, container, false)

        var i1 = Internet()

        var args = UpdateEmployeeDocumentFragmentArgs.fromBundle(requireArguments())

        aadharadapter = SelectedImagesAdapter(aadharImages,"AddNewEmployee",requireActivity()) { removeImage(it,1) }
        panadapter = SelectedPanImagesAdapter(panImages,"AddNewEmployee",requireActivity()) { removeImage(it,2) }
        uanadapter = SelectedUanImagesAdapter(uanImages,"AddNewEmployee",requireActivity()) { removeImage(it,3) }
        passportadapter = SelectedPassportImagesAdapter(passportImages,"AddNewEmployee",requireActivity()) { removeImage(it,4) }
        pfadapter = SelectedPfImagesAdapter(pfImages,"AddNewEmployee",requireActivity()) { removeImage(it,5) }
        esisadapter = SelectedEsisImagesAdapter(esisImages,"AddNewEmployee",requireActivity()) { removeImage(it,6) }

        binding.aadharList.adapter = aadharadapter
        binding.panList.adapter = panadapter
        binding.uanList.adapter = uanadapter
        binding.passportList.adapter = passportadapter
        binding.pfList.adapter = pfadapter
        binding.esisList.adapter = esisadapter


        if(args.aadhar!="")
        {
            binding.editTextTextPersonName99.setText(args.aadhar)
        }
        if(args.pan!="")
        {
            binding.editTextTextPersonName100.setText(args.pan)
        }
        if(args.uan!="")
        {
            binding.editTextTextPersonName101.setText(args.uan)
        }
        if(args.pf!="")
        {
            binding.editTextTextPersonName102.setText(args.pf)
        }
        if(args.esis!="")
        {
            binding.editTextTextPersonName103.setText(args.esis)
        }
        if(args.passport!="")
        {
            binding.editTextTextPersonName104.setText(args.passport)
        }

        if(args.uanDate!="")
        {
            binding.button32.text = args.uanDate
            uan = args.uanDate
        }

        if(args.esisDate!="")
        {
            binding.button33.text = args.esisDate
            esis = args.esisDate
        }

        if(args.passportDate!="")
        {
            binding.button34.text = args.passportDate
            passport = args.passportDate
        }

        if(i1.checkConnection(requireContext()))
        {
            //to load images from database
            getEmployeeAllDocs(args.id)
        }


        binding.addDoc.setOnClickListener {
            selectImages(100)
        }

        binding.addDoc2.setOnClickListener {
            selectImages(101)
        }
        binding.addDoc3.setOnClickListener {
            selectImages(102)
        }

        binding.addDoc4.setOnClickListener {
            selectImages(103)
        }

        binding.addDoc5.setOnClickListener {
            selectImages(104)
        }

        binding.addDoc6.setOnClickListener {
            selectImages(105)
        }

        binding.button21.setOnClickListener {
            updateEmployeeDocs()
        }


        //dates
        val calendar = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            updateSelectedDate(calendar)
        }

        binding.button32.setOnClickListener { openDatePicker("uan", calendar, datePicker) }
        binding.button33.setOnClickListener { openDatePicker("esis", calendar, datePicker) }
        binding.button34.setOnClickListener { openDatePicker("passport", calendar, datePicker) }

        return binding.root
    }

    private fun updateEmployeeDocs() {

        aadhar_no = binding.editTextTextPersonName99.text.toString()
        pan_no = binding.editTextTextPersonName100.text.toString()
        uan_no = binding.editTextTextPersonName101.text.toString()
        pf_no = binding.editTextTextPersonName102.text.toString()
        esis_no = binding.editTextTextPersonName103.text.toString()
        passport_no = binding.editTextTextPersonName104.text.toString()
//
//        if (TextUtils.isEmpty(first_name)) {
//            binding.editTextTextPersonName78.error = "Please enter first name"
//            binding.editTextTextPersonName78.requestFocus()
//            return
//        }
//        if (TextUtils.isEmpty(middle_name)) {
//            binding.editTextTextPersonName79.error = "Please enter middle name"
//            binding.editTextTextPersonName79.requestFocus()
//            return
//        }
//
//        if (TextUtils.isEmpty(last_name)) {
//            binding.editTextTextPersonName80.error = "Please enter last name"
//            binding.editTextTextPersonName80.requestFocus()
//            return
//        }
//
//        if (selectedGenderId == -1) {
//            // No option is selected
//            Toast.makeText(requireContext(), "Please select a gender", Toast.LENGTH_SHORT).show()
//        } else {
//            // Option is selected
//            val selectedRadioButton = view?.findViewById<RadioButton>(selectedGenderId)
//            gender = selectedRadioButton?.text.toString()
//        }
//
//        if (TextUtils.isEmpty(designation)) {
//            binding.empDesignation.error = "Please select designation!!"
//            binding.empDesignation.requestFocus()
//            return
//        }
//
//        if (TextUtils.isEmpty(phone)) {
//            binding.editTextTextPersonName97.error = "Please enter phone number!!"
//            binding.editTextTextPersonName97.requestFocus()
//            return
//        }



        // If no image is selected
//        if(selectedImages.size == 0)
//        {
//            Toast.makeText(requireContext(),"Please select atleast 1 document image!!", Toast.LENGTH_SHORT).show()
//        }
//        else
//        {

        if(aadharImages.size>0 && TextUtils.isEmpty(aadhar_no))
        {
            binding.editTextTextPersonName99.error = "Please add aadhar number!!"
            binding.editTextTextPersonName99.requestFocus()
            return
        }

        else if(aadharImages.size<=0 && aadhar_no.length>0)
        {
            binding.editTextTextPersonName99.error = "Please select aadhar images!!"
            binding.editTextTextPersonName99.requestFocus()
            return
        }
        else if(panImages.size>0 && TextUtils.isEmpty(pan_no))
        {
            binding.editTextTextPersonName100.error = "Please add pan number!!"
            binding.editTextTextPersonName100.requestFocus()
            return
        }
        else if(panImages.size<=0 && pan_no.length>0)
        {
            binding.editTextTextPersonName100.error = "Please select pan images!!"
            binding.editTextTextPersonName100.requestFocus()
            return
        }
        else if(uanImages.size>0 && TextUtils.isEmpty(uan_no))
        {
            binding.editTextTextPersonName101.error = "Please add uan number!!"
            binding.editTextTextPersonName101.requestFocus()
            return
        }
        else if(uanImages.size<=0 && uan_no.length>0)
        {
            binding.editTextTextPersonName101.error = "Please select uan images!!"
            binding.editTextTextPersonName101.requestFocus()
            return
        }
        else if(pfImages.size>0 && TextUtils.isEmpty(pf_no))
        {
            binding.editTextTextPersonName102.error = "Please add pf number!!"
            binding.editTextTextPersonName102.requestFocus()
            return
        }
        else if(pfImages.size<=0 && pf_no.length>0)
        {
            binding.editTextTextPersonName102.error = "Please select pf images!!"
            binding.editTextTextPersonName102.requestFocus()
            return
        }
        else if(esisImages.size>0 && TextUtils.isEmpty(esis_no))
        {
            binding.editTextTextPersonName103.error = "Please add esis number!!"
            binding.editTextTextPersonName103.requestFocus()
            return
        }
        else if(esisImages.size<=0 && esis_no.length>0)
        {
            binding.editTextTextPersonName103.error = "Please select esis images!!"
            binding.editTextTextPersonName103.requestFocus()
            return
        }
        else if(passportImages.size>0 && TextUtils.isEmpty(passport_no))
        {
            binding.editTextTextPersonName104.error = "Please add passport number!!"
            binding.editTextTextPersonName104.requestFocus()
            return
        }
        else if(passportImages.size<=0 && passport_no.length>0)
        {
            binding.editTextTextPersonName104.error = "Please select passport images!!"
            binding.editTextTextPersonName104.requestFocus()
            return
        }
        else
        {
            binding.button21.isEnabled = false
            showProgressDialog()
            // binding.progressBar5.visibility = View.VISIBLE

            val stringRequest = object : StringRequest(
                Request.Method.POST, URLs.URL_UPDATE_EMPLOYEES_ALL_DOCS,
                Response.Listener { response ->

                    try {
                        //converting response to json object
                        val obj = JSONObject(response)

                        //if no error in response
                        if (!obj.getBoolean("error")) {

                            // binding.progressBar5.visibility = View.GONE
                            hideProgressDialog()

                            Toast.makeText(
                                requireContext(),
                                obj.getString("message"),
                                Toast.LENGTH_SHORT
                            ).show()

                            var args = UpdateEmployeeDocumentFragmentArgs.fromBundle(requireArguments())
                            findNavController().navigate(UpdateEmployeeDocumentFragmentDirections.actionUpdateEmployeeDocumentFragmentToEmployeeDetailsFragment(args.id))

                        }
                        else {
                            // binding.progressBar5.visibility = View.GONE
                            hideProgressDialog()
                            Toast.makeText(requireContext(), obj.getString("message"), Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: JSONException) {
                        
                        hideProgressDialog()
                    }
                },
                Response.ErrorListener { error ->
                    if (error.message != null) {
                        // binding.progressBar5.visibility = View.GONE
                        hideProgressDialog()
                        Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
                    }
                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val params = HashMap<String, String>()
    var args = UpdateEmployeeDocumentFragmentArgs.fromBundle(requireArguments())

                    params["created_by"] = SharedPrefManager.getInstance(requireContext()).user.id
                    params["emp_id"] = args.id

                    //dates
                    params["uan"] = uan
                    params["esis"] = esis
                    params["passport"] = passport

                    params["aadharCount"] = aadharImages.size.toString()
                    params["panCount"] = panImages.size.toString()
                    params["uanCount"] = uanImages.size.toString()
                    params["passportCount"] = passportImages.size.toString()
                    params["pfCount"] = pfImages.size.toString()
                    params["esisCount"] = esisImages.size.toString()
                    params["aadhar_no"] = aadhar_no
                    params["pan_no"] = pan_no
                    params["uan_no"] = uan_no
                    params["pf_no"] = pf_no
                    params["passport_no"] = passport_no
                    params["esis_no"] = esis_no
                    params["fname"] = args.fname
                    params["lname"] = args.lname

                    if(aadharImages.size>0)
                    {
                        for(i in 0..aadharImages.size-1)
                        {
                            var num = (i+1).toString()
                            params["aadhar$num"] = imageUriToBase64Max100KB(requireActivity().contentResolver,aadharImages.get(i))
                        }
                    }

                    if(panImages.size>0)
                    {
                        for(i in 0..panImages.size-1)
                        {
                            var num = (i+1).toString()
                            params["pan$num"] = imageUriToBase64Max100KB(requireActivity().contentResolver,panImages.get(i))
                        }
                    }

                    if(uanImages.size>0)
                    {
                        for(i in 0..uanImages.size-1)
                        {
                            var num = (i+1).toString()
                            params["uan$num"] = imageUriToBase64Max100KB(requireActivity().contentResolver,uanImages.get(i))
                        }
                    }

                    if(passportImages.size>0)
                    {
                        for(i in 0..passportImages.size-1)
                        {
                            var num = (i+1).toString()
                            params["passport$num"] = imageUriToBase64Max100KB(requireActivity().contentResolver,passportImages.get(i))
                        }
                    }

                    if(pfImages.size>0)
                    {
                        for(i in 0..pfImages.size-1)
                        {
                            var num = (i+1).toString()
                            params["pf$num"] = imageUriToBase64Max100KB(requireActivity().contentResolver,pfImages.get(i))
                        }
                    }

                    if(esisImages.size>0)
                    {
                        for(i in 0..esisImages.size-1)
                        {
                            var num = (i+1).toString()
                            params["esis$num"] = imageUriToBase64Max100KB(requireActivity().contentResolver,esisImages.get(i))
                        }
                    }


                    return params
                }
            }

            stringRequest.retryPolicy = DefaultRetryPolicy(
                10_000, // increase timeout (10s, adjust as needed)
                0,      // 0 retries (no retry after failure)
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )

            VolleySingleton.getInstance(requireActivity().applicationContext)
                .addToRequestQueue(stringRequest)
       }


    }

    private fun getEmployeeAllDocs(emp_id: String) {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_EMPLOYEES_ALL_DOCS,
            Response.Listener { response ->
                try {
                    val obj = JSONObject(response)

                    if (!obj.getBoolean("error")) {
                        // Clear old data
                        aadharImages.clear()
                        panImages.clear()
                        uanImages.clear()
                        pfImages.clear()
                        passportImages.clear()
                        esisImages.clear()

                        // Load data into lists
                        val aadharArray = obj.getJSONArray("aadhar")
                        val panArray = obj.getJSONArray("pan")
                        val uanArray = obj.getJSONArray("uan")
                        val pfArray = obj.getJSONArray("pf")
                        val passportArray = obj.getJSONArray("passport")
                        val esisArray = obj.getJSONArray("esis")

                        for (i in 0 until aadharArray.length()) {
                            aadharImages.add(Uri.parse(aadharArray.getString(i)))
                        }

                        for (i in 0 until panArray.length()) {
                            panImages.add(Uri.parse(panArray.getString(i)))
                        }

                        for (i in 0 until uanArray.length()) {
                            uanImages.add(Uri.parse(uanArray.getString(i)))
                        }

                        for (i in 0 until pfArray.length()) {
                            pfImages.add(Uri.parse(pfArray.getString(i)))
                        }

                        for (i in 0 until passportArray.length()) {
                            passportImages.add(Uri.parse(passportArray.getString(i)))
                        }

                        for (i in 0 until esisArray.length()) {
                            esisImages.add(Uri.parse(esisArray.getString(i)))
                        }

                        // Notify adapters
                        aadharadapter.notifyDataSetChanged()
                        panadapter.notifyDataSetChanged()
                        uanadapter.notifyDataSetChanged()
                        pfadapter.notifyDataSetChanged()
                        passportadapter.notifyDataSetChanged()
                        esisadapter.notifyDataSetChanged()

                    } else {
                        Toast.makeText(
                            requireActivity(),
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                } catch (e: JSONException) {
                    
                    Toast.makeText(requireActivity(), "JSON parsing error", Toast.LENGTH_LONG).show()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(requireActivity(), error.message ?: "Volley error", Toast.LENGTH_LONG).show()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                return hashMapOf("emp_id" to emp_id)
            }
        }

        VolleySingleton.getInstance(requireContext()).addToRequestQueue(stringRequest)
    }

    private fun selectImages(code:Int) {
//        ImagePicker.with(this)
//            .crop()	    			//Crop image(Optional), Check Customization for more option
//            .compress(1024)			//Final image size will be less than 1 MB(Optional)
//            //.maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
//            .start(code)

        ImagePicker.with(this@UpdateEmployeeDocumentFragment)
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
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val imageBytes = baos.toByteArray()
                val selectedPicture = android.util.Base64.encodeToString(
                    imageBytes,
                    android.util.Base64.DEFAULT
                )

                // Text recognition
                val textRecognizer =
                    TextRecognizer.Builder(requireActivity().applicationContext).build()
                val imageFrame: Frame = Frame.Builder().setBitmap(bitmap).build()

                var imageText = ""
                val textBlocks = textRecognizer.detect(imageFrame)
                for (i in 0 until textBlocks.size()) {
                    val textBlock = textBlocks[textBlocks.keyAt(i)]
                    imageText += textBlock.value + "\n"
                }

                Log.i("11111", "Block Text: ${imageText}")

                when (currentImageType) { // <- helper variable to know which list to update
                    100 -> {
                        aadharImages.add(resultUri)
                        aadharadapter.notifyDataSetChanged()
                        if(binding.editTextTextPersonName99.length()<12)
                        {
                          //  getAadharNumber(imageText)
                            getAadharNumber(resultUri)
                        }
                    }
                    101 -> {
                        panImages.add(resultUri)
                        panadapter.notifyDataSetChanged()
                        if(binding.editTextTextPersonName100.length()<10)
                        {
//                            getPanNumber(imageText)
                            getPanNumber(resultUri)
                        }
                    }
                    102 -> {
                        uanImages.add(resultUri)
                        uanadapter.notifyDataSetChanged()
                        if(binding.editTextTextPersonName101.length()<12)
                        {
                            getUanNumber(imageText)
                        }
                    }
                    103 -> {
                        pfImages.add(resultUri)
                        pfadapter.notifyDataSetChanged()
                        if(binding.editTextTextPersonName102.length()<22)
                        {
                            getPfNumber(imageText)
                        }
                    }
                    104 -> {
                        esisImages.add(resultUri)
                        esisadapter.notifyDataSetChanged()
                        if(binding.editTextTextPersonName103.length()<17)
                        {
                            getEsisNumber(imageText)
                        }
                    }
                    105 -> {
                        passportImages.add(resultUri)
                        passportadapter.notifyDataSetChanged()
                        if(binding.editTextTextPersonName104.length()<8)
                        {
                            getPassportNumber(imageText)
                        }
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

    private fun removeImage(imageUri: Uri,code:Int) {
        when(code)
        {
            1->{ aadharImages.remove(imageUri)
                aadharadapter.notifyDataSetChanged()}
            2->{ panImages.remove(imageUri)
                panadapter.notifyDataSetChanged()}
            3->{ uanImages.remove(imageUri)
                uanadapter.notifyDataSetChanged()}
            4->{ passportImages.remove(imageUri)
                passportadapter.notifyDataSetChanged()}
            5->{ pfImages.remove(imageUri)
                pfadapter.notifyDataSetChanged()}
            6->{ esisImages.remove(imageUri)
                esisadapter.notifyDataSetChanged()}
        }
    }

/*    fun imageUriToBase64(contentResolver: ContentResolver, imageUri: Uri): String {
        val inputStream: InputStream? = contentResolver.openInputStream(imageUri)
        val byteArrayOutputStream = ByteArrayOutputStream()

        inputStream?.use { stream ->
            val buffer = ByteArray(1024)
            var bytesRead: Int

            while (stream.read(buffer).also { bytesRead = it } != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead)
            }
        }

        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }*/

    fun imageUriToBase64(contentResolver: ContentResolver, imageUri: Uri): String {
        val byteArrayOutputStream = ByteArrayOutputStream()

        try {
            val inputStream: InputStream? = when {
                imageUri.scheme == "http" || imageUri.scheme == "https" -> {
                    // Online URL, open HTTP connection
                    val url = URL(imageUri.toString())
                    val connection = url.openConnection() as HttpURLConnection
                    connection.doInput = true
                    connection.connect()
                    connection.inputStream
                }
                else -> {
                    // Local file/content URI
                    contentResolver.openInputStream(imageUri)
                }
            }

            inputStream?.use { stream ->
                val buffer = ByteArray(1024)
                var bytesRead: Int
                while (stream.read(buffer).also { bytesRead = it } != -1) {
                    byteArrayOutputStream.write(buffer, 0, bytesRead)
                }
            }
        } catch (e: Exception) {
            
            return ""
        }

        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    //to store images under 100kb
    fun imageUriToBase64Max100KB(
        contentResolver: ContentResolver,
        imageUri: Uri
    ): String {

        try {
            // 1️⃣ Get InputStream EXACTLY like your old function
            val inputStream: InputStream = when {
                imageUri.scheme == "http" || imageUri.scheme == "https" -> {
                    val url = URL(imageUri.toString())
                    val connection = url.openConnection() as HttpURLConnection
                    connection.doInput = true
                    connection.connect()
                    connection.inputStream
                }
                else -> {
                    contentResolver.openInputStream(imageUri)
                        ?: return ""
                }
            }

            // 2️⃣ Decode bitmap safely from stream
            val originalBitmap = BitmapFactory.decodeStream(inputStream)
                ?: return ""

            // 3️⃣ Resize ONLY if needed (never upscale)
            val maxWidth = 1080
            val resizedBitmap = if (originalBitmap.width > maxWidth) {
                val ratio = originalBitmap.height.toFloat() / originalBitmap.width.toFloat()
                Bitmap.createScaledBitmap(
                    originalBitmap,
                    maxWidth,
                    (maxWidth * ratio).toInt(),
                    true
                )
            } else {
                originalBitmap
            }

            // 4️⃣ Compress until under 100 KB
            var quality = 90
            var compressedBytes: ByteArray

            do {
                val baos = ByteArrayOutputStream()
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos)
                compressedBytes = baos.toByteArray()
                quality -= 5
            } while (compressedBytes.size > 100 * 1024 && quality > 20)

            // 5️⃣ Convert to Base64
            return Base64.encodeToString(compressedBytes, Base64.NO_WRAP)

        } catch (e: Exception) {
            
            return ""
        }
    }


    private fun getAadharNumber(aadhar_raw_txt:Uri) {
        var base64image = imageUriToBase64(requireActivity().contentResolver,aadhar_raw_txt)
        showDocProgressDialog()
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_AADHAR_DATA,
            Response.Listener { response ->
                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
//                        Toast.makeText(
//                            requireActivity().applicationContext,
//                            obj.getString("message"),
//                            Toast.LENGTH_SHORT
//                        ).show()
                        //set values
                        hideDocProgressDialog()
                        binding.editTextTextPersonName99.setText( obj.getString("aadhar_num"))
                    } else {
                        val msg = obj.getString("message")
                        if (!msg.contains("BILLING_DISABLED", ignoreCase = true)) {
                            Toast.makeText(
                                requireContext(),
                                msg,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        hideDocProgressDialog()
                    }

                } catch (e: JSONException) {
                    
                    hideDocProgressDialog()
                }
            },
            Response.ErrorListener { error ->
                val errorMsg = error.message
                if (errorMsg != null && !errorMsg.contains("BILLING_DISABLED", ignoreCase = true)) {
                    Toast.makeText(
                        requireActivity().applicationContext,
                        errorMsg,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                hideDocProgressDialog()
            })  {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

                params["aadhar_text"] = base64image

                return params
            }
        }

        stringRequest.retryPolicy = DefaultRetryPolicy(
            60000,  // 20 seconds
            0,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun getPanNumber(pan_raw_txt:Uri) {
        var base64image = imageUriToBase64(requireActivity().contentResolver,pan_raw_txt)
        showDocProgressDialog()
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_PAN_DATA,
            Response.Listener { response ->
                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
//                        Toast.makeText(
//                            requireActivity().applicationContext,
//                            obj.getString("message"),
//                            Toast.LENGTH_SHORT
//                        ).show()
                        //set values
                        hideDocProgressDialog()
                        binding.editTextTextPersonName100.setText( obj.getString("pan_num"))
                    } else {
                        val msg = obj.getString("message")
                        if (!msg.contains("BILLING_DISABLED", ignoreCase = true)) {
                            Toast.makeText(
                                requireContext(),
                                msg,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        hideDocProgressDialog()
                    }

                } catch (e: JSONException) {
                    
                    hideDocProgressDialog()
                }
            },
            Response.ErrorListener { error ->
                val errorMsg = error.message
                if (errorMsg != null && !errorMsg.contains("BILLING_DISABLED", ignoreCase = true)) {
                    Toast.makeText(
                        requireActivity().applicationContext,
                        errorMsg,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                hideDocProgressDialog()
            })  {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

                params["pan_text"] = base64image

                return params
            }
        }

        stringRequest.retryPolicy = DefaultRetryPolicy(
            60000,  // 20 seconds
            0,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )


        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun getUanNumber(uan_raw_txt:String) {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_UAN_DATA,
            Response.Listener { response ->
                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
//                        Toast.makeText(
//                            requireActivity().applicationContext,
//                            obj.getString("message"),
//                            Toast.LENGTH_SHORT
//                        ).show()
                        //set values
                        binding.editTextTextPersonName101.setText( obj.getString("uan_num"))

                        obj.getString("uan_date").takeIf { !it.isNullOrBlank() }?.let {
                            binding.button32.text = it
                            uan = it
                        }

                    } else {
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } catch (e: JSONException) {
                    
                }
            },
            Response.ErrorListener { error ->
                if (error.message != null) {
                    Toast.makeText(
                        requireActivity().applicationContext,
                        error.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

                params["uan_text"] = uan_raw_txt

                return params
            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun getPfNumber(pf_raw_txt:String) {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_PF_DATA,
            Response.Listener { response ->
                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
//                        Toast.makeText(
//                            requireActivity().applicationContext,
//                            obj.getString("message"),
//                            Toast.LENGTH_SHORT
//                        ).show()
                        //set values
                        binding.editTextTextPersonName102.setText( obj.getString("pf_num"))
                    } else {
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } catch (e: JSONException) {
                    
                }
            },
            Response.ErrorListener { error ->
                if (error.message != null) {
                    Toast.makeText(
                        requireActivity().applicationContext,
                        error.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

                params["pf_text"] = pf_raw_txt

                return params
            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun getEsisNumber(esis_raw_txt:String) {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_ESIS_DATA,
            Response.Listener { response ->
                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
//                        Toast.makeText(
//                            requireActivity().applicationContext,
//                            obj.getString("message"),
//                            Toast.LENGTH_SHORT
//                        ).show()
                        //set values
                        binding.editTextTextPersonName103.setText( obj.getString("esis_num"))

                        obj.getString("esis_date").takeIf { !it.isNullOrBlank() }?.let {
                            binding.button33.text = it
                            esis = it
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } catch (e: JSONException) {
                    
                }
            },
            Response.ErrorListener { error ->
                if (error.message != null) {
                    Toast.makeText(
                        requireActivity().applicationContext,
                        error.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

                params["esis_text"] = esis_raw_txt

                return params
            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }

    private fun getPassportNumber(pass_raw_txt:String) {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_PASSPORT_DATA,
            Response.Listener { response ->
                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
//                        Toast.makeText(
//                            requireActivity().applicationContext,
//                            obj.getString("message"),
//                            Toast.LENGTH_SHORT
//                        ).show()
                        //set values
                        binding.editTextTextPersonName104.setText( obj.getString("passport_num"))

                        obj.getString("passport_date").takeIf { !it.isNullOrBlank() }?.let {
                            binding.button34.text = it
                            passport = it
                        }

                    } else {
                        Toast.makeText(
                            requireContext(),
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } catch (e: JSONException) {
                    
                }
            },
            Response.ErrorListener { error ->
                if (error.message != null) {
                    Toast.makeText(
                        requireActivity().applicationContext,
                        error.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

                params["pass_text"] = pass_raw_txt

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
                .setTitle("Updating Documents")
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

    private fun openDatePicker(
        type: String,
        calendar: Calendar,
        listener: DatePickerDialog.OnDateSetListener
    ) {
        selectedType = type

        DatePickerDialog(
            requireActivity(),
            listener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun updateSelectedDate(calendar: Calendar) {

        val displayFormat = SimpleDateFormat("dd-MM-yyyy", Locale.UK)
        val apiFormat = SimpleDateFormat("yyyy-MM-dd", Locale.UK)

        val showDate = displayFormat.format(calendar.time)
        val dbDate = apiFormat.format(calendar.time)

        when (selectedType) {
            "uan" -> {
                uan = dbDate
                binding.button32.text = showDate
            }

            "esis" -> {
                esis = dbDate
                binding.button33.text = showDate
            }

            "passport" -> {
                passport = dbDate
                binding.button34.text = showDate
            }
        }
    }

    private fun showDocProgressDialog() {
        if (progressDialog == null) {
            val progressBar = ProgressBar(requireContext())
            progressBar.isIndeterminate = true
            val padding = 50
            progressBar.setPadding(padding, padding, padding, padding)

            progressDialog = AlertDialog.Builder(requireContext())
                .setTitle("Fetching Details")
                .setMessage("Please wait...")
                .setView(progressBar)
                .setCancelable(false)
                .create()
        }
        progressDialog?.show()
    }

    private fun hideDocProgressDialog() {
        progressDialog?.dismiss()
    }

}