
public class SortArray implements Runnable {

    private int[] array;

    public SortArray(int[] array) {

        this.array = array;
    }

    @Override
    public void run() {
        sort();
    }

    private void sort(){
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                if (array[i] < array[j]){
                    swap(i, j);
                }
            }
        }
    }

    private void swap(int a, int b){
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    public int[] getArray() {
        return array;
    }
}
