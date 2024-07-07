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

import Courses.CodeFinder;

public class CodeFinderTest {

    public static String[] courses;
    public static String[] gradCourses;

    @BeforeAll
    public static void setup() {
        courses = parseCourses("src/test/resources/course_list.txt");
        assertNotNull(courses, "error parsing courses");

        gradCourses = parseCourses("src/test/resources/grad_course_list.txt");
        assertNotNull(gradCourses, "error parsing grad courses");
    }

    @Test
    public void testContainsCourse() {
        for (String courseline : courses) {
            CodeFinder cFinder = new CodeFinder(courseline);
            assertTrue(cFinder.containsCourse(), "Course was not found in: " + courseline);
        }
    }

    @Test
    public void testContainsCourse_forGradCourses() {
        for (String gradCourseline : gradCourses) {
            CodeFinder cFinder = new CodeFinder(gradCourseline);
            assertTrue(cFinder.containsCourse(), "Course was not found in: " + gradCourseline);
        }
    }

    @Test
    public void testGetCourse() {
        for (String courseline : courses) {
            String course = courseline.split("-")[0];
            course = course.split("•").length == 2 ? course.split("•")[1].trim() : course.trim();
            CodeFinder cFinder = new CodeFinder(courseline);
            assertTrue(cFinder.containsCourse());
            assertTrue(course.startsWith(cFinder.getCourse()), "Expected: " + course + " Actual: " + cFinder.getCourse());
        }
    }

    @Test
    public void testGetCourse_withAB() {
        for (String courseline : courses) {
            String course = courseline.split("-")[0];
            course = course.split("•").length == 2 ? course.split("•")[1].trim() : course.trim();
            CodeFinder cFinder = new CodeFinder(course.replaceAll("A/B", "") + "B");
            course += "B";
            assertTrue(cFinder.containsCourse());
            assertTrue(course.startsWith(cFinder.getCourse()), "Expected: " + course + " Actual: " + cFinder.getCourse());
        }
    }

    @Test
    public void testGetCourse_forGradCourses() {
        for (String gradCourseline : gradCourses) {
            String course = gradCourseline.split("/")[0];
            course = course.split("•").length == 2 ? course.split("•")[1].trim() : course.trim();
            CodeFinder cFinder = new CodeFinder(gradCourseline);
            assertTrue(cFinder.containsCourse());
            assertTrue(course.contains(cFinder.getCourse()), "Expected: " + course + " Actual: " + cFinder.getCourse());
        }
    }

    private static String[] parseCourses(String filepath) {
        String[] result = null;
        try(BufferedReader br = new BufferedReader(new FileReader(new File(filepath)))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
        
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
            result = everything.split(System.lineSeparator());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
