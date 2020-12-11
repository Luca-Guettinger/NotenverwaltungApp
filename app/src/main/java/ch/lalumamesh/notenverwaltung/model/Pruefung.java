package ch.lalumamesh.notenverwaltung.model;

public class Pruefung {
    private Long id;
    private String title;
    private Double note;
    private Semester semester;
    private Fach fach;
    public Pruefung(Long id, String title, Double note, Semester semester, Fach fach) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.semester = semester;
        this.fach = fach;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getNote() {
        return note;
    }

    public void setNote(Double note) {
        this.note = note;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Fach getFach() {
        return fach;
    }

    public void setFach(Fach fach) {
        this.fach = fach;
    }

    @Override
    public String toString() {
        return "Pruefung{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", note=" + note +
                ", semester_id=" + semester.getId() +
                ", fach_id=" + fach.getId() +
                '}';
    }
}
