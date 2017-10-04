package com.mikeoye.afrihub.utils;

import android.text.TextUtils;
import android.widget.EditText;

import java.util.List;

/**
 * Created by lami on 3/1/2017.
 */

public final class FormHelper {

    private FormHelper() {
        throw new AssertionError("This class should not be instantiated");
    }

    public static boolean validateNonEmpty(List<EditText> fields) {
        for (EditText field : fields) {
            if (TextUtils.isEmpty(field.getText().toString())) {
                return false;
            }
        }
        return true;
    }

    public static boolean validatePasswords(EditText passFieldOne, EditText passFieldTwo) {
        String passOne = passFieldOne.getText().toString();
        String passTwo = passFieldTwo.getText().toString();

        if (TextUtils.isEmpty(passOne) || TextUtils.isEmpty(passTwo)) {
            return false;
        }
        // Check that passwords match
        if (!passOne.equals(passTwo)) {
            return false;
        }
        return true;
    }

}
