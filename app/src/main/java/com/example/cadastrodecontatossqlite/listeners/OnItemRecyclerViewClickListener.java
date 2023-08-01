package com.example.cadastrodecontatossqlite.listeners;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class OnItemRecyclerViewClickListener implements RecyclerView.OnItemTouchListener {

    public GestureDetector detector;
    public OnClickListener listener;

    public interface OnClickListener {
        void onItemClick(View view, int position);
        void onLongItemClick(View view, int position);
    }

    public OnItemRecyclerViewClickListener(Context context, RecyclerView recyclerView, OnClickListener listener) {
        this.listener = listener;
        this.detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(),e.getY());
                if(child != null && listener != null) {
                    listener.onLongItemClick(child, recyclerView.getChildAdapterPosition(child));
                }
                super.onLongPress(e);
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent ev) {
        View child = recyclerView.findChildViewUnder(ev.getX(),ev.getY());
        if(child != null && listener != null && detector.onTouchEvent(ev)) {
            listener.onItemClick(child, recyclerView.getChildAdapterPosition(child));
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent ev) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }
}
