import java.io.*;

public class Streamer extends Thread{

	SyncMonitor sm = Core.sm;
	boolean isSrc;
	File dir;

	public Streamer(String s, boolean b) {
		isSrc = b;
		dir = new File (s);
	}

	@Override
	public void run() {
		if (isSrc) {
			readFromBuf();
		} else {
			writeFromBuf();
		}
	}

	private void writeFromBuf() {
		OutputStream os = null;
		try {
			while (sm.check()) {
				sleep(1000);
			}
			SyncMonitor.Data data = sm.getData();
			File f = new File (Core.dest + data.file);
			System.out.println(f.getAbsolutePath());
			if (!f.exists()) {
				new File (f.getParent()).mkdirs();
				f.createNewFile();
			}

			os = new FileOutputStream(f);
			int length = data.length;
			byte[] buf = data.data;
			while (length > 0) {
				os.write(buf, 0, length);
				while (sm.check()) {
					sleep(5);
				}
				data = sm.getData();
				buf = data.data;
				length = data.length;
			}
			System.out.println("File copied!~ " + f.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	private void readFromBuf() {
		for (File f : dir.listFiles()) {
			if (f.isDirectory()) {
				copyDir (f.getAbsolutePath());
			} else {
				copyFile (f.getAbsolutePath());
			}
		}

	}

	private void copyFile(String path) {
		InputStream is = null;
		try {
			is = new FileInputStream(path);
			byte[] buffer = new byte[Core.bufSuze];
			int length;
			while ((length = is.read(buffer)) > 0) {
				int size = sm.add (buffer, length, path.substring(Core.source.length()));
				if (size > 10000) {
					try {
						sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void copyDir(String path) {
		for (File f : new File (path).listFiles()) {
			if (f.isDirectory()) {
				copyDir (f.getAbsolutePath());
			} else {
				copyFile(f.getAbsolutePath());
			}
		}
	}
}
