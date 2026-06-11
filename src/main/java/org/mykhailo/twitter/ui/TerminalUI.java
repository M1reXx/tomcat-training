package org.mykhailo.twitter.ui;

public class TerminalUI {
    private static final int WIDTH = 38;

    private static void printLine(String text) {
        String content = text.length() > WIDTH ? text.substring(0, WIDTH) : text;
        System.out.printf("│ %-" + WIDTH + "s │\n", content);
    }

    private static void printCentered(String text) {
        int padding = (WIDTH - text.length()) / 2;
        if (padding < 0) padding = 0;
        String centeredText = " ".repeat(padding) + text;
        System.out.printf("│ %-" + WIDTH + "s │\n", centeredText);
    }

    public static String menuCreator(String header, String... options) {
        System.out.println("┌────────────────────────────────────────┐");
        printCentered(header);
        System.out.println("├────────────────────────────────────────┤");

        for (int i = 0; i < options.length; i++) {
            printLine("[" + (i + 1) + "] - " + options[i]);
        }

        System.out.println("└────────────────────────────────────────┘");

        String choice = IO.readln("❯ Choice: ");

        System.out.println();

        return choice;
    }

    public static String choiceUI(String text, String input) {
        System.out.println("┌────────────────────────────────────────┐");
        printLine(text);
        System.out.println("└────────────────────────────────────────┘");

        String choice = IO.readln("❯ " + input + ": ");

        System.out.println();

        return choice;
    }

    public static void infoUI(String text) {
        System.out.println("┌────────────────────────────────────────┐");
        printLine(text);
        System.out.println("└────────────────────────────────────────┘");

        System.out.println();
    }
}
