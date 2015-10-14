/**
 * Used to store and maintain users
 */
public class UserEntity {

    public int[] friends;
    public final int id;
    public boolean isInited = false;

    public UserEntity (int id) {
        this.id = id;
        isInited = true;
        friends = UserAPI.getFriends(id);
        if (Core.DEBUG) {
            for (int i: friends) {
                System.out.println(i);
            }
        }
        String userInfo = UserAPI.getUserInfo(id);
    }

    public UserEntity (int id, boolean init) {
        this.id = id;
        isInited = init;
        if (!init) {
            return;
        } else {

        }
    }

}
