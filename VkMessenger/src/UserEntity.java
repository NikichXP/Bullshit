/**
 * Used to store and maintain users
 */
public class UserEntity {

    public int[] friends;
    public final int id;

    public UserEntity (int id) {
        this.id = id;
        friends = UserAPI.getFriends(id);
        if (Core.DEBUG) {
            for (int i: friends) {
                System.out.println(i);
            }
        }
    }

}
