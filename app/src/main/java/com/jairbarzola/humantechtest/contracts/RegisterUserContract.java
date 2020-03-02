package com.jairbarzola.humantechtest.contracts;

public interface RegisterUserContract {
    interface  View {
        void setLoading(String message, boolean active);
        void next();
        void showModalMessage(String message, int flag);
    }

    interface  Presenter {
        void registerUser (String email,String password,String name);

    }
}
