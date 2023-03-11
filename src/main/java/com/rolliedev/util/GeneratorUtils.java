package com.rolliedev.util;

import org.paukov.combinatorics3.Generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class GeneratorUtils {

    private GeneratorUtils() {
    }

    public static List<List<List<Integer>>> getPairs(List<Integer> vIndexes) {
        var allPairs = generatePairs(vIndexes);
        return Generator.combination(allPairs)
                .simple(vIndexes.size() / 2)
                .stream()
                .parallel()
                .filter(Predicate.not(pairs -> hasMutualVertices(pairs, vIndexes.size())))
                .collect(Collectors.toList());
    }


    private static boolean hasMutualVertices(List<List<Integer>> pairs, int cnt) {
        return pairs.stream()
                .flatMap(Collection::stream)
                .distinct()
                .count() != cnt;
    }

    private static List<List<Integer>> generatePairs(List<Integer> vIndexes) {
        List<List<Integer>> pairs = new ArrayList<>();
        for (int i = 0; i < vIndexes.size() - 1; i++) {
            for (int j = i + 1; j < vIndexes.size(); j++) {
                pairs.add(List.of(vIndexes.get(i), vIndexes.get(j)));
            }
        }
        return pairs;
    }
}
