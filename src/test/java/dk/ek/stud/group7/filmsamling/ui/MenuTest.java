package dk.ek.stud.group7.filmsamling.ui;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Locale;

class MenuTest {
    private static final Menu<Integer> oneItem = Menu.numbered(null, List.of(new Menu.NumberedItem<>("Only item", 42)));
    private static final Menu<Integer> oneItemWithTitle = Menu.numbered("Title", List.of(new Menu.NumberedItem<>("Only item", 42)));
    private static final Menu<Integer> twoItems = Menu.numbered(null, List.of(
            new Menu.NumberedItem<>("Item one", 42),
            new Menu.NumberedItem<>("Item two", 1337)));
    private static final Menu<Integer> tenItems = Menu.numbered(null, List.of(
            new Menu.NumberedItem<>("Item one", 1),
            new Menu.NumberedItem<>("Item two", 2),
            new Menu.NumberedItem<>("Item three", 3),
            new Menu.NumberedItem<>("Item four", 4),
            new Menu.NumberedItem<>("Item five", 5),
            new Menu.NumberedItem<>("Item six", 6),
            new Menu.NumberedItem<>("Item seven", 7),
            new Menu.NumberedItem<>("Item eight", 8),
            new Menu.NumberedItem<>("Item nine", 9),
            new Menu.NumberedItem<>("Item ten", 10)
    ));
    private static final Menu<String> twoItemsWithIds = Menu.of(null, List.of(
            new Menu.Item<>("Item A", "Super Smoothie", "A"),
            new Menu.Item<>("Item B", "Super Burger", "B")
    ));

    @Test
    void nullItemsThrows() {
        assertThrows(IllegalArgumentException.class, () -> Menu.numbered(null, null));
    }

    @Test
    void zeroItemsThrows() {
        assertThrows(IllegalArgumentException.class, () -> Menu.numbered(null, List.of()));
    }

    @Test
    void duplicateIdsThrow() {
        assertThrows(IllegalArgumentException.class, () -> Menu.of(null, List.of(
                new Menu.Item<>("A", "Apricot", 1),
                new Menu.Item<>("A", "Apple", 2)
        )));
    }

    @Test
    void duplicateIdsAreCaseInsensitive() {
        assertThrows(IllegalArgumentException.class, () -> Menu.of(null, List.of(
                new Menu.Item<>("A", "Apricot", 1),
                new Menu.Item<>("a", "Apple", 2)
        )));
    }

    @Test
    void nullInputThrows() {
        assertThrows(IllegalArgumentException.class, () -> oneItem.resolve(null));
    }

    @ParameterizedTest
    @CsvSource(value = {
            ",",
            "'',''",
            "A,A",
            "A ,A",
            " A,A",
            " A ,A",
    })
    void titleIsValidated(String input, String output) {
        // Arrange
        var menu = Menu.numbered(input, List.of(new Menu.NumberedItem<>("Only item", 42)));

        // Act
        var title = menu.title().orElse(null);

        // Assert
        assertEquals(output, title);
    }

    @Test
    void oneItemRenders() {
        // Act
        var text = oneItem.render();

        // Assert
        assertEquals("""
                1. Only item
                """, text);
    }

    @Test
    void oneItemRendersWithTitle() {
        // Act
        var text = oneItemWithTitle.render();

        // Assert
        assertEquals("""
                Title
                
                1. Only item
                """, text);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1", " 1", "1 ", " 1 ",
            "", "O", "On", "Only item",
            "o", "on", "only item"
    })
    void oneItemResolvesByInput(String input) throws MenuException {
        // Act
        var value = oneItem.resolve(input);

        // Assert
        assertEquals(42, value);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1.", "-", "0"
    })
    void oneItemInputNotFound(String input) {
        // Act + Assert
        assertThrows(MenuException.NotFound.class, () -> oneItem.resolve(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "2",
            "Item t", "Item tw", "Item two"
    })
    void itemTwoOfTwoResolvesUnambiguously(String input) throws MenuException {
        // Act
        var value = twoItems.resolve(input);

        // Assert
        assertEquals(1337, value);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "", "I", "Item", "Item ",
            "Super", "Super "
    })
    void itemTwoOfTwoIsAmbiguous(String input) {
        // Act + Assert
        var e = assertThrows(MenuException.Ambiguous.class, () -> twoItemsWithIds.resolve(input));

        // Assert
        assertEquals(List.of("Super Smoothie", "Super Burger"), e.matches);
    }

    /**
     * When input is "1", ids "1" and "10" could be ambiguous.
     */
    @Test
    void itemOneOfTenResolvesUnambiguously() throws MenuException {
        // Act
        var value = tenItems.resolve("1");

        // Assert
        assertEquals(1, value);
    }

    @Test
    void itemDisplayThrowsWhenNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Menu.Item<Void>(null, "A", "One and only", "One and only", null));
    }

    @Test
    void itemLowerIdThrowsWhenNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Menu.Item<Void>("Alpha", null, "One and only", "One and only", null));
    }

    @Test
    void itemLabelThrowsWhenNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Menu.Item<Void>("Alpha", "A", null, "One and only", null));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "''",
            "A",
            "A ",
            " A",
            " A ",
    })
    void itemDisplayIsUnchanged(String displayVerbatim) {
        // Arrange
        var item = new Menu.Item<Void>(displayVerbatim, "A", "One and only", "One and only", null);

        // Act
        var display = item.display();

        // Assert
        assertEquals(displayVerbatim, display);
    }

    @Test
    void itemIdIsLowerCased() {
        // Arrange
        String idVerbatim = "A";
        var item = new Menu.Item<Void>("Alpha", idVerbatim, "One and only", "One and only", null);

        // Act
        var lowerId = item.lowerId();

        // Assert
        assertEquals(idVerbatim.toLowerCase(Locale.ROOT), lowerId);
    }

    @Test
    void itemLabelIsLowerCased() {
        // Arrange
        String labelVerbatim = "One and only";
        var item = new Menu.Item<Void>("Alpha", "A", labelVerbatim, labelVerbatim, null);

        // Act
        var label = item.label();
        var lowerLabel = item.lowerLabel();

        // Assert
        assertEquals(labelVerbatim, label);
        assertEquals(labelVerbatim.toLowerCase(Locale.ROOT), lowerLabel);
    }
}