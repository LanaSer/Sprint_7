package Package;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {
    public static Courier generic(){
        return new Courier("ninia", "1234", "syske");

    }
    public static Courier random(){
        return new Courier(RandomStringUtils.randomAlphabetic(10),"1234", "syske");

    }
}
