package calc.main.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

public class calculatorActivity extends Activity{
	
	private TextView display,history;
	private String firstNumber,secondNumber,operator;
	private boolean isResult,isDot;
	public static final String shared_preference="CalculatorPreference";
	

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
		Toast.makeText(this, "Wecome to the Calculator",Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d("Entry-Log","Inside the onResume() method on main Activity");
		this.display=(TextView)findViewById(R.id.display);
		this.history=(TextView)findViewById(R.id.history);
		this.display.setText("0");
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
		Log.d("Entry-Log","Inside Calculation() method of Main Activity");
		
		if(this.firstNumber.length()==0 || this.secondNumber.length()==0){
			return;
		}
		
		double first=Double.valueOf(firstNumber);
		double second=Double.valueOf(secondNumber);
		double result=first;
		
		Log.d("DebugLog","Value of first number is: " + first);
		Log.d("DebugLog","Value of second number is: "+second);
		
		if(operator.equals("+")){
			result=first+second;
		}
		else if(operator.equals("-")){
			result=first-second;
		}
		else if(operator.equals("X")){
			result=first*second;
		}
		else if(operator.equals("/")){
			result=first/second;
		}
		else if(operator.equals("%")){
			result=(first*second)/100;
		}
		
		Log.d("DebugLog","Value of Result is: " + result);
		
		int intresult=(int)result;
		if(intresult==result){
			this.firstNumber=Integer.toString(intresult);
		}
		else{
			this.firstNumber= Double.toString(result);
		}
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
		this.display.setText(firstNumber);
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
}
