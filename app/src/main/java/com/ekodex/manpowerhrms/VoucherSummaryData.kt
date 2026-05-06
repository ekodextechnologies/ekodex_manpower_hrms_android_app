package com.ekodex.manpowerhrms

data class VoucherSummaryData(
    var date:String,
    var total:String,
    var pending:String,
    var rejected:String,
    var approved:String,
    var paid:String
)
