package com.ekodex.manpowerhrms;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;

public class AddNewEmployeeFragmentDirections {
  private AddNewEmployeeFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionAddNewEmployeeFragmentToAttendanceSupervisorFragment() {
    return new ActionOnlyNavDirections(R.id.action_addNewEmployeeFragment_to_attendanceSupervisorFragment);
  }
}
