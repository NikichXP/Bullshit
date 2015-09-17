import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class Configurator {
	private static Configurator instance = new Configurator();
	private Configurator () {
		Configurator.key = new ArrayList<String> ();
		Configurator.value = new ArrayList<String> ();
	}
	public static Configurator getConfigurator(){
		return instance;
	}
	private String defaultSettingsFile = new String ("D:/settings.pce");
	
	private String settingsFile;
	private static ArrayList<String> key;
	private static ArrayList<String> value;

	public void load (String setFile) {
		List<String> list;
		if (setFile != null) {
			this.settingsFile = setFile;
		} else {
			setFile = defaultSettingsFile;
		}
		BufferedReader input = null;
		try {
			input = new BufferedReader(new FileReader(settingsFile));
		}
		catch (FileNotFoundException e) {
			System.out.println("File \"" + settingsFile + "\" not found");
	    }
		list = new ArrayList<String>();
	    try {
	    	String tmp;
	    	while ((tmp = input.readLine()) != null)
	    		list.add(tmp);
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	    String temp;
	    StringBuilder object = new StringBuilder ();
	    StringBuilder param = new StringBuilder ();
		boolean f1 = false;
	    for (int i = 0; i < list.size(); i++) {
	    	object = new StringBuilder ();
		    param = new StringBuilder ();
	    	temp = new String (list.get(i));
	    	f1 = false;
			for (int j = 0; j < temp.length(); j++) {
				if (temp.charAt(j) == '=') {
					f1 = true;
				}
				if (f1 == false && temp.charAt(j) != '=') {
					object.append(temp.charAt(j));
				}
				if (f1 == true && temp.charAt(j) != '=') {
					param.append(temp.charAt(j));
				}
				
			}
			Configurator.key.add(object.toString());
			Configurator.value.add(param.toString());
	    }
	    System.out.println("Config loaded.");
	}

	public String getProperty(String req){
		String return_ = null;
		for (int i = 0; i < Configurator.key.size(); i++) {
			if (req == Configurator.key.get(i)) {
				return_ = Configurator.value.get(i);
			}
		}
		return return_;
	}
	
	public void setProperty(String key, String value){
		Configurator.key.add(key);
		Configurator.value.add(value);
	}
	
	public boolean changeProperty (String key_, String value_) {
		boolean return_ = false;
		for (int i = 0; i < Configurator.key.size(); i++ ) {
			if (Configurator.key.get(i) == key_) {
				Configurator.value.set(i, value_);
				return_ = true;
			}
		}
		return return_;
	}
	
	public boolean removeProperty (String key) {
		boolean return_ = false;
		for (int i = 0; i < Configurator.key.size(); i++) { 
			if (Configurator.key.get(i) == key) {
				Configurator.key.remove(i);
				Configurator.value.remove(i);
			}
		}
		return return_;
	}
	
	public void save() throws IOException{
		BufferedWriter bw = null;
		FileOutputStream fileOutputStream = null;
		StringBuilder stb = new StringBuilder();
		File file = new File (settingsFile);
		if(!file.exists()) {
            file.createNewFile();
        }
		fileOutputStream = new FileOutputStream(settingsFile);
		bw = new BufferedWriter(new OutputStreamWriter(fileOutputStream, "UTF-8"));
		
		for (int i = 0; i < Configurator.key.size(); i++) {
			stb.append(Configurator.key.get(i)+'='+Configurator.value.get(i)+'\n');	
		}
		bw.append(stb.toString());
		bw.flush();
		bw.close();
		fileOutputStream.flush();
		fileOutputStream.close();
	}
}
