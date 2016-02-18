import java.io.FileNotFoundException;
import java.util.Arrays;


public class MainClass {
    public static void main(String[] args) throws FileNotFoundException {
        SimplePermutation method = new SimplePermutation(10);
        method.generateKey(10);
        System.out.println("Key : " + Arrays.toString(method.key));
        method.encrypt();
        method.decrypt();
    }

}
