package com.ekodex.manpowerhrms.Recruitment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.ekodex.manpowerhrms.R;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class RecruitmentFragmentDirections {
  private RecruitmentFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionRecruitmentFragmentToRecruitmentCreatePostFragment() {
    return new ActionOnlyNavDirections(R.id.action_recruitmentFragment_to_recruitmentCreatePostFragment);
  }

  @NonNull
  public static ActionRecruitmentFragmentToRecruitmentApplicantDocumentFragment actionRecruitmentFragmentToRecruitmentApplicantDocumentFragment(
      @NonNull String resumeLink) {
    return new ActionRecruitmentFragmentToRecruitmentApplicantDocumentFragment(resumeLink);
  }

  @NonNull
  public static ActionRecruitmentFragmentToEditJobPostFragment actionRecruitmentFragmentToEditJobPostFragment(
      @NonNull String postId) {
    return new ActionRecruitmentFragmentToEditJobPostFragment(postId);
  }

  public static class ActionRecruitmentFragmentToRecruitmentApplicantDocumentFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionRecruitmentFragmentToRecruitmentApplicantDocumentFragment(
        @NonNull String resumeLink) {
      if (resumeLink == null) {
        throw new IllegalArgumentException("Argument \"resumeLink\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("resumeLink", resumeLink);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionRecruitmentFragmentToRecruitmentApplicantDocumentFragment setResumeLink(
        @NonNull String resumeLink) {
      if (resumeLink == null) {
        throw new IllegalArgumentException("Argument \"resumeLink\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("resumeLink", resumeLink);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("resumeLink")) {
        String resumeLink = (String) arguments.get("resumeLink");
        __result.putString("resumeLink", resumeLink);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_recruitmentFragment_to_recruitmentApplicantDocumentFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getResumeLink() {
      return (String) arguments.get("resumeLink");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionRecruitmentFragmentToRecruitmentApplicantDocumentFragment that = (ActionRecruitmentFragmentToRecruitmentApplicantDocumentFragment) object;
      if (arguments.containsKey("resumeLink") != that.arguments.containsKey("resumeLink")) {
        return false;
      }
      if (getResumeLink() != null ? !getResumeLink().equals(that.getResumeLink()) : that.getResumeLink() != null) {
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
      result = 31 * result + (getResumeLink() != null ? getResumeLink().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionRecruitmentFragmentToRecruitmentApplicantDocumentFragment(actionId=" + getActionId() + "){"
          + "resumeLink=" + getResumeLink()
          + "}";
    }
  }

  public static class ActionRecruitmentFragmentToEditJobPostFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionRecruitmentFragmentToEditJobPostFragment(@NonNull String postId) {
      if (postId == null) {
        throw new IllegalArgumentException("Argument \"postId\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("postId", postId);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionRecruitmentFragmentToEditJobPostFragment setPostId(@NonNull String postId) {
      if (postId == null) {
        throw new IllegalArgumentException("Argument \"postId\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("postId", postId);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("postId")) {
        String postId = (String) arguments.get("postId");
        __result.putString("postId", postId);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_recruitmentFragment_to_editJobPostFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getPostId() {
      return (String) arguments.get("postId");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionRecruitmentFragmentToEditJobPostFragment that = (ActionRecruitmentFragmentToEditJobPostFragment) object;
      if (arguments.containsKey("postId") != that.arguments.containsKey("postId")) {
        return false;
      }
      if (getPostId() != null ? !getPostId().equals(that.getPostId()) : that.getPostId() != null) {
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
      result = 31 * result + (getPostId() != null ? getPostId().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionRecruitmentFragmentToEditJobPostFragment(actionId=" + getActionId() + "){"
          + "postId=" + getPostId()
          + "}";
    }
  }
}
