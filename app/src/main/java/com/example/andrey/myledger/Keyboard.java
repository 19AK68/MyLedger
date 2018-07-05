package com.example.andrey.myledger;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import android.view.View;

public class Keyboard  {

    public static void hide (View view) {

        Context context = view.getContext();
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);



    }
}
