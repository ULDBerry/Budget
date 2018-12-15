package ie.ul.daveberry.budget.view;

import java.util.List;

import ie.ul.daveberry.budget.model.Expense;

public interface TodaysExpenseView {
  void displayTotalExpense(Long totalExpense);
  void displayTodaysExpenses(List<Expense> expenses);
}
