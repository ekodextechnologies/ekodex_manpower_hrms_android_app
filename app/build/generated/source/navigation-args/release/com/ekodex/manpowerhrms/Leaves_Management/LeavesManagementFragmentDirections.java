package com.ekodex.manpowerhrms.Leaves_Management;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.ekodex.manpowerhrms.R;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class LeavesManagementFragmentDirections {
  private LeavesManagementFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionLeavesManagementFragmentToAddLeaveFragment2() {
    return new ActionOnlyNavDirections(R.id.action_leavesManagementFragment_to_addLeaveFragment2);
  }

  @NonNull
  public static ActionLeavesManagementFragmentToLeavesDetailFragment actionLeavesManagementFragmentToLeavesDetailFragment(
      @NonNull String leaveId) {
    return new ActionLeavesManagementFragmentToLeavesDetailFragment(leaveId);
  }

  public static class ActionLeavesManagementFragmentToLeavesDetailFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionLeavesManagementFragmentToLeavesDetailFragment(@NonNull String leaveId) {
      if (leaveId == null) {
        throw new IllegalArgumentException("Argument \"leave_id\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("leave_id", leaveId);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionLeavesManagementFragmentToLeavesDetailFragment setLeaveId(
        @NonNull String leaveId) {
      if (leaveId == null) {
        throw new IllegalArgumentException("Argument \"leave_id\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("leave_id", leaveId);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("leave_id")) {
        String leaveId = (String) arguments.get("leave_id");
        __result.putString("leave_id", leaveId);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_leavesManagementFragment_to_leavesDetailFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getLeaveId() {
      return (String) arguments.get("leave_id");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionLeavesManagementFragmentToLeavesDetailFragment that = (ActionLeavesManagementFragmentToLeavesDetailFragment) object;
      if (arguments.containsKey("leave_id") != that.arguments.containsKey("leave_id")) {
        return false;
      }
      if (getLeaveId() != null ? !getLeaveId().equals(that.getLeaveId()) : that.getLeaveId() != null) {
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
      result = 31 * result + (getLeaveId() != null ? getLeaveId().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionLeavesManagementFragmentToLeavesDetailFragment(actionId=" + getActionId() + "){"
          + "leaveId=" + getLeaveId()
          + "}";
    }
  }
}
