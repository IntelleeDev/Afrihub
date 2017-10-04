package com.mikeoye.afrihub.commons;

/**
 * Created by lami on 3/1/2017.
 */

public final class FirebaseConstants {

    public FirebaseConstants() {
        throw new AssertionError("This class should not be instantiated");
    }

    public static final String USERS_CHILD     =  "users";
    public static final String COURSES_CHILD   =  "courses";
    public static final String TUTORS_CHILD    =  "tutors";
    public static final String STUDENTS_CHILD  =  "students";

    public static final String PROFILE_UPDATE_PATH = USERS_CHILD + "/";

}
