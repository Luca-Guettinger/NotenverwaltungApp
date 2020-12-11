package ch.lalumamesh.notenverwaltung.model;


public class Semester {
    private Long id;
    private String name;

    @Override
    public String toString() {
        return name + " (" + id + ")";
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Semester(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
