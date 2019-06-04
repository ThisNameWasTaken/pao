import Classes.Group;
import Classes.MyFile;
import Classes.PDF;
import Classes.User;
import Services.AccountService;

import java.util.ArrayList;

public class Main {
  public static void main(String args[]) {
    User razvan = new User("Razvan");

    AccountService accountService = new AccountService("timestamp-logs.txt");

    ArrayList<Group> csvGroups = accountService.loadGroupsFromCsv("groups-in.csv");

    accountService.logInUser(razvan);
    accountService.joinGroup(csvGroups.get(1));
    accountService.uploadFileToGroup(new MyFile("ipsum", "txt"), csvGroups.get(1));

    ArrayList<String[]> groupsCsvRows = new ArrayList<String[]>();

    for(Group group : csvGroups) {
      groupsCsvRows.add(group.toCsvRow());
    }

    accountService.writeToCsv("groups-out.csv", Group.getCsvHeader(), groupsCsvRows);
  }
}
