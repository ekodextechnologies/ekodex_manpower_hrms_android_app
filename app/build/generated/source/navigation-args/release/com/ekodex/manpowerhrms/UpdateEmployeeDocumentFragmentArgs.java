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

public class UpdateEmployeeDocumentFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private UpdateEmployeeDocumentFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private UpdateEmployeeDocumentFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static UpdateEmployeeDocumentFragmentArgs fromBundle(@NonNull Bundle bundle) {
    UpdateEmployeeDocumentFragmentArgs __result = new UpdateEmployeeDocumentFragmentArgs();
    bundle.setClassLoader(UpdateEmployeeDocumentFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("id")) {
      String id;
      id = bundle.getString("id");
      if (id == null) {
        throw new IllegalArgumentException("Argument \"id\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("id", id);
    } else {
      throw new IllegalArgumentException("Required argument \"id\" is missing and does not have an android:defaultValue");
    }
    if (bundle.containsKey("aadhar")) {
      String aadhar;
      aadhar = bundle.getString("aadhar");
      if (aadhar == null) {
        throw new IllegalArgumentException("Argument \"aadhar\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("aadhar", aadhar);
    } else {
      throw new IllegalArgumentException("Required argument \"aadhar\" is missing and does not have an android:defaultValue");
    }
    if (bundle.containsKey("pan")) {
      String pan;
      pan = bundle.getString("pan");
      if (pan == null) {
        throw new IllegalArgumentException("Argument \"pan\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("pan", pan);
    } else {
      throw new IllegalArgumentException("Required argument \"pan\" is missing and does not have an android:defaultValue");
    }
    if (bundle.containsKey("uan")) {
      String uan;
      uan = bundle.getString("uan");
      if (uan == null) {
        throw new IllegalArgumentException("Argument \"uan\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("uan", uan);
    } else {
      throw new IllegalArgumentException("Required argument \"uan\" is missing and does not have an android:defaultValue");
    }
    if (bundle.containsKey("pf")) {
      String pf;
      pf = bundle.getString("pf");
      if (pf == null) {
        throw new IllegalArgumentException("Argument \"pf\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("pf", pf);
    } else {
      throw new IllegalArgumentException("Required argument \"pf\" is missing and does not have an android:defaultValue");
    }
    if (bundle.containsKey("esis")) {
      String esis;
      esis = bundle.getString("esis");
      if (esis == null) {
        throw new IllegalArgumentException("Argument \"esis\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("esis", esis);
    } else {
      throw new IllegalArgumentException("Required argument \"esis\" is missing and does not have an android:defaultValue");
    }
    if (bundle.containsKey("passport")) {
      String passport;
      passport = bundle.getString("passport");
      if (passport == null) {
        throw new IllegalArgumentException("Argument \"passport\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("passport", passport);
    } else {
      throw new IllegalArgumentException("Required argument \"passport\" is missing and does not have an android:defaultValue");
    }
    if (bundle.containsKey("fname")) {
      String fname;
      fname = bundle.getString("fname");
      if (fname == null) {
        throw new IllegalArgumentException("Argument \"fname\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("fname", fname);
    } else {
      throw new IllegalArgumentException("Required argument \"fname\" is missing and does not have an android:defaultValue");
    }
    if (bundle.containsKey("lname")) {
      String lname;
      lname = bundle.getString("lname");
      if (lname == null) {
        throw new IllegalArgumentException("Argument \"lname\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("lname", lname);
    } else {
      throw new IllegalArgumentException("Required argument \"lname\" is missing and does not have an android:defaultValue");
    }
    if (bundle.containsKey("uan_date")) {
      String uanDate;
      uanDate = bundle.getString("uan_date");
      if (uanDate == null) {
        throw new IllegalArgumentException("Argument \"uan_date\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("uan_date", uanDate);
    } else {
      throw new IllegalArgumentException("Required argument \"uan_date\" is missing and does not have an android:defaultValue");
    }
    if (bundle.containsKey("esis_date")) {
      String esisDate;
      esisDate = bundle.getString("esis_date");
      if (esisDate == null) {
        throw new IllegalArgumentException("Argument \"esis_date\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("esis_date", esisDate);
    } else {
      throw new IllegalArgumentException("Required argument \"esis_date\" is missing and does not have an android:defaultValue");
    }
    if (bundle.containsKey("passport_date")) {
      String passportDate;
      passportDate = bundle.getString("passport_date");
      if (passportDate == null) {
        throw new IllegalArgumentException("Argument \"passport_date\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("passport_date", passportDate);
    } else {
      throw new IllegalArgumentException("Required argument \"passport_date\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static UpdateEmployeeDocumentFragmentArgs fromSavedStateHandle(
      @NonNull SavedStateHandle savedStateHandle) {
    UpdateEmployeeDocumentFragmentArgs __result = new UpdateEmployeeDocumentFragmentArgs();
    if (savedStateHandle.contains("id")) {
      String id;
      id = savedStateHandle.get("id");
      if (id == null) {
        throw new IllegalArgumentException("Argument \"id\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("id", id);
    } else {
      throw new IllegalArgumentException("Required argument \"id\" is missing and does not have an android:defaultValue");
    }
    if (savedStateHandle.contains("aadhar")) {
      String aadhar;
      aadhar = savedStateHandle.get("aadhar");
      if (aadhar == null) {
        throw new IllegalArgumentException("Argument \"aadhar\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("aadhar", aadhar);
    } else {
      throw new IllegalArgumentException("Required argument \"aadhar\" is missing and does not have an android:defaultValue");
    }
    if (savedStateHandle.contains("pan")) {
      String pan;
      pan = savedStateHandle.get("pan");
      if (pan == null) {
        throw new IllegalArgumentException("Argument \"pan\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("pan", pan);
    } else {
      throw new IllegalArgumentException("Required argument \"pan\" is missing and does not have an android:defaultValue");
    }
    if (savedStateHandle.contains("uan")) {
      String uan;
      uan = savedStateHandle.get("uan");
      if (uan == null) {
        throw new IllegalArgumentException("Argument \"uan\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("uan", uan);
    } else {
      throw new IllegalArgumentException("Required argument \"uan\" is missing and does not have an android:defaultValue");
    }
    if (savedStateHandle.contains("pf")) {
      String pf;
      pf = savedStateHandle.get("pf");
      if (pf == null) {
        throw new IllegalArgumentException("Argument \"pf\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("pf", pf);
    } else {
      throw new IllegalArgumentException("Required argument \"pf\" is missing and does not have an android:defaultValue");
    }
    if (savedStateHandle.contains("esis")) {
      String esis;
      esis = savedStateHandle.get("esis");
      if (esis == null) {
        throw new IllegalArgumentException("Argument \"esis\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("esis", esis);
    } else {
      throw new IllegalArgumentException("Required argument \"esis\" is missing and does not have an android:defaultValue");
    }
    if (savedStateHandle.contains("passport")) {
      String passport;
      passport = savedStateHandle.get("passport");
      if (passport == null) {
        throw new IllegalArgumentException("Argument \"passport\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("passport", passport);
    } else {
      throw new IllegalArgumentException("Required argument \"passport\" is missing and does not have an android:defaultValue");
    }
    if (savedStateHandle.contains("fname")) {
      String fname;
      fname = savedStateHandle.get("fname");
      if (fname == null) {
        throw new IllegalArgumentException("Argument \"fname\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("fname", fname);
    } else {
      throw new IllegalArgumentException("Required argument \"fname\" is missing and does not have an android:defaultValue");
    }
    if (savedStateHandle.contains("lname")) {
      String lname;
      lname = savedStateHandle.get("lname");
      if (lname == null) {
        throw new IllegalArgumentException("Argument \"lname\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("lname", lname);
    } else {
      throw new IllegalArgumentException("Required argument \"lname\" is missing and does not have an android:defaultValue");
    }
    if (savedStateHandle.contains("uan_date")) {
      String uanDate;
      uanDate = savedStateHandle.get("uan_date");
      if (uanDate == null) {
        throw new IllegalArgumentException("Argument \"uan_date\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("uan_date", uanDate);
    } else {
      throw new IllegalArgumentException("Required argument \"uan_date\" is missing and does not have an android:defaultValue");
    }
    if (savedStateHandle.contains("esis_date")) {
      String esisDate;
      esisDate = savedStateHandle.get("esis_date");
      if (esisDate == null) {
        throw new IllegalArgumentException("Argument \"esis_date\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("esis_date", esisDate);
    } else {
      throw new IllegalArgumentException("Required argument \"esis_date\" is missing and does not have an android:defaultValue");
    }
    if (savedStateHandle.contains("passport_date")) {
      String passportDate;
      passportDate = savedStateHandle.get("passport_date");
      if (passportDate == null) {
        throw new IllegalArgumentException("Argument \"passport_date\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("passport_date", passportDate);
    } else {
      throw new IllegalArgumentException("Required argument \"passport_date\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getId() {
    return (String) arguments.get("id");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getAadhar() {
    return (String) arguments.get("aadhar");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getPan() {
    return (String) arguments.get("pan");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getUan() {
    return (String) arguments.get("uan");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getPf() {
    return (String) arguments.get("pf");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getEsis() {
    return (String) arguments.get("esis");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getPassport() {
    return (String) arguments.get("passport");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getFname() {
    return (String) arguments.get("fname");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getLname() {
    return (String) arguments.get("lname");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getUanDate() {
    return (String) arguments.get("uan_date");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getEsisDate() {
    return (String) arguments.get("esis_date");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getPassportDate() {
    return (String) arguments.get("passport_date");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("id")) {
      String id = (String) arguments.get("id");
      __result.putString("id", id);
    }
    if (arguments.containsKey("aadhar")) {
      String aadhar = (String) arguments.get("aadhar");
      __result.putString("aadhar", aadhar);
    }
    if (arguments.containsKey("pan")) {
      String pan = (String) arguments.get("pan");
      __result.putString("pan", pan);
    }
    if (arguments.containsKey("uan")) {
      String uan = (String) arguments.get("uan");
      __result.putString("uan", uan);
    }
    if (arguments.containsKey("pf")) {
      String pf = (String) arguments.get("pf");
      __result.putString("pf", pf);
    }
    if (arguments.containsKey("esis")) {
      String esis = (String) arguments.get("esis");
      __result.putString("esis", esis);
    }
    if (arguments.containsKey("passport")) {
      String passport = (String) arguments.get("passport");
      __result.putString("passport", passport);
    }
    if (arguments.containsKey("fname")) {
      String fname = (String) arguments.get("fname");
      __result.putString("fname", fname);
    }
    if (arguments.containsKey("lname")) {
      String lname = (String) arguments.get("lname");
      __result.putString("lname", lname);
    }
    if (arguments.containsKey("uan_date")) {
      String uanDate = (String) arguments.get("uan_date");
      __result.putString("uan_date", uanDate);
    }
    if (arguments.containsKey("esis_date")) {
      String esisDate = (String) arguments.get("esis_date");
      __result.putString("esis_date", esisDate);
    }
    if (arguments.containsKey("passport_date")) {
      String passportDate = (String) arguments.get("passport_date");
      __result.putString("passport_date", passportDate);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("id")) {
      String id = (String) arguments.get("id");
      __result.set("id", id);
    }
    if (arguments.containsKey("aadhar")) {
      String aadhar = (String) arguments.get("aadhar");
      __result.set("aadhar", aadhar);
    }
    if (arguments.containsKey("pan")) {
      String pan = (String) arguments.get("pan");
      __result.set("pan", pan);
    }
    if (arguments.containsKey("uan")) {
      String uan = (String) arguments.get("uan");
      __result.set("uan", uan);
    }
    if (arguments.containsKey("pf")) {
      String pf = (String) arguments.get("pf");
      __result.set("pf", pf);
    }
    if (arguments.containsKey("esis")) {
      String esis = (String) arguments.get("esis");
      __result.set("esis", esis);
    }
    if (arguments.containsKey("passport")) {
      String passport = (String) arguments.get("passport");
      __result.set("passport", passport);
    }
    if (arguments.containsKey("fname")) {
      String fname = (String) arguments.get("fname");
      __result.set("fname", fname);
    }
    if (arguments.containsKey("lname")) {
      String lname = (String) arguments.get("lname");
      __result.set("lname", lname);
    }
    if (arguments.containsKey("uan_date")) {
      String uanDate = (String) arguments.get("uan_date");
      __result.set("uan_date", uanDate);
    }
    if (arguments.containsKey("esis_date")) {
      String esisDate = (String) arguments.get("esis_date");
      __result.set("esis_date", esisDate);
    }
    if (arguments.containsKey("passport_date")) {
      String passportDate = (String) arguments.get("passport_date");
      __result.set("passport_date", passportDate);
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
    UpdateEmployeeDocumentFragmentArgs that = (UpdateEmployeeDocumentFragmentArgs) object;
    if (arguments.containsKey("id") != that.arguments.containsKey("id")) {
      return false;
    }
    if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) {
      return false;
    }
    if (arguments.containsKey("aadhar") != that.arguments.containsKey("aadhar")) {
      return false;
    }
    if (getAadhar() != null ? !getAadhar().equals(that.getAadhar()) : that.getAadhar() != null) {
      return false;
    }
    if (arguments.containsKey("pan") != that.arguments.containsKey("pan")) {
      return false;
    }
    if (getPan() != null ? !getPan().equals(that.getPan()) : that.getPan() != null) {
      return false;
    }
    if (arguments.containsKey("uan") != that.arguments.containsKey("uan")) {
      return false;
    }
    if (getUan() != null ? !getUan().equals(that.getUan()) : that.getUan() != null) {
      return false;
    }
    if (arguments.containsKey("pf") != that.arguments.containsKey("pf")) {
      return false;
    }
    if (getPf() != null ? !getPf().equals(that.getPf()) : that.getPf() != null) {
      return false;
    }
    if (arguments.containsKey("esis") != that.arguments.containsKey("esis")) {
      return false;
    }
    if (getEsis() != null ? !getEsis().equals(that.getEsis()) : that.getEsis() != null) {
      return false;
    }
    if (arguments.containsKey("passport") != that.arguments.containsKey("passport")) {
      return false;
    }
    if (getPassport() != null ? !getPassport().equals(that.getPassport()) : that.getPassport() != null) {
      return false;
    }
    if (arguments.containsKey("fname") != that.arguments.containsKey("fname")) {
      return false;
    }
    if (getFname() != null ? !getFname().equals(that.getFname()) : that.getFname() != null) {
      return false;
    }
    if (arguments.containsKey("lname") != that.arguments.containsKey("lname")) {
      return false;
    }
    if (getLname() != null ? !getLname().equals(that.getLname()) : that.getLname() != null) {
      return false;
    }
    if (arguments.containsKey("uan_date") != that.arguments.containsKey("uan_date")) {
      return false;
    }
    if (getUanDate() != null ? !getUanDate().equals(that.getUanDate()) : that.getUanDate() != null) {
      return false;
    }
    if (arguments.containsKey("esis_date") != that.arguments.containsKey("esis_date")) {
      return false;
    }
    if (getEsisDate() != null ? !getEsisDate().equals(that.getEsisDate()) : that.getEsisDate() != null) {
      return false;
    }
    if (arguments.containsKey("passport_date") != that.arguments.containsKey("passport_date")) {
      return false;
    }
    if (getPassportDate() != null ? !getPassportDate().equals(that.getPassportDate()) : that.getPassportDate() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getId() != null ? getId().hashCode() : 0);
    result = 31 * result + (getAadhar() != null ? getAadhar().hashCode() : 0);
    result = 31 * result + (getPan() != null ? getPan().hashCode() : 0);
    result = 31 * result + (getUan() != null ? getUan().hashCode() : 0);
    result = 31 * result + (getPf() != null ? getPf().hashCode() : 0);
    result = 31 * result + (getEsis() != null ? getEsis().hashCode() : 0);
    result = 31 * result + (getPassport() != null ? getPassport().hashCode() : 0);
    result = 31 * result + (getFname() != null ? getFname().hashCode() : 0);
    result = 31 * result + (getLname() != null ? getLname().hashCode() : 0);
    result = 31 * result + (getUanDate() != null ? getUanDate().hashCode() : 0);
    result = 31 * result + (getEsisDate() != null ? getEsisDate().hashCode() : 0);
    result = 31 * result + (getPassportDate() != null ? getPassportDate().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "UpdateEmployeeDocumentFragmentArgs{"
        + "id=" + getId()
        + ", aadhar=" + getAadhar()
        + ", pan=" + getPan()
        + ", uan=" + getUan()
        + ", pf=" + getPf()
        + ", esis=" + getEsis()
        + ", passport=" + getPassport()
        + ", fname=" + getFname()
        + ", lname=" + getLname()
        + ", uanDate=" + getUanDate()
        + ", esisDate=" + getEsisDate()
        + ", passportDate=" + getPassportDate()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull UpdateEmployeeDocumentFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(@NonNull String id, @NonNull String aadhar, @NonNull String pan,
        @NonNull String uan, @NonNull String pf, @NonNull String esis, @NonNull String passport,
        @NonNull String fname, @NonNull String lname, @NonNull String uanDate,
        @NonNull String esisDate, @NonNull String passportDate) {
      if (id == null) {
        throw new IllegalArgumentException("Argument \"id\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("id", id);
      if (aadhar == null) {
        throw new IllegalArgumentException("Argument \"aadhar\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("aadhar", aadhar);
      if (pan == null) {
        throw new IllegalArgumentException("Argument \"pan\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("pan", pan);
      if (uan == null) {
        throw new IllegalArgumentException("Argument \"uan\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("uan", uan);
      if (pf == null) {
        throw new IllegalArgumentException("Argument \"pf\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("pf", pf);
      if (esis == null) {
        throw new IllegalArgumentException("Argument \"esis\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("esis", esis);
      if (passport == null) {
        throw new IllegalArgumentException("Argument \"passport\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("passport", passport);
      if (fname == null) {
        throw new IllegalArgumentException("Argument \"fname\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("fname", fname);
      if (lname == null) {
        throw new IllegalArgumentException("Argument \"lname\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("lname", lname);
      if (uanDate == null) {
        throw new IllegalArgumentException("Argument \"uan_date\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("uan_date", uanDate);
      if (esisDate == null) {
        throw new IllegalArgumentException("Argument \"esis_date\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("esis_date", esisDate);
      if (passportDate == null) {
        throw new IllegalArgumentException("Argument \"passport_date\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("passport_date", passportDate);
    }

    @NonNull
    public UpdateEmployeeDocumentFragmentArgs build() {
      UpdateEmployeeDocumentFragmentArgs result = new UpdateEmployeeDocumentFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setId(@NonNull String id) {
      if (id == null) {
        throw new IllegalArgumentException("Argument \"id\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("id", id);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setAadhar(@NonNull String aadhar) {
      if (aadhar == null) {
        throw new IllegalArgumentException("Argument \"aadhar\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("aadhar", aadhar);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setPan(@NonNull String pan) {
      if (pan == null) {
        throw new IllegalArgumentException("Argument \"pan\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("pan", pan);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setUan(@NonNull String uan) {
      if (uan == null) {
        throw new IllegalArgumentException("Argument \"uan\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("uan", uan);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setPf(@NonNull String pf) {
      if (pf == null) {
        throw new IllegalArgumentException("Argument \"pf\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("pf", pf);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setEsis(@NonNull String esis) {
      if (esis == null) {
        throw new IllegalArgumentException("Argument \"esis\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("esis", esis);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setPassport(@NonNull String passport) {
      if (passport == null) {
        throw new IllegalArgumentException("Argument \"passport\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("passport", passport);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setFname(@NonNull String fname) {
      if (fname == null) {
        throw new IllegalArgumentException("Argument \"fname\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("fname", fname);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setLname(@NonNull String lname) {
      if (lname == null) {
        throw new IllegalArgumentException("Argument \"lname\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("lname", lname);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setUanDate(@NonNull String uanDate) {
      if (uanDate == null) {
        throw new IllegalArgumentException("Argument \"uan_date\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("uan_date", uanDate);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setEsisDate(@NonNull String esisDate) {
      if (esisDate == null) {
        throw new IllegalArgumentException("Argument \"esis_date\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("esis_date", esisDate);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setPassportDate(@NonNull String passportDate) {
      if (passportDate == null) {
        throw new IllegalArgumentException("Argument \"passport_date\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("passport_date", passportDate);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getId() {
      return (String) arguments.get("id");
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getAadhar() {
      return (String) arguments.get("aadhar");
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getPan() {
      return (String) arguments.get("pan");
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getUan() {
      return (String) arguments.get("uan");
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getPf() {
      return (String) arguments.get("pf");
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getEsis() {
      return (String) arguments.get("esis");
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getPassport() {
      return (String) arguments.get("passport");
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getFname() {
      return (String) arguments.get("fname");
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getLname() {
      return (String) arguments.get("lname");
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getUanDate() {
      return (String) arguments.get("uan_date");
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getEsisDate() {
      return (String) arguments.get("esis_date");
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getPassportDate() {
      return (String) arguments.get("passport_date");
    }
  }
}
