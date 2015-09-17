import javax.swing.Icon;

public class Locale {
	private static String loc;
	public Locale () {
		
	}
	public void setLocale (String loc) {
		this.loc = loc;
	}
	
	
	public String free() {
		switch (loc) {
		case "ruRU":
			return "Свободно: ";
		default:
			return "Free: ";
		}
	}
	public String fwd() {
		switch (loc) {
		case "ruRU":
			return "Открыть";
		default:
			return "Forward";
		}
	}
}
