package com.ekodex.manpowerhrms.Profile;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.ekodex.manpowerhrms.R;

public class MyProfileFragmentDirections {
  private MyProfileFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionMyProfileFragmentToEditMyProfileFragment() {
    return new ActionOnlyNavDirections(R.id.action_myProfileFragment_to_editMyProfileFragment);
  }

  @NonNull
  public static NavDirections actionMyProfileFragmentToMyBanksFragment() {
    return new ActionOnlyNavDirections(R.id.action_myProfileFragment_to_myBanksFragment);
  }
}
