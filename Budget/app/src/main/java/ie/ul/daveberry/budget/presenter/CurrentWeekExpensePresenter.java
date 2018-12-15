package ie.ul.daveberry.budget.presenter;

import ie.ul.daveberry.budget.database.ExpenseDatabaseHelper;
import ie.ul.daveberry.budget.utils.ExpenseCollection;
import ie.ul.daveberry.budget.view.CurrentWeekExpenseView;

public class CurrentWeekExpensePresenter {

  private CurrentWeekExpenseView view;
  private ExpenseDatabaseHelper database;
  private ExpenseCollection expenseCollection;

  public CurrentWeekExpensePresenter(ExpenseDatabaseHelper database, CurrentWeekExpenseView view) {
    this.database = database;
    this.view = view;
    expenseCollection = new ExpenseCollection(this.database.getCurrentWeeksExpenses());
  }

  public void renderTotalExpenses() {
    view.displayTotalExpenses(expenseCollection.getTotalExpense());
  }

  public void renderCurrentWeeksExpenses() {
    view.displayCurrentWeeksExpenses(expenseCollection.groupByDate());
  }
}
