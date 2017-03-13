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

public class historyActivity extends Activity{
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
		Intent i=new Intent(getApplicationContext(),calculatorActivity.class);
		startActivity(i);
	}

	public void readInternalFile() {
		table = (TableLayout)findViewById(R.id.historyTable);
		try{
			int count=0;
			TableRow tr = null;
			TextView tv = null;
			String tText="";
			BufferedReader inputReader=new BufferedReader(
					new InputStreamReader(openFileInput(calculatorActivity.Internal_File_Name)));
			String input;
			while((input=inputReader.readLine())!=null) {
				Log.d("DebugLog",input);
				if(count==0) {
					tText="";
					tr=new TableRow(this);
					tr.setLayoutParams(new TableRow.LayoutParams(
							TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
					tv=new TextView(this);
					tv.setLayoutParams(new TableRow.LayoutParams(
							TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
					tv.setClickable(true);
				}
				tText=tText+input+"\n";
				count++;
				if(count==3) {
					count=0;
					tv.setText(tText);
					tr.addView(tv);
					table.addView(tr,new TableLayout.LayoutParams(
							TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
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
			TableRow tr = null;
			TextView tv = null;
			String tText="";
			FileInputStream fIn = new FileInputStream(myFile);
	        BufferedReader myReader = new BufferedReader( new InputStreamReader(fIn));
			String input;
			while((input=myReader.readLine())!=null) {
				Log.d("DebugLog",input);
				if(count==0) {
					tText="";
					tr=new TableRow(this);
					tr.setLayoutParams(new TableRow.LayoutParams(
							TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
					tv=new TextView(this);
					tv.setLayoutParams(new TableRow.LayoutParams(
							TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
					tv.setClickable(true);
				}
				tText=tText+input+"\n";
				count++;
				if(count==3) {
					count=0;
					tv.setText(tText);
					tr.addView(tv);
					table.addView(tr,new TableLayout.LayoutParams(
							TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
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
				TableRow tr = null;
				TextView tv = null;
				String tText="";
				BufferedReader inputReader=new BufferedReader(
						new InputStreamReader(openFileInput(calculatorActivity.Local_File_name)));
				String input;
				while((input=inputReader.readLine())!=null) {
					Log.d("DebugLog",input);
					if(count==0) {
						tText="";
						tr=new TableRow(this);
						tr.setLayoutParams(new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
						tr.setGravity(android.view.Gravity.CENTER);
						tv=new TextView(this);
						tv.setLayoutParams(new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
						tv.setClickable(true);
						tv.setOnClickListener(new View.OnClickListener() {
							public void onClick(View arg0) {
								onViewClick(arg0);	
							}
						});
					}
					tText=tText+input+"\n";
					count++;
					if(count==3) {
						count=0;
						tv.setText(tText);
						tr.addView(tv);
						table.addView(tr,new TableLayout.LayoutParams(
								TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
					}
				}
			}
			catch(Exception e) {
				Log.d("ExceptionLog",e.getMessage());
			}
		}
		
		public void GetDb() {
			Log.d("DebugLog","Inside GetDatabase of History");
			List<history> allHistory=calculatorActivity.db.getAllHistory();
			Log.d("DebugLog","Inside All Database get of History");
			table=(TableLayout)findViewById(R.id.historyTable);
			for(history his:allHistory) {
				assignRow(his);
			}
		}
		
		public void assignRow(history history) {
			TableRow tr;
			TextView tv;
			String tText=""+history.getTime()+"\n"+history.getEquation()+"\n"
					+history.getResult();
			tr=new TableRow(this);
			tr.setLayoutParams(new TableRow.LayoutParams(
					TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
			tr.setGravity(android.view.Gravity.CENTER);
			tv=new TextView(this);
			tv.setLayoutParams(new TableRow.LayoutParams(
					TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));
			tv.setClickable(true);
			tv.setOnClickListener(new View.OnClickListener() {
				public void onClick(View arg0) {
					onViewClick(arg0);	
				}
			});
			tv.setText(tText);
			tr.addView(tv);
			table.addView(tr,new TableLayout.LayoutParams(
					TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
		}
		
		public void onViewClick(View arg0) {
			TextView view=(TextView) arg0;
			Log.d("DebugLog","Inside OnViewClick");
			Intent i=new Intent(getApplicationContext(),calculatorActivity.class);
			String text[]=view.getText().toString().split("\\n");
			i.putExtra("history", text[1]);
			i.putExtra("result", text[2]);
			
			startActivity(i);
		}
	}
	
