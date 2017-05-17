package com.mitrakreasindo.pos.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by error on 16/05/17.
 */

public class User {

    private int id;
    private String name;
    private String role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static List<User> data(){
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setId(1);
        user.setName("Nur Cahyo");
        user.setRole("Administrator");
        users.add(user);

        User user2 = new User();
        user2.setId(2);
        user2.setName("Divergent");
        user2.setRole("all");
        users.add(user2);

        User user3 = new User();
        user3.setId(3);
        user3.setName("Dauntles");
        user3.setRole("police");
        users.add(user3);

        User user4 = new User();
        user4.setId(4);
        user4.setName("Erudite");
        user4.setRole("ads manager");
        users.add(user4);

        User user5 = new User();
        user5.setId(5);
        user5.setName("User 5");
        user5.setRole("mimin");
        users.add(user5);

        User user6 = new User();
        user6.setId(6);
        user6.setName("Nur Cahyo");
        user6.setRole("Administrator");
        users.add(user6);

        User user7 = new User();
        user7.setId(7);
        user7.setName("Nur Cahyo");
        user7.setRole("Administrator");
        users.add(user7);

        User user8 = new User();
        user8.setId(8);
        user8.setName("Nur Cahyo");
        user8.setRole("Administrator");
        users.add(user8);

        User user9 = new User();
        user9.setId(9);
        user9.setName("Nur Cahyo");
        user9.setRole("Administrator");
        users.add(user9);

        return users;
    }


}
