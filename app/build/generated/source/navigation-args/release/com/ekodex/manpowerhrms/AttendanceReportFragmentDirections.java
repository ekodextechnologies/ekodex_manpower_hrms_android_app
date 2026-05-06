package com.ekodex.manpowerhrms;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class AttendanceReportFragmentDirections {
  private AttendanceReportFragmentDirections() {
  }

  @NonNull
  public static ActionAttendanceReportFragmentToSupervisorAttendanceDetailsFragment actionAttendanceReportFragmentToSupervisorAttendanceDetailsFragment(
      @NonNull String fromDate, @NonNull String toDate, @NonNull String empCode,
      @NonNull String status) {
    return new ActionAttendanceReportFragmentToSupervisorAttendanceDetailsFragment(fromDate, toDate, empCode, status);
  }

  public static class ActionAttendanceReportFragmentToSupervisorAttendanceDetailsFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionAttendanceReportFragmentToSupervisorAttendanceDetailsFragment(
        @NonNull String fromDate, @NonNull String toDate, @NonNull String empCode,
        @NonNull String status) {
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
    public ActionAttendanceReportFragmentToSupervisorAttendanceDetailsFragment setFromDate(
        @NonNull String fromDate) {
      if (fromDate == null) {
        throw new IllegalArgumentException("Argument \"from_date\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("from_date", fromDate);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionAttendanceReportFragmentToSupervisorAttendanceDetailsFragment setToDate(
        @NonNull String toDate) {
      if (toDate == null) {
        throw new IllegalArgumentException("Argument \"to_date\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("to_date", toDate);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionAttendanceReportFragmentToSupervisorAttendanceDetailsFragment setEmpCode(
        @NonNull String empCode) {
      if (empCode == null) {
        throw new IllegalArgumentException("Argument \"emp_code\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("emp_code", empCode);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionAttendanceReportFragmentToSupervisorAttendanceDetailsFragment setStatus(
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
      return R.id.action_attendanceReportFragment_to_supervisorAttendanceDetailsFragment;
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
      ActionAttendanceReportFragmentToSupervisorAttendanceDetailsFragment that = (ActionAttendanceReportFragmentToSupervisorAttendanceDetailsFragment) object;
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
      return "ActionAttendanceReportFragmentToSupervisorAttendanceDetailsFragment(actionId=" + getActionId() + "){"
          + "fromDate=" + getFromDate()
          + ", toDate=" + getToDate()
          + ", empCode=" + getEmpCode()
          + ", status=" + getStatus()
          + "}";
    }
  }
}
