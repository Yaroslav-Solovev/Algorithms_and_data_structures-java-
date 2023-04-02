
public class task1{
    public void sort(int arr[]){
        int n = arr.length;
        // Построение кучи
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);
        // Извлекаем элементы из кучи   
        for (int i = n - 1; i >= 0; i--){
            // Перемещаем текущий корень в конец
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            // Вызываем процедуру heapify на уменьшенной куче
            heapify(arr, i, 0);
        }
    }
    // Процедура для преобразования в двоичную кучу поддерева с корневым узлом i, что является индексом в arr[]. n - размер кучи
    void heapify(int arr[], int n, int i){
        int largestElement = i; // Инициализируем наибольший элемент как корень
        int left = 2*i + 1;
        int right = 2*i + 2;
        // Если левый дочерний элемент больше корня
        if (left < n && arr[left] > arr[largestElement])
            largestElement = left;
        // Если правый дочерний элемент больше, чем самый большой элемент на данный момент
        if (right < n && arr[right] > arr[largestElement]) largestElement = right;
        // Если самый большой элемент не корень
        if (largestElement != i){
            int swap = arr[i];
            arr[i] = arr[largestElement];
            arr[largestElement] = swap;
            // Рекурсивно преобразуем в двоичную кучу затронутое поддерево
            heapify(arr, n, largestElement);
        }
    }
    /* Вспомогательная функция для вывода на экран массива размера n */
    static void printArray(int arr[]){
        int n = arr.length;
        for (int i = 0; i < n; ++i)
            System.out.print(arr[i] + " ");
        System.out.println();
    }
    // Управляющая программа
    public static void main(String args[]){
        int arr[] = {8, 25, 17, 5, 23, 1};
        task1 ob = new task1();
        ob.sort(arr);
        System.out.println("Результат сортировки: ");
        printArray(arr);
    }
}