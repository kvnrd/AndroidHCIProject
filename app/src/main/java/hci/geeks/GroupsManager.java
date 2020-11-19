package hci.geeks;

import java.util.ArrayList;

public class GroupsManager {

    private ArrayList<Group> groups;

    public void newList(){
        groups = new ArrayList<>();
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public Group getGroups(int index){
        return groups.get(index);
    }

    public void addGroup(Group group){
        this.groups.add(group);
    }

    public void removeGroup(int index){
        groups.remove(index);
    }
}
