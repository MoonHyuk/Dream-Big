package Recruit.Company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

import Recruit.Company.YonseiRecruit;
import Recruit.Job;
import com.google.gson.JsonArray;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class YonseiRecruitTest {
    private final YonseiRecruit yonseiRecruit;
    private final JsonArray list;

    YonseiRecruitTest() throws IOException {
        yonseiRecruit = new YonseiRecruit();

        String content = readFileInResources("YonseiRecruit/page_1");
        list = yonseiRecruit.getList(content);
    }

    @Test
    void testGetJobsOnTheDayOf() throws IOException, InterruptedException {
        ArrayList<Job> jobs;

        jobs = yonseiRecruit.getJobsOnTheDayOf(LocalDate.parse("2020-11-23"));
        assertEquals(6, jobs.size());
        assertEquals("[일반-신촌]사무원(정규직전환조건) 일반행정 2011 모집안내", jobs.get(0).subject);
        assertEquals("[일반-신촌]약사(정규직) 세브)약무국 2011 모집", jobs.get(1).subject);
        assertEquals("[일반-신촌]치과위생사(계약직) 치병)보존과 2011 모집", jobs.get(2).subject);
        assertEquals("[일반-신촌]간호사(계약직) 세브)영상의학과 2011 모집", jobs.get(3).subject);
        assertEquals("[일반-신촌]간호사(계약직) 세브)진료협력센터 2011 모집", jobs.get(4).subject);
        assertEquals("[일반-신촌]임상병리사(계약직) 심혈관)심장내과 2011 모집", jobs.get(5).subject);

        jobs = yonseiRecruit.getJobsOnTheDayOf(LocalDate.parse("2020-11-18"));
        assertEquals(1, jobs.size());
        assertEquals("[일반-강남] 간호사(계약직;정규직전환조건) 임상연구관리실 20.11 모집안내", jobs.get(0).subject);

    }

    @Test
    void testGetList() throws IOException {
        assertEquals(
            cleanWhitespaces(readFileInResources("YonseiRecruit/list_0")),
            cleanWhitespaces(list.get(0).toString())
        );

        assertEquals(
            cleanWhitespaces(readFileInResources("YonseiRecruit/list_1")),
            cleanWhitespaces(list.get(1).toString())
        );
    }

    @Test
    void testGetCompanyName() {
        assertEquals("연세대학병원", yonseiRecruit.getCompanyName());
    }

    @Test
    void testGetLink() {
        assertEquals("https://yuhs.recruiter.co.kr/app/jobnotice/view?systemKindCode=MRS2&jobnoticeSn=42529", yonseiRecruit.getLink(list.get(0)));
    }

    @Test
    void testGetSubject() {
        assertEquals("[일반-신촌]약사(정규직) 세브)약무국 2012 모집", yonseiRecruit.getSubject(list.get(0)));
    }

    @Test
    void testGetStartDate() {
        LocalDate startDate = yonseiRecruit.getStartDate(list.get(0));
        assertEquals("2020-12-09", startDate.toString());
    }

    @Test
    void testGetEndDate() {
        LocalDate endDate = yonseiRecruit.getEndDate(list.get(0));
        assertEquals("2020-12-18", endDate.toString());
    }

    private String readFileInResources(String path) throws IOException {
        Path file = Path.of("", "src/test/resources").resolve(path);
        return Files.readString(file);
    }

    private String cleanWhitespaces(String s) {
        return s.replaceAll("\\s+", "");
    }
}
