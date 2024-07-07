package Courses;

public class CourseFactory {

    //private boolean codeFound = false;
    private boolean unitsFound = false;
    private boolean gradeFound = false;
    private Course course = new Course();

    public boolean isCourseBuilt() { return /*codeFound &&*/ unitsFound && gradeFound; }
    public Course getCourse() {
        Course result = course;
        resetFactory();
        return result;
    }

    public void buildCourse(String line) {

        UnitFinder uFinder = new UnitFinder(line);
        if (uFinder.containsUnits()) {
            gradeFound = false;
            course.setUnits(uFinder.getUnits());
            unitsFound = true;
        }

        CodeFinder cFinder = new CodeFinder(line);
        if (cFinder.containsCourse()) {
            course.setCode(cFinder.getCourse());
            //codeFound = true;
        }

        GradeFinder gFinder = new GradeFinder();
        if (gFinder.containsGrade(line)) {
            course.setGrade(gFinder.getGrade(line));
            gradeFound = true;
        }
    }

    private void resetFactory() {
        //codeFound = false;
        unitsFound = false;
        gradeFound = false;
        course = new Course();
    }

}
