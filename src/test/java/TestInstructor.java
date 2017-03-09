import api.IAdmin;
import api.IInstructor;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Created by Angie on 3/7/17.
 */
public class TestInstructor {

    private IAdmin admin;
    private IInstructor instructor;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.instructor = new Instructor();
    }
    /** instructor assigned**/
    @Test
    public void testAddHomework() {
        this.admin.createClass("Test1", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor", "Test1", 2017, "Homework1", "first homework");
        assertTrue(this.instructor.homeworkExists("Test1", 2017, "Homework1" ));
    }
    /** instructor wrong class / not assigned to class **/
    @Test
    public void testAddHomework1() {
        this.admin.createClass("Test1", 2017, "Instructor1", 15);
        this.admin.createClass("Test2", 2017, "Instructor2", 15);
        this.instructor.addHomework("Instructor2", "Test1", 2017, "Homework1", "first homework");
        assertFalse(this.instructor.homeworkExists("Test1", 2017, "Homework1" ));
    }

    /** homework past year **/
    @Test
    public void testAddHomework2() {
        this.admin.createClass("Test1", 2017, "Instructor1", 15);
        this.instructor.addHomework("Instructor1", "Test1", 2016, "Homework1", "first homework");
        assertFalse(this.instructor.homeworkExists("Test1", 2016, "Homework1" ));
    }
    /** homework wrong year **/
    @Test
    public void testAddHomework3() {
        this.admin.createClass("Test2", 2017, "Instructor1", 15);
        this.instructor.addHomework("Instructor1", "Test1", 2018, "Homework1", "first homework");
        assertFalse(this.instructor.homeworkExists("Test1", 2018, "Homework1" ));
    }

    /** duplicate hw ?!?!?
     @Test
     public void testAddHomework4() {
     admin.createClass("Test", 2017, "Instructor", 15);
     this.instructor.addHomework("Instructor", "Test1", 2018, "Homework1", "first homework");
     assertFalse(this.instructor.homeworkExists("Test1", 2018, "Homework1" ));
     } **/

    /** wrong year **/
    @Test
    public void testAddHomework4() {
        this.admin.createClass("Test1", 2017, "Instructor1", 15);

        this.instructor.addHomework("Instructor1", "Test1", 2018, "Homework1", "first homework");
        assertFalse(this.instructor.homeworkExists("Test2", 2018, "Homework1"));
    }

    @Test
    public void testAddHomework5() {
        this.admin.createClass("Test1", 2017, "Instructor1", 15);
        this.instructor.addHomework("Instructor2", "Test1", 2017, "Homework1", "first homework");
    }

    /** instructor assigned, homework assigned, student submitted **/
    @Test
    public void testAssignGrade() {
        this.admin.createClass("Test1", 2017, "Instructor1", 15);
        this.instructor.addHomework("Instructor1", "Test1", 2017, "Homework1", "First Homework");
        IStudent student = new Student();
        student.registerForClass("Vincent", "Test1", 2017);
        student.submitHomework("Vincent", "Homework1", "Solution", "Test1" , 2017);
        this.instructor.assignGrade("Instructor1", "Test1", 2017, "Homework1", "Vincent", 100);
        assertNotNull(this.instructor.getGrade("Test1", 2017, "Homework1", "Vincent"));
    }

    /** instructor not assigned **/
    @Test
    public void testAssignGrade2() {
        this.admin.createClass("Test1", 2017, "Instructor1", 15);
        this.instructor.addHomework("Instructor1", "Test1", 2017, "Homework1", "First Homework");
        IStudent student = new Student();
        student.registerForClass("Vincent", "Test1", 2017);
        student.submitHomework("Vincent", "Homework1", "Solution", "Test1" , 2017);
        this.instructor.assignGrade("InstructorNOTASSIGNED", "Test1", 2017, "Homework1", "Vincent", 100);
        assertNull(this.instructor.getGrade("Test1", 2017, "Homework1", "Vincent"));
    }

    /** homework not assigned**/
    @Test
    public void testAssignGrade3() {
        this.admin.createClass("Test1", 2017, "Instructor1", 15);
        this.instructor.addHomework("Instructor1", "Test1", 2017, "Homework2", "First Homework");
        IStudent student = new Student();
        student.registerForClass("Vincent", "Test1", 2017);
        student.submitHomework("Vincent", "Homework1", "Solution", "Test1" , 2017);
        this.instructor.assignGrade("Instructor1", "Test1", 2017, "Homework1", "Vincent", 100);
        assertNull(this.instructor.getGrade("Test1", 2017, "Homework1", "Vincent"));
    }

    /** Student never submitted homework **/
    @Test
    public void testAssignGrade4() {
        this.admin.createClass("Test1", 2017, "Instructor1", 15);
        this.instructor.addHomework("Instructor1", "Test1", 2017, "Homework1", "First Homework");
        IStudent student = new Student();
        IStudent student1 =  new Student();
        student.registerForClass("Vincent", "Test1", 2017);
        student1.registerForClass("NOTVincent", "Test1", 2017);

        student1.submitHomework("NOTVincent", "Homework1", "Solution", "Test1" , 2017);

        this.instructor.assignGrade("Instructor1", "Test1", 2017, "Homework1", "Vincent", 100);
        this.instructor.assignGrade("Instructor1", "Test1", 2017, "Homework1", "NOTVincent", 80 );

        assertNull(this.instructor.getGrade("Test1", 2017, "Homework1", "Vincent"));
    }
    /** percent grade over 100 **/
    @Test
    public void testAssignGrade5() {
        this.admin.createClass("Test1", 2017, "Instructor1", 15);
        this.instructor.addHomework("Instructor1", "Test1", 2017, "Homework1", "First Homework");
        IStudent student = new Student();
        student.registerForClass("Vincent", "Test1", 2017);
        student.submitHomework("Vincent", "Homework1", "Solution", "Test1" , 2017);
        this.instructor.assignGrade("Instructor1", "Test1", 2017, "Homework1", "Vincent", 101);
        assertNotNull(this.instructor.getGrade("Test1", 2017, "Homework1", "Vincent"));
    }

    /** percent grade under 0 **/
    @Test
    public void testAssignGrade6() {
        this.admin.createClass("Test1", 2017, "Instructor1", 15);
        this.instructor.addHomework("Instructor1", "Test1", 2017, "Homework1", "First Homework");
        IStudent student = new Student();
        student.registerForClass("Vincent", "Test1", 2017);
        student.submitHomework("Vincent", "Homework1", "Solution", "Test1" , 2017);
        this.instructor.assignGrade("Instructor1", "Test1", 2017, "Homework1", "Vincent", -1);
        assertNull(this.instructor.getGrade("Test1", 2017, "Homework1", "Vincent"));
    }
}
