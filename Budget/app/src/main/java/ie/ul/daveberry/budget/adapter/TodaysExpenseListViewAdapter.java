package ie.ul.daveberry.budget.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

import ie.ul.daveberry.budget.model.Expense;

public class TodaysExpenseListViewAdapter extends ArrayAdapter {
  private final List<Expense> expenses;

  public TodaysExpenseListViewAdapter(List<Expense> expenses, Context context, int resource) {
    super(context, resource);
    this.expenses = expenses;
  }

  @Override
  public int getCount() {
    return expenses.size();
  }

  @Override
  // TODO Add a way to the euro_symobol string
  public Object getItem(int position) {
    Expense expense = expenses.get(position);
    return expense.getType() + " - €" + expense.getAmount();
  }

  @Override
  public long getItemId(int position) {
    return expenses.get(position).getId();
  }
}
