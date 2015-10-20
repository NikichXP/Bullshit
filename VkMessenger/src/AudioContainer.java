
public class AudioContainer {

    public int userid;
    public Audio[] audio;

    public AudioContainer (int id) {
        this.userid = id;
        String req = API.getAudioList(id);
        String tracks = req.substring(req.indexOf('[')-1, req.lastIndexOf(']'));
        String[] track = new String[Integer.parseInt(req.substring(req.indexOf("\"count\":") + "\"count\":".length(),
                req.indexOf(',', req.indexOf("\"count\":") + "\"count\":".length()+1)))];
        int i = 0, lvl = 0, ptr = 1;
        for (int j = 0; j < tracks.length(); j++) {
            switch (tracks.charAt(j)) {
                case '{':
                    lvl++;
                    break;
                case '}':
                    lvl--;
                    break;
                case ',':
                    if (lvl == 0) {
                        track[i] = tracks.substring(ptr, j);
                        ptr = j+1;
                        System.out.println(track[i]);
                        i++;
                    }
                    break;
                default:
                    break;
            }
        }
        audio = new Audio[track.length];
        String[] tmp, pair;
        for (i = 0; i < track.length; i++) {
            tmp = track[i].split(",");

        }

    }

    private class Audio {
        private int id;
        private int ownerid;
        private int duration;
        private long date;
        private String url;
        private String artist;
        private String title;
    }

}
