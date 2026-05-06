package com.ekodex.manpowerhrms

import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ekodex.manpowerhrms.Others.Internet
import com.ekodex.manpowerhrms.Others.SharedPrefManager
import com.ekodex.manpowerhrms.Others.URLs
import com.ekodex.manpowerhrms.Others.VolleySingleton
import com.ekodex.manpowerhrms.databinding.FragmentAddNewEmployBinding
import org.json.JSONException
import org.json.JSONObject
import android.content.ContentResolver
import android.graphics.Bitmap
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.ProgressBar
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.text.TextRecognizer
import com.yalantis.ucrop.UCrop
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import com.android.volley.DefaultRetryPolicy
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddNewEmployeeFragment : Fragment() {

    lateinit var binding: FragmentAddNewEmployBinding
    private var progressDialog: AlertDialog? = null
    var doc_type = ""
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

    //smart
    var count = 0
    var name = ""
    var contact_and_pin = ""
    var address = ""

    var gender = ""


    private var selectedType = ""
    private var dob = ""
    private var doj = ""
    private var uan = ""
    private var esis = ""
    private var passport = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_new_employ, container, false)

        var i1 = Internet()

        var docs = arrayOf("Aadhar Card","Pan Card","Passport","Driving License","Educational")
        val docAdapter = ArrayAdapter(requireContext(),R.layout.pay_to_dropdown_layout,docs)
        binding.empDocType.setAdapter(docAdapter)

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


        if(i1.checkConnection(requireContext()))
        {
            getAllDesignations()
        }
        else
        {
            Toast.makeText(requireContext(),"Please Check internet connection!!", Toast.LENGTH_LONG).show()
        }

        binding.button21.setOnClickListener {
            if(i1.checkConnection(requireContext()))
            {
                addNewEmployee()
            }
            else
            {
                Toast.makeText(requireContext(),"Please Check internet connection!!", Toast.LENGTH_LONG).show()
            }
        }

        binding.empDocType.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //doc_type = employees.get(position).id


            }

        })

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

        // One calendar
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
        binding.button32.setOnClickListener { openDatePicker("uan", calendar, datePicker) }
        binding.button33.setOnClickListener { openDatePicker("esis", calendar, datePicker) }
        binding.button34.setOnClickListener { openDatePicker("passport", calendar, datePicker) }


        return binding.root
    }

    private fun selectImages(code:Int) {
//        ImagePicker.with(this)
//            .crop()	    			//Crop image(Optional), Check Customization for more option
//            .compress(1024)			//Final image size will be less than 1 MB(Optional)
//            //.maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
//            .start(code)

        ImagePicker.with(this@AddNewEmployeeFragment)
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
                           // getPanNumber(imageText)
                        getPanNumber(resultUri)
                        //}
                    }
                    102 -> {
                        uanImages.add(resultUri)
                        uanadapter.notifyDataSetChanged()
//                        if(binding.editTextTextPersonName101.length()<12)
//                        {
                            getUanNumber(imageText)
                       // }
                    }
                    103 -> {
                        pfImages.add(resultUri)
                        pfadapter.notifyDataSetChanged()
//                        if(binding.editTextTextPersonName102.length()<22)
//                        {
                            getPfNumber(imageText)
                       // }
                    }
                    104 -> {
                        esisImages.add(resultUri)
                        esisadapter.notifyDataSetChanged()
//                        if(binding.editTextTextPersonName103.length()<17)
//                        {
                            getEsisNumber(imageText)
                       // }
                    }
                    105 -> {
                        passportImages.add(resultUri)
                        passportadapter.notifyDataSetChanged()
//                        if(binding.editTextTextPersonName104.length()<8)
//                        {
                            getPassportNumber(imageText)
                       // }
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



/*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            data?.let {
                if (it.clipData != null) {
                    for (i in 0 until it.clipData!!.itemCount) {
                        aadharImages.add(it.clipData!!.getItemAt(i).uri)
                    }
                } else {
                    it.data?.let { uri -> aadharImages.add(uri) }
                }
                aadharadapter.notifyDataSetChanged()
            }
        }
        else if (requestCode == 101 && resultCode == RESULT_OK) {
            data?.let {
                if (it.clipData != null) {
                    for (i in 0 until it.clipData!!.itemCount) {
                        panImages.add(it.clipData!!.getItemAt(i).uri)
                    }
                } else {
                    it.data?.let { uri -> panImages.add(uri) }
                }
                panadapter.notifyDataSetChanged()
            }
        }
        else if (requestCode == 102 && resultCode == RESULT_OK) {
            data?.let {
                if (it.clipData != null) {
                    for (i in 0 until it.clipData!!.itemCount) {
                        uanImages.add(it.clipData!!.getItemAt(i).uri)
                    }
                } else {
                    it.data?.let { uri -> uanImages.add(uri) }
                }
                uanadapter.notifyDataSetChanged()
            }
        }
        else if (requestCode == 103 && resultCode == RESULT_OK) {
            data?.let {
                if (it.clipData != null) {
                    for (i in 0 until it.clipData!!.itemCount) {
                        pfImages.add(it.clipData!!.getItemAt(i).uri)
                    }
                } else {
                    it.data?.let { uri -> pfImages.add(uri) }
                }
                pfadapter.notifyDataSetChanged()
            }
        }
        else if (requestCode == 104 && resultCode == RESULT_OK) {
            data?.let {
                if (it.clipData != null) {
                    for (i in 0 until it.clipData!!.itemCount) {
                        esisImages.add(it.clipData!!.getItemAt(i).uri)
                    }
                } else {
                    it.data?.let { uri -> esisImages.add(uri) }
                }
                esisadapter.notifyDataSetChanged()
            }
        }
        else if (requestCode == 105 && resultCode == RESULT_OK) {
            data?.let {
                if (it.clipData != null) {
                    for (i in 0 until it.clipData!!.itemCount) {
                        passportImages.add(it.clipData!!.getItemAt(i).uri)
                    }
                } else {
                    it.data?.let { uri -> passportImages.add(uri) }
                }
                passportadapter.notifyDataSetChanged()
            }
        }
    }
*/

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

                            var names = arrayListOf<String>()

                            for (i in (array.length() - 1) downTo 0) {
                                val objectArtist = array.getJSONObject(i)
                                names.add(objectArtist.getString("title"))
                            }
                            val adapter1 = ArrayAdapter(requireContext(),R.layout.pay_to_dropdown_layout,names)
                            binding.empDesignation.setAdapter(adapter1)
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

    private fun addNewEmployee() {
        var first_name = binding.editTextTextPersonName78.text.toString()
        var middle_name =  binding.editTextTextPersonName79.text.toString()
        var last_name = binding.editTextTextPersonName80.text.toString()
        var father_name = binding.editTextTextPersonName116.text.toString()
        var designation =  binding.empDesignation.text.toString()
        var phone =  binding.editTextTextPersonName97.text.toString()
        val selectedGenderId = binding.gender.checkedRadioButtonId

        aadhar_no = binding.editTextTextPersonName99.text.toString()
        pan_no = binding.editTextTextPersonName100.text.toString()
        uan_no = binding.editTextTextPersonName101.text.toString()
        pf_no = binding.editTextTextPersonName102.text.toString()
        esis_no = binding.editTextTextPersonName103.text.toString()
        passport_no = binding.editTextTextPersonName104.text.toString()

        //var salary = binding.editTextTextPersonName82.text.toString()
        var doc_type = binding.empDocType.text.toString()

        if (TextUtils.isEmpty(first_name)) {
            binding.editTextTextPersonName78.error = "Please enter first name"
            binding.editTextTextPersonName78.requestFocus()
            return
        }
        if (TextUtils.isEmpty(middle_name)) {
            binding.editTextTextPersonName79.error = "Please enter middle name"
            binding.editTextTextPersonName79.requestFocus()
            return
        }

        if (TextUtils.isEmpty(last_name)) {
            binding.editTextTextPersonName80.error = "Please enter last name"
            binding.editTextTextPersonName80.requestFocus()
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

        //DOJ
        if (TextUtils.isEmpty(doj)) {
            binding.button31.error = "Please select Date Of Joining"
            binding.button31.requestFocus()
            return
        }

        if (TextUtils.isEmpty(designation)) {
            binding.empDesignation.error = "Please select designation"
            binding.empDesignation.requestFocus()
            return
        }

//        if (TextUtils.isEmpty(phone)) {
//            binding.editTextTextPersonName97.error = "Please enter phone number!!"
//            binding.editTextTextPersonName97.requestFocus()
//            return
//        }
//        if(selectedImages.size>0)
//        {
//            if (TextUtils.isEmpty(doc_type)) {
//                binding.empDocType.error = "Please select document type!!"
//                binding.empDocType.requestFocus()
//                return
//            }
//        }
//        if (TextUtils.isEmpty(salary)) {
//            binding.editTextTextPersonName82.error = "Please enter salary"
//            binding.editTextTextPersonName82.requestFocus()
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
                Request.Method.POST, URLs.URL_ADD_NEW_EMPLOYEE,
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

                            findNavController().navigate(AddNewEmployeeFragmentDirections.actionAddNewEmployeeFragmentToAttendanceSupervisorFragment())

                        }
                        else {
                           // binding.progressBar5.visibility = View.GONE
                            hideProgressDialog()
                            Toast.makeText(requireContext(), obj.getString("message"), Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: JSONException) {
                        
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

                    params["fname"] = first_name
                    params["mname"] = middle_name
                    params["lname"] = last_name
                    params["father_name"] = father_name
                    params["full_name"] = "${first_name} ${middle_name} ${last_name}"
                    params["pincode"] = binding.editTextTextPersonName124.text.toString()
                    params["dob"] = dob
                    params["doj"] = doj
                    params["uan"] = uan
                    params["esis"] = esis
                    params["passport"] = passport
                    params["designation"] = designation
                    params["gender"] = gender
                    params["address"] = binding.editTextTextPersonName120.text.toString()
                    params["created_by"] = SharedPrefManager.getInstance(requireContext()).user.id
                    params["client_id"] = SharedPrefManager.getInstance(requireContext()).user.client_id
                    params["client_code"] = SharedPrefManager.getInstance(requireContext()).user.client_code
                    params["site_id"] = SharedPrefManager.getInstance(requireContext()).user.site_id

                    params["aadharCount"] = aadharImages.size.toString()
                    params["panCount"] = panImages.size.toString()
                    params["uanCount"] = uanImages.size.toString()
                    params["passportCount"] = passportImages.size.toString()
                    params["pfCount"] = pfImages.size.toString()
                    params["esisCount"] = esisImages.size.toString()
                    params["phone"] = phone

                    params["aadhar_no"] = aadhar_no
                    params["pan_no"] = pan_no
                    params["uan_no"] = uan_no
                    params["pf_no"] = pf_no
                    params["passport_no"] = passport_no
                    params["esis_no"] = esis_no

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
            stringRequest.tag = "add_employee"
            VolleySingleton.getInstance(requireActivity().applicationContext)
                .addToRequestQueue(stringRequest)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        VolleySingleton.getInstance(requireActivity()).requestQueue.cancelAll("add_employee")
    }


    //to pass image to document ai
    fun imageUriToBase64(contentResolver: ContentResolver, imageUri: Uri): String {
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
        return Base64.encodeToString(byteArray, Base64.NO_WRAP)
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
                            binding.editTextTextPersonName97.setText(it)
                        }

                        obj.getString("address").takeIf { !it.isNullOrBlank() }?.let {
                            binding.editTextTextPersonName120.setText(it)
                        }

                        obj.getString("first_name").takeIf { !it.isNullOrBlank() }?.let {
                            binding.editTextTextPersonName78.setText(it)
                        }

                        obj.getString("middle_name").takeIf { !it.isNullOrBlank() }?.let {
                            binding.editTextTextPersonName79.setText(it)
                        }

                        obj.getString("father_name").takeIf { !it.isNullOrBlank() }?.let {
                            binding.editTextTextPersonName116.setText(it)
                        }

                        obj.getString("last_name").takeIf { !it.isNullOrBlank() }?.let {
                            binding.editTextTextPersonName80.setText(it)
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
                            binding.editTextTextPersonName124.setText(it)
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
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = java.util.HashMap<String, String>()

                Log.i("11111","aadhar base64 - ${base64image}")
//                params["aadhar_text"] = aadhar_raw_txt
                params["aadhar_text"] = base64image

                return params
            }
        }

        stringRequest.retryPolicy = DefaultRetryPolicy(
            300000,  // 20 seconds
            0,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        stringRequest.tag = "add_employee"
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

                        obj.getString("pan_num").takeIf { !it.isNullOrBlank() }?.let {
                            binding.editTextTextPersonName100.setText(it)
                            pan_no = it
                        }

                        obj.getString("dob").takeIf { !it.isNullOrBlank() }?.let {
                            binding.button24.text = it
                            dob = it
                        }

                        hideDocProgressDialog()

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
        stringRequest.tag = "add_employee"
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
        stringRequest.tag = "add_employee"
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
        stringRequest.tag = "add_employee"
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
        stringRequest.tag = "add_employee"
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
        stringRequest.tag = "add_employee"
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
                .setTitle("Adding Employee")
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

//    private fun updateToDateLable(calener: Calendar) {
//        val myFormat = "dd-MM-yyyy"
//        val sdf = SimpleDateFormat(myFormat, Locale.UK)
//// Here we can use calendar selected date
////        binding.button24.text = (sdf.format(calener.time))
//        val myFormat2 = "yyyy-MM-dd"
//        val sdf2 = SimpleDateFormat(myFormat2, Locale.UK)
//        dob = (sdf2.format(calener.time))
//        binding.button24.text = (sdf.format(calener.time))
//    }

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



}



