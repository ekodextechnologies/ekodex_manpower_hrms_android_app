package com.ekodex.manpowerhrms;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;

public class TravelAccomodationFragmentDirections {
  private TravelAccomodationFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionTravelAccomodationFragmentToTravelManagementFragment() {
    return new ActionOnlyNavDirections(R.id.action_travelAccomodationFragment_to_travelManagementFragment);
  }
}
