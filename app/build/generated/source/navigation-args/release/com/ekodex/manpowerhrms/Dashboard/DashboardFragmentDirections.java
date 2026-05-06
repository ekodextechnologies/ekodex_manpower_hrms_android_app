package com.ekodex.manpowerhrms.Dashboard;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.ekodex.manpowerhrms.R;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class DashboardFragmentDirections {
  private DashboardFragmentDirections() {
  }

  @NonNull
  public static ActionDashboardFragmentToEmployeeDirectoryFragment actionDashboardFragmentToEmployeeDirectoryFragment(
      @NonNull String status, @NonNull String fromDate, @NonNull String toDate) {
    return new ActionDashboardFragmentToEmployeeDirectoryFragment(status, fromDate, toDate);
  }

  @NonNull
  public static ActionDashboardFragmentToVoucherManagementFragment actionDashboardFragmentToVoucherManagementFragment(
      @NonNull String fromDate, @NonNull String toDate) {
    return new ActionDashboardFragmentToVoucherManagementFragment(fromDate, toDate);
  }

  @NonNull
  public static NavDirections actionDashboardFragmentToAttendanceFragment() {
    return new ActionOnlyNavDirections(R.id.action_dashboardFragment_to_attendanceFragment);
  }

  @NonNull
  public static NavDirections actionDashboardFragmentToHolidaysFragment() {
    return new ActionOnlyNavDirections(R.id.action_dashboardFragment_to_holidaysFragment);
  }

  @NonNull
  public static ActionDashboardFragmentToLeavesManagementFragment actionDashboardFragmentToLeavesManagementFragment(
      @NonNull String fromDate, @NonNull String toDate) {
    return new ActionDashboardFragmentToLeavesManagementFragment(fromDate, toDate);
  }

  @NonNull
  public static NavDirections actionDashboardFragmentToBirthdaysFragment() {
    return new ActionOnlyNavDirections(R.id.action_dashboardFragment_to_birthdaysFragment);
  }

  @NonNull
  public static NavDirections actionDashboardFragmentToEventsFragment() {
    return new ActionOnlyNavDirections(R.id.action_dashboardFragment_to_eventsFragment);
  }

  @NonNull
  public static NavDirections actionDashboardFragmentToExpensesFragment() {
    return new ActionOnlyNavDirections(R.id.action_dashboardFragment_to_expensesFragment);
  }

  @NonNull
  public static ActionDashboardFragmentToKycSummaryManagementFragment actionDashboardFragmentToKycSummaryManagementFragment(
      @NonNull String type) {
    return new ActionDashboardFragmentToKycSummaryManagementFragment(type);
  }

  @NonNull
  public static NavDirections actionDashboardFragmentToNewsFragment() {
    return new ActionOnlyNavDirections(R.id.action_dashboardFragment_to_newsFragment);
  }

  @NonNull
  public static NavDirections actionDashboardFragmentToRecruitmentFragment() {
    return new ActionOnlyNavDirections(R.id.action_dashboardFragment_to_recruitmentFragment);
  }

  @NonNull
  public static NavDirections actionDashboardFragmentToTravelManagementFragment() {
    return new ActionOnlyNavDirections(R.id.action_dashboardFragment_to_travelManagementFragment);
  }

  @NonNull
  public static NavDirections actionDashboardFragmentToAttendanceSupervisorFragment() {
    return new ActionOnlyNavDirections(R.id.action_dashboardFragment_to_attendanceSupervisorFragment);
  }

  @NonNull
  public static ActionDashboardFragmentToSupervisorAttendanceFragment actionDashboardFragmentToSupervisorAttendanceFragment(
      @NonNull String status, @NonNull String from, @NonNull String to) {
    return new ActionDashboardFragmentToSupervisorAttendanceFragment(status, from, to);
  }

  @NonNull
  public static ActionDashboardFragmentToSupervisorAttendanceDetailsFragment actionDashboardFragmentToSupervisorAttendanceDetailsFragment(
      @NonNull String fromDate, @NonNull String toDate, @NonNull String empCode,
      @NonNull String status) {
    return new ActionDashboardFragmentToSupervisorAttendanceDetailsFragment(fromDate, toDate, empCode, status);
  }

  public static class ActionDashboardFragmentToEmployeeDirectoryFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionDashboardFragmentToEmployeeDirectoryFragment(@NonNull String status,
        @NonNull String fromDate, @NonNull String toDate) {
      if (status == null) {
        throw new IllegalArgumentException("Argument \"status\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("status", status);
      if (fromDate == null) {
        throw new IllegalArgumentException("Argument \"from_date\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("from_date", fromDate);
      if (toDate == null) {
        throw new IllegalArgumentException("Argument \"to_date\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("to_date", toDate);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionDashboardFragmentToEmployeeDirectoryFragment setStatus(@NonNull String status) {
      if (status == null) {
        throw new IllegalArgumentException("Argument \"status\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("status", status);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionDashboardFragmentToEmployeeDirectoryFragment setFromDate(
        @NonNull String fromDate) {
      if (fromDate == null) {
        throw new IllegalArgumentException("Argument \"from_date\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("from_date", fromDate);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionDashboardFragmentToEmployeeDirectoryFragment setToDate(@NonNull String toDate) {
      if (toDate == null) {
        throw new IllegalArgumentException("Argument \"to_date\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("to_date", toDate);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("status")) {
        String status = (String) arguments.get("status");
        __result.putString("status", status);
      }
      if (arguments.containsKey("from_date")) {
        String fromDate = (String) arguments.get("from_date");
        __result.putString("from_date", fromDate);
      }
      if (arguments.containsKey("to_date")) {
        String toDate = (String) arguments.get("to_date");
        __result.putString("to_date", toDate);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_dashboardFragment_to_employeeDirectoryFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getStatus() {
      return (String) arguments.get("status");
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getFromDate() {
      return (String) arguments.get("from_date");
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getToDate() {
      return (String) arguments.get("to_date");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionDashboardFragmentToEmployeeDirectoryFragment that = (ActionDashboardFragmentToEmployeeDirectoryFragment) object;
      if (arguments.containsKey("status") != that.arguments.containsKey("status")) {
        return false;
      }
      if (getStatus() != null ? !getStatus().equals(that.getStatus()) : that.getStatus() != null) {
        return false;
      }
      if (arguments.containsKey("from_date") != that.arguments.containsKey("from_date")) {
        return false;
      }
      if (getFromDate() != null ? !getFromDate().equals(that.getFromDate()) : that.getFromDate() != null) {
        return false;
      }
      if (arguments.containsKey("to_date") != that.arguments.containsKey("to_date")) {
        return false;
      }
      if (getToDate() != null ? !getToDate().equals(that.getToDate()) : that.getToDate() != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
      result = 31 * result + (getFromDate() != null ? getFromDate().hashCode() : 0);
      result = 31 * result + (getToDate() != null ? getToDate().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionDashboardFragmentToEmployeeDirectoryFragment(actionId=" + getActionId() + "){"
          + "status=" + getStatus()
          + ", fromDate=" + getFromDate()
          + ", toDate=" + getToDate()
          + "}";
    }
  }

  public static class ActionDashboardFragmentToVoucherManagementFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionDashboardFragmentToVoucherManagementFragment(@NonNull String fromDate,
        @NonNull String toDate) {
      if (fromDate == null) {
        throw new IllegalArgumentException("Argument \"from_date\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("from_date", fromDate);
      if (toDate == null) {
        throw new IllegalArgumentException("Argument \"to_date\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("to_date", toDate);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionDashboardFragmentToVoucherManagementFragment setFromDate(
        @NonNull String fromDate) {
      if (fromDate == null) {
        throw new IllegalArgumentException("Argument \"from_date\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("from_date", fromDate);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionDashboardFragmentToVoucherManagementFragment setToDate(@NonNull String toDate) {
      if (toDate == null) {
        throw new IllegalArgumentException("Argument \"to_date\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("to_date", toDate);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionDashboardFragmentToVoucherManagementFragment setOpenTab(int openTab) {
      this.arguments.put("openTab", openTab);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("from_date")) {
        String fromDate = (String) arguments.get("from_date");
        __result.putString("from_date", fromDate);
      }
      if (arguments.containsKey("to_date")) {
        String toDate = (String) arguments.get("to_date");
        __result.putString("to_date", toDate);
      }
      if (arguments.containsKey("openTab")) {
        int openTab = (int) arguments.get("openTab");
        __result.putInt("openTab", openTab);
      } else {
        __result.putInt("openTab", 0);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_dashboardFragment_to_voucherManagementFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getFromDate() {
      return (String) arguments.get("from_date");
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getToDate() {
      return (String) arguments.get("to_date");
    }

    @SuppressWarnings("unchecked")
    public int getOpenTab() {
      return (int) arguments.get("openTab");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionDashboardFragmentToVoucherManagementFragment that = (ActionDashboardFragmentToVoucherManagementFragment) object;
      if (arguments.containsKey("from_date") != that.arguments.containsKey("from_date")) {
        return false;
      }
      if (getFromDate() != null ? !getFromDate().equals(that.getFromDate()) : that.getFromDate() != null) {
        return false;
      }
      if (arguments.containsKey("to_date") != that.arguments.containsKey("to_date")) {
        return false;
      }
      if (getToDate() != null ? !getToDate().equals(that.getToDate()) : that.getToDate() != null) {
        return false;
      }
      if (arguments.containsKey("openTab") != that.arguments.containsKey("openTab")) {
        return false;
      }
      if (getOpenTab() != that.getOpenTab()) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getFromDate() != null ? getFromDate().hashCode() : 0);
      result = 31 * result + (getToDate() != null ? getToDate().hashCode() : 0);
      result = 31 * result + getOpenTab();
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionDashboardFragmentToVoucherManagementFragment(actionId=" + getActionId() + "){"
          + "fromDate=" + getFromDate()
          + ", toDate=" + getToDate()
          + ", openTab=" + getOpenTab()
          + "}";
    }
  }

  public static class ActionDashboardFragmentToLeavesManagementFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionDashboardFragmentToLeavesManagementFragment(@NonNull String fromDate,
        @NonNull String toDate) {
      if (fromDate == null) {
        throw new IllegalArgumentException("Argument \"from_date\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("from_date", fromDate);
      if (toDate == null) {
        throw new IllegalArgumentException("Argument \"to_date\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("to_date", toDate);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionDashboardFragmentToLeavesManagementFragment setFromDate(@NonNull String fromDate) {
      if (fromDate == null) {
        throw new IllegalArgumentException("Argument \"from_date\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("from_date", fromDate);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionDashboardFragmentToLeavesManagementFragment setToDate(@NonNull String toDate) {
      if (toDate == null) {
        throw new IllegalArgumentException("Argument \"to_date\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("to_date", toDate);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionDashboardFragmentToLeavesManagementFragment setOpenTab(int openTab) {
      this.arguments.put("openTab", openTab);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("from_date")) {
        String fromDate = (String) arguments.get("from_date");
        __result.putString("from_date", fromDate);
      }
      if (arguments.containsKey("to_date")) {
        String toDate = (String) arguments.get("to_date");
        __result.putString("to_date", toDate);
      }
      if (arguments.containsKey("openTab")) {
        int openTab = (int) arguments.get("openTab");
        __result.putInt("openTab", openTab);
      } else {
        __result.putInt("openTab", 0);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_dashboardFragment_to_leavesManagementFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getFromDate() {
      return (String) arguments.get("from_date");
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getToDate() {
      return (String) arguments.get("to_date");
    }

    @SuppressWarnings("unchecked")
    public int getOpenTab() {
      return (int) arguments.get("openTab");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionDashboardFragmentToLeavesManagementFragment that = (ActionDashboardFragmentToLeavesManagementFragment) object;
      if (arguments.containsKey("from_date") != that.arguments.containsKey("from_date")) {
        return false;
      }
      if (getFromDate() != null ? !getFromDate().equals(that.getFromDate()) : that.getFromDate() != null) {
        return false;
      }
      if (arguments.containsKey("to_date") != that.arguments.containsKey("to_date")) {
        return false;
      }
      if (getToDate() != null ? !getToDate().equals(that.getToDate()) : that.getToDate() != null) {
        return false;
      }
      if (arguments.containsKey("openTab") != that.arguments.containsKey("openTab")) {
        return false;
      }
      if (getOpenTab() != that.getOpenTab()) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getFromDate() != null ? getFromDate().hashCode() : 0);
      result = 31 * result + (getToDate() != null ? getToDate().hashCode() : 0);
      result = 31 * result + getOpenTab();
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionDashboardFragmentToLeavesManagementFragment(actionId=" + getActionId() + "){"
          + "fromDate=" + getFromDate()
          + ", toDate=" + getToDate()
          + ", openTab=" + getOpenTab()
          + "}";
    }
  }

  public static class ActionDashboardFragmentToKycSummaryManagementFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionDashboardFragmentToKycSummaryManagementFragment(@NonNull String type) {
      if (type == null) {
        throw new IllegalArgumentException("Argument \"type\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("type", type);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionDashboardFragmentToKycSummaryManagementFragment setType(@NonNull String type) {
      if (type == null) {
        throw new IllegalArgumentException("Argument \"type\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("type", type);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("type")) {
        String type = (String) arguments.get("type");
        __result.putString("type", type);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_dashboardFragment_to_kycSummaryManagementFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getType() {
      return (String) arguments.get("type");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionDashboardFragmentToKycSummaryManagementFragment that = (ActionDashboardFragmentToKycSummaryManagementFragment) object;
      if (arguments.containsKey("type") != that.arguments.containsKey("type")) {
        return false;
      }
      if (getType() != null ? !getType().equals(that.getType()) : that.getType() != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getType() != null ? getType().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionDashboardFragmentToKycSummaryManagementFragment(actionId=" + getActionId() + "){"
          + "type=" + getType()
          + "}";
    }
  }

  public static class ActionDashboardFragmentToSupervisorAttendanceFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionDashboardFragmentToSupervisorAttendanceFragment(@NonNull String status,
        @NonNull String from, @NonNull String to) {
      if (status == null) {
        throw new IllegalArgumentException("Argument \"status\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("status", status);
      if (from == null) {
        throw new IllegalArgumentException("Argument \"from\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("from", from);
      if (to == null) {
        throw new IllegalArgumentException("Argument \"to\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("to", to);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionDashboardFragmentToSupervisorAttendanceFragment setStatus(@NonNull String status) {
      if (status == null) {
        throw new IllegalArgumentException("Argument \"status\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("status", status);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionDashboardFragmentToSupervisorAttendanceFragment setFrom(@NonNull String from) {
      if (from == null) {
        throw new IllegalArgumentException("Argument \"from\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("from", from);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionDashboardFragmentToSupervisorAttendanceFragment setTo(@NonNull String to) {
      if (to == null) {
        throw new IllegalArgumentException("Argument \"to\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("to", to);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("status")) {
        String status = (String) arguments.get("status");
        __result.putString("status", status);
      }
      if (arguments.containsKey("from")) {
        String from = (String) arguments.get("from");
        __result.putString("from", from);
      }
      if (arguments.containsKey("to")) {
        String to = (String) arguments.get("to");
        __result.putString("to", to);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_dashboardFragment_to_supervisorAttendanceFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getStatus() {
      return (String) arguments.get("status");
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getFrom() {
      return (String) arguments.get("from");
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getTo() {
      return (String) arguments.get("to");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionDashboardFragmentToSupervisorAttendanceFragment that = (ActionDashboardFragmentToSupervisorAttendanceFragment) object;
      if (arguments.containsKey("status") != that.arguments.containsKey("status")) {
        return false;
      }
      if (getStatus() != null ? !getStatus().equals(that.getStatus()) : that.getStatus() != null) {
        return false;
      }
      if (arguments.containsKey("from") != that.arguments.containsKey("from")) {
        return false;
      }
      if (getFrom() != null ? !getFrom().equals(that.getFrom()) : that.getFrom() != null) {
        return false;
      }
      if (arguments.containsKey("to") != that.arguments.containsKey("to")) {
        return false;
      }
      if (getTo() != null ? !getTo().equals(that.getTo()) : that.getTo() != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
      result = 31 * result + (getFrom() != null ? getFrom().hashCode() : 0);
      result = 31 * result + (getTo() != null ? getTo().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionDashboardFragmentToSupervisorAttendanceFragment(actionId=" + getActionId() + "){"
          + "status=" + getStatus()
          + ", from=" + getFrom()
          + ", to=" + getTo()
          + "}";
    }
  }

  public static class ActionDashboardFragmentToSupervisorAttendanceDetailsFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionDashboardFragmentToSupervisorAttendanceDetailsFragment(@NonNull String fromDate,
        @NonNull String toDate, @NonNull String empCode, @NonNull String status) {
      if (fromDate == null) {
        throw new IllegalArgumentException("Argument \"from_date\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("from_date", fromDate);
      if (toDate == null) {
        throw new IllegalArgumentException("Argument \"to_date\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("to_date", toDate);
      if (empCode == null) {
        throw new IllegalArgumentException("Argument \"emp_code\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("emp_code", empCode);
      if (status == null) {
        throw new IllegalArgumentException("Argument \"status\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("status", status);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionDashboardFragmentToSupervisorAttendanceDetailsFragment setFromDate(
        @NonNull String fromDate) {
      if (fromDate == null) {
        throw new IllegalArgumentException("Argument \"from_date\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("from_date", fromDate);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionDashboardFragmentToSupervisorAttendanceDetailsFragment setToDate(
        @NonNull String toDate) {
      if (toDate == null) {
        throw new IllegalArgumentException("Argument \"to_date\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("to_date", toDate);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionDashboardFragmentToSupervisorAttendanceDetailsFragment setEmpCode(
        @NonNull String empCode) {
      if (empCode == null) {
        throw new IllegalArgumentException("Argument \"emp_code\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("emp_code", empCode);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionDashboardFragmentToSupervisorAttendanceDetailsFragment setStatus(
        @NonNull String status) {
      if (status == null) {
        throw new IllegalArgumentException("Argument \"status\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("status", status);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("from_date")) {
        String fromDate = (String) arguments.get("from_date");
        __result.putString("from_date", fromDate);
      }
      if (arguments.containsKey("to_date")) {
        String toDate = (String) arguments.get("to_date");
        __result.putString("to_date", toDate);
      }
      if (arguments.containsKey("emp_code")) {
        String empCode = (String) arguments.get("emp_code");
        __result.putString("emp_code", empCode);
      }
      if (arguments.containsKey("status")) {
        String status = (String) arguments.get("status");
        __result.putString("status", status);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_dashboardFragment_to_supervisorAttendanceDetailsFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getFromDate() {
      return (String) arguments.get("from_date");
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getToDate() {
      return (String) arguments.get("to_date");
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getEmpCode() {
      return (String) arguments.get("emp_code");
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getStatus() {
      return (String) arguments.get("status");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionDashboardFragmentToSupervisorAttendanceDetailsFragment that = (ActionDashboardFragmentToSupervisorAttendanceDetailsFragment) object;
      if (arguments.containsKey("from_date") != that.arguments.containsKey("from_date")) {
        return false;
      }
      if (getFromDate() != null ? !getFromDate().equals(that.getFromDate()) : that.getFromDate() != null) {
        return false;
      }
      if (arguments.containsKey("to_date") != that.arguments.containsKey("to_date")) {
        return false;
      }
      if (getToDate() != null ? !getToDate().equals(that.getToDate()) : that.getToDate() != null) {
        return false;
      }
      if (arguments.containsKey("emp_code") != that.arguments.containsKey("emp_code")) {
        return false;
      }
      if (getEmpCode() != null ? !getEmpCode().equals(that.getEmpCode()) : that.getEmpCode() != null) {
        return false;
      }
      if (arguments.containsKey("status") != that.arguments.containsKey("status")) {
        return false;
      }
      if (getStatus() != null ? !getStatus().equals(that.getStatus()) : that.getStatus() != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getFromDate() != null ? getFromDate().hashCode() : 0);
      result = 31 * result + (getToDate() != null ? getToDate().hashCode() : 0);
      result = 31 * result + (getEmpCode() != null ? getEmpCode().hashCode() : 0);
      result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionDashboardFragmentToSupervisorAttendanceDetailsFragment(actionId=" + getActionId() + "){"
          + "fromDate=" + getFromDate()
          + ", toDate=" + getToDate()
          + ", empCode=" + getEmpCode()
          + ", status=" + getStatus()
          + "}";
    }
  }
}
