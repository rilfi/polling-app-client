package dcf;


import java.util.Objects;

public class FlyingEntry {

    private String studentName;
    private String aircraftType;
    private String date;
    private Double duration;
    private  Long studentReference;

    public Long getStudentReference() {
        return studentReference;
    }

    public void setStudentReference(Long studentReference) {
        this.studentReference = studentReference;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getAircraftType() {
        return aircraftType;
    }

    public void setAircraftType(String aircraftType) {
        this.aircraftType = aircraftType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "FlyingEntry{" +
                "studentName='" + studentName + '\'' +
                ", aircraftType='" + aircraftType + '\'' +
                ", date='" + date + '\'' +
                ", duration=" + duration +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlyingEntry that = (FlyingEntry) o;
        return Objects.equals(studentName, that.studentName) &&
                Objects.equals(aircraftType, that.aircraftType) &&
                Objects.equals(date, that.date) &&
                Objects.equals(duration, that.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentName, aircraftType, date, duration);
    }
}
