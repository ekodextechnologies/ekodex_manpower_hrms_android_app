package com.ekodex.manpowerhrms.Others

object URLs {

    //offline url new -> for testingprivat e
   //val ROOT_URL = "http://192.168.0.108/HRMS/hrms_api.php?apicall="

    //online url 1
    ///private val RO OT_URL = "https://sassoftware.co.in/hrms/hrms_api.php?apicall="

    //online url 2
    //private val ROOT_URL = "https://shreesaiventures.com/hrms/hrms_api.php?apicall="

    //online url 3
    //private val ROOT_URL = "https://saiventures.in/hrms/hrms_api.php?apicall="

    //online url 4
    //private val ROOT_URL = "https://hrms.myapplications.co.in/hrms/hrms_api.php?apicall="

    //online url 5
    private val ROOT_URL = "https://ekodexhrms.myapplications.co.in/hrms/hrms_api.php?apicall="

    //login register
    val URL_LOGIN = ROOT_URL + "login"
    val URL_REGISTER = ROOT_URL + "signup"
    val URL_FORGOT_PASSWORD = ROOT_URL + "forgotPassword"
    val URL_EDIT_MY_PROFILE= ROOT_URL + "editProfile"

    val URL_GET_MY_PROFILE= ROOT_URL + "getMyProfile"
    val URL_CHECK_IN= ROOT_URL + "checkIn"
    val URL_CHECK_OUT= ROOT_URL + "checkOut"
    val URL_CAN_WE_CHECK_OUT= ROOT_URL + "canWeCheckOut"
    val URL_ATTENDANCE_STATUS= ROOT_URL + "attendanceStatus"
    val URL_GET_ATTENDANCE_HISTORY= ROOT_URL + "getAttendanceHistory"
    val URL_GET_ALL_EMPLOYEES= ROOT_URL + "getAllEmployees"
    val URL_GET_HOLIDAYS= ROOT_URL + "getHolidays"

    val URL_ADD_EMPLOYEE_LEAVE= ROOT_URL + "addEmployeeLeave"




    val URL_GET_LEAVES= ROOT_URL + "getLeaves"
    val URL_ADD_LEAVE= ROOT_URL + "addLeave"
    val URL_APPROVE_EMPLOYEE_LEAVE= ROOT_URL + "approveEmployeeLeave"
    val URL_REJECT_EMPLOYEE_LEAVE= ROOT_URL + "rejectEmployeeLeave"
    val URL_GET_PARTICULAR_LEAVE= ROOT_URL + "getParticularLeave"






    val URL_GET_BIRTHDAYS= ROOT_URL + "getBirthdays"
    val URL_GET_ALL_VOUCHERS= ROOT_URL + "getAllVouchers"
    val URL_GET_VOUCHER_SPLIT_DETAILS= ROOT_URL + "getVoucherSplitDetails"
    val URL_GET_VOUCHER_IMAGES= ROOT_URL + "getVoucherImages"
    val URL_GET_PARTICULAR_VOUCHERS= ROOT_URL + "getParticularVouchers"
    //for edit voucher we need id's
    val URL_GET_PARTICULAR_VOUCHER_IDS= ROOT_URL + "getParticularVouchersIds"
    val URL_GET_EVENTS= ROOT_URL + "getEvents"
    val URL_GET_EXPENSES= ROOT_URL + "getExpenses"
    val URL_ADD_EXPENSE= ROOT_URL + "addExpense"
    val URL_UPDATE_ONBOARD_STATUS= ROOT_URL + "updateOnboardStatus"
    val URL_GET_JOBS= ROOT_URL + "getJobs"
    val URL_GET_AADHAR_DATA = ROOT_URL + "getAadharData"
    val URL_GET_PAN_DATA = ROOT_URL + "getPanData"
    val URL_GET_UAN_DATA = ROOT_URL + "getUanData"
    val URL_GET_PF_DATA = ROOT_URL + "getPfData"
    val URL_GET_ESIS_DATA = ROOT_URL + "getEsisData"
    val URL_GET_PASSPORT_DATA = ROOT_URL + "getPassportData"
    val URL_GET_APPLICANTS= ROOT_URL + "getApplicants"
    val URL_CREATE_POST= ROOT_URL + "createPost"
    val URL_ADD_POST_DETAILS= ROOT_URL + "addPostDetails"
    val URL_GET_POST_DETAILS= ROOT_URL + "getPostDetails"
    val URL_UPDATE_POST= ROOT_URL + "updatePost"
    val URL_GET_ALL_CATEGORIES= ROOT_URL + "getAllCategories"
    val URL_CREATE_TRAVEL= ROOT_URL + "createTravel"
    val URL_ADD_TRAVEL_SCHEDULE= ROOT_URL + "addTravelSchedule"
    val URL_GET_TRAVEL_REQUESTS= ROOT_URL + "getTravelRequests"

    val URL_ADD_SUPERVISOR_ATTENDANCE= ROOT_URL + "addSupervisorAttendance"
    val URL_ADD_NEW_EMPLOYEE= ROOT_URL + "addNewEmployee"
    val URL_GET_ALL_DESIGNATIONS= ROOT_URL + "getAllDesignations"
    val URL_GET_ALL_EMPLOYEES_FOR_SUPERVISOR= ROOT_URL + "getAllEmployeesForSupervisor"


    val URL_GET_ALL_COMPANIES= ROOT_URL + "getAllCompanies"
    val URL_GET_ALL_BRANCHES= ROOT_URL + "getAllBranches"
    val URL_GET_ALL_VENDORS= ROOT_URL + "getAllVendors"

    val URL_GET_EMPLOYEE_DETAILS = ROOT_URL + "getEmployeeDetails"
    //toshow in emolyee details page at once
    val URL_GET_ALL_EMPLOYEE_DOCS = ROOT_URL + "getAllEmployeeDocs"

    //edit employee document page
    val URL_GET_EMPLOYEES_ALL_DOCS = ROOT_URL + "getEmployeesAllDocs"


    //edit employee document page
    val URL_UPDATE_EMPLOYEES_ALL_DOCS = ROOT_URL + "updateEmployeesAllDocs"

    val URL_UPDATE_JOB_LEFT_STATUS = ROOT_URL + "updateJobLeftStatus"
    val URL_UPDATE_JOB_LEFT_STATUS_INDIVIDUAL = ROOT_URL + "updateJobLeftStatusIndividual"
    val URL_GET_EMPLOYEE_BANK_DETAILS = ROOT_URL + "getEmployeeBankDetails"
    //bank details from users table for admin,supervisor like roles
    val URL_GET_BENEFICIARY_BANK_DETAILS = ROOT_URL + "getBeneficiaryBankDetails"
    val URL_UPDATE_EMPLOYEE_BANK_DETAILS = ROOT_URL + "updateEmployeeBankDetails"
    val URL_UPDATE_EMPLOYEE_PERSONAL_DETAILS = ROOT_URL + "updateEmployeePersonalDetails"
    val URL_UPDATE_EMPLOYEE_DOCUMENT_DETAILS = ROOT_URL + "updateEmployeeDocumentDetails"
    val URL_GET_SUPERVISOR_ATTENDANCE = ROOT_URL + "getSupervisorAttendance"

    //error report
    val URL_GET_ATTENDANCE_ERROR_REPORT = ROOT_URL + "attendanceErrorReport"



    val URL_GET_SUPERVISOR_ATTENDANCE_DOWNLOAD = ROOT_URL + "getSupervisorAttendanceDownload"






    val URL_GET_SUPERVISOR_ATTENDANCE_REPORT = ROOT_URL + "getSupervisorAttendanceReport"
    val URL_GET_SUPERVISOR_ATTENDANCE_REPORTFOR_DOWNLOAD = ROOT_URL + "getSupervisorAttendanceReportForDownload"


 //dashboard employee summary
 val URL_GET_EMPLOYEE_ATTENDANCE_REPORT = ROOT_URL + "getEmployeeAttendanceReport"




 //for dashboard count
    val URL_GET_SUPERVISOR_ATTENDANCE_COUNT_FOR_DASHBOARD = ROOT_URL + "getSupervisorAttendanceCountForDashboard"
    val URL_GET_SUPERVISOR_VOUCHER_COUNT_FOR_DASHBOARD = ROOT_URL + "getSupervisorVoucherCountForDashboard"
    val URL_GET_SUPERVISOR_NET_DAYS_COUNT_FOR_DASHBOARD = ROOT_URL + "getSupervisorNetDaysCountForDashboard"
    val URL_GET_KYC_SUMMARY_COUNTS_FOR_DASHBOARD = ROOT_URL + "getKycSummaryCountsForDashboard"

    val URL_GET_SUPERVISOR_EMPLOYEES_COUNT = ROOT_URL + "getSupervisorEmployeesCount"
    val URL_UPDATE_SUPERVISOR_ATTENDANCE_STATUS = ROOT_URL + "updateSupervisorAttendanceStatus"

    val URL_GET_ALL_GANGS= ROOT_URL + "getAllGangs"
    val URL_GET_ALL_TYPES_FOR_VOUCHER= ROOT_URL + "getAllTypesForVoucher"

    val URL_GET_ALL_BANKS= ROOT_URL + "getAllBanks"
    val URL_ADD_VOUCHER= ROOT_URL + "addVoucher"
    val URL_UPDATE_VOUCHER= ROOT_URL + "updateVoucher"
    val URL_APPROVE_VOUCHER= ROOT_URL + "approveVoucher"
    val URL_REJECT_VOUCHER= ROOT_URL + "rejectVoucher"
    val URL_GET_VOUCHER_SUMMARY= ROOT_URL + "getVoucherSummary"
    val URL_GET_VOUCHER_SUMMARY_FOR_DOWNLOAD = ROOT_URL + "getVoucherSummaryForDownload"
    val URL_GET_ALL_SALARY_SLIPS = ROOT_URL + "getAllSalarySlips"

    val URL_GET_KYC_SUMMARY_EMPLOYEES = ROOT_URL + "getKycSummaryEmployees"

    val URL_GET_PARTICULAR_USER_BANKS = ROOT_URL + "getParticularUserBanks"

    val URL_INSER_WORK_ORDER = ROOT_URL + "insertWorkOrder"
    val URL_ADD_DEVICE_TOKEN = ROOT_URL + "addDeviceToken"

    val URL_UPDATE_PARTICULAR_BANK_DETAIL = ROOT_URL + "updateParticularBankDetails"
    val URL_GET_PARTICULAR_BANK_DETAILS = ROOT_URL + "getParticularBankDetails"
    val URL_ADD_NEW_BANK_DETAIL = ROOT_URL + "addNewBankDetails"
    val URL_DELETE_PARTICULAR_BANK = ROOT_URL + "deleteParticularBank"


    val URL_GET_BULK_ATTENDANCE= ROOT_URL + "getBulkAttendance"

    val URL_GET_ALL_VOUCHER_TYPES = ROOT_URL + "getAllVoucherTypes"


    val URL_SEND_PASSWORD_RECOVERY= ROOT_URL + "sendPasswordRecoveryCode"
    val URL_RESET_PASSWORD= ROOT_URL + "resetPassword"

    val URL_GET_EMPLOYEES_LEAVE_BALANCE = ROOT_URL + "getEmployeesLeaveBalance"



}














































