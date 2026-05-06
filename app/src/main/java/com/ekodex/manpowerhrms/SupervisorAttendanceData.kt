package com.ekodex.manpowerhrms

data class SupervisorAttendanceData(
    var id: String,
    var emp_name: String,
    var emp_code: String,
    var rank: String,
    var status: String,
    var date: String,
    var created_by: String,
    var check_in: String,
    var check_out: String
)