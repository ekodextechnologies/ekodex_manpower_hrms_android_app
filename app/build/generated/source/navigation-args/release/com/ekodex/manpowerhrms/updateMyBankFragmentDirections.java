package com.ekodex.manpowerhrms;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;

public class updateMyBankFragmentDirections {
  private updateMyBankFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionUpdateMyBankFragmentToMyBanksFragment() {
    return new ActionOnlyNavDirections(R.id.action_updateMyBankFragment_to_myBanksFragment);
  }
}
