package com.ekodex.manpowerhrms;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;

public class addNewBankFragmentDirections {
  private addNewBankFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionAddNewBanksFragmentToMyBanksFragment() {
    return new ActionOnlyNavDirections(R.id.action_addNewBanksFragment_to_myBanksFragment);
  }
}
