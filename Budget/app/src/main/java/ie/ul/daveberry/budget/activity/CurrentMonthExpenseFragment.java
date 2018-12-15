package ie.ul.daveberry.budget.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.echo.holographlibrary.Bar;
import com.echo.holographlibrary.BarGraph;

import java.util.ArrayList;
import java.util.List;

import ie.ul.daveberry.budget.R;
import ie.ul.daveberry.budget.database.ExpenseDatabaseHelper;
import ie.ul.daveberry.budget.presenter.CurrentMonthExpensePresenter;
import ie.ul.daveberry.budget.view.CurrentMonthExpenseView;

public class CurrentMonthExpenseFragment extends Fragment implements CurrentMonthExpenseView {

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.expense_graph, container, false);
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    ExpenseDatabaseHelper expenseDatabaseHelper = new ExpenseDatabaseHelper(getActivity());
    CurrentMonthExpensePresenter presenter = new CurrentMonthExpensePresenter(this, expenseDatabaseHelper);

    presenter.plotGraph();
    presenter.showTotalExpense();
    expenseDatabaseHelper.close();
  }

  @Override
  public void displayGraph(List<Bar> points) {
    BarGraph graph = (BarGraph)getActivity().findViewById(R.id.graph);
    graph.setBars((ArrayList<Bar>) points);
  }

  @Override
  public void displayTotalExpense(Long totalExpense) {
    TextView totalExpenseTextBox = (TextView) getActivity().findViewById(R.id.current_months_total_expense);
    //
    totalExpenseTextBox.setText(getString(R.string.todays_funds) + getString(R.string.total_expense) + " " + getString(R.string.euro_sym) + totalExpense);
  }

  @Override
  public int getGraphColor() {
    return getActivity().getResources().getColor(R.color.light_blue);
  }
}
