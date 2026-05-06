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

public class AttendanceErrorReportFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private AttendanceErrorReportFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private AttendanceErrorReportFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static AttendanceErrorReportFragmentArgs fromBundle(@NonNull Bundle bundle) {
    AttendanceErrorReportFragmentArgs __result = new AttendanceErrorReportFragmentArgs();
    bundle.setClassLoader(AttendanceErrorReportFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("status")) {
      String status;
      status = bundle.getString("status");
      if (status == null) {
        throw new IllegalArgumentException("Argument \"status\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("status", status);
    } else {
      throw new IllegalArgumentException("Required argument \"status\" is missing and does not have an android:defaultValue");
    }
    if (bundle.containsKey("from")) {
      String from;
      from = bundle.getString("from");
      if (from == null) {
        throw new IllegalArgumentException("Argument \"from\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("from", from);
    } else {
      throw new IllegalArgumentException("Required argument \"from\" is missing and does not have an android:defaultValue");
    }
    if (bundle.containsKey("to")) {
      String to;
      to = bundle.getString("to");
      if (to == null) {
        throw new IllegalArgumentException("Argument \"to\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("to", to);
    } else {
      throw new IllegalArgumentException("Required argument \"to\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static AttendanceErrorReportFragmentArgs fromSavedStateHandle(
      @NonNull SavedStateHandle savedStateHandle) {
    AttendanceErrorReportFragmentArgs __result = new AttendanceErrorReportFragmentArgs();
    if (savedStateHandle.contains("status")) {
      String status;
      status = savedStateHandle.get("status");
      if (status == null) {
        throw new IllegalArgumentException("Argument \"status\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("status", status);
    } else {
      throw new IllegalArgumentException("Required argument \"status\" is missing and does not have an android:defaultValue");
    }
    if (savedStateHandle.contains("from")) {
      String from;
      from = savedStateHandle.get("from");
      if (from == null) {
        throw new IllegalArgumentException("Argument \"from\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("from", from);
    } else {
      throw new IllegalArgumentException("Required argument \"from\" is missing and does not have an android:defaultValue");
    }
    if (savedStateHandle.contains("to")) {
      String to;
      to = savedStateHandle.get("to");
      if (to == null) {
        throw new IllegalArgumentException("Argument \"to\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("to", to);
    } else {
      throw new IllegalArgumentException("Required argument \"to\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getStatus() {
    return (String) arguments.get("status");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getFrom() {
    return (String) arguments.get("from");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getTo() {
    return (String) arguments.get("to");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("status")) {
      String status = (String) arguments.get("status");
      __result.putString("status", status);
    }
    if (arguments.containsKey("from")) {
      String from = (String) arguments.get("from");
      __result.putString("from", from);
    }
    if (arguments.containsKey("to")) {
      String to = (String) arguments.get("to");
      __result.putString("to", to);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("status")) {
      String status = (String) arguments.get("status");
      __result.set("status", status);
    }
    if (arguments.containsKey("from")) {
      String from = (String) arguments.get("from");
      __result.set("from", from);
    }
    if (arguments.containsKey("to")) {
      String to = (String) arguments.get("to");
      __result.set("to", to);
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
    AttendanceErrorReportFragmentArgs that = (AttendanceErrorReportFragmentArgs) object;
    if (arguments.containsKey("status") != that.arguments.containsKey("status")) {
      return false;
    }
    if (getStatus() != null ? !getStatus().equals(that.getStatus()) : that.getStatus() != null) {
      return false;
    }
    if (arguments.containsKey("from") != that.arguments.containsKey("from")) {
      return false;
    }
    if (getFrom() != null ? !getFrom().equals(that.getFrom()) : that.getFrom() != null) {
      return false;
    }
    if (arguments.containsKey("to") != that.arguments.containsKey("to")) {
      return false;
    }
    if (getTo() != null ? !getTo().equals(that.getTo()) : that.getTo() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
    result = 31 * result + (getFrom() != null ? getFrom().hashCode() : 0);
    result = 31 * result + (getTo() != null ? getTo().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "AttendanceErrorReportFragmentArgs{"
        + "status=" + getStatus()
        + ", from=" + getFrom()
        + ", to=" + getTo()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull AttendanceErrorReportFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(@NonNull String status, @NonNull String from, @NonNull String to) {
      if (status == null) {
        throw new IllegalArgumentException("Argument \"status\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("status", status);
      if (from == null) {
        throw new IllegalArgumentException("Argument \"from\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("from", from);
      if (to == null) {
        throw new IllegalArgumentException("Argument \"to\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("to", to);
    }

    @NonNull
    public AttendanceErrorReportFragmentArgs build() {
      AttendanceErrorReportFragmentArgs result = new AttendanceErrorReportFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setStatus(@NonNull String status) {
      if (status == null) {
        throw new IllegalArgumentException("Argument \"status\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("status", status);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setFrom(@NonNull String from) {
      if (from == null) {
        throw new IllegalArgumentException("Argument \"from\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("from", from);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setTo(@NonNull String to) {
      if (to == null) {
        throw new IllegalArgumentException("Argument \"to\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("to", to);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getStatus() {
      return (String) arguments.get("status");
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getFrom() {
      return (String) arguments.get("from");
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getTo() {
      return (String) arguments.get("to");
    }
  }
}
