public class Direct {

    private int amountThreads;
    private int[][] arraysForSort;
    private int lengthOfArray;

    public Direct(int amountThreads) {
        this.amountThreads = amountThreads;
    }

    public int[] startSort(int[] array) {
        if (array.length / amountThreads <= 2) {
            amountThreads = amountThreads / 2;
        }
        lengthOfArray = array.length;
        arraysForSort = divideArray(array);
        Thread[] threads = new Thread[amountThreads];
        SortArray[] sortArrays = new SortArray[amountThreads];
        for (int i = 0; i < arraysForSort.length; i++) {
            sortArrays[i] = new SortArray(arraysForSort[i]);
        }

        for (int i = 0; i < amountThreads; i++) {
            threads[i] = new Thread(sortArrays[i]);
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < sortArrays.length; i++) {
            arraysForSort[i] = sortArrays[i].getArray();
        }

        return mergeArrays();
    }

    //    Раздеяем массив на массивы по количеству потоков. К последнему присоединяем хвостик
    private int[][] divideArray(int[] array) {
        int[][] arrays = new int[amountThreads][];
        int start = 0, end = 0;
        for (int i = 0; i < amountThreads; i++) {
            start = (i * (array.length / amountThreads));
            end = ((i + 1) * (array.length / amountThreads));
            arrays[i] = new int[end - start];
            System.arraycopy(array, start, arrays[i], 0, (end - start));
        }
        if (array.length % (end - start) != 0) {
            arrays[amountThreads - 1] = new int[array.length - arrays[0].length * (amountThreads - 1)];
            System.arraycopy(array, start, arrays[amountThreads - 1], 0, array.length - start);
        }

        return arrays;
    }

//    объединяем массивы в один отсортированный
    private int[] mergeArrays() {

        int[] arrayToReturn = new int[lengthOfArray];
        int count = 0;
//добавляем поэлементно, сравнивая добавленный элемент с предыдущим
        for (int i = 0; i < arraysForSort[0].length; i++) {
            for (int j = 0; j < amountThreads; j++) {
                arrayToReturn[count++] = arraysForSort[j][i];
                for (int z = count - 1; z > 0; z--) {
                    if (arrayToReturn[z] < arrayToReturn[z - 1])
                        arrayToReturn = swap(arrayToReturn, z, z - 1);
                    else break;
                }
            }
        }
//        добавляем хвостик, так же сравнивая элементы массива
        if (lengthOfArray > count)
            for (int i = 0; i < arraysForSort.length; i++) {
                if (arraysForSort[i].length > arraysForSort[0].length){
                    for (int j = arraysForSort[0].length; j < arraysForSort[i].length; j++) {
                        arrayToReturn[count++] = arraysForSort[i][j];
                        for (int z = count - 1; z > 0; z--) {
                            if (arrayToReturn[z] < arrayToReturn[z - 1])
                                arrayToReturn = swap(arrayToReturn, z, z - 1);
                            else break;
                        }
                    }
                    break;
                }
            }
        return arrayToReturn;
    }

    private int[] swap(int[] array, int b, int a) {
        int temp = array[b];
        array[b] = array[a];
        array[a] = temp;
        return array;
    }

}
