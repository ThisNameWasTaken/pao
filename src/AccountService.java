import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AccountService {
  private User user;

  public void logInUser(User user) { // 1
    this.user = user;

    System.out.println("Logged in as " + user.getName());
    System.out.println("");
  }

  public void joinGroup(Group group) { // 2
    this.addUserToGroup(this.user, group);
  }

  public void leaveGroup(Group group) { // 3
    this.user.leaveGroup(group);
    group.removeUser(this.user);

    System.out.println(this.user.getName() + " left " + group.getName());
    System.out.println(group + ": { users: " + group.getUsers() + ", admins: " + group.getAdmins() + "}");
    System.out.println("");
  }

  public Group createGroup(Group group) { // 4
    this.user.joinGroup(group);
    group.addAdmin(this.user);

    System.out.println(this.user.getName() + " created " + group.getName());
    System.out.println(group + ": { users: " + group.getUsers() + ", admins: " + group.getAdmins() + "}");
    System.out.println("");

    return group;
  }

  public void kickUserFromGroup(User userToKick, Group group) { // 5
    if(!group.isAdmin(this.user)) {
      System.out.println(this.user.getName() + " is not an Admin of " + group.getName() + "\n");
      return;
    }

    if(userToKick == this.user) {
      System.out.println("Users cannot kick themselves\n");
      return;
    }

    userToKick.leaveGroup(group);
    group.removeUser(userToKick);
    System.out.println(userToKick.getName() + " kicked from " + group.getName() + " by " + this.user.getName());
    System.out.println(group + ": { users: " + group.getUsers() + ", admins: " + group.getAdmins() + "}");
    System.out.println("");
  }

  public void addUserToGroup(User user, Group group) {
    user.joinGroup(group);
    group.addUser(this.user);

    System.out.println(user.getName() + " joined " + group.getName());
    System.out.println(group + ": { users: " + group.getUsers() + ", admins: " + group.getAdmins() + "}");
    System.out.println("");
  }

  public void addAdminToGroup(User admin, Group group) { // 9
    group.removeUser(admin);
    group.addAdmin(admin);

    System.out.println(admin.getName() + " was promoted to admin by " + this.user.getName());
    System.out.println(group + ": { users: " + group.getUsers() + ", admins: " + group.getAdmins() + "}");
    System.out.println("");
  }

  public void removeAdminToGroup(User admin, Group group) { // 10
    group.removeAdmin(admin);

    System.out.println(admin.getName() + " was downgraded to user by " + this.user.getName());
    System.out.println(group + ": { users: " + group.getUsers() + ", admins: " + group.getAdmins() + "}");
    System.out.println("");
  }

  public void uploadFileToGroup(MyFile file, Group group) { // 6
    if(!group.isMember(this.user)) {
      System.out.println(this.user.getName() + " is not a member of " + group.getName());
      return;
    }

    System.out.println(file.getName() + " was uploaded by " + this.user.getName());
    System.out.println("");
  }

  public void deleteFileFromGroup(MyFile file, Group group) { // 7
    if(!group.isMember(this.user)) {
      System.out.println(this.user.getName() + " is not a member of " + group.getName());
      return;
    }

    System.out.println(file.getName() + " was deleted by " + this.user.getName());
    System.out.println("");
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
//        System.out.println("properties: ");
//        for(String property : properties) {
//          System.out.println(property);
//        }

//        System.out.println("Group name:");
        String groupName = properties[0];
//        System.out.println(groupName);

        String[] groupAdminNames = properties[1].split(";");
        ArrayList<User> admins = new ArrayList<User>();

//        System.out.println("Admins:");
        for(String adminName : groupAdminNames ) {
//          System.out.println(adminName);
          admins.add(new User(adminName));
        }

        String[] groupUserNames = properties[2].split(";");
        ArrayList<User> users = new ArrayList<User>();

//        System.out.println("Users: ");
        for(String userName : groupUserNames ) {
//          System.out.println(userName);
          users.add(new User(userName));
        }

        String[] groupFileNames = properties[3].split(";");
        ArrayList<MyFile> files = new ArrayList<MyFile>();

//        System.out.println("Files: ");
        for(String fileName : groupFileNames ) {
//          System.out.println(fileName);
          String[] splits = fileName.split("\\.");
//          System.out.print(" FileName: " + splits[0] + " extension: " + "\n");
          files.add(new MyFile(splits[0], splits[1]));
        }

//        System.out.println("Group added");
        groups.add(new Group(groupName, admins, users, files));
      }

      System.out.println(groups.toString());

      scanner.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());;
    }

    return groups;
  }


}
