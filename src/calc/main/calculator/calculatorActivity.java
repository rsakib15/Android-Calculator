package calc.main.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class calculatorActivity extends Activity{
	
	private TextView display,history;
	private String firstNumber,secondNumber,operator;
	private boolean isResult,isClear,flag,isDot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d("Entry-Tag","Inside the OnCreate() method on main Activity");
		setContentView(R.layout.main);
		
		this.firstNumber="";
		this.secondNumber="";
		this.isClear=false;
		this.isResult=false;
		this.operator="";
		this.flag=false;
		this.isDot=false;
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
		this.display.setText(this.display.getText());
		
	}
	
	public void onNumberClick(View arg0) {		
		Button btn=(Button)arg0;
		Log.d("Debug-Log","Inside onNumberClick , Pressed Button "+btn.getText());
		
		if(this.isResult) {
			onReset();
		}
		if(this.display.getText().length()<=11){
			if(this.display.getText().equals("0") && !btn.getText().toString().equals(".")) {
				this.display.setText("");
			}
			if(this.display.getText().equals("0") && btn.getText().toString().equals(".")) {
				this.display.setText("0");
			}
			if(this.display.getText().equals("") && btn.getText().toString().equals(".")) {
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
		Log.d("Debug-Log","Insi de OnOperatorClick , Pressed Operator "+ btn.getText());
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
		isClear=true;
		
		}
	}
	
	private void calculation(){
		if(this.firstNumber.length()==0 || this.secondNumber.length()==0) return;
		Log.d("EntryLog","Inside PerformCalculation of Main Activity");
		double a=Double.valueOf(firstNumber);
		Log.d("DebugLog","Value of a is: "+a);
		double b=Double.valueOf(secondNumber);
		Log.d("DebugLog","Value of b is: "+b);
		double res=a;
		if(operator.equals("+")) res=a+b;
		else if(operator.equals("-")) res=a-b;
		else if(operator.equals("X")) res=a*b;
		else if(operator.equals("/")) res=a/b;
		int rs=(int)res;
		if(rs==res) this.firstNumber=Integer.toString(rs);
		else this.firstNumber= Double.toString(res);
	}
	
	public void onEqualClick(View arg0){
		Button btn=(Button)arg0;
		Log.d("Debug-Log","Inside onEqualClick , Pressed "+ btn.getText());	
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
		Log.d("Entry-Log","Inside onReset");
		this.firstNumber="";
		this.secondNumber="";
		this.isClear=false;
		this.isResult=false;
		this.operator="";
		this.history.setText("");
		this.display.setText("0");
		this.flag=false;
		this.isDot=false;
	}
	
	public void onClear(){
		Log.d("Entry-Log","Inside onClear");
		this.display.setText("0");
		isDot=false;
		flag=false;
	}
}
