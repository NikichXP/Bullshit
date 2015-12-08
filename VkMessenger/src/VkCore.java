import java.nio.file.*;


/**
 * Main script for debug (now) and launch (future)
 */
public class VkCore {

    public static String tokenId = tokenId();
    public static String tokenId() {
		if (tokenId == null) {
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
		}
		return tokenId;
	}
    public final static int userId = 9867491;
    public final static boolean DEBUG = true;
    public final static int BUF_SIZE = 1024;

    public static void main (String [] args) {
        UserEntity u = new UserEntity(userId);
//        System.out.println(UserAPI.getUserInfo(userId));

    }
}
