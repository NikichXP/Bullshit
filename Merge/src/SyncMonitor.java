import java.util.*;


public class SyncMonitor {

	private LinkedList<Data> data;

	public SyncMonitor () {
		data = new LinkedList<>();
	}

	public synchronized boolean check () {
		if (data.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public synchronized int add (byte[] data, int length, String name) {
		this.data.add(new Data(data, length, name));
		return this.data.size();
	}

	public Data getData() {
		return data.poll();
	}

	public class Data {
		public byte[] data;
		public String file;
		public int length;

		public Data(byte[] data, int length, String name) {
			this.data = data;
			this.file = name;
			this.length = length;
		}

	}

}
