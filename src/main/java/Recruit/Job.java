package Recruit;

import java.time.LocalDate;

public class Job {
    public String subject;
    public String link;
    public LocalDate startDate;
    public LocalDate endDate;

    public Job (String subject, String link, LocalDate startDate, LocalDate endDate) {
        this.subject = subject;
        this.link = link;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}