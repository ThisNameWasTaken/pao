package Services;

import Services.LogService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Classes.*;

public class AccountService {
  private User user;
  private LogService logService;

  public AccountService (String timestampFilePath) {
    this.logService = new LogService(timestampFilePath);
  }

  public AccountService () {
    this.logService = new LogService();
  }

  public void logInUser(User user) { // 1
    this.user = user;

    this.logService.logMessage("Logged in as " + user.getName());
  }

  public void joinGroup(Group group) { // 2
    this.addUserToGroup(this.user, group);
  }

  public void leaveGroup(Group group) { // 3
    this.user.leaveGroup(group);
    group.removeUser(this.user);

    this.logService.logMessage(this.user.getName() + " left " + group.getName());
  }

  public Group createGroup(Group group) { // 4
    this.user.joinGroup(group);
    group.addAdmin(this.user);

    this.logService.logMessage(this.user.getName() + " created " + group.getName());

    return group;
  }

  public void kickUserFromGroup(User userToKick, Group group) { // 5
    if(!group.isAdmin(this.user)) {
      System.out.println(this.user.getName() + " is not an Admin of " + group.getName());
      return;
    }

    if(userToKick == this.user) {
      System.out.println("Users cannot kick themselves\n");
      return;
    }

    userToKick.leaveGroup(group);
    group.removeUser(userToKick);

    this.logService.logMessage(userToKick.getName() + " kicked from " + group.getName() + " by " + this.user.getName());
  }

  public void addUserToGroup(User user, Group group) {
    user.joinGroup(group);
    group.addUser(this.user);

    this.logService.logMessage(user.getName() + " joined " + group.getName());
  }

  public void addAdminToGroup(User admin, Group group) { // 9
    group.removeUser(admin);
    group.addAdmin(admin);

    this.logService.logMessage(admin.getName() + " was promoted to admin by " + this.user.getName());
  }

  public void removeAdminToGroup(User admin, Group group) { // 10
    group.removeAdmin(admin);

    this.logService.logMessage(admin.getName() + " was promoted to admin by " + this.user.getName());
  }

  public void uploadFileToGroup(MyFile file, Group group) { // 6
    if(!group.isMember(this.user)) {
      System.out.println(this.user.getName() + " is not a member of " + group.getName());
      return;
    }

    group.uploadFile(file);
    this.logService.logMessage(file.getName() + " was uploaded by " + this.user.getName());
  }

  public void deleteFileFromGroup(MyFile file, Group group) { // 7
    if(!group.isMember(this.user)) {
      System.out.println(this.user.getName() + " is not a member of " + group.getName());
      return;
    }

    group.removeFile(file);
    this.logService.logMessage(file.getName() + " was deleted by " + this.user.getName());
  }

  public void playFileFromGroup(MyFile file, Group group) { // 8
    if(!group.isMember(this.user)) {
      System.out.println(this.user.getName() + " is not a member of " + group.getName());
      return;
    }

    file.open();
  }

  public void writeToCsv(String filePath, String[] header, List<String[]> rows) {
    try (PrintWriter writer = new PrintWriter(new File(filePath))) {

      StringBuilder stringBuilder = new StringBuilder();

      for(String element : header) {
        stringBuilder.append(element);
        stringBuilder.append(',');
      }

      if(stringBuilder.length() > 0) {
        if (stringBuilder.charAt(stringBuilder.length() - 1) == ',') {
          stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
      }

      stringBuilder.append('\n');

      for(String[] row : rows) {
        for(String element : row) {
          stringBuilder.append(element);
          stringBuilder.append(',');
        }

        if(stringBuilder.length() > 0) {
          if (stringBuilder.charAt(stringBuilder.length() - 1) == ',') {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
          }
        }

        stringBuilder.append('\n');
      }

      writer.write(stringBuilder.toString());
    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    }
  }

  public ArrayList<Group> loadGroupsFromCsv(String filePath) {
    ArrayList<Group> groups = new ArrayList<Group>();

    try {
      Scanner scanner = new Scanner(new File(filePath));

//    Skip the header
      if(scanner.hasNextLine()) {
        scanner.nextLine();
      }

      while(scanner.hasNextLine()) {
        String[] properties = scanner.nextLine().split(",");

        String groupName = properties[0];

        String[] groupAdminNames = properties[1].split(";");
        ArrayList<User> admins = new ArrayList<User>();

        for(String adminName : groupAdminNames ) {
          admins.add(new User(adminName));
        }

        String[] groupUserNames = properties[2].split(";");
        ArrayList<User> users = new ArrayList<User>();

        for(String userName : groupUserNames ) {
          users.add(new User(userName));
        }

        String[] groupFileNames = properties[3].split(";");
        ArrayList<MyFile> files = new ArrayList<MyFile>();

        for(String fileName : groupFileNames ) {
          String[] splits = fileName.split("\\.");
          files.add(new MyFile(splits[0], splits[1]));
        }

        groups.add(new Group(groupName, admins, users, files));
      }

      scanner.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());;
    }

    return groups;
  }
}
