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

public class SupervisorAttendanceFragmentDirections {
  private SupervisorAttendanceFragmentDirections() {
  }

  @NonNull
  public static ActionSupervisorAttendanceFragmentSelf actionSupervisorAttendanceFragmentSelf(
      @NonNull String status, @NonNull String from, @NonNull String to) {
    return new ActionSupervisorAttendanceFragmentSelf(status, from, to);
  }

  public static class ActionSupervisorAttendanceFragmentSelf implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionSupervisorAttendanceFragmentSelf(@NonNull String status, @NonNull String from,
        @NonNull String to) {
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
    @SuppressWarnings("unchecked")
    public ActionSupervisorAttendanceFragmentSelf setStatus(@NonNull String status) {
      if (status == null) {
        throw new IllegalArgumentException("Argument \"status\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("status", status);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionSupervisorAttendanceFragmentSelf setFrom(@NonNull String from) {
      if (from == null) {
        throw new IllegalArgumentException("Argument \"from\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("from", from);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionSupervisorAttendanceFragmentSelf setTo(@NonNull String to) {
      if (to == null) {
        throw new IllegalArgumentException("Argument \"to\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("to", to);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
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

    @Override
    public int getActionId() {
      return R.id.action_supervisorAttendanceFragment_self;
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

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionSupervisorAttendanceFragmentSelf that = (ActionSupervisorAttendanceFragmentSelf) object;
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
      if (getActionId() != that.getActionId()) {
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
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionSupervisorAttendanceFragmentSelf(actionId=" + getActionId() + "){"
          + "status=" + getStatus()
          + ", from=" + getFrom()
          + ", to=" + getTo()
          + "}";
    }
  }
}
