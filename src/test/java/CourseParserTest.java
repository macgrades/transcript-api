import org.junit.jupiter.api.Test;

import Courses.Course;
import PDF.CourseParser;
import PDF.TextConverter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CourseParserTest {

    public static List<Course> helper(String[] courses, int expected) {
        List<Course> result = CourseParser.getGradedCourses(courses);
        assertEquals(expected, result.size());
        return result;
    }

    @Test
    public void realTranscript() {
        try {
            String[] result = TextConverter.convertPDFtoTxt("src/test/resources/transcript.pdf");
            List<Course> courses = CourseParser.getGradedCourses(result);
            for (Course course : courses) {
                System.out.println(course.getCode() + " " + course.getUnits() + " " + course.getGrade());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void allCoursesPresent() {
        String course1 = "MATH 1ZA3 Engineering Mathematics I 3.00/3.00 A+";
        String course2 = "PHYSICS 1D03 Mechanics 3.00/3.00 A";
        String course3 = "SFWRENG 2DM3 Discrete Math. With Appl. I 3.00/3.00 A+";
        String[] courses = {course1, course2, course3};
        helper(courses, 3);
    }

    @Test
    public void firstCourseNotFinished() {
        String course1 = "MATH 1ZA3 Engineering Mathematics I 3.00/3.00 WRONG";
        String course2 = "PHYSICS 1D03 Mechanics 3.00/3.00 A";
        String course3 = "SFWRENG 2DM3 Discrete Math. With Appl. I 3.00/3.00 A+";
        String[] courses = {course1, course2, course3};
        List<Course> result = helper(courses, 2);
        assertEquals(result.get(0).getCode(), "PHYSICS 1D03");
        assertEquals(result.get(0).getUnits(), "3.00");
        assertEquals(result.get(0).getGrade(), "A");
        assertEquals(result.get(1).getCode(), "SFWRENG 2DM3");
        assertEquals(result.get(1).getUnits(), "3.00");
        assertEquals(result.get(1).getGrade(), "A+");
    }

    @Test
    public void middleCourseMissingCode() {
        String course1 = "MATH 1ZA3 Engineering Mathematics I 3.00/3.00 A+";
        String course2 = "Mechanics 3.00/3.00 C-";
        String course3 = "SFWRENG 2DM3 Discrete Math. With Appl. I 3.00/3.00 A+";
        String[] courses = {course1, course2, course3};
        helper(courses, 3);
    }

    @Test
    public void firstCourseSplitBetweenLines() {
        String course1_1 = "MATH 1ZA3 ";
        String stuff = "some useless stuff";
        String course1_2 = "Engineering Mathematics I";
        String course1_3 = "3.00/3.00";
        String stuff2 = "some more useless stuff";
        String course1_4 = "A+";
        String course2 = "PHYSICS 1D03 Mechanics 3.00/3.00 A";
        String course3 = "SFWRENG 2DM3 Discrete Math. With Appl. I 3.00/3.00 A+";
        String[] courses = {course1_1, stuff, course1_2, course1_3, stuff2, course1_4, course2, course3};
        helper(courses, 3);
    }

    @Test
    public void firstCourseSplitBetweenLinesMissingCode() {
        String course1_1 = "NAME MISSING";
        String stuff = "some useless stuff";
        String course1_2 = "Engineering Mathematics I";
        String course1_3 = "3.00/3.00";
        String stuff2 = "some more useless stuff";
        String course1_4 = "A+";
        String course2 = "PHYSICS 1D03 Mechanics 3.00/3.00 A";
        String course3 = "SFWRENG 2DM3 Discrete Math. With Appl. I 3.00/3.00 A+";
        String[] courses = {course1_1, stuff, course1_2, course1_3, stuff2, course1_4, course2, course3};
        helper(courses, 3);
    }

    @Test
    public void firstCourseSplitBetweenLinesNotIdentifiableByGrade() {
        String course1_1 = "MATH 1ZA3 ";
        String stuff = "some useless stuff";
        String course1_2 = "Engineering Mathematics I";
        String course1_3 = "3.00/0.00";
        String stuff2 = "some more useless stuff";
        String course1_4 = "F";
        String course2 = "PHYSICS 1D03 Mechanics 3.00/3.00 A";
        String course3 = "SFWRENG 2DM3 Discrete Math. With Appl. I 3.00/3.00 B";
        String[] courses = {course1_1, stuff, course1_2, course1_3, stuff2, course1_4, course2, course3};
        List<Course> results = helper(courses,3);
        assertEquals(results.get(1).getCode(), "PHYSICS 1D03");
        assertEquals(results.get(1).getUnits(), "3.00");
        assertEquals(results.get(1).getGrade(), "A");
        assertEquals(results.get(2).getCode(), "SFWRENG 2DM3");
        assertEquals(results.get(2).getUnits(), "3.00");
        assertEquals(results.get(2).getGrade(), "B");
    }

    @Test
    public void secondCourseValidButMissingCode() {
        String course1 = "MATH 1ZA3 Engineering Mathematics I 3.00/3.00 A+";
        String course2 = "SOMECODE Mechanics 3.00/3.00 A";
        String course3 = "SFWRENG 2DM3 Discrete Math. With Appl. I 3.00/3.00 A+";
        String[] courses = {course1, course2, course3};
        List<Course> result = CourseParser.getGradedCourses(courses);
        assertEquals(3, result.size());
        assertEquals(result.get(1).getCode(), "Unrecognized Course");
        assertEquals(result.get(1).getUnits(), "3.00");
        assertEquals(result.get(1).getGrade(), "A");
    }

    @Test
    public void failedCourse() {
        String course1 = "MATH 1ZA3 Engineering Mathematics I 3.00/0.00 F";
        String[] courses = {course1};
        helper(courses, 1);
    }
}
