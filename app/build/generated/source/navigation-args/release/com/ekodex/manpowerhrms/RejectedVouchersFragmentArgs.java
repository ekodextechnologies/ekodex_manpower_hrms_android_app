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

public class RejectedVouchersFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private RejectedVouchersFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private RejectedVouchersFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static RejectedVouchersFragmentArgs fromBundle(@NonNull Bundle bundle) {
    RejectedVouchersFragmentArgs __result = new RejectedVouchersFragmentArgs();
    bundle.setClassLoader(RejectedVouchersFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("from_date")) {
      String fromDate;
      fromDate = bundle.getString("from_date");
      if (fromDate == null) {
        throw new IllegalArgumentException("Argument \"from_date\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("from_date", fromDate);
    } else {
      throw new IllegalArgumentException("Required argument \"from_date\" is missing and does not have an android:defaultValue");
    }
    if (bundle.containsKey("to_date")) {
      String toDate;
      toDate = bundle.getString("to_date");
      if (toDate == null) {
        throw new IllegalArgumentException("Argument \"to_date\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("to_date", toDate);
    } else {
      throw new IllegalArgumentException("Required argument \"to_date\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static RejectedVouchersFragmentArgs fromSavedStateHandle(
      @NonNull SavedStateHandle savedStateHandle) {
    RejectedVouchersFragmentArgs __result = new RejectedVouchersFragmentArgs();
    if (savedStateHandle.contains("from_date")) {
      String fromDate;
      fromDate = savedStateHandle.get("from_date");
      if (fromDate == null) {
        throw new IllegalArgumentException("Argument \"from_date\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("from_date", fromDate);
    } else {
      throw new IllegalArgumentException("Required argument \"from_date\" is missing and does not have an android:defaultValue");
    }
    if (savedStateHandle.contains("to_date")) {
      String toDate;
      toDate = savedStateHandle.get("to_date");
      if (toDate == null) {
        throw new IllegalArgumentException("Argument \"to_date\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("to_date", toDate);
    } else {
      throw new IllegalArgumentException("Required argument \"to_date\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getFromDate() {
    return (String) arguments.get("from_date");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getToDate() {
    return (String) arguments.get("to_date");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("from_date")) {
      String fromDate = (String) arguments.get("from_date");
      __result.putString("from_date", fromDate);
    }
    if (arguments.containsKey("to_date")) {
      String toDate = (String) arguments.get("to_date");
      __result.putString("to_date", toDate);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("from_date")) {
      String fromDate = (String) arguments.get("from_date");
      __result.set("from_date", fromDate);
    }
    if (arguments.containsKey("to_date")) {
      String toDate = (String) arguments.get("to_date");
      __result.set("to_date", toDate);
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
    RejectedVouchersFragmentArgs that = (RejectedVouchersFragmentArgs) object;
    if (arguments.containsKey("from_date") != that.arguments.containsKey("from_date")) {
      return false;
    }
    if (getFromDate() != null ? !getFromDate().equals(that.getFromDate()) : that.getFromDate() != null) {
      return false;
    }
    if (arguments.containsKey("to_date") != that.arguments.containsKey("to_date")) {
      return false;
    }
    if (getToDate() != null ? !getToDate().equals(that.getToDate()) : that.getToDate() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getFromDate() != null ? getFromDate().hashCode() : 0);
    result = 31 * result + (getToDate() != null ? getToDate().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "RejectedVouchersFragmentArgs{"
        + "fromDate=" + getFromDate()
        + ", toDate=" + getToDate()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull RejectedVouchersFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(@NonNull String fromDate, @NonNull String toDate) {
      if (fromDate == null) {
        throw new IllegalArgumentException("Argument \"from_date\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("from_date", fromDate);
      if (toDate == null) {
        throw new IllegalArgumentException("Argument \"to_date\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("to_date", toDate);
    }

    @NonNull
    public RejectedVouchersFragmentArgs build() {
      RejectedVouchersFragmentArgs result = new RejectedVouchersFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setFromDate(@NonNull String fromDate) {
      if (fromDate == null) {
        throw new IllegalArgumentException("Argument \"from_date\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("from_date", fromDate);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setToDate(@NonNull String toDate) {
      if (toDate == null) {
        throw new IllegalArgumentException("Argument \"to_date\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("to_date", toDate);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getFromDate() {
      return (String) arguments.get("from_date");
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getToDate() {
      return (String) arguments.get("to_date");
    }
  }
}
