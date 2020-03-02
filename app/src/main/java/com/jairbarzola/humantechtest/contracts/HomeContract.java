package com.jairbarzola.humantechtest.contracts;

import com.jairbarzola.humantechtest.data.entities.UserEntity;

public interface HomeContract {

    interface  View {
        void setLoading(String message, boolean active);
        void next();
        void showModalMessage(String message, int flag);
    }

    interface  Presenter {
        void logout ();
        UserEntity getUser();

    }

}
