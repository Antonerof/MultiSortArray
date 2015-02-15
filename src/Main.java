import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Direct a = new Direct(5);
        int[] array = new int[52];
        Random random = new Random();

        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(100);
        }

        array = a.startSort(array);

        System.out.println(Arrays.toString(array));

    }
}
