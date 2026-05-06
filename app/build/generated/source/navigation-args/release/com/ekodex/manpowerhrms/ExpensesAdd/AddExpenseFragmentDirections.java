package com.ekodex.manpowerhrms.ExpensesAdd;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.ekodex.manpowerhrms.R;

public class AddExpenseFragmentDirections {
  private AddExpenseFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionAddExpenseFragmentToExpensesFragment() {
    return new ActionOnlyNavDirections(R.id.action_addExpenseFragment_to_expensesFragment);
  }
}
