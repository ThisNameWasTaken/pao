import java.util.ArrayList;
import java.util.List;

public class Group {
  private List<User> users;
  private List<User> admins;
  private String name;
  private List<MyFile> files;

  public void uploadFile(MyFile file) {
    this.files.add(file);
  }

  public void removeFile(MyFile file) {
    this.files.remove(file);
  }

  public Group(String name, List<User> admins, List<User> users, List<MyFile> files) {
    this(name, admins, users);

    this.files = files;
  }

  public Group(String name, List<User> admins, List<User> users) {
    this(name, admins);

    this.users = users;

    for(User user : users) {
      user.joinGroup(this);
    }
  }

  public Group(String name, List<User> admins) {
    this(name);

    this.admins = admins;

    for(User admin : admins) {
      admin.joinGroup(this);
    }
  }

  public Group(String name) {
    this.name = name;
    this.users = new ArrayList<User>();
    this.admins = new ArrayList<User>();
    this.files = new ArrayList<MyFile>();
  }

  public List<User> getUsers() {
    return users;
  }

//  public void setUsers(List<User> users) {
//    this.users = users;
//  }

  public void addUser(User user) { this.users.add(user);}

  public void removeUser(User user) { this.users.remove(user); }

  public List<User> getAdmins() {
    return admins;
  }

//  public void setAdmins(List<User> admins) {
//    this.admins = admins;
//  }

  public void addAdmin(User admin) { this.admins.add(admin); }

  public void removeAdmin(User admin) { this.admins.remove(admin); }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isMember(User user) { return this.users.contains(user); }

  public boolean isAdmin(User user) { return this.admins.contains(user); }

  @Override
  public String toString() {
    return "'" + this.getName() + "'";
  }

  public String[] toCsvRow() {
    StringBuilder userNamesStringBuilder = new StringBuilder();

    for(User user : this.users) {
      userNamesStringBuilder.append(user.getName() + ';');
    }
    if(userNamesStringBuilder.length() > 0) {
      userNamesStringBuilder.deleteCharAt(userNamesStringBuilder.length() - 1);
    }

    StringBuilder adminNamesStringBuilder = new StringBuilder();
    for(User admin: this.admins) {
      adminNamesStringBuilder.append(admin.getName() + ';');
    }
    if(adminNamesStringBuilder.length() > 0) {
      adminNamesStringBuilder.deleteCharAt(adminNamesStringBuilder.length() - 1);
    }

    StringBuilder fileNamesStringBuilder = new StringBuilder();
    for(MyFile file : this.files) {
      fileNamesStringBuilder.append(file.getName() + ';');
    }
    if(fileNamesStringBuilder.length() > 0) {
      fileNamesStringBuilder.deleteCharAt(fileNamesStringBuilder.length() - 1);
    }

    return new String[] {
        this.getName(),
        userNamesStringBuilder.toString(),
        adminNamesStringBuilder.toString(),
        fileNamesStringBuilder.toString()
    };
  }

  public static String[] getCsvHeader() {
    return new String[] {
        "Group Name",
        "Users",
        "Admins",
        "Files",
    };
  }
}
