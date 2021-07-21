package edu.pdx.cs410J.jgolds;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * A unit test for code in the <code>Project3</code> class.  This is different
 * from <code>Project3IT</code> which is an integration test (and can handle the calls
 * to {@link System#exit(int)} and the like.
 */
class Project3Test {

  @Test
  void readmeCanBeReadAsResource() throws IOException {
    try (
      InputStream readme = Project3.class.getResourceAsStream("README.txt")
    ) {
      assertThat(readme, not(nullValue()));
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line = reader.readLine();
      while(line != null) {
        System.out.println(line);
        line = reader.readLine();
      }
    }
  }

  @Test
  void checkBeginTimes(){
    Project3 obj = new Project3();
    String beginTime = "7:33";
    obj.parseTimes(beginTime);
    beginTime = "07:45";
    obj.parseTimes(beginTime);
    beginTime = "12:22";
    obj.parseTimes(beginTime);
  }

  @Test
  void checkEndTimes(){
    Project3 obj = new Project3();
    String endTime = "2:44";
    obj.parseTimes(endTime);
    endTime = "07:45";
    obj.parseTimes(endTime);
    endTime = "12:22";
    obj.parseTimes(endTime);
  }

  @Test
  void checkBeginDates(){
    Project3 obj = new Project3();
    String beginDate = "07/13/2000";
    obj.parseDates(beginDate);
    beginDate = "7/13/2000";
    obj.parseDates(beginDate);
    beginDate = "7/1/2000";
    obj.parseDates(beginDate);
    beginDate = "07/1/2000";
    obj.parseDates(beginDate);
  }

  @Test
  void checkEndDates(){
    Project3 obj = new Project3();
    String endDate = "3/13/2000";
    obj.parseDates(endDate);
    endDate = "3/31/2000";
    obj.parseDates(endDate);
    endDate = "03/31/2000";
    obj.parseDates(endDate);
    endDate = "02/28/2000";
    obj.parseDates(endDate);
  }
}
