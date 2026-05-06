package com.ekodex.manpowerhrms

data class SupervisorAttendanceReportData(
    var date:String,
    var total_expected:String,
    var total_present:String,
    var total_absent:String
)
