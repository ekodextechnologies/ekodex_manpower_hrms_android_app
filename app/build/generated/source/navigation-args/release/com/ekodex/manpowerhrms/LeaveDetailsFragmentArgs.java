package com.ekodex.manpowerhrms;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavArgs;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class LeaveDetailsFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private LeaveDetailsFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private LeaveDetailsFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static LeaveDetailsFragmentArgs fromBundle(@NonNull Bundle bundle) {
    LeaveDetailsFragmentArgs __result = new LeaveDetailsFragmentArgs();
    bundle.setClassLoader(LeaveDetailsFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("leave_id")) {
      String leaveId;
      leaveId = bundle.getString("leave_id");
      if (leaveId == null) {
        throw new IllegalArgumentException("Argument \"leave_id\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("leave_id", leaveId);
    } else {
      throw new IllegalArgumentException("Required argument \"leave_id\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static LeaveDetailsFragmentArgs fromSavedStateHandle(
      @NonNull SavedStateHandle savedStateHandle) {
    LeaveDetailsFragmentArgs __result = new LeaveDetailsFragmentArgs();
    if (savedStateHandle.contains("leave_id")) {
      String leaveId;
      leaveId = savedStateHandle.get("leave_id");
      if (leaveId == null) {
        throw new IllegalArgumentException("Argument \"leave_id\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("leave_id", leaveId);
    } else {
      throw new IllegalArgumentException("Required argument \"leave_id\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getLeaveId() {
    return (String) arguments.get("leave_id");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("leave_id")) {
      String leaveId = (String) arguments.get("leave_id");
      __result.putString("leave_id", leaveId);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("leave_id")) {
      String leaveId = (String) arguments.get("leave_id");
      __result.set("leave_id", leaveId);
    }
    return __result;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
        return true;
    }
    if (object == null || getClass() != object.getClass()) {
        return false;
    }
    LeaveDetailsFragmentArgs that = (LeaveDetailsFragmentArgs) object;
    if (arguments.containsKey("leave_id") != that.arguments.containsKey("leave_id")) {
      return false;
    }
    if (getLeaveId() != null ? !getLeaveId().equals(that.getLeaveId()) : that.getLeaveId() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getLeaveId() != null ? getLeaveId().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "LeaveDetailsFragmentArgs{"
        + "leaveId=" + getLeaveId()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull LeaveDetailsFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(@NonNull String leaveId) {
      if (leaveId == null) {
        throw new IllegalArgumentException("Argument \"leave_id\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("leave_id", leaveId);
    }

    @NonNull
    public LeaveDetailsFragmentArgs build() {
      LeaveDetailsFragmentArgs result = new LeaveDetailsFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setLeaveId(@NonNull String leaveId) {
      if (leaveId == null) {
        throw new IllegalArgumentException("Argument \"leave_id\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("leave_id", leaveId);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getLeaveId() {
      return (String) arguments.get("leave_id");
    }
  }
}
