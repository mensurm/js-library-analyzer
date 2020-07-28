package de.scalable.capital;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
            System.out.println(SearchResultParser.getResults(args[0]).toString());
    }
}
