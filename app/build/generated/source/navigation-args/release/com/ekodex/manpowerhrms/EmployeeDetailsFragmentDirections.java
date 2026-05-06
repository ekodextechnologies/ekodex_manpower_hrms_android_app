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

public class EmployeeDetailsFragmentDirections {
  private EmployeeDetailsFragmentDirections() {
  }

  @NonNull
  public static ActionEmployeeDetailsFragmentToUpdateEmployeeBankDetailsFragment actionEmployeeDetailsFragmentToUpdateEmployeeBankDetailsFragment(
      @NonNull String id) {
    return new ActionEmployeeDetailsFragmentToUpdateEmployeeBankDetailsFragment(id);
  }

  @NonNull
  public static ActionEmployeeDetailsFragmentToUpdateEmployeePersonalDetailsFragment actionEmployeeDetailsFragmentToUpdateEmployeePersonalDetailsFragment(
      @NonNull String id) {
    return new ActionEmployeeDetailsFragmentToUpdateEmployeePersonalDetailsFragment(id);
  }

  @NonNull
  public static ActionEmployeeDetailsFragmentToUpdateEmployeeDocumentFragment actionEmployeeDetailsFragmentToUpdateEmployeeDocumentFragment(
      @NonNull String id, @NonNull String aadhar, @NonNull String pan, @NonNull String uan,
      @NonNull String pf, @NonNull String esis, @NonNull String passport, @NonNull String fname,
      @NonNull String lname, @NonNull String uanDate, @NonNull String esisDate,
      @NonNull String passportDate) {
    return new ActionEmployeeDetailsFragmentToUpdateEmployeeDocumentFragment(id, aadhar, pan, uan, pf, esis, passport, fname, lname, uanDate, esisDate, passportDate);
  }

  public static class ActionEmployeeDetailsFragmentToUpdateEmployeeBankDetailsFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionEmployeeDetailsFragmentToUpdateEmployeeBankDetailsFragment(@NonNull String id) {
      if (id == null) {
        throw new IllegalArgumentException("Argument \"id\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("id", id);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionEmployeeDetailsFragmentToUpdateEmployeeBankDetailsFragment setId(
        @NonNull String id) {
      if (id == null) {
        throw new IllegalArgumentException("Argument \"id\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("id", id);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("id")) {
        String id = (String) arguments.get("id");
        __result.putString("id", id);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_employeeDetailsFragment_to_updateEmployeeBankDetailsFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getId() {
      return (String) arguments.get("id");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionEmployeeDetailsFragmentToUpdateEmployeeBankDetailsFragment that = (ActionEmployeeDetailsFragmentToUpdateEmployeeBankDetailsFragment) object;
      if (arguments.containsKey("id") != that.arguments.containsKey("id")) {
        return false;
      }
      if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) {
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
      result = 31 * result + (getId() != null ? getId().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionEmployeeDetailsFragmentToUpdateEmployeeBankDetailsFragment(actionId=" + getActionId() + "){"
          + "id=" + getId()
          + "}";
    }
  }

  public static class ActionEmployeeDetailsFragmentToUpdateEmployeePersonalDetailsFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionEmployeeDetailsFragmentToUpdateEmployeePersonalDetailsFragment(
        @NonNull String id) {
      if (id == null) {
        throw new IllegalArgumentException("Argument \"id\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("id", id);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionEmployeeDetailsFragmentToUpdateEmployeePersonalDetailsFragment setId(
        @NonNull String id) {
      if (id == null) {
        throw new IllegalArgumentException("Argument \"id\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("id", id);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("id")) {
        String id = (String) arguments.get("id");
        __result.putString("id", id);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_employeeDetailsFragment_to_updateEmployeePersonalDetailsFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getId() {
      return (String) arguments.get("id");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionEmployeeDetailsFragmentToUpdateEmployeePersonalDetailsFragment that = (ActionEmployeeDetailsFragmentToUpdateEmployeePersonalDetailsFragment) object;
      if (arguments.containsKey("id") != that.arguments.containsKey("id")) {
        return false;
      }
      if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) {
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
      result = 31 * result + (getId() != null ? getId().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionEmployeeDetailsFragmentToUpdateEmployeePersonalDetailsFragment(actionId=" + getActionId() + "){"
          + "id=" + getId()
          + "}";
    }
  }

  public static class ActionEmployeeDetailsFragmentToUpdateEmployeeDocumentFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionEmployeeDetailsFragmentToUpdateEmployeeDocumentFragment(@NonNull String id,
        @NonNull String aadhar, @NonNull String pan, @NonNull String uan, @NonNull String pf,
        @NonNull String esis, @NonNull String passport, @NonNull String fname,
        @NonNull String lname, @NonNull String uanDate, @NonNull String esisDate,
        @NonNull String passportDate) {
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
    @SuppressWarnings("unchecked")
    public ActionEmployeeDetailsFragmentToUpdateEmployeeDocumentFragment setId(@NonNull String id) {
      if (id == null) {
        throw new IllegalArgumentException("Argument \"id\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("id", id);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionEmployeeDetailsFragmentToUpdateEmployeeDocumentFragment setAadhar(
        @NonNull String aadhar) {
      if (aadhar == null) {
        throw new IllegalArgumentException("Argument \"aadhar\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("aadhar", aadhar);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionEmployeeDetailsFragmentToUpdateEmployeeDocumentFragment setPan(
        @NonNull String pan) {
      if (pan == null) {
        throw new IllegalArgumentException("Argument \"pan\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("pan", pan);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionEmployeeDetailsFragmentToUpdateEmployeeDocumentFragment setUan(
        @NonNull String uan) {
      if (uan == null) {
        throw new IllegalArgumentException("Argument \"uan\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("uan", uan);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionEmployeeDetailsFragmentToUpdateEmployeeDocumentFragment setPf(@NonNull String pf) {
      if (pf == null) {
        throw new IllegalArgumentException("Argument \"pf\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("pf", pf);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionEmployeeDetailsFragmentToUpdateEmployeeDocumentFragment setEsis(
        @NonNull String esis) {
      if (esis == null) {
        throw new IllegalArgumentException("Argument \"esis\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("esis", esis);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionEmployeeDetailsFragmentToUpdateEmployeeDocumentFragment setPassport(
        @NonNull String passport) {
      if (passport == null) {
        throw new IllegalArgumentException("Argument \"passport\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("passport", passport);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionEmployeeDetailsFragmentToUpdateEmployeeDocumentFragment setFname(
        @NonNull String fname) {
      if (fname == null) {
        throw new IllegalArgumentException("Argument \"fname\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("fname", fname);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionEmployeeDetailsFragmentToUpdateEmployeeDocumentFragment setLname(
        @NonNull String lname) {
      if (lname == null) {
        throw new IllegalArgumentException("Argument \"lname\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("lname", lname);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionEmployeeDetailsFragmentToUpdateEmployeeDocumentFragment setUanDate(
        @NonNull String uanDate) {
      if (uanDate == null) {
        throw new IllegalArgumentException("Argument \"uan_date\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("uan_date", uanDate);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionEmployeeDetailsFragmentToUpdateEmployeeDocumentFragment setEsisDate(
        @NonNull String esisDate) {
      if (esisDate == null) {
        throw new IllegalArgumentException("Argument \"esis_date\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("esis_date", esisDate);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionEmployeeDetailsFragmentToUpdateEmployeeDocumentFragment setPassportDate(
        @NonNull String passportDate) {
      if (passportDate == null) {
        throw new IllegalArgumentException("Argument \"passport_date\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("passport_date", passportDate);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
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

    @Override
    public int getActionId() {
      return R.id.action_employeeDetailsFragment_to_updateEmployeeDocumentFragment;
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

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionEmployeeDetailsFragmentToUpdateEmployeeDocumentFragment that = (ActionEmployeeDetailsFragmentToUpdateEmployeeDocumentFragment) object;
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
      if (getActionId() != that.getActionId()) {
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
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionEmployeeDetailsFragmentToUpdateEmployeeDocumentFragment(actionId=" + getActionId() + "){"
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
  }
}
