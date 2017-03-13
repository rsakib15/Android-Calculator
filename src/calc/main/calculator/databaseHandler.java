package calc.main.calculator;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class databaseHandler extends SQLiteOpenHelper{
	private static final int DATABASE_VERSION=1;
	private static final String DATABASE_NAME= "historyDB";
	private static final String TABLE_HISTORY="history";
	
	private static final String HISTORY_KEY_ID="id";
	private static final String HISTORY_KEY_TIME="time";
	private static final String HISTORY_KEY_EQUATION="equation";
	private static final String HISTORY_KEY_RESULT="result";
		
	public databaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		String CREATE_HISTORY_TABLE = "CREATE TABLE " + TABLE_HISTORY + "("
                + HISTORY_KEY_ID + " INTEGER PRIMARY KEY," + HISTORY_KEY_TIME + " TEXT,"
                + HISTORY_KEY_EQUATION + " TEXT,"
                + HISTORY_KEY_RESULT + " TEXT"
                + ")";
		
		Log.d("DebugLog",CREATE_HISTORY_TABLE);
        
        arg0.execSQL(CREATE_HISTORY_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
		this.onCreate(arg0);
	}
	
	void addHistory(history history) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(HISTORY_KEY_TIME, history.getTime());
        values.put(HISTORY_KEY_EQUATION, history.getEquation());
        values.put(HISTORY_KEY_RESULT, history.getResult());
 
        db.insert(TABLE_HISTORY, null, values);
        db.close(); 
    }
	
	history getHistory(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_HISTORY, new String[] { HISTORY_KEY_TIME,HISTORY_KEY_EQUATION,
        		HISTORY_KEY_RESULT }, HISTORY_KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
       
        if (cursor != null) cursor.moveToFirst();
 
        history history = new history(cursor.getString(0),cursor.getString(1),
        		cursor.getString(2));
        cursor.close();
        return history;
    }
	
	public List<history> getAllHistory() {
		Log.i("debugLog","Inside getAllHistory");
        List<history> historyList = new ArrayList<history>();
        String selectQuery = "SELECT  * FROM " + TABLE_HISTORY;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if (cursor.moveToFirst()) {
            do {
                history history = new history();
                history.setID(Integer.parseInt(cursor.getString(0)));
                history.setTime(cursor.getString(1));
                history.setEquation(cursor.getString(2));
                history.setResult(cursor.getString(3));
                historyList.add(history);
            } 
            while (cursor.moveToNext());
        }
        cursor.close();
        return historyList;
    }
}
