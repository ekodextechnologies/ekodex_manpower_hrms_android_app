package com.ekodex.manpowerhrms.Leaves_Pending;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.ekodex.manpowerhrms.R;

public class PendingLeaveFragmentDirections {
  private PendingLeaveFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionPendingLeaveFragmentToAddLeaveFragment() {
    return new ActionOnlyNavDirections(R.id.action_pendingLeaveFragment_to_addLeaveFragment);
  }
}
