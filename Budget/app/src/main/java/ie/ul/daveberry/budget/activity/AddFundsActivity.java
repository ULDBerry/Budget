package ie.ul.daveberry.budget.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import ie.ul.daveberry.budget.R;

public class AddFundsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_fund); //activity_add_funds
    }

    public void addFund(View view){


        Toast toast = Toast.makeText(this, getString(R.string.add_fund_success), Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();

    }
}
