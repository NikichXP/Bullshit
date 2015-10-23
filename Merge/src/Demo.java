import java.util.LinkedList;

/**
 * Demo of how the threads should cooperate
 */
public class Demo {
	static Monitor m = new Monitor();

	public static void main (String [] args) {
		Sender s = new Sender();
		Reciever r = new Reciever();
		s.start();
		r.start();
	}

	static class Sender extends Thread {
		public void run ()  {
			int i = 0;
			while (true) {
				m.list.add("text" + i);
				i++;
				try {
					//sleep((int) (Math.random() * 1000));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	static class Reciever extends Thread {
		public void run () {
			while (true) {
				try {
					//sleep((int) (Math.random() * 1000));
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println(m.list.poll());
			}
		}
	}

	static class Monitor {
		LinkedList<String> list = new LinkedList<>();
	}
}
