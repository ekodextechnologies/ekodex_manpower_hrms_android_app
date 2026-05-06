package com.ekodex.manpowerhrms;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;

public class RecruitmentPostDetailsFragmentDirections {
  private RecruitmentPostDetailsFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionRecruitmentPostDetailsFragmentToRecruitmentCreatePostFragment(
      ) {
    return new ActionOnlyNavDirections(R.id.action_recruitmentPostDetailsFragment_to_recruitmentCreatePostFragment);
  }
}
