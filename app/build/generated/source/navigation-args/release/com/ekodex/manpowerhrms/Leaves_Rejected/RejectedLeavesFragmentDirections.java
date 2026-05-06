package com.ekodex.manpowerhrms.Leaves_Rejected;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.ekodex.manpowerhrms.R;

public class RejectedLeavesFragmentDirections {
  private RejectedLeavesFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionRejectedLeavesFragmentToAddLeaveFragment() {
    return new ActionOnlyNavDirections(R.id.action_rejectedLeavesFragment_to_addLeaveFragment);
  }
}
