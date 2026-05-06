package com.ekodex.manpowerhrms.Profile;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.ekodex.manpowerhrms.R;

public class EditMyProfileFragmentDirections {
  private EditMyProfileFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionEditMyProfileFragmentToDashboardFragment() {
    return new ActionOnlyNavDirections(R.id.action_editMyProfileFragment_to_dashboardFragment);
  }
}
