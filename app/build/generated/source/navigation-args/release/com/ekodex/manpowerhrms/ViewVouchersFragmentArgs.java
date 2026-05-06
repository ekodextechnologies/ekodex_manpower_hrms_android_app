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

public class ViewVouchersFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private ViewVouchersFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private ViewVouchersFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static ViewVouchersFragmentArgs fromBundle(@NonNull Bundle bundle) {
    ViewVouchersFragmentArgs __result = new ViewVouchersFragmentArgs();
    bundle.setClassLoader(ViewVouchersFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("voucher_no")) {
      String voucherNo;
      voucherNo = bundle.getString("voucher_no");
      if (voucherNo == null) {
        throw new IllegalArgumentException("Argument \"voucher_no\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("voucher_no", voucherNo);
    } else {
      throw new IllegalArgumentException("Required argument \"voucher_no\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static ViewVouchersFragmentArgs fromSavedStateHandle(
      @NonNull SavedStateHandle savedStateHandle) {
    ViewVouchersFragmentArgs __result = new ViewVouchersFragmentArgs();
    if (savedStateHandle.contains("voucher_no")) {
      String voucherNo;
      voucherNo = savedStateHandle.get("voucher_no");
      if (voucherNo == null) {
        throw new IllegalArgumentException("Argument \"voucher_no\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("voucher_no", voucherNo);
    } else {
      throw new IllegalArgumentException("Required argument \"voucher_no\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getVoucherNo() {
    return (String) arguments.get("voucher_no");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("voucher_no")) {
      String voucherNo = (String) arguments.get("voucher_no");
      __result.putString("voucher_no", voucherNo);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("voucher_no")) {
      String voucherNo = (String) arguments.get("voucher_no");
      __result.set("voucher_no", voucherNo);
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
    ViewVouchersFragmentArgs that = (ViewVouchersFragmentArgs) object;
    if (arguments.containsKey("voucher_no") != that.arguments.containsKey("voucher_no")) {
      return false;
    }
    if (getVoucherNo() != null ? !getVoucherNo().equals(that.getVoucherNo()) : that.getVoucherNo() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getVoucherNo() != null ? getVoucherNo().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ViewVouchersFragmentArgs{"
        + "voucherNo=" + getVoucherNo()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull ViewVouchersFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(@NonNull String voucherNo) {
      if (voucherNo == null) {
        throw new IllegalArgumentException("Argument \"voucher_no\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("voucher_no", voucherNo);
    }

    @NonNull
    public ViewVouchersFragmentArgs build() {
      ViewVouchersFragmentArgs result = new ViewVouchersFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setVoucherNo(@NonNull String voucherNo) {
      if (voucherNo == null) {
        throw new IllegalArgumentException("Argument \"voucher_no\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("voucher_no", voucherNo);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getVoucherNo() {
      return (String) arguments.get("voucher_no");
    }
  }
}
