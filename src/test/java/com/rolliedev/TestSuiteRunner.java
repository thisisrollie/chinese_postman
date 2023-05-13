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
        BellmanFordOnUndirectedGraphTest.class
})
public class TestSuiteRunner {
}
