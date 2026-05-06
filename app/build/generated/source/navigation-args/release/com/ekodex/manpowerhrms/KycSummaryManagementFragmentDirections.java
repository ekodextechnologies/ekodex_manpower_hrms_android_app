package com.ekodex.manpowerhrms;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class KycSummaryManagementFragmentDirections {
  private KycSummaryManagementFragmentDirections() {
  }

  @NonNull
  public static ActionKycSummaryManagementFragmentToEmployeeDetailsFragment actionKycSummaryManagementFragmentToEmployeeDetailsFragment(
      @NonNull String id) {
    return new ActionKycSummaryManagementFragmentToEmployeeDetailsFragment(id);
  }

  public static class ActionKycSummaryManagementFragmentToEmployeeDetailsFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionKycSummaryManagementFragmentToEmployeeDetailsFragment(@NonNull String id) {
      if (id == null) {
        throw new IllegalArgumentException("Argument \"id\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("id", id);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionKycSummaryManagementFragmentToEmployeeDetailsFragment setId(@NonNull String id) {
      if (id == null) {
        throw new IllegalArgumentException("Argument \"id\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("id", id);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("id")) {
        String id = (String) arguments.get("id");
        __result.putString("id", id);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_kycSummaryManagementFragment_to_employeeDetailsFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getId() {
      return (String) arguments.get("id");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionKycSummaryManagementFragmentToEmployeeDetailsFragment that = (ActionKycSummaryManagementFragmentToEmployeeDetailsFragment) object;
      if (arguments.containsKey("id") != that.arguments.containsKey("id")) {
        return false;
      }
      if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) {
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
      result = 31 * result + (getId() != null ? getId().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionKycSummaryManagementFragmentToEmployeeDetailsFragment(actionId=" + getActionId() + "){"
          + "id=" + getId()
          + "}";
    }
  }
}
