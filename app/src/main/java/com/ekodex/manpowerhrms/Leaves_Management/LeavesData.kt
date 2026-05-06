package com.ekodex.manpowerhrms.Leaves_Management

data class LeavesData(
    var id:String,
    var from:String,
    var to:String,
    var totaldays:String,
    var desc:String,
    var status:String,
    var type:String,
    var approved_by:String,
    var rejected_by:String,
    var created_by:String,
    var created_on:String,
    var approved_on:String,
    var rejected_on:String,
    var client:String,
    var site:String,
    var client_id:String,
    var site_id:String,
    var from_time:String
)
