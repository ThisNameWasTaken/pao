import java.util.ArrayList;
import java.util.Arrays;

public class Main {
  public static void main(String args[]) {
    // Groups
    Group ipsum = new Group("Ipsum");
    Group dolor = new Group("Dolor");
    Group sit = new Group("Sit");
    Group testGroup = new Group("TestGroup");

    // Users
    User adi = new User("Adrian", new ArrayList<Group>(Arrays.asList(ipsum)));
    User mihai = new User("Mihai", new ArrayList<Group>(Arrays.asList(dolor)));
    User alexandra = new User("Alexandra", new ArrayList<Group>(Arrays.asList(sit)));

    // Groups with admins
    Group lorem = new Group("Lorem", new ArrayList<User>(Arrays.asList(adi)));

    // Files
    MyFile file = new MP4("TCR");

    // Services
    AccountService accountService = new AccountService();

    // Service actions
    accountService.logInUser(adi); // 1
    Group amet = accountService.createGroup(new Group("Amet")); // 2
    accountService.joinGroup(testGroup); // 3
//    accountService.leaveGroup(ipsum); // 4
    accountService.addAdminToGroup(alexandra, amet); // 5
//    accountService.removeAdminToGroup(alexandra, amet); // 6
    accountService.addUserToGroup(mihai, amet); // 7
//    accountService.kickUserFromGroup(mihai, amet); // 8
    accountService.uploadFileToGroup(file, amet); // 9
    accountService.playFileFromGroup(file, amet); // 10
//    accountService.deleteFileFromGroup(file, amet); // 11

    for(String element : amet.getCsvHeader()) {
      System.out.println(element);
    }

    for(String element : amet.toCsvRow()) {
      System.out.println(element);
    }

    for(String element : alexandra.getCsvHeader()) {
      System.out.println(element);
    }

    for(String element : alexandra.toCsvRow()) {
      System.out.println(element);
    }

    for(String element : file.getCsvHeader()) {
      System.out.println(element);
    }

    for(String element : file.toCsvRow()) {
      System.out.println(element);
    }

    accountService.writeToCsv(
      "users-1.csv",
      User.getCsvHeader(),
      new ArrayList<String[]>(){{
        add(adi.toCsvRow());
        add(mihai.toCsvRow());
        add(alexandra.toCsvRow());
      }}
    );

    accountService.writeToCsv(
        "groups-1.csv",
        Group.getCsvHeader(),
        new ArrayList<String[]>(){{
          add(lorem.toCsvRow());
          add(ipsum.toCsvRow());
          add(dolor.toCsvRow());
          add(sit.toCsvRow());
          add(amet.toCsvRow());
          add(testGroup.toCsvRow());
        }}
    );

    accountService.writeToCsv(
        "files-1.csv",
        MyFile.getCsvHeader(),
        new ArrayList<String[]>(){{
          add(file.toCsvRow());
        }}
    );
  }
}
