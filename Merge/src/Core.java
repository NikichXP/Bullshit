
public class Core {

	public static SyncMonitor sm = new SyncMonitor();
	public static int bufSuze = 0xF0;
	public static String source = "B:/MSc/";
	public static String dest = "X:/MSc/";

	public static void main(String[] args) {

		Streamer inStream = new Streamer (source, true);
		System.out.println("1");
		Streamer outStream = new Streamer (dest, false);
		System.out.println("2");

		inStream.start();
		System.out.println("3");
		outStream.start();
		System.out.println("5");


	}

}
