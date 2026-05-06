package com.ekodex.manpowerhrms.Others
data class User(
    var id:String,
    var emp_code:String,
    var fname:String,
    var lname:String,
    var gender:String,
    var rank:String,
    var email:String,
    var phone:String,
    var address:String,
    var state:String,
    var city:String,
    var pincode:String,
    var onboarding:String,
    var role:String,
    var client_id:String,
    var site_id:String,
    var client_code:String,
    var site_selected:Int,
    var copy_client_id:String,
    var copy_site_id:String,
    var bank_name: String,
    var account_no: String,
    var bank_ifsc: String,
    var ac_holder_name: String,
    var bank_address: String,
    var bank_state: String,
    var bank_city: String,
    var bank_micr: String,
    var card_no: String
    )

//onlineToOfflineSyncStatus -> used to only 1 time sync after login , of products , bills , new orders, users data etc.
//otherwise even if we delete a product it will again sync it from dashboard function ..







