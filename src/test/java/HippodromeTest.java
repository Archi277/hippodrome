import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HippodromeTest {

    @Test
    List<Horse> createHorses(int quantity) {
        List<Horse> horseList = new ArrayList<>();
        List<Horse> mockHorseList = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            horseList.add(new Horse("horse " + i, 2.0, 3.0));
            mockHorseList.add(Mockito.spy(horseList.get(i)));
        }
        return  mockHorseList;
    }

    @Test
    @DisplayName("constructorFirstParameterNull")
    void constructorFirstParameterNull() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    @DisplayName("constructorFirstParameterEmpty")
    void constructorFirstParameterEmpty() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<Horse>()));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }
    @Test
    @DisplayName("getHorses")
    void getHorses() {
        List<Horse> mockHorsesList = createHorses(30);
        Hippodrome hippodrome = new Hippodrome(mockHorsesList);
        assertEquals(mockHorsesList, hippodrome.getHorses());
    }

    @Test
    @DisplayName("moveAllHorses")
    void move() {
        List<Horse> mockHorsesList = createHorses(50);
        Hippodrome hippodrome = new Hippodrome(mockHorsesList);
        hippodrome.move();
        for(Horse horse: mockHorsesList){
            Mockito.verify(horse).move();
        }
    }

    @Test
    @DisplayName("getWinner")
    void getWinner() {
        List<Horse> horsesList = new ArrayList<>();
        horsesList.add(new Horse("1", 2.0,1.0));
        horsesList.add(new Horse("2", 2.0,2.0));
        horsesList.add(new Horse("3", 2.0,3.0));
        Hippodrome hippodrome = new Hippodrome(horsesList);
        assertEquals(horsesList.get(2), hippodrome.getWinner());
    }
}
