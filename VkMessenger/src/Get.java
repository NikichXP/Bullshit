import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * This is simply cleanGet-request class
 */
public class Get {

    public static String get (String url) {
        return cleanGet("https://api.vk.com/method/" + url + "&v=5.37&access_token=" + Core.tokenId);
    }

    private static String cleanGet(String url) {
        String ret = "";
        URLConnection conn;
        InputStream inStream = null;
        try {
            URL u1 = new URL(url);
            if (Core.DEBUG) {
                System.out.println(url);
            }
            conn = u1.openConnection();
            inStream = conn.getInputStream();
            InputStreamReader in = new InputStreamReader(inStream);
            char[] buf = new char[10];
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
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }

}
