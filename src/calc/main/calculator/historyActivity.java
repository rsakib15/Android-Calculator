package calc.main.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class historyActivity extends Activity{
	public static final String fileName="storage";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("DebugLog","Inside onCreate of History");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history);
	}
}
