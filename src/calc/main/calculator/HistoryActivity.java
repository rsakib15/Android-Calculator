package calc.main.calculator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import calc.main.calculator.R;

public class HistoryActivity extends Activity{
	public static final String fileName="storage.dat";
	TableLayout table;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("DebugLog","Inside onCreate of History");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Log.d("DebugLog","Inside onResume of History");
		super.onResume();
		//readInternalFile();
		//readExternalFile();
		GetDb();
	}
	
	public void onBackClick(View arg0) {
		Log.d("DebugLog","Inside OnBackClick");
		Intent i=new Intent(getApplicationContext(),CalculatorActivity.class);
		startActivity(i);
	}

	public void readInternalFile() {
		table = (TableLayout)findViewById(R.id.historyTable);
		try{
			int count=0;
			TableRow tablerow = null;
			TextView tableview = null;
			String tableText="";
			BufferedReader inputReader=new BufferedReader(new InputStreamReader(openFileInput(CalculatorActivity.Internal_File_Name)));
			String input;
			while((input=inputReader.readLine())!=null) {
				if(count==0) {
					tableText="";
					tablerow=new TableRow(this);
					tablerow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
					tableview=new TextView(this);
					tableview.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
					tableview.setClickable(true);
				}
				tableText=tableText+input+"\n\n";
				count++;
				if(count==3) {
					count=0;
					tableview.setText(tableText);
					tablerow.addView(tableview);
					table.addView(tablerow,new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
				}
			}
		}
		catch(Exception e) {
			Log.d("Exception-Log",e.getMessage());
		}
	}
	

	public void readExternalFile() {
		table = (TableLayout)findViewById(R.id.historyTable);
		File myFile = new File("/sdcard/mysdfile.txt");
		
		try{
			int count=0;
			TableRow tablerow = null;
			TextView tableview = null;
			String tableText="";
			FileInputStream file = new FileInputStream(myFile);
	        BufferedReader myReader = new BufferedReader( new InputStreamReader(file));
			String input;
			while((input=myReader.readLine())!=null) {
				Log.d("DebugLog",input);
				if(count==0) {
					tableText="";
					tablerow=new TableRow(this);
					tablerow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
					tableview=new TextView(this);
					tableview.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
					tableview.setClickable(true);
				}
				tableText=tableText+input+"\n\n";
				count++;
				if(count==3) {
					count=0;
					tableview.setText(tableText);
					tablerow.addView(tableview);
					table.addView(tablerow,new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
				}
			}
		}
		catch(Exception e) {
			Log.d("Exception-Log",e.getMessage());
		}
	}
	
	public void ReadStorageFile() {
			table=(TableLayout)findViewById(R.id.historyTable);
			try{
				int count=0;
				TableRow tablerow = null;
				TextView tableview = null;
				String tableText="";
				BufferedReader inputReader=new BufferedReader(new InputStreamReader(openFileInput(CalculatorActivity.Local_File_name)));
				String input;
				while((input=inputReader.readLine())!=null) {
					Log.d("DebugLog",input);
					if(count==0) {
						tableText="";
						tablerow=new TableRow(this);
						tablerow.setLayoutParams(new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
						tablerow.setGravity(android.view.Gravity.CENTER);
						tableview=new TextView(this);
						tableview.setLayoutParams(new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
						tableview.setClickable(true);
						tableview.setOnClickListener(new View.OnClickListener() {
							public void onClick(View arg0) {
								onViewClick(arg0);	
							}
						});
					}
					tableText=tableText+input+"\n\n";
					count++;
					if(count==3) {
						count=0;
						tableview.setText(tableText);
						tablerow.addView(tableview);
						table.addView(tablerow,new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
					}
				}
			}
			catch(Exception e) {
				Log.d("ExceptionLog",e.getMessage());
			}
		}
		
		public void GetDb() {
			Log.d("Debug-Log","Inside GetDatabase of History Activity");
			List<History> allHistory=CalculatorActivity.db.getAllHistory();
			table=(TableLayout)findViewById(R.id.historyTable);
			for(History history : allHistory) {
				assignRow(history);
			}
		}
		
		public void assignRow(History history) {
			TableRow tablerow;
			TextView tableview;
			String tText=""+history.getTime()+ "\n" + history.getEquation() + "\n" + history.getResult();
			tablerow=new TableRow(this);
			tablerow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
			tablerow.setGravity(android.view.Gravity.CENTER);
			tableview=new TextView(this);
			tableview.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
			tableview.setClickable(true);
			tableview.setOnClickListener(new View.OnClickListener() {
				public void onClick(View arg0) {
					onViewClick(arg0);	
				}
			});
			tableview.setText(tText);
			tablerow.addView(tableview);
			table.addView(tablerow,new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
		}
		
		public void onViewClick(View arg0) {
			TextView view=(TextView) arg0;
			Log.d("DebugLog","Inside OnViewClick");
			Intent i=new Intent(getApplicationContext(),CalculatorActivity.class);
			String text[]=view.getText().toString().split("\\n");
			i.putExtra("history", text[1]);
			i.putExtra("result", text[2]);
			startActivity(i);
		}
	}
	
