import java.util.regex.*;

/**
 * UserDAO to use API
 */
public class UserAPI {
//
//    public static String getUserInfo (int id) {
//        return getUserInfo({id});
//    }

    public static String getUserInfo (int... id) {
        StringBuilder request = new StringBuilder();
        request.append("users.get?user_ids=");
        for (int i : id) {
            request.append(i+",");
        }
        request.append("&fields=sex,bdate,city,country,online_mobile,domain,has_mobile,contacts,connections,site," +
                "can_post,can_see_all_posts,can_see_audio,can_write_private_message,status,last_seen,common_count," +
                "relation,relatives,counters,screen_name,maiden_name");

        return Get.get(request.toString());
    }

    public static int[] getFriends (int id) {
        String friendStr = Get.get("friends.get?user_id=" + id);
        Pattern p = Pattern.compile("items\":\\[([\\d]+,)*[\\d]+");
        Matcher m = p.matcher(friendStr);
        m.find();
        String test = friendStr.substring(m.start(), m.end());
        test = test.substring(8);
        String[] frnds = test.split(",");
        int [] friends = new int[frnds.length];
        for (int i = 0; i < friends.length; i++) {
            friends[i] = Integer.parseInt(frnds[i]);
        }
        return friends;
    }

}
