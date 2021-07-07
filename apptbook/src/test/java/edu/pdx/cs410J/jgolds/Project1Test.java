package edu.pdx.cs410J.jgolds;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * A unit test for code in the <code>Project1</code> class.  This is different
 * from <code>Project1IT</code> which is an integration test (and can handle the calls
 * to {@link System#exit(int)} and the like.
 */
class Project1Test {

  @Test
  void readmeCanBeReadAsResource() throws IOException {
    try (
      InputStream readme = Project1.class.getResourceAsStream("README.txt")
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
    Project1 obj = new Project1();
    String beginTime = "7:33";
    obj.parseTimes(beginTime);
    beginTime = "07:45";
    obj.parseTimes(beginTime);
    beginTime = "22:22";
    obj.parseTimes(beginTime);
  }

  @Test
  void checkEndTimes(){
    Project1 obj = new Project1();
    String endTime = "13:44";
    obj.parseTimes(endTime);
    endTime = "07:45";
    obj.parseTimes(endTime);
    endTime = "22:22";
    obj.parseTimes(endTime);
  }

  @Test
  void checkBeginDates(){
    Project1 obj = new Project1();
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
    Project1 obj = new Project1();
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
