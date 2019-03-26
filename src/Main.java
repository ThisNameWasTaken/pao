import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    File file = new MP4("TCR");

    // Services
    AccountService accountService = new AccountService();

    // Service actions
    accountService.logInUser(adi); // 1
    Group amet = accountService.createGroup(new Group("Amet")); // 2
    accountService.joinGroup(testGroup); // 3
    accountService.leaveGroup(ipsum); // 4
    accountService.addAdminToGroup(alexandra, amet); // 5
    accountService.removeAdminToGroup(alexandra, amet); // 6
    accountService.addUserToGroup(mihai, amet); // 7
    accountService.kickUserFromGroup(mihai, amet); // 8
    accountService.uploadFileToGroup(file, amet); // 9
    accountService.playFileFromGroup(file, amet); // 10
    accountService.deleteFileFromGroup(file, amet); // 11
  }
}
