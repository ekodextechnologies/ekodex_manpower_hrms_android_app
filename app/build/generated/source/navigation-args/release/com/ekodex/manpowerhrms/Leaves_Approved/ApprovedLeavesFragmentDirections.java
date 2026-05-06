package com.ekodex.manpowerhrms.Leaves_Approved;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.ekodex.manpowerhrms.R;

public class ApprovedLeavesFragmentDirections {
  private ApprovedLeavesFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionApprovedLeavesFragmentToAddLeaveFragment() {
    return new ActionOnlyNavDirections(R.id.action_approvedLeavesFragment_to_addLeaveFragment);
  }
}
