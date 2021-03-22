import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class TQSstackTest {

    private TQSstack<Integer> stack;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        stack = new TQSstack<>();
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Empty On Construction")
    void emptyOnConstruction() {
        assertTrue(stack.isEmpty());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Size Zero On Construction")
    void zeroOnConstruction() {
        assertEquals(0, stack.size());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Size After n pushes")
    void sizeAfterNPushes() {
        for (int i=0; i<5; i++){
            stack.push(i);
        }
        assertFalse(stack.isEmpty());
        assertEquals(5, stack.size());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Push Then Pop")
    void pushThenPop() {
        stack.push(100);
        assertEquals(100, stack.pop());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Push Then Peek")
    void pushThenPeek() {
        stack.push(100);
        int tempSize = stack.size();
        assertEquals(100, stack.peek());
        assertEquals(tempSize, stack.size());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Popping all")
    void fullPop() {
        for (int i = 0; i < stack.size(); i++){
            stack.pop();
        }
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Pop Empty")
    void PopEmpty() {
        assertThrows(NoSuchElementException.class, () -> {
            stack.pop();
        });
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Peek Empty")
    void PeekEmpty() {
        assertThrows(NoSuchElementException.class, () -> {
            stack.peek();
        });
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Push into a full bounded stack")
    void pushFullBounded() {

        stack = new TQSstack<>(3);

        for(int i = 0 ; i < 3 ; i++){
            stack.push(i);
        }

        assertThrows(IllegalStateException.class, () -> {
            stack.push(1);
        });

    }
}