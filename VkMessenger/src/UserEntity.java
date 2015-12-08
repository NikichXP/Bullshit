/**
 * Used to store and maintain users
 */
public class UserEntity {

    public int[] friends;
    public final int id;
    public boolean isInited = false;

    public String name;
    public String surname;
    public boolean male;
    /** Path to user, like vk.com/username */
    public String domain;
    /** Sense? */
    public String screenName;
    public String birthdate;
    public String city;
    public String country;
    public boolean mobile;
    public boolean canPost;
    public boolean seePosts;
    public boolean seeAudio;
    public boolean canWrite;
    public String mobilePhone;
    public String homePhone;
    public String site;
    public String status;
    public String lastSeen;
    public int commonCount;
    public String counters;

    public AudioContainer audio;

    private String unparsedData;

    public UserEntity (int id) {
        this.id = id;
        friends = API.getFriends(id);
        unparsedData = API.getUserInfo(id);
        System.out.println(unparsedData);
        if (unparsedData.length() < 20) { //random
            return;
        }
        unparsedData = unparsedData.substring(14, unparsedData.length()-3);
        System.out.println(unparsedData);
        int lvl = 0;
        int dots = 0;
        for (byte c : unparsedData.getBytes()) {
            switch (c) {
                case ',':
                    if (lvl == 0) {
                        dots++;
                    }
                    break;
                case '{':
                    lvl++;
                    break;
                case '}':
                    lvl--;
                    break;
            }
        }
        String data[] = new String [dots];
        int index = 0;
        dots = 0;
        lvl = 0;
        for (int i = 0; i < unparsedData.length(); i++) {
            switch (unparsedData.charAt(i)) {
                case ',':
                    if (lvl == 0) {
                        data[dots] = unparsedData.substring(index, i);
                        index = i+1;
                        dots++;
                    }
                    break;
                case '{':
                    lvl++;
                    break;
                case '}':
                    lvl--;
                    break;
            }
        }
        for (String s : data) {
            String[] arr = s.split(":");
            switch (cut(arr[0])) {
                case "id":
                    if (this.id != Integer.parseInt(arr[1])) {
                        return;
                    }
                    break;
                case "first_name":
                    this.name = cut(arr[1]);
                    break;
                case "last_name":
                    this.surname = cut (arr[1]);
                    break;
                case "sex":
                    if (arr[1].equals("2")) {
                        this.male = true;
                    } else {
                        this.male = false;
                    }
                    break;
                case "domain":
                    this.domain = cut (arr[1]);
                    break;
                case "screen_name":
                    this.screenName = cut (arr[1]);
                    break;
                case "bdate":
                    this.birthdate = cut (arr[1]);
                    break;
                case "has_mobile":
                    this.mobile = arr[1].equals("1");
                    break;
                case "can_post":
                    this.canPost = arr[1].equals("1");
                    break;
                case "can_see_all_posts":
                    this.seePosts = arr[1].equals("1");
                    break;
                case "can_see_audio":
                    this.seeAudio = arr[1].equals("1");
                    break;
                case "can_write_private_message":
                    this.canWrite = arr[1].equals("1");
                    break;
                case "mobile_phone":
                    if (arr[1].length() != 2) {
                        this.mobilePhone = cut (arr[1]);
                    }
                    break;
                case "home_phone":
                    if (arr[1].length() != 2) {
                        this.homePhone = cut (arr[1]);
                    }
                    break;
                case "status":
                    if (arr[1].length() != 2) {
                        this.status = cut (arr[1]);
                    }
                    break;
                case "last_seen":
                    this.lastSeen = "";
                    for (int i = 1; i < arr.length; i++) {
                        lastSeen += arr[i] + "|";
                    }
                    break;
                case "common_count":
                    this.commonCount = Integer.parseInt(arr[1]);
                    break;
                case "counters":
                    this.counters = "";
                    for (int i = 1; i < arr.length; i++) {
                        counters += arr[i] + "|";
                    }
                    break;
                case "site":
                    this.site = cut(arr[1]);
                    break;
                case "city":
                    this.city = "";
                    for (int i = 1; i < arr.length; i++) {
                        city += arr[i] + "|";
                    }
                    break;
                case "country":
                    this.counters = "";
                    for (int i = 1; i < arr.length; i++) {
                        country += arr[i] + "|";
                    }
                    break;
                default:
                    System.out.println("Unhandled value: " + arr[0]);
            }
        }
        this.audio = new AudioContainer(id);
        isInited = true;
    }

    private String cut (String x) {
        if (x.startsWith("\"") & x.endsWith("\"")) {
            return x.substring(1, x.length()-1);
        } else {
            return x;
        }
    }

}
