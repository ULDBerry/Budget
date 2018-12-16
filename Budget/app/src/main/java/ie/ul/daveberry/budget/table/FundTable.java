package ie.ul.daveberry.budget.table;

import android.provider.BaseColumns;

public class FundTable implements BaseColumns {
    public static final String TABLE_NAME = "funds";
    public static final String AMOUNT = "amount";

    public static final String CREATE_TABLE_QUERY = "create table " + TABLE_NAME + " ("+
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            AMOUNT +" REAL)";


    public static final String SELECT_FUND = "SELECT amount FROM " + TABLE_NAME + " LIMIT 1";


}
