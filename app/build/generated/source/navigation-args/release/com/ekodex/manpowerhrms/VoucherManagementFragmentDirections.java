package com.ekodex.manpowerhrms;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class VoucherManagementFragmentDirections {
  private VoucherManagementFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionVoucherManagementFragmentToAddVoucherFragment() {
    return new ActionOnlyNavDirections(R.id.action_voucherManagementFragment_to_addVoucherFragment);
  }

  @NonNull
  public static NavDirections actionVoucherManagementFragmentToAddEmployeeVoucherFragment() {
    return new ActionOnlyNavDirections(R.id.action_voucherManagementFragment_to_addEmployeeVoucherFragment);
  }

  @NonNull
  public static NavDirections actionVoucherManagementFragmentToAddSiteVoucherFragment() {
    return new ActionOnlyNavDirections(R.id.action_voucherManagementFragment_to_addSiteVoucherFragment);
  }

  @NonNull
  public static NavDirections actionVoucherManagementFragmentToAddSplitVoucherFragment() {
    return new ActionOnlyNavDirections(R.id.action_voucherManagementFragment_to_addSplitVoucherFragment);
  }

  @NonNull
  public static ActionVoucherManagementFragmentToViewVouchersFragment actionVoucherManagementFragmentToViewVouchersFragment(
      @NonNull String voucherNo) {
    return new ActionVoucherManagementFragmentToViewVouchersFragment(voucherNo);
  }

  @NonNull
  public static ActionVoucherManagementFragmentToEditVoucherFragment actionVoucherManagementFragmentToEditVoucherFragment(
      @NonNull String voucherNo) {
    return new ActionVoucherManagementFragmentToEditVoucherFragment(voucherNo);
  }

  @NonNull
  public static ActionVoucherManagementFragmentSelf actionVoucherManagementFragmentSelf(
      @NonNull String fromDate, @NonNull String toDate) {
    return new ActionVoucherManagementFragmentSelf(fromDate, toDate);
  }

  @NonNull
  public static ActionVoucherManagementFragmentToVoucherDetailsFragment actionVoucherManagementFragmentToVoucherDetailsFragment(
      @NonNull String voucherNo) {
    return new ActionVoucherManagementFragmentToVoucherDetailsFragment(voucherNo);
  }

  public static class ActionVoucherManagementFragmentToViewVouchersFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionVoucherManagementFragmentToViewVouchersFragment(@NonNull String voucherNo) {
      if (voucherNo == null) {
        throw new IllegalArgumentException("Argument \"voucher_no\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("voucher_no", voucherNo);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionVoucherManagementFragmentToViewVouchersFragment setVoucherNo(
        @NonNull String voucherNo) {
      if (voucherNo == null) {
        throw new IllegalArgumentException("Argument \"voucher_no\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("voucher_no", voucherNo);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("voucher_no")) {
        String voucherNo = (String) arguments.get("voucher_no");
        __result.putString("voucher_no", voucherNo);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_voucherManagementFragment_to_viewVouchersFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getVoucherNo() {
      return (String) arguments.get("voucher_no");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionVoucherManagementFragmentToViewVouchersFragment that = (ActionVoucherManagementFragmentToViewVouchersFragment) object;
      if (arguments.containsKey("voucher_no") != that.arguments.containsKey("voucher_no")) {
        return false;
      }
      if (getVoucherNo() != null ? !getVoucherNo().equals(that.getVoucherNo()) : that.getVoucherNo() != null) {
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
      result = 31 * result + (getVoucherNo() != null ? getVoucherNo().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionVoucherManagementFragmentToViewVouchersFragment(actionId=" + getActionId() + "){"
          + "voucherNo=" + getVoucherNo()
          + "}";
    }
  }

  public static class ActionVoucherManagementFragmentToEditVoucherFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionVoucherManagementFragmentToEditVoucherFragment(@NonNull String voucherNo) {
      if (voucherNo == null) {
        throw new IllegalArgumentException("Argument \"voucher_no\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("voucher_no", voucherNo);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionVoucherManagementFragmentToEditVoucherFragment setVoucherNo(
        @NonNull String voucherNo) {
      if (voucherNo == null) {
        throw new IllegalArgumentException("Argument \"voucher_no\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("voucher_no", voucherNo);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("voucher_no")) {
        String voucherNo = (String) arguments.get("voucher_no");
        __result.putString("voucher_no", voucherNo);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_voucherManagementFragment_to_editVoucherFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getVoucherNo() {
      return (String) arguments.get("voucher_no");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionVoucherManagementFragmentToEditVoucherFragment that = (ActionVoucherManagementFragmentToEditVoucherFragment) object;
      if (arguments.containsKey("voucher_no") != that.arguments.containsKey("voucher_no")) {
        return false;
      }
      if (getVoucherNo() != null ? !getVoucherNo().equals(that.getVoucherNo()) : that.getVoucherNo() != null) {
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
      result = 31 * result + (getVoucherNo() != null ? getVoucherNo().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionVoucherManagementFragmentToEditVoucherFragment(actionId=" + getActionId() + "){"
          + "voucherNo=" + getVoucherNo()
          + "}";
    }
  }

  public static class ActionVoucherManagementFragmentSelf implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionVoucherManagementFragmentSelf(@NonNull String fromDate, @NonNull String toDate) {
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
    public ActionVoucherManagementFragmentSelf setFromDate(@NonNull String fromDate) {
      if (fromDate == null) {
        throw new IllegalArgumentException("Argument \"from_date\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("from_date", fromDate);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionVoucherManagementFragmentSelf setToDate(@NonNull String toDate) {
      if (toDate == null) {
        throw new IllegalArgumentException("Argument \"to_date\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("to_date", toDate);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionVoucherManagementFragmentSelf setOpenTab(int openTab) {
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
      return R.id.action_voucherManagementFragment_self;
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
      ActionVoucherManagementFragmentSelf that = (ActionVoucherManagementFragmentSelf) object;
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
      return "ActionVoucherManagementFragmentSelf(actionId=" + getActionId() + "){"
          + "fromDate=" + getFromDate()
          + ", toDate=" + getToDate()
          + ", openTab=" + getOpenTab()
          + "}";
    }
  }

  public static class ActionVoucherManagementFragmentToVoucherDetailsFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionVoucherManagementFragmentToVoucherDetailsFragment(@NonNull String voucherNo) {
      if (voucherNo == null) {
        throw new IllegalArgumentException("Argument \"voucher_no\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("voucher_no", voucherNo);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionVoucherManagementFragmentToVoucherDetailsFragment setVoucherNo(
        @NonNull String voucherNo) {
      if (voucherNo == null) {
        throw new IllegalArgumentException("Argument \"voucher_no\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("voucher_no", voucherNo);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("voucher_no")) {
        String voucherNo = (String) arguments.get("voucher_no");
        __result.putString("voucher_no", voucherNo);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_voucherManagementFragment_to_VoucherDetailsFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getVoucherNo() {
      return (String) arguments.get("voucher_no");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionVoucherManagementFragmentToVoucherDetailsFragment that = (ActionVoucherManagementFragmentToVoucherDetailsFragment) object;
      if (arguments.containsKey("voucher_no") != that.arguments.containsKey("voucher_no")) {
        return false;
      }
      if (getVoucherNo() != null ? !getVoucherNo().equals(that.getVoucherNo()) : that.getVoucherNo() != null) {
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
      result = 31 * result + (getVoucherNo() != null ? getVoucherNo().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionVoucherManagementFragmentToVoucherDetailsFragment(actionId=" + getActionId() + "){"
          + "voucherNo=" + getVoucherNo()
          + "}";
    }
  }
}
