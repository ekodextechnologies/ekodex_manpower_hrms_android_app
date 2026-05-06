package com.ekodex.manpowerhrms.Travel_Management;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.ekodex.manpowerhrms.R;

public class TravelManagementFragmentDirections {
  private TravelManagementFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionTravelManagementFragmentToTravelcreateRequestFragment() {
    return new ActionOnlyNavDirections(R.id.action_travelManagementFragment_to_travelcreateRequestFragment);
  }
}
