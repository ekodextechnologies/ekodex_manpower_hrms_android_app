package com.ekodex.manpowerhrms;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;

public class AttendanceSupervisorFragmentDirections {
  private AttendanceSupervisorFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionAttendanceSupervisorFragmentToAddNewEmployeeFragment() {
    return new ActionOnlyNavDirections(R.id.action_attendanceSupervisorFragment_to_addNewEmployeeFragment);
  }

  @NonNull
  public static NavDirections actionAttendanceSupervisorFragmentSelf() {
    return new ActionOnlyNavDirections(R.id.action_attendanceSupervisorFragment_self);
  }
}
