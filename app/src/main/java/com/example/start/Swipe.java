package com.example.start;

import android.content.Intent;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class Swipe extends GestureDetector.SimpleOnGestureListener {

    private static int MIN_SWIPE_DISTANCE_X = 100;
    private static int MIN_SWIPE_DISTANCE_Y = 100;

    private static int MAX_SWIPE_DISTANCE_X = 1000;
    private static int MAX_SWIPE_DISTANCE_Y = 1000;


    private Activity3 activity3 = null;
    public Activity3 getActivity3(){
        return activity3;
    }
    public void setActivity3(Activity3 activity3){
        this.activity3 = activity3;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float deltaX = e1.getX() - e2.getX();
        float deltaY = e1.getY() - e2.getY();

        float deltaXAbs = Math.abs(deltaX);
        float deltaYAbs = Math.abs(deltaY);

        if (deltaXAbs >= MIN_SWIPE_DISTANCE_X && deltaXAbs <= MAX_SWIPE_DISTANCE_X) {
            if (deltaX > 0) {
                this.activity3.startActivities(new Intent[]{new  Intent(activity3.getApplicationContext(), Home.class)});
            } else {
            }
        }return true;
    }
}
