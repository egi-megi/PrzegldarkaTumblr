package com.example.android.przegldarkatumblr;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * Created by egi-megi on 14.10.18.
 */

// This WebVie is to take the photo from html without other things

public class MyWebView extends WebView {

    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
