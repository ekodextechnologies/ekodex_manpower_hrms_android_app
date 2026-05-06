package com.ekodex.manpowerhrms;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.ekodex.manpowerhrms.databinding.ActivityForgotPasswordBindingImpl;
import com.ekodex.manpowerhrms.databinding.ActivityLoginBindingImpl;
import com.ekodex.manpowerhrms.databinding.ActivityMainBindingImpl;
import com.ekodex.manpowerhrms.databinding.ActivitySplashScreenBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentAddEmployeeVoucherBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentAddExpenseBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentAddLeaveBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentAddNewBankBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentAddNewEmployBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentAddSiteVoucherBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentAddSplitVoucherBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentAddVoucherBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentApprovedLeavesBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentApprovedVouchersBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentAttendanceBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentAttendanceErrorReportBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentAttendanceReportBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentAttendanceSupervisorBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentBirthdaysBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentBulkAttendanceDownloadBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentDashboardBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentEditJobPostBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentEditMyProfileBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentEditVoucherBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentEmployeeDetailsBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentEmployeeDirectoryBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentEventsBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentExpensesBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentHolidaysBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentKycDoneBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentKycNotDoneBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentKycSummaryManagementBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentLeaveDetailsBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentLeavesManagementBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentMyBanksBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentMyProfileBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentNewsBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentNewsDetailsBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentOnboard1BindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentOnboard2BindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentOnboard3BindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentOnboardingBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentPaidVouchersBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentPendingLeaveBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentPendingVouchersBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentRecruitmentApplicantDocumentBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentRecruitmentApplicantsBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentRecruitmentBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentRecruitmentCreatePostBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentRecruitmentPostDetailsBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentRecruitmentPostsBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentRejectedLeavesBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentRejectedVouchersBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentSalarySlipBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentSupervisorAttendancBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentSupervisorAttendanceBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentTravelAccomodationBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentTravelManagementBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentTravelReportBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentTravelRequestBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentTravelScheduleBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentTravelcreateRequestBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentUpdateEmployeeBankDetailsBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentUpdateEmployeeDocumentBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentUpdateEmployeePersonalDetailsBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentUpdateMyBankBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentUploadWorkOrderBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentViewVouchersBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentVoucherDetailsBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentVoucherManagementBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentVoucherSummaryBindingImpl;
import com.ekodex.manpowerhrms.databinding.FragmentVouchersBindingImpl;
import com.ekodex.manpowerhrms.databinding.HeaderLayoutBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYFORGOTPASSWORD = 1;

  private static final int LAYOUT_ACTIVITYLOGIN = 2;

  private static final int LAYOUT_ACTIVITYMAIN = 3;

  private static final int LAYOUT_ACTIVITYSPLASHSCREEN = 4;

  private static final int LAYOUT_FRAGMENTADDEMPLOYEEVOUCHER = 5;

  private static final int LAYOUT_FRAGMENTADDEXPENSE = 6;

  private static final int LAYOUT_FRAGMENTADDLEAVE = 7;

  private static final int LAYOUT_FRAGMENTADDNEWBANK = 8;

  private static final int LAYOUT_FRAGMENTADDNEWEMPLOY = 9;

  private static final int LAYOUT_FRAGMENTADDSITEVOUCHER = 10;

  private static final int LAYOUT_FRAGMENTADDSPLITVOUCHER = 11;

  private static final int LAYOUT_FRAGMENTADDVOUCHER = 12;

  private static final int LAYOUT_FRAGMENTAPPROVEDLEAVES = 13;

  private static final int LAYOUT_FRAGMENTAPPROVEDVOUCHERS = 14;

  private static final int LAYOUT_FRAGMENTATTENDANCE = 15;

  private static final int LAYOUT_FRAGMENTATTENDANCEERRORREPORT = 16;

  private static final int LAYOUT_FRAGMENTATTENDANCEREPORT = 17;

  private static final int LAYOUT_FRAGMENTATTENDANCESUPERVISOR = 18;

  private static final int LAYOUT_FRAGMENTBIRTHDAYS = 19;

  private static final int LAYOUT_FRAGMENTBULKATTENDANCEDOWNLOAD = 20;

  private static final int LAYOUT_FRAGMENTDASHBOARD = 21;

  private static final int LAYOUT_FRAGMENTEDITJOBPOST = 22;

  private static final int LAYOUT_FRAGMENTEDITMYPROFILE = 23;

  private static final int LAYOUT_FRAGMENTEDITVOUCHER = 24;

  private static final int LAYOUT_FRAGMENTEMPLOYEEDETAILS = 25;

  private static final int LAYOUT_FRAGMENTEMPLOYEEDIRECTORY = 26;

  private static final int LAYOUT_FRAGMENTEVENTS = 27;

  private static final int LAYOUT_FRAGMENTEXPENSES = 28;

  private static final int LAYOUT_FRAGMENTHOLIDAYS = 29;

  private static final int LAYOUT_FRAGMENTKYCDONE = 30;

  private static final int LAYOUT_FRAGMENTKYCNOTDONE = 31;

  private static final int LAYOUT_FRAGMENTKYCSUMMARYMANAGEMENT = 32;

  private static final int LAYOUT_FRAGMENTLEAVEDETAILS = 33;

  private static final int LAYOUT_FRAGMENTLEAVESMANAGEMENT = 34;

  private static final int LAYOUT_FRAGMENTMYBANKS = 35;

  private static final int LAYOUT_FRAGMENTMYPROFILE = 36;

  private static final int LAYOUT_FRAGMENTNEWS = 37;

  private static final int LAYOUT_FRAGMENTNEWSDETAILS = 38;

  private static final int LAYOUT_FRAGMENTONBOARD1 = 39;

  private static final int LAYOUT_FRAGMENTONBOARD2 = 40;

  private static final int LAYOUT_FRAGMENTONBOARD3 = 41;

  private static final int LAYOUT_FRAGMENTONBOARDING = 42;

  private static final int LAYOUT_FRAGMENTPAIDVOUCHERS = 43;

  private static final int LAYOUT_FRAGMENTPENDINGLEAVE = 44;

  private static final int LAYOUT_FRAGMENTPENDINGVOUCHERS = 45;

  private static final int LAYOUT_FRAGMENTRECRUITMENT = 46;

  private static final int LAYOUT_FRAGMENTRECRUITMENTAPPLICANTDOCUMENT = 47;

  private static final int LAYOUT_FRAGMENTRECRUITMENTAPPLICANTS = 48;

  private static final int LAYOUT_FRAGMENTRECRUITMENTCREATEPOST = 49;

  private static final int LAYOUT_FRAGMENTRECRUITMENTPOSTDETAILS = 50;

  private static final int LAYOUT_FRAGMENTRECRUITMENTPOSTS = 51;

  private static final int LAYOUT_FRAGMENTREJECTEDLEAVES = 52;

  private static final int LAYOUT_FRAGMENTREJECTEDVOUCHERS = 53;

  private static final int LAYOUT_FRAGMENTSALARYSLIP = 54;

  private static final int LAYOUT_FRAGMENTSUPERVISORATTENDANC = 55;

  private static final int LAYOUT_FRAGMENTSUPERVISORATTENDANCE = 56;

  private static final int LAYOUT_FRAGMENTTRAVELACCOMODATION = 57;

  private static final int LAYOUT_FRAGMENTTRAVELMANAGEMENT = 58;

  private static final int LAYOUT_FRAGMENTTRAVELREPORT = 59;

  private static final int LAYOUT_FRAGMENTTRAVELREQUEST = 60;

  private static final int LAYOUT_FRAGMENTTRAVELSCHEDULE = 61;

  private static final int LAYOUT_FRAGMENTTRAVELCREATEREQUEST = 62;

  private static final int LAYOUT_FRAGMENTUPDATEEMPLOYEEBANKDETAILS = 63;

  private static final int LAYOUT_FRAGMENTUPDATEEMPLOYEEDOCUMENT = 64;

  private static final int LAYOUT_FRAGMENTUPDATEEMPLOYEEPERSONALDETAILS = 65;

  private static final int LAYOUT_FRAGMENTUPDATEMYBANK = 66;

  private static final int LAYOUT_FRAGMENTUPLOADWORKORDER = 67;

  private static final int LAYOUT_FRAGMENTVIEWVOUCHERS = 68;

  private static final int LAYOUT_FRAGMENTVOUCHERDETAILS = 69;

  private static final int LAYOUT_FRAGMENTVOUCHERMANAGEMENT = 70;

  private static final int LAYOUT_FRAGMENTVOUCHERSUMMARY = 71;

  private static final int LAYOUT_FRAGMENTVOUCHERS = 72;

  private static final int LAYOUT_HEADERLAYOUT = 73;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(73);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.activity_forgot_password, LAYOUT_ACTIVITYFORGOTPASSWORD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.activity_login, LAYOUT_ACTIVITYLOGIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.activity_main, LAYOUT_ACTIVITYMAIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.activity_splash_screen, LAYOUT_ACTIVITYSPLASHSCREEN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_add_employee_voucher, LAYOUT_FRAGMENTADDEMPLOYEEVOUCHER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_add_expense, LAYOUT_FRAGMENTADDEXPENSE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_add_leave, LAYOUT_FRAGMENTADDLEAVE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_add_new_bank, LAYOUT_FRAGMENTADDNEWBANK);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_add_new_employ, LAYOUT_FRAGMENTADDNEWEMPLOY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_add_site_voucher, LAYOUT_FRAGMENTADDSITEVOUCHER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_add_split_voucher, LAYOUT_FRAGMENTADDSPLITVOUCHER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_add_voucher, LAYOUT_FRAGMENTADDVOUCHER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_approved_leaves, LAYOUT_FRAGMENTAPPROVEDLEAVES);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_approved_vouchers, LAYOUT_FRAGMENTAPPROVEDVOUCHERS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_attendance, LAYOUT_FRAGMENTATTENDANCE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_attendance_error_report, LAYOUT_FRAGMENTATTENDANCEERRORREPORT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_attendance_report, LAYOUT_FRAGMENTATTENDANCEREPORT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_attendance_supervisor, LAYOUT_FRAGMENTATTENDANCESUPERVISOR);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_birthdays, LAYOUT_FRAGMENTBIRTHDAYS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_bulk_attendance_download, LAYOUT_FRAGMENTBULKATTENDANCEDOWNLOAD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_dashboard, LAYOUT_FRAGMENTDASHBOARD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_edit_job_post, LAYOUT_FRAGMENTEDITJOBPOST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_edit_my_profile, LAYOUT_FRAGMENTEDITMYPROFILE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_edit_voucher, LAYOUT_FRAGMENTEDITVOUCHER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_employee_details, LAYOUT_FRAGMENTEMPLOYEEDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_employee_directory, LAYOUT_FRAGMENTEMPLOYEEDIRECTORY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_events, LAYOUT_FRAGMENTEVENTS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_expenses, LAYOUT_FRAGMENTEXPENSES);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_holidays, LAYOUT_FRAGMENTHOLIDAYS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_kyc_done, LAYOUT_FRAGMENTKYCDONE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_kyc_not_done, LAYOUT_FRAGMENTKYCNOTDONE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_kyc_summary_management, LAYOUT_FRAGMENTKYCSUMMARYMANAGEMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_leave_details, LAYOUT_FRAGMENTLEAVEDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_leaves_management, LAYOUT_FRAGMENTLEAVESMANAGEMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_my_banks, LAYOUT_FRAGMENTMYBANKS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_my_profile, LAYOUT_FRAGMENTMYPROFILE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_news, LAYOUT_FRAGMENTNEWS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_news_details, LAYOUT_FRAGMENTNEWSDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_onboard1, LAYOUT_FRAGMENTONBOARD1);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_onboard2, LAYOUT_FRAGMENTONBOARD2);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_onboard3, LAYOUT_FRAGMENTONBOARD3);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_onboarding, LAYOUT_FRAGMENTONBOARDING);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_paid_vouchers, LAYOUT_FRAGMENTPAIDVOUCHERS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_pending_leave, LAYOUT_FRAGMENTPENDINGLEAVE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_pending_vouchers, LAYOUT_FRAGMENTPENDINGVOUCHERS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_recruitment, LAYOUT_FRAGMENTRECRUITMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_recruitment_applicant_document, LAYOUT_FRAGMENTRECRUITMENTAPPLICANTDOCUMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_recruitment_applicants, LAYOUT_FRAGMENTRECRUITMENTAPPLICANTS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_recruitment_create_post, LAYOUT_FRAGMENTRECRUITMENTCREATEPOST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_recruitment_post_details, LAYOUT_FRAGMENTRECRUITMENTPOSTDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_recruitment_posts, LAYOUT_FRAGMENTRECRUITMENTPOSTS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_rejected_leaves, LAYOUT_FRAGMENTREJECTEDLEAVES);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_rejected_vouchers, LAYOUT_FRAGMENTREJECTEDVOUCHERS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_salary_slip, LAYOUT_FRAGMENTSALARYSLIP);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_supervisor_attendanc, LAYOUT_FRAGMENTSUPERVISORATTENDANC);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_supervisor_attendance, LAYOUT_FRAGMENTSUPERVISORATTENDANCE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_travel_accomodation, LAYOUT_FRAGMENTTRAVELACCOMODATION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_travel_management, LAYOUT_FRAGMENTTRAVELMANAGEMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_travel_report, LAYOUT_FRAGMENTTRAVELREPORT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_travel_request, LAYOUT_FRAGMENTTRAVELREQUEST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_travel_schedule, LAYOUT_FRAGMENTTRAVELSCHEDULE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_travelcreate_request, LAYOUT_FRAGMENTTRAVELCREATEREQUEST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_update_employee_bank_details, LAYOUT_FRAGMENTUPDATEEMPLOYEEBANKDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_update_employee_document, LAYOUT_FRAGMENTUPDATEEMPLOYEEDOCUMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_update_employee_personal_details, LAYOUT_FRAGMENTUPDATEEMPLOYEEPERSONALDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_update_my_bank, LAYOUT_FRAGMENTUPDATEMYBANK);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_upload_work_order, LAYOUT_FRAGMENTUPLOADWORKORDER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_view_vouchers, LAYOUT_FRAGMENTVIEWVOUCHERS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_voucher_details, LAYOUT_FRAGMENTVOUCHERDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_voucher_management, LAYOUT_FRAGMENTVOUCHERMANAGEMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_voucher_summary, LAYOUT_FRAGMENTVOUCHERSUMMARY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.fragment_vouchers, LAYOUT_FRAGMENTVOUCHERS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.ekodex.manpowerhrms.R.layout.header_layout, LAYOUT_HEADERLAYOUT);
  }

  private final ViewDataBinding internalGetViewDataBinding0(DataBindingComponent component,
      View view, int internalId, Object tag) {
    switch(internalId) {
      case  LAYOUT_ACTIVITYFORGOTPASSWORD: {
        if ("layout/activity_forgot_password_0".equals(tag)) {
          return new ActivityForgotPasswordBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_forgot_password is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYLOGIN: {
        if ("layout/activity_login_0".equals(tag)) {
          return new ActivityLoginBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_login is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYMAIN: {
        if ("layout/activity_main_0".equals(tag)) {
          return new ActivityMainBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_main is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYSPLASHSCREEN: {
        if ("layout/activity_splash_screen_0".equals(tag)) {
          return new ActivitySplashScreenBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_splash_screen is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTADDEMPLOYEEVOUCHER: {
        if ("layout/fragment_add_employee_voucher_0".equals(tag)) {
          return new FragmentAddEmployeeVoucherBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_add_employee_voucher is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTADDEXPENSE: {
        if ("layout/fragment_add_expense_0".equals(tag)) {
          return new FragmentAddExpenseBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_add_expense is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTADDLEAVE: {
        if ("layout/fragment_add_leave_0".equals(tag)) {
          return new FragmentAddLeaveBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_add_leave is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTADDNEWBANK: {
        if ("layout/fragment_add_new_bank_0".equals(tag)) {
          return new FragmentAddNewBankBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_add_new_bank is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTADDNEWEMPLOY: {
        if ("layout/fragment_add_new_employ_0".equals(tag)) {
          return new FragmentAddNewEmployBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_add_new_employ is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTADDSITEVOUCHER: {
        if ("layout/fragment_add_site_voucher_0".equals(tag)) {
          return new FragmentAddSiteVoucherBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_add_site_voucher is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTADDSPLITVOUCHER: {
        if ("layout/fragment_add_split_voucher_0".equals(tag)) {
          return new FragmentAddSplitVoucherBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_add_split_voucher is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTADDVOUCHER: {
        if ("layout/fragment_add_voucher_0".equals(tag)) {
          return new FragmentAddVoucherBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_add_voucher is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTAPPROVEDLEAVES: {
        if ("layout/fragment_approved_leaves_0".equals(tag)) {
          return new FragmentApprovedLeavesBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_approved_leaves is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTAPPROVEDVOUCHERS: {
        if ("layout/fragment_approved_vouchers_0".equals(tag)) {
          return new FragmentApprovedVouchersBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_approved_vouchers is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTATTENDANCE: {
        if ("layout/fragment_attendance_0".equals(tag)) {
          return new FragmentAttendanceBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_attendance is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTATTENDANCEERRORREPORT: {
        if ("layout/fragment_attendance_error_report_0".equals(tag)) {
          return new FragmentAttendanceErrorReportBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_attendance_error_report is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTATTENDANCEREPORT: {
        if ("layout/fragment_attendance_report_0".equals(tag)) {
          return new FragmentAttendanceReportBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_attendance_report is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTATTENDANCESUPERVISOR: {
        if ("layout/fragment_attendance_supervisor_0".equals(tag)) {
          return new FragmentAttendanceSupervisorBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_attendance_supervisor is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTBIRTHDAYS: {
        if ("layout/fragment_birthdays_0".equals(tag)) {
          return new FragmentBirthdaysBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_birthdays is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTBULKATTENDANCEDOWNLOAD: {
        if ("layout/fragment_bulk_attendance_download_0".equals(tag)) {
          return new FragmentBulkAttendanceDownloadBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_bulk_attendance_download is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTDASHBOARD: {
        if ("layout/fragment_dashboard_0".equals(tag)) {
          return new FragmentDashboardBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_dashboard is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTEDITJOBPOST: {
        if ("layout/fragment_edit_job_post_0".equals(tag)) {
          return new FragmentEditJobPostBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_edit_job_post is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTEDITMYPROFILE: {
        if ("layout/fragment_edit_my_profile_0".equals(tag)) {
          return new FragmentEditMyProfileBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_edit_my_profile is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTEDITVOUCHER: {
        if ("layout/fragment_edit_voucher_0".equals(tag)) {
          return new FragmentEditVoucherBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_edit_voucher is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTEMPLOYEEDETAILS: {
        if ("layout/fragment_employee_details_0".equals(tag)) {
          return new FragmentEmployeeDetailsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_employee_details is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTEMPLOYEEDIRECTORY: {
        if ("layout/fragment_employee_directory_0".equals(tag)) {
          return new FragmentEmployeeDirectoryBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_employee_directory is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTEVENTS: {
        if ("layout/fragment_events_0".equals(tag)) {
          return new FragmentEventsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_events is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTEXPENSES: {
        if ("layout/fragment_expenses_0".equals(tag)) {
          return new FragmentExpensesBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_expenses is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTHOLIDAYS: {
        if ("layout/fragment_holidays_0".equals(tag)) {
          return new FragmentHolidaysBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_holidays is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTKYCDONE: {
        if ("layout/fragment_kyc_done_0".equals(tag)) {
          return new FragmentKycDoneBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_kyc_done is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTKYCNOTDONE: {
        if ("layout/fragment_kyc_not_done_0".equals(tag)) {
          return new FragmentKycNotDoneBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_kyc_not_done is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTKYCSUMMARYMANAGEMENT: {
        if ("layout/fragment_kyc_summary_management_0".equals(tag)) {
          return new FragmentKycSummaryManagementBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_kyc_summary_management is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTLEAVEDETAILS: {
        if ("layout/fragment_leave_details_0".equals(tag)) {
          return new FragmentLeaveDetailsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_leave_details is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTLEAVESMANAGEMENT: {
        if ("layout/fragment_leaves_management_0".equals(tag)) {
          return new FragmentLeavesManagementBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_leaves_management is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTMYBANKS: {
        if ("layout/fragment_my_banks_0".equals(tag)) {
          return new FragmentMyBanksBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_my_banks is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTMYPROFILE: {
        if ("layout/fragment_my_profile_0".equals(tag)) {
          return new FragmentMyProfileBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_my_profile is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTNEWS: {
        if ("layout/fragment_news_0".equals(tag)) {
          return new FragmentNewsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_news is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTNEWSDETAILS: {
        if ("layout/fragment_news_details_0".equals(tag)) {
          return new FragmentNewsDetailsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_news_details is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTONBOARD1: {
        if ("layout/fragment_onboard1_0".equals(tag)) {
          return new FragmentOnboard1BindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_onboard1 is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTONBOARD2: {
        if ("layout/fragment_onboard2_0".equals(tag)) {
          return new FragmentOnboard2BindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_onboard2 is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTONBOARD3: {
        if ("layout/fragment_onboard3_0".equals(tag)) {
          return new FragmentOnboard3BindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_onboard3 is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTONBOARDING: {
        if ("layout/fragment_onboarding_0".equals(tag)) {
          return new FragmentOnboardingBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_onboarding is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTPAIDVOUCHERS: {
        if ("layout/fragment_paid_vouchers_0".equals(tag)) {
          return new FragmentPaidVouchersBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_paid_vouchers is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTPENDINGLEAVE: {
        if ("layout/fragment_pending_leave_0".equals(tag)) {
          return new FragmentPendingLeaveBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_pending_leave is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTPENDINGVOUCHERS: {
        if ("layout/fragment_pending_vouchers_0".equals(tag)) {
          return new FragmentPendingVouchersBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_pending_vouchers is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTRECRUITMENT: {
        if ("layout/fragment_recruitment_0".equals(tag)) {
          return new FragmentRecruitmentBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_recruitment is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTRECRUITMENTAPPLICANTDOCUMENT: {
        if ("layout/fragment_recruitment_applicant_document_0".equals(tag)) {
          return new FragmentRecruitmentApplicantDocumentBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_recruitment_applicant_document is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTRECRUITMENTAPPLICANTS: {
        if ("layout/fragment_recruitment_applicants_0".equals(tag)) {
          return new FragmentRecruitmentApplicantsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_recruitment_applicants is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTRECRUITMENTCREATEPOST: {
        if ("layout/fragment_recruitment_create_post_0".equals(tag)) {
          return new FragmentRecruitmentCreatePostBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_recruitment_create_post is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTRECRUITMENTPOSTDETAILS: {
        if ("layout/fragment_recruitment_post_details_0".equals(tag)) {
          return new FragmentRecruitmentPostDetailsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_recruitment_post_details is invalid. Received: " + tag);
      }
    }
    return null;
  }

  private final ViewDataBinding internalGetViewDataBinding1(DataBindingComponent component,
      View view, int internalId, Object tag) {
    switch(internalId) {
      case  LAYOUT_FRAGMENTRECRUITMENTPOSTS: {
        if ("layout/fragment_recruitment_posts_0".equals(tag)) {
          return new FragmentRecruitmentPostsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_recruitment_posts is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTREJECTEDLEAVES: {
        if ("layout/fragment_rejected_leaves_0".equals(tag)) {
          return new FragmentRejectedLeavesBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_rejected_leaves is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTREJECTEDVOUCHERS: {
        if ("layout/fragment_rejected_vouchers_0".equals(tag)) {
          return new FragmentRejectedVouchersBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_rejected_vouchers is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTSALARYSLIP: {
        if ("layout/fragment_salary_slip_0".equals(tag)) {
          return new FragmentSalarySlipBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_salary_slip is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTSUPERVISORATTENDANC: {
        if ("layout/fragment_supervisor_attendanc_0".equals(tag)) {
          return new FragmentSupervisorAttendancBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_supervisor_attendanc is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTSUPERVISORATTENDANCE: {
        if ("layout/fragment_supervisor_attendance_0".equals(tag)) {
          return new FragmentSupervisorAttendanceBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_supervisor_attendance is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTTRAVELACCOMODATION: {
        if ("layout/fragment_travel_accomodation_0".equals(tag)) {
          return new FragmentTravelAccomodationBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_travel_accomodation is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTTRAVELMANAGEMENT: {
        if ("layout/fragment_travel_management_0".equals(tag)) {
          return new FragmentTravelManagementBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_travel_management is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTTRAVELREPORT: {
        if ("layout/fragment_travel_report_0".equals(tag)) {
          return new FragmentTravelReportBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_travel_report is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTTRAVELREQUEST: {
        if ("layout/fragment_travel_request_0".equals(tag)) {
          return new FragmentTravelRequestBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_travel_request is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTTRAVELSCHEDULE: {
        if ("layout/fragment_travel_schedule_0".equals(tag)) {
          return new FragmentTravelScheduleBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_travel_schedule is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTTRAVELCREATEREQUEST: {
        if ("layout/fragment_travelcreate_request_0".equals(tag)) {
          return new FragmentTravelcreateRequestBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_travelcreate_request is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTUPDATEEMPLOYEEBANKDETAILS: {
        if ("layout/fragment_update_employee_bank_details_0".equals(tag)) {
          return new FragmentUpdateEmployeeBankDetailsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_update_employee_bank_details is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTUPDATEEMPLOYEEDOCUMENT: {
        if ("layout/fragment_update_employee_document_0".equals(tag)) {
          return new FragmentUpdateEmployeeDocumentBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_update_employee_document is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTUPDATEEMPLOYEEPERSONALDETAILS: {
        if ("layout/fragment_update_employee_personal_details_0".equals(tag)) {
          return new FragmentUpdateEmployeePersonalDetailsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_update_employee_personal_details is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTUPDATEMYBANK: {
        if ("layout/fragment_update_my_bank_0".equals(tag)) {
          return new FragmentUpdateMyBankBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_update_my_bank is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTUPLOADWORKORDER: {
        if ("layout/fragment_upload_work_order_0".equals(tag)) {
          return new FragmentUploadWorkOrderBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_upload_work_order is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTVIEWVOUCHERS: {
        if ("layout/fragment_view_vouchers_0".equals(tag)) {
          return new FragmentViewVouchersBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_view_vouchers is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTVOUCHERDETAILS: {
        if ("layout/fragment_voucher_details_0".equals(tag)) {
          return new FragmentVoucherDetailsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_voucher_details is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTVOUCHERMANAGEMENT: {
        if ("layout/fragment_voucher_management_0".equals(tag)) {
          return new FragmentVoucherManagementBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_voucher_management is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTVOUCHERSUMMARY: {
        if ("layout/fragment_voucher_summary_0".equals(tag)) {
          return new FragmentVoucherSummaryBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_voucher_summary is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTVOUCHERS: {
        if ("layout/fragment_vouchers_0".equals(tag)) {
          return new FragmentVouchersBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_vouchers is invalid. Received: " + tag);
      }
      case  LAYOUT_HEADERLAYOUT: {
        if ("layout/header_layout_0".equals(tag)) {
          return new HeaderLayoutBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for header_layout is invalid. Received: " + tag);
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      // find which method will have it. -1 is necessary becausefirst id starts with 1;
      int methodIndex = (localizedLayoutId - 1) / 50;
      switch(methodIndex) {
        case 0: {
          return internalGetViewDataBinding0(component, view, localizedLayoutId, tag);
        }
        case 1: {
          return internalGetViewDataBinding1(component, view, localizedLayoutId, tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(1);

    static {
      sKeys.put(0, "_all");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(73);

    static {
      sKeys.put("layout/activity_forgot_password_0", com.ekodex.manpowerhrms.R.layout.activity_forgot_password);
      sKeys.put("layout/activity_login_0", com.ekodex.manpowerhrms.R.layout.activity_login);
      sKeys.put("layout/activity_main_0", com.ekodex.manpowerhrms.R.layout.activity_main);
      sKeys.put("layout/activity_splash_screen_0", com.ekodex.manpowerhrms.R.layout.activity_splash_screen);
      sKeys.put("layout/fragment_add_employee_voucher_0", com.ekodex.manpowerhrms.R.layout.fragment_add_employee_voucher);
      sKeys.put("layout/fragment_add_expense_0", com.ekodex.manpowerhrms.R.layout.fragment_add_expense);
      sKeys.put("layout/fragment_add_leave_0", com.ekodex.manpowerhrms.R.layout.fragment_add_leave);
      sKeys.put("layout/fragment_add_new_bank_0", com.ekodex.manpowerhrms.R.layout.fragment_add_new_bank);
      sKeys.put("layout/fragment_add_new_employ_0", com.ekodex.manpowerhrms.R.layout.fragment_add_new_employ);
      sKeys.put("layout/fragment_add_site_voucher_0", com.ekodex.manpowerhrms.R.layout.fragment_add_site_voucher);
      sKeys.put("layout/fragment_add_split_voucher_0", com.ekodex.manpowerhrms.R.layout.fragment_add_split_voucher);
      sKeys.put("layout/fragment_add_voucher_0", com.ekodex.manpowerhrms.R.layout.fragment_add_voucher);
      sKeys.put("layout/fragment_approved_leaves_0", com.ekodex.manpowerhrms.R.layout.fragment_approved_leaves);
      sKeys.put("layout/fragment_approved_vouchers_0", com.ekodex.manpowerhrms.R.layout.fragment_approved_vouchers);
      sKeys.put("layout/fragment_attendance_0", com.ekodex.manpowerhrms.R.layout.fragment_attendance);
      sKeys.put("layout/fragment_attendance_error_report_0", com.ekodex.manpowerhrms.R.layout.fragment_attendance_error_report);
      sKeys.put("layout/fragment_attendance_report_0", com.ekodex.manpowerhrms.R.layout.fragment_attendance_report);
      sKeys.put("layout/fragment_attendance_supervisor_0", com.ekodex.manpowerhrms.R.layout.fragment_attendance_supervisor);
      sKeys.put("layout/fragment_birthdays_0", com.ekodex.manpowerhrms.R.layout.fragment_birthdays);
      sKeys.put("layout/fragment_bulk_attendance_download_0", com.ekodex.manpowerhrms.R.layout.fragment_bulk_attendance_download);
      sKeys.put("layout/fragment_dashboard_0", com.ekodex.manpowerhrms.R.layout.fragment_dashboard);
      sKeys.put("layout/fragment_edit_job_post_0", com.ekodex.manpowerhrms.R.layout.fragment_edit_job_post);
      sKeys.put("layout/fragment_edit_my_profile_0", com.ekodex.manpowerhrms.R.layout.fragment_edit_my_profile);
      sKeys.put("layout/fragment_edit_voucher_0", com.ekodex.manpowerhrms.R.layout.fragment_edit_voucher);
      sKeys.put("layout/fragment_employee_details_0", com.ekodex.manpowerhrms.R.layout.fragment_employee_details);
      sKeys.put("layout/fragment_employee_directory_0", com.ekodex.manpowerhrms.R.layout.fragment_employee_directory);
      sKeys.put("layout/fragment_events_0", com.ekodex.manpowerhrms.R.layout.fragment_events);
      sKeys.put("layout/fragment_expenses_0", com.ekodex.manpowerhrms.R.layout.fragment_expenses);
      sKeys.put("layout/fragment_holidays_0", com.ekodex.manpowerhrms.R.layout.fragment_holidays);
      sKeys.put("layout/fragment_kyc_done_0", com.ekodex.manpowerhrms.R.layout.fragment_kyc_done);
      sKeys.put("layout/fragment_kyc_not_done_0", com.ekodex.manpowerhrms.R.layout.fragment_kyc_not_done);
      sKeys.put("layout/fragment_kyc_summary_management_0", com.ekodex.manpowerhrms.R.layout.fragment_kyc_summary_management);
      sKeys.put("layout/fragment_leave_details_0", com.ekodex.manpowerhrms.R.layout.fragment_leave_details);
      sKeys.put("layout/fragment_leaves_management_0", com.ekodex.manpowerhrms.R.layout.fragment_leaves_management);
      sKeys.put("layout/fragment_my_banks_0", com.ekodex.manpowerhrms.R.layout.fragment_my_banks);
      sKeys.put("layout/fragment_my_profile_0", com.ekodex.manpowerhrms.R.layout.fragment_my_profile);
      sKeys.put("layout/fragment_news_0", com.ekodex.manpowerhrms.R.layout.fragment_news);
      sKeys.put("layout/fragment_news_details_0", com.ekodex.manpowerhrms.R.layout.fragment_news_details);
      sKeys.put("layout/fragment_onboard1_0", com.ekodex.manpowerhrms.R.layout.fragment_onboard1);
      sKeys.put("layout/fragment_onboard2_0", com.ekodex.manpowerhrms.R.layout.fragment_onboard2);
      sKeys.put("layout/fragment_onboard3_0", com.ekodex.manpowerhrms.R.layout.fragment_onboard3);
      sKeys.put("layout/fragment_onboarding_0", com.ekodex.manpowerhrms.R.layout.fragment_onboarding);
      sKeys.put("layout/fragment_paid_vouchers_0", com.ekodex.manpowerhrms.R.layout.fragment_paid_vouchers);
      sKeys.put("layout/fragment_pending_leave_0", com.ekodex.manpowerhrms.R.layout.fragment_pending_leave);
      sKeys.put("layout/fragment_pending_vouchers_0", com.ekodex.manpowerhrms.R.layout.fragment_pending_vouchers);
      sKeys.put("layout/fragment_recruitment_0", com.ekodex.manpowerhrms.R.layout.fragment_recruitment);
      sKeys.put("layout/fragment_recruitment_applicant_document_0", com.ekodex.manpowerhrms.R.layout.fragment_recruitment_applicant_document);
      sKeys.put("layout/fragment_recruitment_applicants_0", com.ekodex.manpowerhrms.R.layout.fragment_recruitment_applicants);
      sKeys.put("layout/fragment_recruitment_create_post_0", com.ekodex.manpowerhrms.R.layout.fragment_recruitment_create_post);
      sKeys.put("layout/fragment_recruitment_post_details_0", com.ekodex.manpowerhrms.R.layout.fragment_recruitment_post_details);
      sKeys.put("layout/fragment_recruitment_posts_0", com.ekodex.manpowerhrms.R.layout.fragment_recruitment_posts);
      sKeys.put("layout/fragment_rejected_leaves_0", com.ekodex.manpowerhrms.R.layout.fragment_rejected_leaves);
      sKeys.put("layout/fragment_rejected_vouchers_0", com.ekodex.manpowerhrms.R.layout.fragment_rejected_vouchers);
      sKeys.put("layout/fragment_salary_slip_0", com.ekodex.manpowerhrms.R.layout.fragment_salary_slip);
      sKeys.put("layout/fragment_supervisor_attendanc_0", com.ekodex.manpowerhrms.R.layout.fragment_supervisor_attendanc);
      sKeys.put("layout/fragment_supervisor_attendance_0", com.ekodex.manpowerhrms.R.layout.fragment_supervisor_attendance);
      sKeys.put("layout/fragment_travel_accomodation_0", com.ekodex.manpowerhrms.R.layout.fragment_travel_accomodation);
      sKeys.put("layout/fragment_travel_management_0", com.ekodex.manpowerhrms.R.layout.fragment_travel_management);
      sKeys.put("layout/fragment_travel_report_0", com.ekodex.manpowerhrms.R.layout.fragment_travel_report);
      sKeys.put("layout/fragment_travel_request_0", com.ekodex.manpowerhrms.R.layout.fragment_travel_request);
      sKeys.put("layout/fragment_travel_schedule_0", com.ekodex.manpowerhrms.R.layout.fragment_travel_schedule);
      sKeys.put("layout/fragment_travelcreate_request_0", com.ekodex.manpowerhrms.R.layout.fragment_travelcreate_request);
      sKeys.put("layout/fragment_update_employee_bank_details_0", com.ekodex.manpowerhrms.R.layout.fragment_update_employee_bank_details);
      sKeys.put("layout/fragment_update_employee_document_0", com.ekodex.manpowerhrms.R.layout.fragment_update_employee_document);
      sKeys.put("layout/fragment_update_employee_personal_details_0", com.ekodex.manpowerhrms.R.layout.fragment_update_employee_personal_details);
      sKeys.put("layout/fragment_update_my_bank_0", com.ekodex.manpowerhrms.R.layout.fragment_update_my_bank);
      sKeys.put("layout/fragment_upload_work_order_0", com.ekodex.manpowerhrms.R.layout.fragment_upload_work_order);
      sKeys.put("layout/fragment_view_vouchers_0", com.ekodex.manpowerhrms.R.layout.fragment_view_vouchers);
      sKeys.put("layout/fragment_voucher_details_0", com.ekodex.manpowerhrms.R.layout.fragment_voucher_details);
      sKeys.put("layout/fragment_voucher_management_0", com.ekodex.manpowerhrms.R.layout.fragment_voucher_management);
      sKeys.put("layout/fragment_voucher_summary_0", com.ekodex.manpowerhrms.R.layout.fragment_voucher_summary);
      sKeys.put("layout/fragment_vouchers_0", com.ekodex.manpowerhrms.R.layout.fragment_vouchers);
      sKeys.put("layout/header_layout_0", com.ekodex.manpowerhrms.R.layout.header_layout);
    }
  }
}
