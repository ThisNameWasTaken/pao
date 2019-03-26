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

  public void uploadFileToGroup(File file, Group group) { // 6
    if(!group.isMember(this.user)) {
      System.out.println(this.user.getName() + " is not a member of " + group.getName());
      return;
    }

    System.out.println(file.getName() + " was uploaded by " + this.user.getName());
    System.out.println("");
  }

  public void deleteFileFromGroup(File file, Group group) { // 7
    if(!group.isMember(this.user)) {
      System.out.println(this.user.getName() + " is not a member of " + group.getName());
      return;
    }

    System.out.println(file.getName() + " was deleted by " + this.user.getName());
    System.out.println("");
  }

  public void playFileFromGroup(File file, Group group) { // 8
    if(!group.isMember(this.user)) {
      System.out.println(this.user.getName() + " is not a member of " + group.getName());
      return;
    }

    file.open();
  }
}
