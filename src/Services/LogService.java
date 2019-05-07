package Services;

import java.io.*;
import java.sql.Timestamp;
import java.util.Date;

public class LogService {
  private final String filePath;
  private final Boolean isLoggingToFile;

  public LogService(final String filePath) {
    this.filePath = filePath;
    this.isLoggingToFile = true;
  }

  public LogService() {
    this.filePath = "";
    this.isLoggingToFile = false;
  }

  public void logMessage(final String message) {
    final Timestamp timestamp = new Timestamp(new Date().getTime());
    final String timestampedMessage = message + "\n timestamp: " + timestamp + "\n";

    if(this.isLoggingToFile) {
      try (
        PrintWriter out = new PrintWriter(
          new BufferedWriter(
            new FileWriter(this.filePath,true)
          )
        )
      ) {
        out.println(timestampedMessage);
      } catch (IOException  e) {
        System.out.println(e.getMessage());
      }
    } else {
      System.out.println(timestampedMessage);
    }
  }
}
