package com.ekodex.manpowerhrms.Recruitment_Post_Create;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import com.ekodex.manpowerhrms.R;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class RecruitmentCreatePostFragmentDirections {
  private RecruitmentCreatePostFragmentDirections() {
  }

  @NonNull
  public static ActionRecruitmentCreatePostFragmentToRecruitmentPostDetailsFragment actionRecruitmentCreatePostFragmentToRecruitmentPostDetailsFragment(
      @NonNull String lastid) {
    return new ActionRecruitmentCreatePostFragmentToRecruitmentPostDetailsFragment(lastid);
  }

  public static class ActionRecruitmentCreatePostFragmentToRecruitmentPostDetailsFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionRecruitmentCreatePostFragmentToRecruitmentPostDetailsFragment(
        @NonNull String lastid) {
      if (lastid == null) {
        throw new IllegalArgumentException("Argument \"lastid\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("lastid", lastid);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionRecruitmentCreatePostFragmentToRecruitmentPostDetailsFragment setLastid(
        @NonNull String lastid) {
      if (lastid == null) {
        throw new IllegalArgumentException("Argument \"lastid\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("lastid", lastid);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("lastid")) {
        String lastid = (String) arguments.get("lastid");
        __result.putString("lastid", lastid);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_recruitmentCreatePostFragment_to_recruitmentPostDetailsFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getLastid() {
      return (String) arguments.get("lastid");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionRecruitmentCreatePostFragmentToRecruitmentPostDetailsFragment that = (ActionRecruitmentCreatePostFragmentToRecruitmentPostDetailsFragment) object;
      if (arguments.containsKey("lastid") != that.arguments.containsKey("lastid")) {
        return false;
      }
      if (getLastid() != null ? !getLastid().equals(that.getLastid()) : that.getLastid() != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getLastid() != null ? getLastid().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionRecruitmentCreatePostFragmentToRecruitmentPostDetailsFragment(actionId=" + getActionId() + "){"
          + "lastid=" + getLastid()
          + "}";
    }
  }
}
