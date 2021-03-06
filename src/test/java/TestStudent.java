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
public class TestStudent {

    private IAdmin admin;
    private IInstructor instructor;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.instructor = new Instructor();
        this.student = new Student();
    }
    /** class exists and has not met enrollment capacity **/
    @Test
    public void testRegisterForClass() {
        this.admin.createClass("ECS189", 2017, "Devanbu", 1);
        this.student.registerForClass("Angie", "ECS189", 2017);
        assertTrue(this.student.isRegisteredFor("Angie", "ECS189", 2017));
    }

    /** class exists and met enrollment capacity **/
    @Test
    public void testRegisterForClass2() {
        this.admin.createClass("ECS189", 2017, "Devanbu", 1);
        IStudent student1 = new Student();
        student1.registerForClass("Vincent", "ECS189", 2017);
        this.student.registerForClass("Angie", "ECS189", 2017);
        assertFalse(this.student.isRegisteredFor("Angie" , "ECS189", 2017));
    }

    /** class does not exist **/
    @Test
    public void testRegisterForClass3() {
        this.admin.createClass("ECS122", 2017, "Bai", 3);
        this.student.registerForClass("Angie", "ECS189", 2017);
        assertFalse(this.student.isRegisteredFor("Angie", "ECS189", 2017));
    }

    /** student registered and class not ended **/
    @Test
    public void testDropClass() {
        this.admin.createClass("ECS189", 2017, "Devanbu", 3);
        this.student.registerForClass("Angie", "ECS189", 2017);
        this.student.dropClass("Angie", "ECS189", 2017);
        assertFalse(this.student.isRegisteredFor("Angie", "ECS189", 2017));
    }

    /** homework exists, student registered , class taught in current year  **/
    @Test
    public void testsubmitHomework(){
        this.admin.createClass("ECS189", 2017, "Devanbu", 10);
        this.student.registerForClass("Angie", "ECS189", 2017);
        this.instructor.addHomework("Devanbu", "ECS189", 2017, "Homework1","First Homework");
        this.student.submitHomework("Angie", "Homework1", "Solution", "ECS189" , 2017);
        assertTrue(this.student.hasSubmitted("Angie", "Homework1", "ECS189", 2017));
    }

    /** homework exists, student not registered, class taught in current year , unreasonable but i was about to be that student ha ha**/
    @Test
    public void testsubmitHomework1(){
        this.admin.createClass("ECS189", 2017, "Devanbu", 10);
        this.admin.createClass("ECS122", 2017, "Bai", 10);

        this.student.registerForClass("Angie", "ECS122", 2017);
        this.instructor.addHomework("Devanbu", "ECS189", 2017, "Homework1","First Homework");
        this.student.submitHomework("Angie", "Homework1", "Solution", "ECS189" , 2017);

        assertFalse(this.student.hasSubmitted("Angie", "Homework1", "ECS189", 2017));
    }

    /** homework doesnt exist, student registered **/
    @Test
    public void testsubmitHomework2(){
        this.admin.createClass("ECS189", 2017, "Devanbu", 10);
        this.admin.createClass("ECS122", 2017, "Bai", 10);

        this.student.registerForClass("Angie", "ECS189", 2017);
        this.instructor.addHomework("Bai", "ECS122", 2017, "Homework1","First Homework");
        this.student.submitHomework("Angie", "Homework1", "Solution", "ECS189" , 2017);

        assertFalse(this.student.hasSubmitted("Angie", "Homework1", "ECS189", 2017));
    }

    /** homework doesnt exist, student not registered **/
    @Test
    public void testsubmitHomework3(){
        this.admin.createClass("ECS189", 2017, "Devanbu", 10);
        this.student.submitHomework("Angie", "Homework1", "Solution", "ECS189" , 2017);
        assertFalse(this.student.hasSubmitted("Angie", "Homework1", "ECS189", 2017));
    }

    @Test
    public void testsubmitHomework4(){
        this.admin.createClass("ECS189", 2017, "Devanbu", 10);
        this.student.registerForClass("Angie", "ECS189", 2017);
        this.instructor.addHomework("Devanbu", "ECS189", 2017, "Homework1","First Homework");

        this.student.submitHomework("Angie", "Homework1", "Solution", "ECS189" , 2018);
        assertFalse(this.student.hasSubmitted("Angie", "Homework1", "ECS189", 2018));
    }

    @Test
    public void testsubmitHomework5(){
        this.admin.createClass("ECS189", 2018, "Devanbu", 10);
        this.student.registerForClass("Angie", "ECS189", 2018);
        this.instructor.addHomework("Devanbu", "ECS189", 2018, "Homework1","First Homework");

        this.student.submitHomework("Angie", "Homework1", "Solution", "ECS189" , 2018);
        assertFalse(this.student.hasSubmitted("Angie", "Homework1", "ECS189", 2018));
    }

}
