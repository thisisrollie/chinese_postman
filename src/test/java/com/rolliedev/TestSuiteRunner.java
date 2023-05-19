package com.rolliedev;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static org.junit.runners.Suite.*;

@RunWith(Suite.class)
@SuiteClasses({
        GeneratorUtilsTest.class,
        FindCombinationsInGraphsTest.class,
        GraphCreationTest.class,
        DijkstraOnUndirectedGraphTest.class,
        DijkstraOnDirectedGraphTest.class,
        BellmanFordOnUndirectedGraphTest.class,
        BellmanFordOnDirectedGraphTest.class,
        JohnsonTest.class,
        ChinesePostmanTest.class
})
public class TestSuiteRunner {
}
