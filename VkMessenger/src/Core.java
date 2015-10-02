import java.nio.file.*;

public class Core {

    public static String tokenId = null;
    public final static int userId = 9867491;

    public static void main (String [] args) {
        try {
            tokenId = new String(Files.readAllBytes(Paths.get("D:/.vk")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        UserAPI get = new UserAPI () ;
        UserEntity u = new UserEntity(get.getFriends(userId));

    }
}
