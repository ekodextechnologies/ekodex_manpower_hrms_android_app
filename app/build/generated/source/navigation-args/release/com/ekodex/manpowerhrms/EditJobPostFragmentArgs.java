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

public class EditJobPostFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private EditJobPostFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private EditJobPostFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static EditJobPostFragmentArgs fromBundle(@NonNull Bundle bundle) {
    EditJobPostFragmentArgs __result = new EditJobPostFragmentArgs();
    bundle.setClassLoader(EditJobPostFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("postId")) {
      String postId;
      postId = bundle.getString("postId");
      if (postId == null) {
        throw new IllegalArgumentException("Argument \"postId\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("postId", postId);
    } else {
      throw new IllegalArgumentException("Required argument \"postId\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static EditJobPostFragmentArgs fromSavedStateHandle(
      @NonNull SavedStateHandle savedStateHandle) {
    EditJobPostFragmentArgs __result = new EditJobPostFragmentArgs();
    if (savedStateHandle.contains("postId")) {
      String postId;
      postId = savedStateHandle.get("postId");
      if (postId == null) {
        throw new IllegalArgumentException("Argument \"postId\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("postId", postId);
    } else {
      throw new IllegalArgumentException("Required argument \"postId\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getPostId() {
    return (String) arguments.get("postId");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("postId")) {
      String postId = (String) arguments.get("postId");
      __result.putString("postId", postId);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("postId")) {
      String postId = (String) arguments.get("postId");
      __result.set("postId", postId);
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
    EditJobPostFragmentArgs that = (EditJobPostFragmentArgs) object;
    if (arguments.containsKey("postId") != that.arguments.containsKey("postId")) {
      return false;
    }
    if (getPostId() != null ? !getPostId().equals(that.getPostId()) : that.getPostId() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getPostId() != null ? getPostId().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "EditJobPostFragmentArgs{"
        + "postId=" + getPostId()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull EditJobPostFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(@NonNull String postId) {
      if (postId == null) {
        throw new IllegalArgumentException("Argument \"postId\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("postId", postId);
    }

    @NonNull
    public EditJobPostFragmentArgs build() {
      EditJobPostFragmentArgs result = new EditJobPostFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setPostId(@NonNull String postId) {
      if (postId == null) {
        throw new IllegalArgumentException("Argument \"postId\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("postId", postId);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getPostId() {
      return (String) arguments.get("postId");
    }
  }
}
