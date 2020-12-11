package ch.lalumamesh.notenverwaltung.model;


public class Fach {
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

    public Fach(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
