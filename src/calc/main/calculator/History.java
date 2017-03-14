package calc.main.calculator;

public class History {
	private int id;
	private String time;
	private String equation;
	private String result;
	
	public History() {
		this.id=0;
		this.time="";
		this.equation="";
		this.result="";
	}
	public History(String time,String equations,String result) {
		this.time=time;
		this.equation=equations;
		this.result=result;
	}
	
	public String getTime() {
		return this.time;
	}
	
	public String getEquation() {
		return this.equation;
	}
	
	public String getResult() {
		return this.result;
	}
	
	public void setTime(String time) {
		this.time=time;
	}
	
	public void setEquation(String equation) {
		this.equation=equation;
	}
	
	public void setResult(String result) {
		this.result=result;
	}
	public void setID(int parseInt) {
		this.id=parseInt;
	}

}
