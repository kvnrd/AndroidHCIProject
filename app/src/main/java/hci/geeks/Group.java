package hci.geeks;

public class Group {

    private int id;
    private String name, subject, privacy;
    int members;

    public Group(int id, String name, String subject, String privacy, int members){
        this.id = id;
        this.name = name;
        this.subject = subject;
        this.privacy = privacy;
        this.members = members;
    }

    public Group(String name, String subject, String privacy, int members){
        this.name = name;
        this.subject = subject;
        this.privacy = privacy;
        this.members = members;
    }

    public void setId(int id) { this.id = id; }

    public int getId() { return id; }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setSubject(String subject){
        this.subject = subject;
    }

    public String getSubject(){
        return this.subject;
    }

    public void setPrivacy(String privacy){
        this.privacy = privacy;
    }

    public String getPrivacy(){
        return this.privacy;
    }

    public void setMembers(int members){
        this.members = members;
    }

    public int getMembers(){
        return this.members;
    }

}
