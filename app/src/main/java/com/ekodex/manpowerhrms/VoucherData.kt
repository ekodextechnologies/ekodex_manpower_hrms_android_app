package com.ekodex.manpowerhrms

data class VoucherData(
    var id:String,
    var date:String,
    var type:String,
    var mode:String,
    var status:String,
    var vendor_comm:String,
    var approved_by:String,
    var rejected_by:String,
    var created_by:String,
    var created_on:String,
    var approved_on:String,
    var rejected_on:String,
    var paid_by:String,
    var paid_on:String,
    var name:String,
    var voucher_no:String,
    var client:String,
    var site:String,
    var gang:String,
    var client_id:String,
    var site_id:String,
    var particular:String,
    var amt:String,
    var utr_no:String
)
