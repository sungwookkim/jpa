package common.util;

import java.util.stream.Collectors;

public class Print {
	private String printStr = "";
	private String printFormat = "=============== %s ===============";
	
	public void Prit() { }
	
	public Print mainStartPrint(String printStr) {
		this.printStr = printStr;
		this.startStr(this.printFormat, printStr);
		
		return this;
	}
	
	public Print mainEndPrint() {
		this.endStr("=");

		return this;
	}
	
	public Print subStartPrint(String printStr) {
		this.printStr = printStr;
		this.startStr(this.printFormat.replaceAll("=", "-"), printStr);
		
		return this;
	}
	
	public Print subEndPrint() {
		this.endStr(this.printFormat.replaceAll("=", "-"), "-");

		return this;			
	}

	private void startStr(String format, String printStr) {
		System.out.println(String.format(format, printStr));
	}
	
	private void endStr(String rtnStr) {
		this.endStr(this.printFormat, rtnStr);
	}
	
	private void endStr(String format, String rtnStr) {
		String str = this.printStr.chars().mapToObj(c -> {
			char checkC = (char)c;
			
			if(0xAC00 <= checkC && 0xD7AF >= checkC) {
				return rtnStr + rtnStr;
			}
			
			return rtnStr;
		}).collect(Collectors.joining());

		System.out.println(String.format(format.replaceAll(" ", rtnStr), str));		
	}
}
