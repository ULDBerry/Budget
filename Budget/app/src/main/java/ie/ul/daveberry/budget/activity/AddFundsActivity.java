package ie.ul.daveberry.budget.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ie.ul.daveberry.budget.R;
import ie.ul.daveberry.budget.database.ExpenseDatabaseHelper;

public class AddFundsActivity extends Activity {

    // todo add getter and setter for funds
    private TextView mFundsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_fund); //activity_add_funds
        Intent receivedIntent = getIntent();
        String funds = getIntent().getStringExtra("funds");
        mFundsTextView = findViewById(R.id.fundInput);
        mFundsTextView.setText(funds);
    }




    public void addFund(View view){
        //Get the user input

        TextView fundInput = (TextView) findViewById(R.id.fundInput);
        ExpenseDatabaseHelper mExpenseDatabaseHelper;
        mExpenseDatabaseHelper = new ExpenseDatabaseHelper(this);


        MainActivity.funds.setFundAmount(Double.parseDouble(fundInput.getText().toString()));

        mExpenseDatabaseHelper.updateFund();
        Log.d("database","attempt to save funds",null);
        //refresh expsense view
        ///todaysExpensePresenter.renderTodaysExpenses();
        //todaysExpensePresenter.renderTotalExpense();
        mExpenseDatabaseHelper.close();


        Toast toast = Toast.makeText(this,  getString(R.string.add_fund_success), Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();

    }
}
