import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HorseTest {

    private String horseName = "cherry";
    private double horseSpeed = 2.0;
    private double distance = 3.0;
    private Horse horse = new Horse(horseName, horseSpeed, distance);

    static Stream<String> argsProviderFactory() {
        return Stream.of("", " ", "    ");
    }

    @Mock
    Horse mockHorse;

    @Test
    @Order(1)
    @DisplayName("constructorFirstParameterNull")
    void constructorFirstParameterNull() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(null, horseSpeed, distance));
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @DisplayName("constructorFirstParameterEmpty")
    @Order(2)
    @ParameterizedTest
    @MethodSource("argsProviderFactory")
    void constructorFirstParameterEmpty(String argument) {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(argument, horseSpeed, distance));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    @Order(3)
    @DisplayName("constructorSecondParameterNegative")
    void constructorSecondParameterNegative() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(horseName, -1.0, distance));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    @Order(4)
    @DisplayName("constructorThirdParameterNegative")
    void constructorThirdParameterNegative() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(horseName, horseSpeed, -1.0));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    @Order(5)
    @DisplayName("getName")
    void getName() {
        assertEquals(horseName, horse.getName());
    }

    @Test
    @Order(6)
    @DisplayName("getSpeed")
    void getSpeed() {
        assertEquals(horseSpeed, horse.getSpeed());
    }

    @Test
    @Order(7)
    void getDistance() {
        Horse anotherHorse = new Horse(horseName, horseSpeed);
        assertEquals(anotherHorse.getDistance(), 0);
        assertEquals(distance, horse.getDistance());
    }

    @Test
    @Order(8)
    @DisplayName("invokeGetRandomInMove")
    void invokeGetRandomInMove() {

        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            horseMockedStatic.when (()-> Horse.getRandomDouble(0.2,0.9)).thenReturn(0.5);
            horse.move();
            horseMockedStatic.verify( () -> Horse.getRandomDouble(0.2,0.9));
        }
    }

    @Test
    @Order(9)
    @DisplayName("checkFormulaInMove")
    void checkFormulaInMove() {

        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            horseMockedStatic.when (()-> Horse.getRandomDouble(0.2,0.9)).thenReturn(0.5);
            horse.move();
            assertEquals(distance+1, 4);
        }
    }

}