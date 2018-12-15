package ie.ul.daveberry.budget.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ie.ul.daveberry.budget.R;
import ie.ul.daveberry.budget.database.ExpenseDatabaseHelper;
import ie.ul.daveberry.budget.presenter.TodaysExpensePresenter;
import ie.ul.daveberry.budget.view.TodaysExpenseView;

public class AddFundsActivity extends Activity {

    // todo add getter and setter for funds
    private TodaysExpenseView view;
    ExpenseDatabaseHelper expenseDatabaseHelper = new ExpenseDatabaseHelper(this);
    TodaysExpensePresenter todaysExpensePresenter = new TodaysExpensePresenter(view, expenseDatabaseHelper);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_fund); //activity_add_funds
    }

    public void addFund(View view){
        //Get the user input
        TextView fundInput = (TextView) findViewById(R.id.fundInput);
        MainActivity.funds.setFundAmount(Double.parseDouble(fundInput.getText().toString()));

        //refresh expsense view
        ///todaysExpensePresenter.renderTodaysExpenses();
        //todaysExpensePresenter.renderTotalExpense();
        expenseDatabaseHelper.close();


        Toast toast = Toast.makeText(this, fundInput.getText().toString() + " " +  getString(R.string.add_fund_success), Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();

    }
}
