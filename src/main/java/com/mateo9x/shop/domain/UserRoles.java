package com.mateo9x.shop.domain;

import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Table(name = "users_roles")
public class UserRoles {

    @JoinColumn(name = "user_id")
    private User user;
    @JoinColumn(name = "roles_id")
    private Role role;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
