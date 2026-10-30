package auca.ac.home_services_booking_system.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class WorkReport {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String taskName;
    private String description;
    private int hoursWorked;
    private LocalDate reportDate;

    @ManyToOne
    @JoinColumn(name="job_booking_id")
    private JobBooking jobBooking;

    public WorkReport(){
    }

    public UUID getId(){
        return id;
    }

    public void setId(UUID id){
        this.id=id;
    }

    public String getTaskName(){
        return taskName;
    }

    public void setTaskName(String taskName){
        this.taskName=taskName;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description=description;
    }

    public int getHoursWorked(){
        return hoursWorked;
    }

    public void setHoursWorked(int hoursWorked){
        this.hoursWorked=hoursWorked;
    }

    public LocalDate getReportDate(){
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate){
        this.reportDate=reportDate;
    }

    public JobBooking getJobBooking(){
        return jobBooking;
    }

    public void setJobBooking(JobBooking jobBooking) {
        this.jobBooking=jobBooking;
    }
}