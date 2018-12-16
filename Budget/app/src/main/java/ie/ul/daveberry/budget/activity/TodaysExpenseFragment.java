package ie.ul.daveberry.budget.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ie.ul.daveberry.budget.R;
import ie.ul.daveberry.budget.adapter.TodaysExpenseListViewAdapter;
import ie.ul.daveberry.budget.database.ExpenseDatabaseHelper;
import ie.ul.daveberry.budget.model.Expense;
import ie.ul.daveberry.budget.presenter.TodaysExpensePresenter;
import ie.ul.daveberry.budget.view.TodaysExpenseView;

public class TodaysExpenseFragment extends Fragment implements TodaysExpenseView {

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.todays_expenses, container, false);
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    ExpenseDatabaseHelper expenseDatabaseHelper = new ExpenseDatabaseHelper(this.getActivity());
    TodaysExpensePresenter todaysExpensePresenter = new TodaysExpensePresenter(this, expenseDatabaseHelper);

    todaysExpensePresenter.renderTodaysExpenses();
    todaysExpensePresenter.renderTotalExpense();
    expenseDatabaseHelper.close();
  }

  @Override
  // TODO: 14/12/2018  add funds text
  public void displayTotalExpense(Long totalExpense) {

    double myFunds = MainActivity.funds.getFundAmount();
    double mytotalExpenses = totalExpense.doubleValue();

    TextView totalExpenseTextBox = (TextView) getActivity().findViewById(R.id.total_expense);
    TextView fundsTextBox = (TextView) getActivity().findViewById(R.id.todays_funds);
    TextView fundsBalanceTextBox = (TextView) getActivity().findViewById(R.id.fund_balance);
    fundsTextBox.setText("Todays Funds " + getActivity().getString(R.string.euro_sym) + String.format("%.2f",myFunds));
    totalExpenseTextBox.setText(getActivity().getString(R.string.total_expense) + " " + getActivity().getString(R.string.euro_sym) + String.format("%.2f",mytotalExpenses));

    if (myFunds - mytotalExpenses <= 0) {
      fundsBalanceTextBox.setTextColor(Color.RED);
    } else {
      fundsBalanceTextBox.setTextColor(Color.BLACK);
    }

    fundsBalanceTextBox.setText("Balance " + getActivity().getString(R.string.euro_sym) + String.format("%.2f",(myFunds - mytotalExpenses)));
  }
    @Override
  public void displayTodaysExpenses(List<Expense> expenses) {
    ListView listView = (ListView) getActivity().findViewById(R.id.todays_expenses_list);
    listView.setAdapter(new TodaysExpenseListViewAdapter(expenses, getActivity(), android.R.layout.simple_list_item_1));
  }
}
