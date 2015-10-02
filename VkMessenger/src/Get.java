import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by nikichxp on 01.10.15.
 */
public class Get {

    public static String get (String url) {
        String ret = null;
        try {
            URL u1 = new URL(url);
            URLConnection conn = u1.openConnection();
            InputStream inStream = conn.getInputStream();
            InputStreamReader in = new InputStreamReader(inStream);
            char[] buf = new char[10];
            ret = new String();
            while (in.ready()) {
                in.read(buf);
                String tmp = new String (buf);
                ret += tmp;
            }
            if (ret.charAt(0) == '{') {
                int lvl = 1;
                for (int i = 1; i < ret.length(); i++) {
                    switch (ret.charAt(i)) {
                        case '{': lvl++; break;
                        case '}': lvl--; break;
                        default: break;
                    }
                    if (lvl == 0) {
                        ret = ret.substring(0, i+1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ret = "bad";
        }
        return ret;
    }

}
