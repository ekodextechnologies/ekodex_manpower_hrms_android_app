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

public class EditVoucherFragmentDirections {
  private EditVoucherFragmentDirections() {
  }

  @NonNull
  public static ActionEditVoucherFragmentToVoucherManagementFragment actionEditVoucherFragmentToVoucherManagementFragment(
      @NonNull String fromDate, @NonNull String toDate) {
    return new ActionEditVoucherFragmentToVoucherManagementFragment(fromDate, toDate);
  }

  public static class ActionEditVoucherFragmentToVoucherManagementFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionEditVoucherFragmentToVoucherManagementFragment(@NonNull String fromDate,
        @NonNull String toDate) {
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
    @SuppressWarnings("unchecked")
    public ActionEditVoucherFragmentToVoucherManagementFragment setFromDate(
        @NonNull String fromDate) {
      if (fromDate == null) {
        throw new IllegalArgumentException("Argument \"from_date\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("from_date", fromDate);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionEditVoucherFragmentToVoucherManagementFragment setToDate(@NonNull String toDate) {
      if (toDate == null) {
        throw new IllegalArgumentException("Argument \"to_date\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("to_date", toDate);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionEditVoucherFragmentToVoucherManagementFragment setOpenTab(int openTab) {
      this.arguments.put("openTab", openTab);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("from_date")) {
        String fromDate = (String) arguments.get("from_date");
        __result.putString("from_date", fromDate);
      }
      if (arguments.containsKey("to_date")) {
        String toDate = (String) arguments.get("to_date");
        __result.putString("to_date", toDate);
      }
      if (arguments.containsKey("openTab")) {
        int openTab = (int) arguments.get("openTab");
        __result.putInt("openTab", openTab);
      } else {
        __result.putInt("openTab", 0);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_editVoucherFragment_to_voucherManagementFragment;
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
    public int getOpenTab() {
      return (int) arguments.get("openTab");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionEditVoucherFragmentToVoucherManagementFragment that = (ActionEditVoucherFragmentToVoucherManagementFragment) object;
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
      if (arguments.containsKey("openTab") != that.arguments.containsKey("openTab")) {
        return false;
      }
      if (getOpenTab() != that.getOpenTab()) {
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
      result = 31 * result + (getFromDate() != null ? getFromDate().hashCode() : 0);
      result = 31 * result + (getToDate() != null ? getToDate().hashCode() : 0);
      result = 31 * result + getOpenTab();
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionEditVoucherFragmentToVoucherManagementFragment(actionId=" + getActionId() + "){"
          + "fromDate=" + getFromDate()
          + ", toDate=" + getToDate()
          + ", openTab=" + getOpenTab()
          + "}";
    }
  }
}
