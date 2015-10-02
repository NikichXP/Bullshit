/**
 * Created by nikichxp on 01.10.15.
 */
public class UserAPI {

    public String getUser (int id) {
        return Get.get ("https://api.vk.com/method/users.get?user_id=" + id + "&v=5.37&access_token=" + Core.tokenId);
    }

}
