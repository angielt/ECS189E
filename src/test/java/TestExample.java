import api.IAdmin;
import api.IInstructor;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Vincent on 23/2/2017.
 */
public class TestExample {

    private IAdmin admin;

    @Before
    public void setup() {
        this.admin = new Admin();
    }

    @Test
    public void testMakeClass() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    @Test
    public void testMakeClass2() {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }

    /** assert false, not supposed to be able to make a class w/ 0 max capacity **/
    @Test
    public void testMakeClass3() {
        this.admin.createClass("Test", 2017, "Instructor", 0);
        assertFalse(this.admin.classExists("Test", 2017));
    }
    /** instructor should not have 3 classes **/
    @Test
    public void testMakeClass4() {
        this.admin.createClass("Test1", 2017, "Instructor", 15);
        this.admin.createClass("Test2", 2017, "Instructor", 15);
        this.admin.createClass("Test3", 2017, "Instructor", 15);
        assertFalse(this.admin.classExists("Test3", 2017));
    }

    /** same class name and year different instructor **/
    @Test
    public void testMakeClass5() {
        this.admin.createClass("Test1", 2017, "Instructor1", 15);
        this.admin.createClass("Test1", 2017, "Instructor2", 15);
        assertFalse(this.admin.getClassInstructor("Test1", 2017).equals("Instructor2"));
    }

    /** negative value capacity **/
    @Test
    public void testMakeClass6() {
        this.admin.createClass("Test1", 2017, "Instructor1", -1);
        assertFalse(this.admin.classExists("Test1", 2017));
    }

    @Test
    public void testMakeClass7() {
        this.admin.createClass("Test1", 2017, "Instructor1", 11);
        this.admin.createClass("Test1", 2020, "Instructor1", 10);
        IInstructor instructor = new Instructor();
        instructor.addHomework("Instructor1", "Test1", 2017, "Homework1" , "first homework");
        assertFalse(instructor.homeworkExists("Test1", 2020,"Homework1" ));
    }

    /**  capacity increased no students **/
    @Test
    public void testChangeCapacity() {
        this.admin.createClass("Test1", 2017, "Instructor1", 15);
        this.admin.changeCapacity("Test1", 2017, 16);
        assertTrue(this.admin.getClassCapacity("Test1", 2017) == 16);
    }

    @Test
    public void testChangeCapacity1() {
        this.admin.createClass("Test1", 2017, "Instructor1", 15);
        this.admin.changeCapacity("Test1", 2017, 13);
        assertTrue(this.admin.getClassCapacity("Test1", 2017) == 13);
    }

    /** changed to same capacity **/
    @Test
    public void testChangeCapacity2() {
        this.admin.createClass("Test1", 2017, "Instructor1", 15);
        this.admin.changeCapacity("Test1", 2017, 15);
        assertTrue(this.admin.getClassCapacity("Test1", 2017) == 15);
    }
    /** changed to capacity lower that enrolled students **/
    @Test
    public void testChangeCapacity3() {
        this.admin.createClass("Test1", 2017, "Instructor1", 15);

        IStudent student1 = new Student();
        IStudent student2 = new Student();
        student1.registerForClass("Vincent", "Test1", 2017);
        student2.registerForClass("Prem", "Test1", 2017);

        this.admin.changeCapacity("Test1", 2017, 1);
        assertTrue(this.admin.getClassCapacity("Test1", 2017) == 15);
    }

    /** changed class capacity to number of enrolled students **/
    @Test
    public void testChangeCapacity4() {
        this.admin.createClass("Test1", 2017, "Instructor1", 15);

        IStudent student1 = new Student();
        IStudent student2 = new Student();
        student1.registerForClass("Vincent", "Test1", 2017);
        student2.registerForClass("Prem", "Test1", 2017);

        this.admin.changeCapacity("Test1", 2017, 2);
        assertTrue(this.admin.getClassCapacity("Test1", 2017) == 2);
    }

    /** should not be 0 **/
    @Test
    public void testChangeCapacity5() {
        this.admin.createClass("Test1", 2017, "Instructor1", 10);
        this.admin.changeCapacity("Test1", 2017, 0);
        assertFalse(this.admin.getClassCapacity("Test1", 2017) == 10);
    }

}

