import java.util.ArrayList;
import java.util.Arrays;

public class Main {
  public static void main(String args[]) {
    User razvan = new User("Razvan");

    AccountService accountService = new AccountService("timestamp-logs.txt");

    ArrayList<Group> csvGroups = accountService.loadGroupsFromCsv("groups-in.csv");

    accountService.logInUser(razvan);
    accountService.joinGroup(csvGroups.get(1));
    accountService.uploadFileToGroup(new PDF("lorem"), csvGroups.get(1));

    ArrayList<String[]> groupsCsvRows = new ArrayList<String[]>();

    for(Group group : csvGroups) {
      groupsCsvRows.add(group.toCsvRow());
    }

    accountService.writeToCsv("groups-out.csv", Group.getCsvHeader(), groupsCsvRows);
  }
}
