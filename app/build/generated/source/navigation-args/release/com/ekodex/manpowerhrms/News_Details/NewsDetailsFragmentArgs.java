package com.ekodex.manpowerhrms.News_Details;

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

public class NewsDetailsFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private NewsDetailsFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private NewsDetailsFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static NewsDetailsFragmentArgs fromBundle(@NonNull Bundle bundle) {
    NewsDetailsFragmentArgs __result = new NewsDetailsFragmentArgs();
    bundle.setClassLoader(NewsDetailsFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("keyword")) {
      String keyword;
      keyword = bundle.getString("keyword");
      if (keyword == null) {
        throw new IllegalArgumentException("Argument \"keyword\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("keyword", keyword);
    } else {
      throw new IllegalArgumentException("Required argument \"keyword\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static NewsDetailsFragmentArgs fromSavedStateHandle(
      @NonNull SavedStateHandle savedStateHandle) {
    NewsDetailsFragmentArgs __result = new NewsDetailsFragmentArgs();
    if (savedStateHandle.contains("keyword")) {
      String keyword;
      keyword = savedStateHandle.get("keyword");
      if (keyword == null) {
        throw new IllegalArgumentException("Argument \"keyword\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("keyword", keyword);
    } else {
      throw new IllegalArgumentException("Required argument \"keyword\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getKeyword() {
    return (String) arguments.get("keyword");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("keyword")) {
      String keyword = (String) arguments.get("keyword");
      __result.putString("keyword", keyword);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("keyword")) {
      String keyword = (String) arguments.get("keyword");
      __result.set("keyword", keyword);
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
    NewsDetailsFragmentArgs that = (NewsDetailsFragmentArgs) object;
    if (arguments.containsKey("keyword") != that.arguments.containsKey("keyword")) {
      return false;
    }
    if (getKeyword() != null ? !getKeyword().equals(that.getKeyword()) : that.getKeyword() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getKeyword() != null ? getKeyword().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "NewsDetailsFragmentArgs{"
        + "keyword=" + getKeyword()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull NewsDetailsFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(@NonNull String keyword) {
      if (keyword == null) {
        throw new IllegalArgumentException("Argument \"keyword\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("keyword", keyword);
    }

    @NonNull
    public NewsDetailsFragmentArgs build() {
      NewsDetailsFragmentArgs result = new NewsDetailsFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setKeyword(@NonNull String keyword) {
      if (keyword == null) {
        throw new IllegalArgumentException("Argument \"keyword\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("keyword", keyword);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getKeyword() {
      return (String) arguments.get("keyword");
    }
  }
}
