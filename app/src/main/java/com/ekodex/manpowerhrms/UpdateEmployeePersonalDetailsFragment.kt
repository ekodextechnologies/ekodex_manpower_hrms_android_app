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
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ekodex.manpowerhrms.Others.Internet
import com.ekodex.manpowerhrms.Others.SharedPrefManager
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.databinding.FragmentUpdateEmployeePersonalDetailsBinding
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

class UpdateEmployeePersonalDetailsFragment : Fragment() {

    lateinit var binding: FragmentUpdateEmployeePersonalDetailsBinding
    var names = arrayListOf<String>()
    private var progressDialog: AlertDialog? = null


    //dates
    private var selectedType = ""
    private var dob = ""
    private var doj = ""
    var gender = ""
    var pan_no = ""
    var aadhar_no = ""

    private val aadharImages = mutableListOf<Uri>()
    private val panImages = mutableListOf<Uri>()

    private lateinit var aadharadapter: SelectedImagesAdapter
    private lateinit var panadapter: SelectedPanImagesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_update_employee_personal_details,
            container,
            false
        )

        aadharadapter = SelectedImagesAdapter(aadharImages,"AddNewEmployee",requireActivity()) { removeImage(it,1) }
        panadapter = SelectedPanImagesAdapter(panImages,"AddNewEmployee",requireActivity()) { removeImage(it,2) }

        binding.aadharList.adapter = aadharadapter
        binding.panList.adapter = panadapter

        var i1 = Internet()

        if(i1.checkConnection(requireContext()))
        {
            getAllDesignations()
            //to load images from database
            getEmployeeAllDocs()
        }
        else
        {
            Toast.makeText(requireContext(),"Please Check internet connection!!", Toast.LENGTH_LONG).show()
        }


        binding.button23.setOnClickListener {
            if(i1.checkConnection(requireContext()))
            {
                updatePersonalDetails()
            }
            else
            {
                Toast.makeText(requireContext(),"Please Check internet connection!!", Toast.LENGTH_LONG).show()
            }
        }

        // dates
        val calendar = Calendar.getInstance()

        //dates
        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            updateSelectedDate(calendar)
        }

        binding.button24.setOnClickListener { openDatePicker("dob", calendar, datePicker) }
        binding.button31.setOnClickListener { openDatePicker("doj", calendar, datePicker) }


        binding.addDoc.setOnClickListener {
            selectImages(100)
        }

        binding.addDoc2.setOnClickListener {
            selectImages(101)
        }
        return binding.root

    }

    private fun selectImages(code:Int) {
//        ImagePicker.with(this)
//            .crop()	    			//Crop image(Optional), Check Customization for more option
//            .compress(1024)			//Final image size will be less than 1 MB(Optional)
//            //.maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
//            .start(code)

        ImagePicker.with(this@UpdateEmployeePersonalDetailsFragment)
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
                        // if(binding.editTextTextPersonName99.length()<12)
                        // {
                       // getAadharNumber(imageText)
                        getAadharNumber(resultUri)
                        //   }
                    }
                    101 -> {
                        panImages.add(resultUri)
                        panadapter.notifyDataSetChanged()
//                        if(binding.editTextTextPersonName100.length()<10)
//                        {
//                        getPanNumber(imageText)
                        getPanNumber(resultUri)
                        //}
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



    private fun updatePersonalDetails() {
        var fname = binding.editTextTextPersonName78.text.toString()
        var mname =  binding.editTextTextPersonName91.text.toString()
        var lname = binding.editTextTextPersonName92.text.toString()
        var father_name = binding.editTextTextPersonName116.text.toString()
        var address =  binding.editTextTextPersonName93.text.toString()
        var state = binding.editTextTextPersonName94.text.toString()
        var city =  binding.editTextTextPersonName95.text.toString()
        var pincode = binding.editTextTextPersonName96.text.toString()
        var rank =  binding.empDesignation.text.toString()
        var phone = binding.editTextTextPersonName117.text.toString()
        var email = binding.editTextTextPersonName118.text.toString()
        var gender = ""
        val selectedGenderId = binding.gender.checkedRadioButtonId
        aadhar_no = binding.editTextTextPersonName99.text.toString()
        pan_no = binding.editTextTextPersonName100.text.toString()


        if (TextUtils.isEmpty(fname)) {
            binding.editTextTextPersonName78.error = "Please enter first name!!"
            binding.editTextTextPersonName78.requestFocus()
            return
        }

        if (TextUtils.isEmpty(mname)) {
            binding.editTextTextPersonName91.error = "Please enter middle name!!"
            binding.editTextTextPersonName91.requestFocus()
            return
        }

        if (TextUtils.isEmpty(lname)) {
            binding.editTextTextPersonName92.error = "Please enter last name!!"
            binding.editTextTextPersonName92.requestFocus()
            return
        }

        if (TextUtils.isEmpty(father_name)) {
            binding.editTextTextPersonName116.error = "Please enter father name"
            binding.editTextTextPersonName116.requestFocus()
            return
        }

        if (selectedGenderId == -1) {
            // No option is selected
            Toast.makeText(requireContext(), "Please select a gender", Toast.LENGTH_SHORT).show()
        } else {
            // Option is selected
            val selectedRadioButton = view?.findViewById<RadioButton>(selectedGenderId)
            gender = selectedRadioButton?.text.toString()
        }


//        if (TextUtils.isEmpty(address)) {
//            binding.editTextTextPersonName93.error = "Please enter address!!"
//            binding.editTextTextPersonName93.requestFocus()
//            return
//        }
//
//        if (TextUtils.isEmpty(state)) {
//            binding.editTextTextPersonName94.error = "Please enter state!!"
//            binding.editTextTextPersonName94.requestFocus()
//            return
//        }
//        if (TextUtils.isEmpty(city)) {
//            binding.editTextTextPersonName95.error = "Please enter city!!"
//            binding.editTextTextPersonName95.requestFocus()
//            return
//        }
//
//        if (TextUtils.isEmpty(pincode)) {
//            binding.editTextTextPersonName96.error = "Please enter pincode!!"
//            binding.editTextTextPersonName96.requestFocus()
//            return
//        }

        //DOJ
        if (TextUtils.isEmpty(doj)) {
            Toast.makeText(requireContext(),"Select Date of joining!!",Toast.LENGTH_LONG).show()
            binding.button31.error = "Please select Date Of Joining"
            binding.button31.requestFocus()
            return
        }

        if (TextUtils.isEmpty(rank)) {
            binding.empDesignation.error = "Please select designation!!"
            binding.empDesignation.requestFocus()
            return
        }

//        if (TextUtils.isEmpty(phone)) {
//            binding.editTextTextPersonName117.error = "Please enter phone number!!"
//            binding.editTextTextPersonName117.requestFocus()
//            return
//        }

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


//        if (TextUtils.isEmpty(email)) {
//            binding.editTextTextPersonName118.error = "Please enter email id!!"
//            binding.editTextTextPersonName118.requestFocus()
//            return
//        }


        showProgressDialog()


        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_UPDATE_EMPLOYEE_PERSONAL_DETAILS,
            Response.Listener { response ->


                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    hideProgressDialog()
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(
                            requireActivity().applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()

                        var args = UpdateEmployeeBankDetailsFragmentArgs.fromBundle(requireArguments())
                        findNavController().navigate(UpdateEmployeePersonalDetailsFragmentDirections.actionUpdateEmployeePersonalDetailsFragmentToEmployeeDetailsFragment(args.id))

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

                var args = UpdateEmployeeBankDetailsFragmentArgs.fromBundle(requireArguments())
                params["id"] = args.id
                params["fname"] = fname
                params["mname"] = mname
                params["lname"] = lname
                params["father_name"] = father_name
                params["full_name"] = "${fname} ${mname} ${lname}"
                params["dob"] = dob
                params["doj"] = doj
                params["pincode"] = pincode
                params["address"] = address
                params["state"] = state
                params["city"] = city
                params["phone"] = phone
                params["email"] = email
                params["rank"] = rank
                params["gender"] = gender
                params["created_by"] = SharedPrefManager.getInstance(requireContext()).user.id
                params["aadharCount"] = aadharImages.size.toString()
                params["panCount"] = panImages.size.toString()
                params["aadhar_no"] = aadhar_no
                params["pan_no"] = pan_no

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

                return params

            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }
    private fun getPersonalDetails() {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_EMPLOYEE_DETAILS,
            Response.Listener { response ->

                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        val userJson = obj.getJSONObject("user")

                        if(userJson.getString("fname")!=null && (userJson.getString("fname"))!="" && userJson.getString("fname")!="null")
                        {
                            binding.editTextTextPersonName78.setText(userJson.getString("fname"))
                        }
                        if(userJson.getString("mname")!=null && (userJson.getString("mname"))!="" && userJson.getString("mname")!="null")
                        {
                            binding.editTextTextPersonName91.setText(userJson.getString("mname"))
                        }
                        if(userJson.getString("lname")!=null && (userJson.getString("lname"))!="" && userJson.getString("lname")!="null")
                        {
                            binding.editTextTextPersonName92.setText(userJson.getString("lname"))
                        }
                        if(userJson.getString("father_name")!=null && (userJson.getString("father_name"))!="" && userJson.getString("father_name")!="null")
                        {
                            binding.editTextTextPersonName116.setText(userJson.getString("father_name"))
                        }
                        if(userJson.getString("address")!=null && (userJson.getString("address"))!="" && userJson.getString("address")!="null")
                        {
                            binding.editTextTextPersonName93.setText(userJson.getString("address"))
                        }
                        if(userJson.getString("state")!=null && (userJson.getString("state"))!="" && userJson.getString("state")!="null")
                        {
                            binding.editTextTextPersonName94.setText(userJson.getString("state"))
                        }
                        if(userJson.getString("city")!=null && (userJson.getString("city"))!="" && userJson.getString("city")!="null")
                        {
                            binding.editTextTextPersonName95.setText(userJson.getString("city"))
                        }
                        if(userJson.getString("pincode")!=null && (userJson.getString("pincode"))!="" && userJson.getString("pincode")!="null")
                        {
                            binding.editTextTextPersonName96.setText(userJson.getString("pincode"))
                        }
                        if(userJson.getString("rank")!=null && (userJson.getString("rank"))!="" && userJson.getString("rank")!="null")
                        {
                            val index = names.indexOfFirst { it.equals(userJson.getString("rank"), ignoreCase = true) }

                            if (index != -1) {
                                // Set the selected item in AutoCompleteTextView without filtering
                                binding.empDesignation.setText(names[index], false) // false = don't filter dropdown
                                binding.empDesignation.setSelection(names[index].length) // optional: move cursor to end
                            }
                        }
                        if(userJson.getString("phone")!=null && (userJson.getString("phone"))!="" && userJson.getString("phone")!="null")
                        {
                            binding.editTextTextPersonName117.setText(userJson.getString("phone"))
                        }
                        if(userJson.getString("email")!=null && (userJson.getString("email"))!="" && userJson.getString("email")!="null")
                        {
                            binding.editTextTextPersonName118.setText(userJson.getString("email"))
                        }

                        //radio button
                        if (userJson.getString("gender").equals("male", ignoreCase = true)) {
                            binding.male.isChecked = true
                        } else if (userJson.getString("gender").equals("female", ignoreCase = true)) {
                            binding.female.isChecked = true
                        }

                        //dates
                        if(userJson.getString("dob")!=null && (userJson.getString("dob"))!="" && userJson.getString("dob")!="null")
                        {
                            binding.button24.text = userJson.getString("dob")
                            dob = userJson.getString("dob")
                        }

                        if(userJson.getString("doj")!=null && (userJson.getString("doj"))!="" && userJson.getString("doj")!="null")
                        {
                            binding.button31.text = userJson.getString("doj")
                            doj = userJson.getString("doj")
                        }

                        if(userJson.getString("aadhar")!=null && (userJson.getString("aadhar"))!="" && userJson.getString("aadhar")!="null")
                        {
                            binding.editTextTextPersonName99.setText(userJson.getString("aadhar"))
                        }
                        if(userJson.getString("pan")!=null && (userJson.getString("pan"))!="" && userJson.getString("pan")!="null")
                        {
                            binding.editTextTextPersonName100.setText(userJson.getString("pan"))
                        }

                    } else {
                        Toast.makeText(
                            requireActivity().applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()

                    }

                } catch (e: JSONException) {
                    
                }

            },
            Response.ErrorListener { error ->
                
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

                var args = UpdateEmployeeBankDetailsFragmentArgs.fromBundle(requireArguments())
                params["id"] = args.id

                return params

            }
        }

        VolleySingleton.getInstance(requireActivity().applicationContext)
            .addToRequestQueue(stringRequest)
    }
    private fun getAllDesignations() {
        val stringRequest = StringRequest(
            Request.Method.GET,
            URLs.URL_GET_ALL_DESIGNATIONS,
            { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("user")

                        //
                        // empDirectoryList.clear()

                        if (array.length() <= 0) {
                            Toast.makeText(requireContext(),"No designations available!!", Toast.LENGTH_SHORT).show()

                        }
                        else{

                            names.clear()

                            for (i in (array.length() - 1) downTo 0) {
                                val objectArtist = array.getJSONObject(i)
                                names.add(objectArtist.getString("title"))
                            }
                            val adapter1 = ArrayAdapter(requireContext(),R.layout.pay_to_dropdown_layout,names)
                            binding.empDesignation.setAdapter(adapter1)

                            getPersonalDetails()
                        }

                    } else {
                        Toast.makeText(
                            requireActivity().applicationContext,
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: JSONException) {
                    
                }
            },
            { volleyError ->
                Toast.makeText(
                    requireContext(),
                    volleyError.message,
                    Toast.LENGTH_LONG
                ).show()
            })

        val requestQueue = Volley.newRequestQueue(requireContext())
        requestQueue.add(stringRequest)
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
            "dob" -> {
                dob = dbDate
                binding.button24.text = showDate
            }

            "doj" -> {
                doj = dbDate
                binding.button31.text = showDate
            }
        }
    }

    private fun removeImage(imageUri: Uri,code:Int) {
        when(code)
        {
            1->{ aadharImages.remove(imageUri)
                aadharadapter.notifyDataSetChanged()}
            2->{ panImages.remove(imageUri)
                panadapter.notifyDataSetChanged()}
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

                        obj.getString("aadhar_num").takeIf { !it.isNullOrBlank() }?.let {
                            binding.editTextTextPersonName99.setText(it)
                        }

                        obj.getString("phone_num").takeIf { !it.isNullOrBlank() }?.let {
                            binding.editTextTextPersonName117.setText(it)
                        }

                        obj.getString("address").takeIf { !it.isNullOrBlank() }?.let {
                            binding.editTextTextPersonName93.setText(it)
                        }

                        obj.getString("first_name").takeIf { !it.isNullOrBlank() }?.let {
                            binding.editTextTextPersonName78.setText(it)
                        }

                        obj.getString("middle_name").takeIf { !it.isNullOrBlank() }?.let {
                            binding.editTextTextPersonName91.setText(it)
                        }

                        obj.getString("father_name").takeIf { !it.isNullOrBlank() }?.let {
                            binding.editTextTextPersonName116.setText(it)
                        }


                        obj.getString("last_name").takeIf { !it.isNullOrBlank() }?.let {
                            binding.editTextTextPersonName92.setText(it)
                        }

// --- Gender radio buttons ---
                        obj.getString("gender").takeIf { !it.isNullOrBlank() }?.let { genderValue ->
                            val gv = genderValue.lowercase()

                            when {
                                gv.contains("female") -> {
                                    binding.female.isChecked = true
                                    gender = "female"
                                }
                                gv.contains("male") -> {
                                    binding.male.isChecked = true
                                    gender = "male"
                                }
                            }
                        }

                        obj.getString("dob").takeIf { !it.isNullOrBlank() }?.let {
                            binding.button24.text = it
                            dob = it
                        }

                        obj.getString("pincode").takeIf { !it.isNullOrBlank() }?.let {
                            binding.editTextTextPersonName96.setText(it)
                        }

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

                        obj.getString("pan_num").takeIf { !it.isNullOrBlank() }?.let {
                            binding.editTextTextPersonName100.setText(it)
                            pan_no = it
                        }

                        obj.getString("dob").takeIf { !it.isNullOrBlank() }?.let {
                            binding.button24.text = it
                            dob = it
                        }

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

    private fun getEmployeeAllDocs() {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URLs.URL_GET_EMPLOYEES_ALL_DOCS,
            Response.Listener { response ->
                try {
                    val obj = JSONObject(response)

                    if (!obj.getBoolean("error")) {
                        // Clear old data
                        aadharImages.clear()
                        panImages.clear()

                        // Load data into lists
                        val aadharArray = obj.getJSONArray("aadhar")
                        val panArray = obj.getJSONArray("pan")

                        for (i in 0 until aadharArray.length()) {
                            aadharImages.add(Uri.parse(aadharArray.getString(i)))
                        }

                        for (i in 0 until panArray.length()) {
                            panImages.add(Uri.parse(panArray.getString(i)))
                        }

                        // Notify adapters
                        aadharadapter.notifyDataSetChanged()
                        panadapter.notifyDataSetChanged()

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
                var args = UpdateEmployeeBankDetailsFragmentArgs.fromBundle(requireArguments())
                return hashMapOf("emp_id" to args.id)
            }
        }

        VolleySingleton.getInstance(requireContext()).addToRequestQueue(stringRequest)
    }

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


    private fun showProgressDialog() {
        if (progressDialog == null) {
            val progressBar = ProgressBar(requireContext())
            progressBar.isIndeterminate = true
            val padding = 50
            progressBar.setPadding(padding, padding, padding, padding)

            progressDialog = AlertDialog.Builder(requireContext())
                .setTitle("Updating Details")
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