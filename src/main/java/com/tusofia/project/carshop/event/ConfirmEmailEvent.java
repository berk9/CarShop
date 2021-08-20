package com.tusofia.project.carshop.event;

import com.tusofia.project.carshop.database.entity.User;
import org.springframework.context.ApplicationEvent;

public class ConfirmEmailEvent extends ApplicationEvent {
    private final User user;

    public ConfirmEmailEvent(Object source, User user) {
        super(source);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
