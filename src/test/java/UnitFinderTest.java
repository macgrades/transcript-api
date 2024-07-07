import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import Courses.UnitFinder;

public class UnitFinderTest {
    @Test
    public void testIsUncompletedCourse_withUncompletedCourse() {
        String line = "3.00/0.00";
        assertTrue(UnitFinder.isUncompletedCourse(line), "Expected: true Actual: false");
        line = "10.00/0.00";
        assertTrue(UnitFinder.isUncompletedCourse(line), "Expected: true Actual: false");
    }
    
    @Test
    public void testIsUncompletedCourse_withCompletedCourse() {
        String line = "0.00/0.00";
        assertFalse(UnitFinder.isUncompletedCourse(line), "Expected: true Actual: false");
        line = "3.00";
        assertFalse(UnitFinder.isUncompletedCourse(line), "Expected: true Actual: false");
        line = "10.00/10.00";
        assertFalse(UnitFinder.isUncompletedCourse(line), "Expected: true Actual: false");
        line = "3.00/3.00";
        assertFalse(UnitFinder.isUncompletedCourse(line), "Expected: true Actual: false");
    }
}
