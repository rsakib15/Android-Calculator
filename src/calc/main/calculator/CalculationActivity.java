package calc.main.calculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class CalculationActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d("EntryLog","Inside onCreate() on Calculation Activity");
		Bundle e=getIntent().getExtras();
		this.Calculation(e);
	}

	public void Calculation(Bundle e) {
		Intent intent=new Intent();
		if(e==null) 
			this.setResult(RESULT_CANCELED,intent);
		else {
			Log.d("Entry-Log","Inside Calculation() method of Calculation Activity");
			String firstNumber,secondNumber,operator;
			firstNumber=e.getString("firstNumber");
			secondNumber=e.getString("secondNumber");
			operator=e.getString("operator");
			
			if(firstNumber.length()==0 || secondNumber.length()==0){
				setResult(RESULT_CANCELED,intent);
			}
			else{
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
					firstNumber=Integer.toString(intresult);
				}
				else{
					firstNumber= Double.toString(result);
				}
				setResult(RESULT_OK,intent);
				intent.putExtra("result", firstNumber);
			}
		}
		finish();
	}
	
}