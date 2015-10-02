import java.util.regex.*;

/**
 * Created by NikichXP on 03.10.2015.
 */
public class UserEntity {

    public int[] friends;

    public UserEntity (String friendStr) {
        Pattern p = Pattern.compile("items\":\\[([\\d]+,)*[\\d]+");
        Matcher m = p.matcher(friendStr);
        m.find();
        String test = friendStr.substring(m.start(), m.end());
        test = test.substring(8);
        String[] frnds = test.split(",");
        this.friends = new int[frnds.length];
        for (int i = 0; i < friends.length; i++) {
            this.friends[i] = Integer.parseInt(frnds[i]);
        }
    }

}
