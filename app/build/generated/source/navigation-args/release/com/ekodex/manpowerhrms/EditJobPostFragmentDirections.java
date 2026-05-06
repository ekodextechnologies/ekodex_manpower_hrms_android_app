package com.ekodex.manpowerhrms;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;

public class EditJobPostFragmentDirections {
  private EditJobPostFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionEditJobPostFragmentToRecruitmentFragment() {
    return new ActionOnlyNavDirections(R.id.action_editJobPostFragment_to_recruitmentFragment);
  }
}
