package dk.ek.stud.group7.filmsamling.ui;

import org.jetbrains.annotations.*;

import java.util.*;
import java.util.stream.Stream;

final class Menu<C> {
    private final String title;
    private final List<Item<C>> items;

    private Menu(@Nullable String title, List<Item<C>> items) {
        validateItems(items);
        this.title = title;
        this.items = List.copyOf(items);
    }

    private static void validateNonEmptyList(List<?> items) {
        if (items == null)
            throw new IllegalArgumentException("items cannot be null");
        if (items.isEmpty())
            throw new IllegalArgumentException("items cannot be empty");
    }

    private static void validateItems(List<? extends Item<?>> items) {
        validateNonEmptyList(items);
        var seen = new HashSet<String>();
        for (var item : items) {
            if (!seen.add(item.lowerId)) {
                throw new IllegalArgumentException("duplicate id: " + item.lowerId);
            }
        }
    }

    static <C> Menu<C> numbered(@Nullable String title, List<NumberedItem<C>> items) {
        validateNonEmptyList(items);
        List<Item<C>> numbered = new ArrayList<>(items.size());
        for (int i = 0, itemsSize = items.size(); i < itemsSize; ++i) {
            String id = String.valueOf(i + 1);
            String display = id + ".";
            var item = items.get(i);
            numbered.add(new Item<>(display, id, item.label, item.value));
        }
        return Menu.of(title, numbered);
    }

    static <C> Menu<C> of(@Nullable String title, List<Item<C>> items) {
        return new Menu<>(title, items);
    }

    Optional<String> title() {
        return Optional.ofNullable(title);
    }

    String render() {
        StringBuilder b = new StringBuilder();
        maybeRenderTitle(b);
        for (var item : items) {
            renderItem(b, item);
        }
        return b.toString();
    }

    C resolve(String input) throws MenuException {
        if (input == null)
            throw new IllegalArgumentException("input cannot be null");

        var lowerInput = input.trim().toLowerCase(Locale.ROOT);

        var exactId = items.stream().filter(i -> i.lowerId.equals(lowerInput)).findFirst();
        if (exactId.isPresent()) {
            return exactId.get().value;
        }

        var matchedIds = items.stream().filter(i -> i.lowerId.startsWith(lowerInput)).toList();
        if (matchedIds.size() == 1) {
            return matchedIds.getFirst().value;
        }

        var matchedLabels = items.stream().filter(i -> i.lowerLabel.startsWith(lowerInput)).toList();
        if (matchedLabels.size() == 1) {
            return matchedLabels.getFirst().value;
        }

        if (matchedIds.isEmpty() && matchedLabels.isEmpty()) {
            throw new MenuException.NotFound();
        }

        if (matchedIds.isEmpty()) {
            throw new MenuException.Ambiguous(matchedLabels.stream()
                    .map(i -> i.label)
                    .toList());
        }

        if (matchedLabels.isEmpty()) {
            throw new MenuException.Ambiguous(matchedIds.stream()
                    .map(i -> i.label)
                    .toList());
        }

        throw new MenuException.Ambiguous(Stream.concat(
                        matchedIds.stream(),
                        matchedLabels.stream())
                .map(i -> i.label)
                .distinct()
                .toList());
    }

    private void maybeRenderTitle(StringBuilder b) {
        if (title == null) return;
        b.append(title);
        b.append(System.lineSeparator());
        b.append(System.lineSeparator());
    }

    private void renderItem(StringBuilder b, Item<C> item) {
        b.append(item.display);
        b.append(" ");
        b.append(item.label);
        b.append(System.lineSeparator());
    }

    record NumberedItem<C>(String label, C value) {
    }

    record Item<C>(String display, String lowerId, String label, String lowerLabel, C value) {
        /**
         * @param lowerId may be any case, stored lowercased
         */
        Item {
            if (display == null)
                throw new IllegalArgumentException("display cannot be null");
            if (lowerId == null)
                throw new IllegalArgumentException("lowerId cannot be null");
            if (label == null)
                throw new IllegalArgumentException("lowerId cannot be null");
            lowerId = lowerId.toLowerCase(Locale.ROOT);
            lowerLabel = label.toLowerCase(Locale.ROOT);
        }

        Item(String display, String id, String label, C value) {
            this(display, id, label, label, value);
        }

        Item(String id, String label, C value) {
            this(id, id, label, value);
        }
    }
}
