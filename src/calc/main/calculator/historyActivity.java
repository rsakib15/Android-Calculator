package calc.main.calculator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class historyActivity extends Activity{
	public static final String fileName="storage";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("DebugLog","Inside onCreate of History");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history);
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		readExternalFile();
	}

	public void readInternalFile() {
		TableLayout table = (TableLayout)findViewById(R.id.historyTable);
		
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
		TableLayout table = (TableLayout)findViewById(R.id.historyTable);
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
}


