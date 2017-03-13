package calc.main.calculator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;

public class calculatorActivity extends Activity{
	private TextView display,history;
	private String firstNumber,secondNumber,operator;
	private boolean isResult,isDot;
	public static final String shared_preference="CalculatorPreference";
	public static final String Internal_File_Name="CalculatorData";
	public static final String External_File_Name="CalculatorData";
	public static final String Local_File_name="CalculatorData";
	public static databaseHandler db;
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d("Entry-Tag","Inside the OnStart() method on main Activity");

		switch (getResources().getConfiguration().orientation) {
        	case Configuration.ORIENTATION_PORTRAIT:
        		setContentView(R.layout.main);
        		break;
        	case Configuration.ORIENTATION_LANDSCAPE:
        		setContentView(R.layout.landscape);
        		break;
		}
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d("Entry-Tag","Inside the OnCreate() method on main Activity");
		setContentView(R.layout.main);
		this.firstNumber="";
		this.secondNumber="";
		this.operator="";
		this.isResult=false;
		this.isDot=false;
		Toast.makeText(this, "Welcome to the Calculator",Toast.LENGTH_SHORT).show();
		
		Bundle extras=getIntent().getExtras();
		if(extras!=null) {
			this.history.setText(extras.getString("history"));
			this.display.setText(extras.getString("result"));
			this.isResult=true;
			if(extras.getString("history")!=null) {
				String parts[]=extras.getString("history").split(" ");
				this.firstNumber=extras.getString("result");
				this.operator=parts[1];
				this.secondNumber=parts[2];
			}
		}
		
		Log.d("EntryLog","Inside OnCreate of Main Activity");
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d("Entry-Log","Inside the onResume() method on main Activity");
		this.display=(TextView)findViewById(R.id.display);
		this.history=(TextView)findViewById(R.id.history);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Log.d("Entry-Log","Inside OnPause of Main Activity");
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		Log.d("EntryLog","Inside OnStop of Main Activity");	
	}
	
	public void onNumberClick(View arg0) {		
		Button btn=(Button)arg0;
		Log.d("Debug-Log","Inside onNumberClick , Pressed Button " + btn.getText());
		
		if(this.isResult) {
			onReset();
		}
		
		if(this.display.getText().toString().length()<=11){
			if(this.display.getText().equals("0") && !btn.getText().toString().equals(".")) {
				this.display.setText("");
			}
			if(this.display.getText().toString().equals("0") && btn.getText().toString().equals(".")) {
				this.display.setText("0");
			}
			if(this.display.getText().toString().equals("") && btn.getText().toString().equals(".")) {
				this.display.setText("0");
			}
			if(btn.getText().toString().equals(".") && !isDot){
				this.display.setText(this.display.getText().toString() + btn.getText().toString());	
				isDot=true;
			}
			if(!btn.getText().toString().equals(".")){
				this.display.setText(this.display.getText().toString() + btn.getText().toString());	
			}
		}
	}
	
	public void onOperatorClick(View arg0){
		Button btn=(Button)arg0;
		Log.d("Debug-Log","Inside OnOperatorClick , Pressed Operator "+ btn.getText());
		this.operator=btn.getText().toString();
		this.isDot=false;
		 
		if(this.firstNumber.length()==0) {
			this.firstNumber=this.display.getText().toString();
			this.history.setText(this.firstNumber+" "+operator);
			this.display.setText("");
		}
		else if(this.isResult) {
			this.isResult=false;
			this.history.setText(this.firstNumber+" "+operator);
			this.display.setText("");
		}
		else {
			this.secondNumber=this.display.getText().toString();
			if(this.secondNumber.length()==0) {
				this.history.setText(this.firstNumber+" "+operator);
				return;
		}
		this.calculation();
		this.history.setText(this.firstNumber+" "+operator);
		this.display.setText("");
		}
	}
	
	private void calculation(){
		Intent i=new Intent(getApplicationContext(),CalculationActivity.class);
		Log.d("Entry-Log","Inside Calculation() method of Main Activity");
		i.putExtra("first", this.firstNumber);
		i.putExtra("second", this.secondNumber);
		i.putExtra("op", this.operator);
		i.putExtra("history", this.history.getText());
		Log.d("Entry-Log","Inside done Calculation() of Main Activity");
		//startActivityForResult(i, 1);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==1) {
			if(resultCode==RESULT_OK) {
				Log.d("OnActivity Result",data.getStringExtra("result"));
				this.firstNumber=data.getStringExtra("result");
				this.display.setText(this.firstNumber);
				InsertDb(this.history.getText().toString(), this.firstNumber);
			}
			
		}
	}
	
	public void InsertDb(String data,String data2) {
		SimpleDateFormat df=new SimpleDateFormat("dd-MMM-yyyy hh:mm");
		String date=df.format(new Date());
		db.addHistory(new history(date,data,data2));
	}
	
	public void onEqualClick(View arg0){
		Button btn=(Button)arg0;
		Log.d("Debug-Log","Inside onEqualClick , Pressed "+ btn.getText());	
		if(this.firstNumber.length()==0) 
			return;
		if(this.isResult) {
			this.history.setText(this.firstNumber+" "+operator+" "+this.secondNumber);
			calculation();
		}
		else {
			secondNumber=this.display.getText().toString();
			this.history.setText(this.firstNumber+" "+operator+" "+this.secondNumber);
			this.calculation();		
			this.isResult=true;
		}
		//this.display.setText(firstNumber);
	}
	
	public void onResetClick(View arg0){
		Button btn=(Button)arg0;
		Log.d("Debug-Log","Inside onReset Click , Pressed " + btn.getText());
		onReset();
	}
	
	public void onClearClick(View arg0) {
		Button btn=(Button)arg0;
		Log.d("Debug-Log","Inside onClearClick , Pressed " + btn.getText());
		onClear();
	}
	
	public void onReset(){
		Log.d("Entry-Log","Inside onReset() function");
		this.firstNumber="";
		this.secondNumber="";
		this.isResult=false;
		this.operator="";
		this.history.setText("");
		this.display.setText("0");
		this.isDot=false;
	}
	
	public void onClear(){
		Log.d("Entry-Log","Inside onClear");
		this.display.setText("0");
		isDot=false;
	}
	
	public void onMemoryStore(View arg0){
		Button btn=(Button) arg0;
		String str;
		Log.d("Debug-Log",this.firstNumber.length()+"");
		
		if(this.firstNumber.length()==0) {
			str=this.display.getText().toString();
		}
		else if(this.isResult) {
			str=this.display.getText().toString();
		}
		else {
			secondNumber=display.getText().toString();
			history.setText(history.getText()+" "+this.secondNumber);
			calculation();
			display.setText(firstNumber);			
			str=firstNumber;
			isResult=true;	
		}
		
		String currentValue=this.getMemoryValue();
		double f=Double.parseDouble(currentValue);
		double s=Double.parseDouble(str);
		if(btn.getText().toString().equals("M+")){
			int res=(int)f+(int)s;
			setMemoryValue(String.valueOf(res));
		}
		else if(btn.getText().toString().equals("M-")){
			int res=(int)f-(int)s;
			setMemoryValue(String.valueOf(res));
		}
		
		Toast.makeText(this,"Value Stored " + getMemoryValue(), Toast.LENGTH_SHORT).show();
	}
	
	public void onMemoryShow(View arg0){
		display.setText(this.getMemoryValue());
		Toast.makeText(this, "Value in Memory : "+this.getMemoryValue(), Toast.LENGTH_SHORT).show();
	}
	
	public void onMemoryClear(View arg0){
		this.setMemoryValue("0");
		this.history.setText(""); 
		Toast.makeText(this, "Memory Cleared", Toast.LENGTH_SHORT).show();
	}
	
	
	public void setMemoryValue(String str){
		SharedPreferences settings=getSharedPreferences(calculatorActivity.shared_preference,0);
		SharedPreferences.Editor editor=settings.edit();
		editor.putString("storevalue",str);
		editor.commit();
	}
	
	public String getMemoryValue(){
		SharedPreferences settings=getSharedPreferences(calculatorActivity.shared_preference,0);
		String val=settings.getString("storevalue", "0");
		if(val.length()==0){
			val="0";
		}
		return val;
	}
	
	public void onHistoryActivity(View arg0){
		Log.d("DebugLog","Inside OnHistory");
		Intent i=new Intent(getApplicationContext(),historyActivity.class);
		startActivity(i);
	}

	
	public void WriteInternal(String h,String d){
		FileOutputStream fs;
		SimpleDateFormat df=new SimpleDateFormat("dd-MMM-yyyy hh:mm");
		String date=df.format(new Date());
		
		try{
			fs=openFileOutput(Internal_File_Name, MODE_PRIVATE | MODE_APPEND);
			fs.write(date.getBytes());
			fs.write(System.getProperty("line.separator").getBytes());
			fs.write(h.getBytes());
			fs.write(System.getProperty("line.separator").getBytes());
			fs.write(d.getBytes());
			fs.write(System.getProperty("line.separator").getBytes());
			fs.flush();
			fs.close();
		}
		catch(Exception e) {
			Log.d("ExceptionLog",e.getMessage());
		}
	}
	
	public void WriteExternal(String h,String d){
		FileOutputStream fs;
		SimpleDateFormat df=new SimpleDateFormat("dd-MMM-yyyy hh:mm");
		String date=df.format(new Date());
		File externalStorageDir = Environment.getExternalStorageDirectory();
		File myFile = new File(externalStorageDir , "mysdfile.txt");
		try {
				FileOutputStream fOut = new FileOutputStream(myFile);
		       OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
		       myOutWriter.append("test");
		       myOutWriter.append(System.getProperty("line.separator").toString());
		       myOutWriter.append("test");
		       myOutWriter.append(System.getProperty("line.separator").toString());
		       myOutWriter.append("test");
		       myOutWriter.append(System.getProperty("line.separator").toString());
		       myOutWriter.close();
		       fOut.close();
            Toast.makeText(getBaseContext(),"Done writing SD 'mysdfile.txt'",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(),Toast.LENGTH_SHORT).show();
        }
	}
	
	protected void onSaveInstanceState(Bundle outState) {
			super.onSaveInstanceState(outState);
			Log.i("Tag-State Changes", "onSaveInstanceState");
			
			TextView dis =  (TextView) findViewById(R.id.display);
			TextView his = (TextView)findViewById(R.id.history);
			CharSequence displayText = dis.getText();
			CharSequence historyText = his.getText();	
			outState.putCharSequence("saveddisplayText", displayText);
			outState.putCharSequence("savedhistoryText", historyText);
			Log.i("Display text",displayText.toString());
			Log.i("History text",historyText.toString());
	}
	
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		   super.onRestoreInstanceState(savedInstanceState);
		   Log.i("TAG-state Restored", "onRestoreInstanceState");
		   
		   TextView dis =(TextView) findViewById(R.id.display);
		   TextView his = (TextView) findViewById(R.id.history);
		   CharSequence displayText = savedInstanceState.getCharSequence("saveddisplayText");
		   CharSequence historyText = savedInstanceState.getCharSequence("savedhistoryText");
		   dis.setText(displayText);
		   his.setText(historyText);
		   
		   Log.i("value in Resotre " ,displayText.toString());
		   Log.i("Vale on Restore History",historyText.toString());
	}
	

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);

	    // Checks the orientation of the screen
	    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
	        Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
	    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
	        Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
	    }
	}
}