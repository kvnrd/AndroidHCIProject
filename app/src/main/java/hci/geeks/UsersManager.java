package hci.geeks;

import java.util.ArrayList;

public class UsersManager {

    private ArrayList<User> users;

    public void newList(){
        users = new ArrayList<>();
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public User getUsers(int index){
        return users.get(index);
    }

    public void addUser(User user){
        this.users.add(user);
    }

    public void removeUser(int index){
        users.remove(index);
    }

}
