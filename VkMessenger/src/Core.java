import java.nio.file.*;


/**
 * Main script for debug (now) and launch (future)
 */
public class Core {

    public static String tokenId = null;
    public final static int userId = 9867491;
    public final static boolean DEBUG = true;

    public static void main (String [] args) {
        try {
            tokenId = new String(Files.readAllBytes(Paths.get("D:/.vk")));
        } catch (Exception e) {
            try {
                tokenId = new String(Files.readAllBytes(Paths.get("/home/nikichxp/.vk")));
            } catch (Exception ex) {
                e.printStackTrace();
                ex.printStackTrace();
            }
        }

        UserEntity u = new UserEntity(userId);
//        System.out.println(UserAPI.getUserInfo(userId));

    }
}
