package com.ekodex.manpowerhrms.News;

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

public class NewsFragmentDirections {
  private NewsFragmentDirections() {
  }

  @NonNull
  public static ActionNewsFragmentToNewsDetailsFragment actionNewsFragmentToNewsDetailsFragment(
      @NonNull String keyword) {
    return new ActionNewsFragmentToNewsDetailsFragment(keyword);
  }

  public static class ActionNewsFragmentToNewsDetailsFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionNewsFragmentToNewsDetailsFragment(@NonNull String keyword) {
      if (keyword == null) {
        throw new IllegalArgumentException("Argument \"keyword\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("keyword", keyword);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionNewsFragmentToNewsDetailsFragment setKeyword(@NonNull String keyword) {
      if (keyword == null) {
        throw new IllegalArgumentException("Argument \"keyword\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("keyword", keyword);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("keyword")) {
        String keyword = (String) arguments.get("keyword");
        __result.putString("keyword", keyword);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_newsFragment_to_newsDetailsFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getKeyword() {
      return (String) arguments.get("keyword");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionNewsFragmentToNewsDetailsFragment that = (ActionNewsFragmentToNewsDetailsFragment) object;
      if (arguments.containsKey("keyword") != that.arguments.containsKey("keyword")) {
        return false;
      }
      if (getKeyword() != null ? !getKeyword().equals(that.getKeyword()) : that.getKeyword() != null) {
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
      result = 31 * result + (getKeyword() != null ? getKeyword().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionNewsFragmentToNewsDetailsFragment(actionId=" + getActionId() + "){"
          + "keyword=" + getKeyword()
          + "}";
    }
  }
}
