package ie.ul.daveberry.budget.view;

import java.util.List;
import java.util.Map;

import ie.ul.daveberry.budget.model.Expense;

public interface CurrentWeekExpenseView {
  void displayCurrentWeeksExpenses(Map<String, List<Expense>> expensesByDate);

  void displayTotalExpenses(Long totalExpense);
}
