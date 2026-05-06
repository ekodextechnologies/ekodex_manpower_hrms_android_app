package com.ekodex.manpowerhrms;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;

public class SupervisorAttendanceDetailsFragmentDirections {
  private SupervisorAttendanceDetailsFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionSupervisorAttendanceDetailsFragmentToAttendanceReportFragment(
      ) {
    return new ActionOnlyNavDirections(R.id.action_supervisorAttendanceDetailsFragment_to_attendanceReportFragment);
  }
}
