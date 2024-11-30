package lsit.Models;

import java.util.UUID;

public class Client {

    public UUID id;

    public String name;
    
    public int credit;

    public Client(UUID id, String name, int credit) {

        this.id = id;

        this.name = name;

        this.credit = credit;

    }
}
