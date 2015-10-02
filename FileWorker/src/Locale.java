public class Locale {
	private static String loc;
	public Locale () {
		
	}
	public void setLocale (String loc) {
		Locale.loc = loc;
	}
	
	
	public String free() {
		switch (loc) {
		case "ruRU":
			return "Cвободно: ";
		default:
			return "Free: ";
		}
	}
	public String fwd() {
		switch (loc) {
		case "ruRU":
			return "Вперед";
		default:
			return "Forward";
		}
	}
}
