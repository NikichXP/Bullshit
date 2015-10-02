/**
 * Created by nikichxp on 01.10.15.
 */
public class Core {

    public final static String tokenId = "0a85e81792e27469f520be72666e37daf47a00b3109074b710cb619626bece4cdf53e68420dc757a766f6";
    public final static int userId = 9867491;

    public static void main (String [] args) {
        UserAPI get = new UserAPI () ;
        System.out.println(get.getUser(userId));
    }
}
