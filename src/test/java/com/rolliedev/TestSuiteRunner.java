package com.rolliedev;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static org.junit.runners.Suite.*;

@RunWith(Suite.class)
@SuiteClasses({
        DijkstraOnUndirectedGraphTest.class,
        DijkstraOnDirectedGraphTest.class
})
public class TestSuiteRunner {
}
