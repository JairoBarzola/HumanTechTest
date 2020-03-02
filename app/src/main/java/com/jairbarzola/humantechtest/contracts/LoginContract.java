package com.jairbarzola.humantechtest.contracts;

public interface LoginContract {

    interface  View {
        void setLoading(String message, boolean active);
        void next();
        void showModalMessage(String message, int flag);
    }

    interface  Presenter {
        void login (String email,String password);

    }


}
