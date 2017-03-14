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
			String firstNumber,secondNumber,operator;
			firstNumber=e.getString("firstNumber");
			secondNumber=e.getString("secondNumber");
			operator=e.getString("operator");
			if(firstNumber.length()==0 || secondNumber.length()==0) 
				setResult(RESULT_CANCELED,intent);
			else {
				double a=Double.valueOf(firstNumber);
				double b=Double.valueOf(secondNumber);
				double res=a;
				if(operator.equals("+")) 
					res=a+b;
				else if(operator.equals("-")) 
					res=a-b;
				else if(operator.equals("X"))
					res=a*b;
				else if(operator.equals("/")) 
					res=a/b;
				int rs=(int)res;
				if(rs==res) 
					firstNumber=Integer.toString(rs);
				else 
					firstNumber= Double.toString(res);
				
				setResult(RESULT_OK,intent);
				intent.putExtra("result", firstNumber);
			}
		}
		finish();
	}
	
}