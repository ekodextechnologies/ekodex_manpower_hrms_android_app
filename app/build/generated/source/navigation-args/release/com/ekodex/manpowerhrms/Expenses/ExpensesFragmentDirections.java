package com.ekodex.manpowerhrms.Expenses;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.ekodex.manpowerhrms.R;

public class ExpensesFragmentDirections {
  private ExpensesFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionExpensesFragmentToAddExpenseFragment() {
    return new ActionOnlyNavDirections(R.id.action_expensesFragment_to_addExpenseFragment);
  }
}
