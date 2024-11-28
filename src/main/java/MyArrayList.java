import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 * Реализует интерфейс {@code Iterable}.
 * <p>Каждый экземпляр {@code MyArrayList} имеет <i>емкость</i>. Емкость — это размер массива,
 * используемого для хранения элементов в списке. Она всегда не меньше размера списка.
 * По мере добавления элементов в MyArrayList его емкость увеличивается автоматически.
 * Приложение может увеличить емкость экземпляра {@code MyArrayList} перед
 * добавлением большого количества элементов с помощью операции {@code ensureCapacity}
 * <p><strong>Обратите внимание, что эта реализация не синхронизирована.</strong>
 *
 * @param <T> тип элементов в этом списке.
 * @author Дмитрий Цветаев.
 */

public class MyArrayList<T> implements Iterable<T> {

    /**
     * Буфер массива, в котором хранятся элементы MyArrayList.
     */
    private Object[] elements;

    /**
     * Размер MyArrayList (количество содержащихся в нем элементов).
     *
     * @serial
     */
    private int size;

    /**
     * Начальная емкость по умолчанию.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Создает пустой список с начальной емкостью десять.
     */
    public MyArrayList() {
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * Добавляет указанный элемент в конец списка.
     *
     * @param e элемент, который будет добавлен к этому списку.
     */
    public void add(T e) {
        ensureCapacity();
        elements[size++] = e;
    }

    /**
     * Вставляет указанный элемент в указанную позицию в этом списке.
     * Смещает элемент, находящийся в этой позиции (если есть),
     * и все последующие элементы вправо (добавляет единицу к их индексам).
     *
     * @param index   индекс, в который должен быть вставлен указанный элемент.
     * @param element элемент, который должен быть вставлен.
     * @throws IndexOutOfBoundsException {@inheritDoc}.
     */
    public void add(int index, T element) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

        ensureCapacity();
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    /**
     * Заменяет элемент в указанной позиции в этом списке указанным элементом.
     *
     * @param index индекс элемента для замены
     * @param element элемент, который будет сохранен в указанной позиции
     * @throws IndexOutOfBoundsException {@inheritDoc}.
     */
    public void set(int index, T element) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

        elements[index] = element; // Заменяем элемент
    }

    /**
     * Возвращает элемент в указанной позиции в этом списке.
     *
     * @param index индекс возвращаемого элемента.
     * @return Элемент в указанной позиции в этом списке.
     * @throws IndexOutOfBoundsException {@inheritDoc}.
     */
    public T get(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        return (T) elements[index];
    }

    /**
     * Удаляет элемент в указанной позиции в этом списке.
     * Смещает все последующие элементы влево (вычитает единицу из их индексов).
     *
     * @param index индекс удаляемого элемента.
     * @throws IndexOutOfBoundsException {@inheritDoc}.
     */
    public void remove(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        elements[--size] = null; // Удаляем ссылку для сборщика мусора
    }

    /**
     * Удаляет элемент {@code e}, такой что {@code Objects.equals(o, e)}, если
     * эта коллекция содержит один или несколько таких элементов.
     * Смещает все последующие.
     *
     * @param o удаляемый элемент.
     * @return {@code true}, если элемент был удален в результате этого вызова.
     * @throws IndexOutOfBoundsException {@inheritDoc}.
     */
    public boolean remove(Object o) {
        boolean isRemoved = false; // Флаг для отслеживания, были ли удалены элементы
        for (int i = 0; i < size; ) {
            if (elements[i].equals(o)) {
                // Сдвигаем элементы влево, чтобы заполнить пробел
                for (int j = i; j < size - 1; j++) {
                    elements[j] = elements[j + 1];
                }
                elements[--size] = null; // Удаляем ссылку на последний элемент
                isRemoved = true; // Устанавливаем флаг, что элемент был удален
            } else i++; // Увеличиваем индекс только если элемент не был удален
        }
        return isRemoved; // Возвращаем true, если хотя бы один элемент был удален
    }


    /**
     * Удаляет все элементы из этого списка. Список будет пустым после этого вызова.
     */
    public void clear() {
        Arrays.fill(elements, 0, size, null);
        size = 0;
    }

    /**
     * Возвращает количество элементов в этом списке.
     *
     * @return Количество элементов в этом списке.
     */
    public int size() {
        return size;
    }

    /**
     * Сортирует с помощью quicksort по заданному компаратору.
     */
    public void sort(Comparator<? super T> comparator) {
        quicksort(0, size - 1, comparator);
    }


    /**
     * Сортировка с помощью quicksort по заданному компаратору.
     */
    private void quicksort(int low, int high, Comparator<? super T> comparator) {
        if (low < high) {
            int pivotIndex = partition(low, high, comparator);
            quicksort(low, pivotIndex - 1, comparator);
            quicksort(pivotIndex + 1, high, comparator);
        }
    }

    /**
     * Разделитель для quicksort.
     *
     * @return Число, которое будет опорным элементом.
     */
    private int partition(int low, int high, Comparator<? super T> comparator) {
        T pivot = (T) elements[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comparator.compare((T) elements[j], pivot) <= 0) {
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, high);
        return i + 1;
    }

    /**
     * Меняем местами элементы для quicksort.
     */
    private void swap(int i, int j) {
        Object temp = elements[i];
        elements[i] = elements[j];
        elements[j] = temp;
    }

    /**
     * Увеличивает емкость при необходимости.
     */
    private void ensureCapacity() {
        if (size == elements.length)
            elements = Arrays.copyOf(elements, elements.length * 2);
    }

    /**
     * Для корректного отображения разных объектов.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            if (i < size - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Возвращает последовательный поток с этой коллекцией в качестве источника.
     *
     * @return {@code Arrays.stream()}
     */
    public Stream<T> stream() {
        return Arrays.stream((T[]) Arrays.copyOf(elements, size));
    }

    /**
     * Позволяет поочередно получить все элементы коллекции.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public T next() {
                return get(currentIndex++);
            }
        };
    }

}


