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

public class RecruitmentPostDetailsFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private RecruitmentPostDetailsFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private RecruitmentPostDetailsFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static RecruitmentPostDetailsFragmentArgs fromBundle(@NonNull Bundle bundle) {
    RecruitmentPostDetailsFragmentArgs __result = new RecruitmentPostDetailsFragmentArgs();
    bundle.setClassLoader(RecruitmentPostDetailsFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("lastid")) {
      String lastid;
      lastid = bundle.getString("lastid");
      if (lastid == null) {
        throw new IllegalArgumentException("Argument \"lastid\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("lastid", lastid);
    } else {
      throw new IllegalArgumentException("Required argument \"lastid\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static RecruitmentPostDetailsFragmentArgs fromSavedStateHandle(
      @NonNull SavedStateHandle savedStateHandle) {
    RecruitmentPostDetailsFragmentArgs __result = new RecruitmentPostDetailsFragmentArgs();
    if (savedStateHandle.contains("lastid")) {
      String lastid;
      lastid = savedStateHandle.get("lastid");
      if (lastid == null) {
        throw new IllegalArgumentException("Argument \"lastid\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("lastid", lastid);
    } else {
      throw new IllegalArgumentException("Required argument \"lastid\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getLastid() {
    return (String) arguments.get("lastid");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("lastid")) {
      String lastid = (String) arguments.get("lastid");
      __result.putString("lastid", lastid);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("lastid")) {
      String lastid = (String) arguments.get("lastid");
      __result.set("lastid", lastid);
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
    RecruitmentPostDetailsFragmentArgs that = (RecruitmentPostDetailsFragmentArgs) object;
    if (arguments.containsKey("lastid") != that.arguments.containsKey("lastid")) {
      return false;
    }
    if (getLastid() != null ? !getLastid().equals(that.getLastid()) : that.getLastid() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getLastid() != null ? getLastid().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "RecruitmentPostDetailsFragmentArgs{"
        + "lastid=" + getLastid()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull RecruitmentPostDetailsFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(@NonNull String lastid) {
      if (lastid == null) {
        throw new IllegalArgumentException("Argument \"lastid\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("lastid", lastid);
    }

    @NonNull
    public RecruitmentPostDetailsFragmentArgs build() {
      RecruitmentPostDetailsFragmentArgs result = new RecruitmentPostDetailsFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setLastid(@NonNull String lastid) {
      if (lastid == null) {
        throw new IllegalArgumentException("Argument \"lastid\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("lastid", lastid);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getLastid() {
      return (String) arguments.get("lastid");
    }
  }
}
