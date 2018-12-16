package ie.ul.daveberry.budget.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ie.ul.daveberry.budget.activity.MainActivity;
import ie.ul.daveberry.budget.model.DBFunds;
import ie.ul.daveberry.budget.model.Expense;
import ie.ul.daveberry.budget.model.ExpenseType;
import ie.ul.daveberry.budget.table.ExpenseTable;
import ie.ul.daveberry.budget.table.ExpenseTypeTable;
import ie.ul.daveberry.budget.table.FundTable;
import ie.ul.daveberry.budget.utils.DateUtil;

import static ie.ul.daveberry.budget.utils.DateUtil.getCurrentDate;
import static ie.ul.daveberry.budget.utils.DateUtil.getCurrentWeeksDates;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

public class ExpenseDatabaseHelper extends SQLiteOpenHelper {
  public static final String EXPENSE_DB = "expense";

//Create Database
  public ExpenseDatabaseHelper(Context context) {
    super(context, EXPENSE_DB, null, 1);
  }

  //todo add create funds table
  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL(ExpenseTable.CREATE_TABLE_QUERY);
    sqLiteDatabase.execSQL(ExpenseTypeTable.CREATE_TABLE_QUERY);
    sqLiteDatabase.execSQL(FundTable.CREATE_TABLE_QUERY);
    seedExpenseTypes(sqLiteDatabase);
    seedFund(sqLiteDatabase);
  }

  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

  }

  public List<String> getExpenseTypes() {
    ArrayList<String> expenseTypes = new ArrayList<>();

    SQLiteDatabase database = this.getWritableDatabase();
    Cursor cursor = database.rawQuery(ExpenseTypeTable.SELECT_ALL, null);

    if(isCursorPopulated(cursor)){
      do {
        String type = cursor.getString(cursor.getColumnIndex(ExpenseTypeTable.TYPE));
        expenseTypes.add(type);
      } while(cursor.moveToNext());
    }

    return expenseTypes;
  }

  public void deleteAll() {
    SQLiteDatabase database = this.getWritableDatabase();
    database.delete(ExpenseTypeTable.TABLE_NAME, "", new String[]{});
    database.delete(ExpenseTable.TABLE_NAME, "", new String[]{});
    database.close();
  }

  public void addExpense(Expense expense) {
    SQLiteDatabase database = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(ExpenseTable.AMOUNT, expense.getAmount());
    values.put(ExpenseTable.TYPE, expense.getType());
    values.put(ExpenseTable.DATE, expense.getDate());

    database.insert(ExpenseTable.TABLE_NAME, null, values);
  }

//todo took out Expsense expense which is the model, i dont have a fund model
    public void updateFund() {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FundTable.AMOUNT, MainActivity.funds.getFundAmount());
        database.update(FundTable.TABLE_NAME, values,"_id = 1",null);
    }

  //public List<DBFunds> getFund() {
  //  SQLiteDatabase database = this.getWritableDatabase();
  //  Cursor cursor = database.rawQuery(FundTable.SELECT_FUND, null);
  //  return buildFunds(cursor);
  //}

    public double getFunds(){

      SQLiteDatabase database = this.getReadableDatabase();

      Cursor cursor = database.rawQuery(FundTable.SELECT_FUND,null);

      // 3. if we got results get the first one
      if (cursor != null)
        cursor.moveToFirst();

      return cursor.getDouble(0);

    }


  public List<Expense> getExpenses() {
    SQLiteDatabase database = this.getWritableDatabase();
    Cursor cursor = database.rawQuery(ExpenseTable.SELECT_ALL, null);

    return buildExpenses(cursor);
  }

  public List<Expense> getTodaysExpenses() {
    SQLiteDatabase database = this.getWritableDatabase();
    Cursor cursor = database.rawQuery(ExpenseTable.getExpensesForDate(getCurrentDate()), null);

    return buildExpenses(cursor);
  }

  public List<Expense> getCurrentWeeksExpenses() {
    SQLiteDatabase database = this.getWritableDatabase();
    Cursor cursor = database.rawQuery(ExpenseTable.getConsolidatedExpensesForDates(getCurrentWeeksDates()), null);
    return buildExpenses(cursor);
  }

  public List<Expense> getExpensesGroupByCategory() {
    SQLiteDatabase database = this.getWritableDatabase();
    Cursor cursor = database.rawQuery(ExpenseTable.SELECT_ALL_GROUP_BY_CATEGORY, null);
    return buildExpenses(cursor);
  }

  public List<Expense> getExpensesForCurrentMonthGroupByCategory() {
    SQLiteDatabase database = this.getWritableDatabase();
    Cursor cursor = database.rawQuery(ExpenseTable.getExpenseForCurrentMonth(DateUtil.currentMonthOfYear()), null);
    return buildExpenses(cursor);
  }

  public void addExpenseType(ExpenseType type) {
    SQLiteDatabase database = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(ExpenseTable.TYPE, type.getType());

    database.insert(ExpenseTypeTable.TABLE_NAME, null, values);
  }

  public void truncate(String tableName) {
    SQLiteDatabase database = this.getWritableDatabase();
    database.execSQL("delete from " + tableName);
  }

  private List<Expense> buildExpenses(Cursor cursor) {
    List<Expense> expenses = new ArrayList<>();
    if(isCursorPopulated(cursor)){
      do {
        String type = cursor.getString(cursor.getColumnIndex(ExpenseTable.TYPE));
        String amount = cursor.getString(cursor.getColumnIndex(ExpenseTable.AMOUNT));
        String date = cursor.getString(cursor.getColumnIndex(ExpenseTable.DATE));
        String id = cursor.getString(cursor.getColumnIndex(ExpenseTable._ID));

        Expense expense = id == null ? new Expense(parseLong(amount), type, date) : new Expense(parseInt(id), parseLong(amount), type, date);
        expenses.add(expense);
      } while(cursor.moveToNext());
    }

    return expenses;
  }

  private List<DBFunds> buildFunds(Cursor cursor) {
    List<DBFunds> dbfunds = new ArrayList<>();
    if(isCursorPopulated(cursor)){
      do {
        String amount = cursor.getString(cursor.getColumnIndex(FundTable.AMOUNT));
        String id = cursor.getString(cursor.getColumnIndex(FundTable._ID));

        DBFunds dbfund = id == null ? new DBFunds(parseDouble(amount)) : new DBFunds(parseInt(id), parseDouble(amount));
        dbfunds.add(dbfund);
      } while(cursor.moveToNext());
    }

    return dbfunds;
  }


  private boolean isCursorPopulated(Cursor cursor) {
    return cursor != null && cursor.moveToFirst();
  }

  private void seedExpenseTypes(SQLiteDatabase sqLiteDatabase) {
    List<ExpenseType> expenseTypes = ExpenseTypeTable.seedData();
    for (ExpenseType expenseType : expenseTypes) {
      ContentValues contentValues = new ContentValues();
      contentValues.put(ExpenseTypeTable.TYPE, expenseType.getType());

      sqLiteDatabase.insert(ExpenseTypeTable.TABLE_NAME, null, contentValues);
    }

  }

  private void seedFund(SQLiteDatabase sqLiteDatabase) {
    //SQLiteDatabase database = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(FundTable.AMOUNT, 0);
    sqLiteDatabase.insert(FundTable.TABLE_NAME, null, values);
    }



}
