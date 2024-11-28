import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;



public class MyArrayListTest {
    private MyArrayList myArrayList;

    @BeforeEach
    void setUp() { myArrayList = new MyArrayList();}

    @Test
    void testAdd() {
        myArrayList.add("item1");
        assertEquals(1, myArrayList.size());
        assertEquals("item1", myArrayList.get(0));
    }

    @Test
    void testAddIndex() {
        myArrayList.add("item1");
        myArrayList.add("item2");
        myArrayList.add(1,"item3");
        assertEquals(3, myArrayList.size());
        assertEquals("item3", myArrayList.get(1));
    }

    @Test
    void testAddAtInvalidIndex() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () ->
                myArrayList.add(-1, "item1")
        );
        assertEquals("Index: -1, Size: 0", exception.getMessage());
    }

    @Test
    void testAddAndRemoveMultipleElementsInt() {
        for (int i = 0; i < 1000; i++) myArrayList.add(i);

        assertEquals(1000, myArrayList.size());

        for (int i = 0; i < 1000; i++) myArrayList.remove(0);

        assertEquals(0, myArrayList.size());
    }

    @Test
    void testAddAndRemoveMultipleElementsString() {
        for (int i = 0; i < 1000; i++) myArrayList.add("item" + i);

        assertEquals(1000, myArrayList.size());

        for (int i = 0; i < 1000; i++) myArrayList.remove(0);

        assertEquals(0, myArrayList.size());
    }

    @Test
    void testSetValidIndex() {
        myArrayList.add("item1");
        myArrayList.set(0, "newItem2");
        assertEquals("newItem2", myArrayList.get(0));
    }

    @Test
    void testSetInvalidIndexNegative() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () ->
                myArrayList.set(-1, "item"));
        assertEquals("Index: -1, Size: 0", exception.getMessage());
    }


    @Test
    void testGet() {
        myArrayList.add("item1");
        myArrayList.add("item2");
        assertEquals("item1", myArrayList.get(0));
        assertEquals("item2", myArrayList.get(1));
    }

    @Test
    void testGetInvalidIndexNegative() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () ->
                myArrayList.get(-1));
        assertEquals("Index: -1, Size: 0", exception.getMessage());
    }

    @Test
    void testRemove() {
        myArrayList.add("item1");
        myArrayList.add("item2");
        myArrayList.remove("item1");
        assertEquals(1, myArrayList.size());
        assertEquals("item2", myArrayList.get(0));
        myArrayList.remove(0);
        assertEquals(0, myArrayList.size());
    }

    @Test
    void testRemoveInvalidIndexNegative() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () ->
                myArrayList.remove(-1)
        );
        assertEquals("Index: -1, Size: 0", exception.getMessage());
    }

    @Test
    void testClear() {
        myArrayList.add("item1");
        myArrayList.add("item2");
        myArrayList.clear();
        assertEquals(0, myArrayList.size());
    }

    @Test
    void testSize() {
        assertEquals(0, myArrayList.size());
        myArrayList.add("item1");
        assertEquals(1, myArrayList.size());
        myArrayList.add("item2");
        assertEquals(2, myArrayList.size());
    }

    @Test
    void testSort() {
        myArrayList.add("banana");
        myArrayList.add("apple");
        myArrayList.add("cherry");
        myArrayList.sort(Comparator.naturalOrder());
        List<String> expected = Arrays.asList("apple", "banana", "cherry");
        assertEquals(expected, myArrayList.stream().collect(Collectors.toList()));
        myArrayList.sort(Comparator.reverseOrder());
        List<String> expected1 = Arrays.asList("cherry", "banana", "apple");
        assertEquals(expected1, myArrayList.stream().collect(Collectors.toList()));
    }

    @Disabled
    @Test
    void testEnsureCapacity() {
    }

    @Test
    void testStream() {
        myArrayList.add("item1");
        myArrayList.add("item2");
        List<Object> items = (List<Object>) myArrayList.stream().collect(Collectors.toList());
        assertEquals(Arrays.asList("item1", "item2"), items);
    }

    @Test
    void toStringTest() {
        assertEquals("[]", myArrayList.toString());
        myArrayList.add("item1");
        assertEquals("[item1]", myArrayList.toString());
        myArrayList.add(null);
        assertEquals("[item1, null]", myArrayList.toString());

    }

    @Test
    void testIteratorOnEmptyCollection() {
        Iterator<String> iterator = myArrayList.iterator();
        assertFalse(iterator.hasNext(), "Iterator should not have next element in an empty collection.");
    }

    @Test
    void testIteratorWithOneElement() {
        myArrayList.add("item1");
        Iterator<String> iterator = myArrayList.iterator();

        assertTrue(iterator.hasNext(), "Iterator should have next element.");
        assertEquals("item1", iterator.next(), "Next element should be 'item1'.");
        assertFalse(iterator.hasNext(), "Iterator should not have next element after retrieving the only item.");
    }

}
