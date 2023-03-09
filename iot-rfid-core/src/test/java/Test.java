import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * @author luojie
 * @createTime 2023/03/07 1:19
 * @description Test
 */
public class Test {
    public static void main(String[] args) {
        String s = "218.17.157.214";
        byte[] bytes = s.getBytes(StandardCharsets.US_ASCII);
        for(byte b : bytes){
            System.out.printf("%x ", b);
        }

        LocalDateTime now = LocalDateTime
                .now()
                .withYear(2020)
                .withMonth(3)
                .withDayOfMonth(9)
                .withHour(13)
                .withMinute(14)
                .withSecond(15)
                .withNano(0);

        System.out.println(now.toString());
    }
}
