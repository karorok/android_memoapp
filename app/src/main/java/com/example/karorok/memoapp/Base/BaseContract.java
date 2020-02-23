package com.example.karorok.memoapp.Base;

public interface BaseContract {

    interface Presenter<V extends View> {

        void setView(V view);

        void releaseView();

    }

    interface View {

        void showMessage(String message);

        void hideKeyboard();

    }
}