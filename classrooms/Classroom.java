package classrooms;

public class Classroom {

    private String id;
    private boolean reserved;

    public Classroom(String id){
        this.id = id;
        this.reserved = false;
    }

    public String getId() {
        return id;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void reserve(){
        reserved = true;
    }

    public void release(){
        reserved = false;
    }
}