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
    String userNamesString = new String();

    for(User user : this.users) {
      userNamesString += (user.getName() + ',');
    }

    String fileNamesString = new String();

    for(MyFile file : this.files) {
      fileNamesString += (file.getName() + ',');
    }

    return new String[] {
        this.getName(),
        userNamesString,
        fileNamesString
    };
  }

  public String[] toCsvHeader() {
    return new String[] {
        "Group Name",
        "Users",
        "Files",
    };
  }
}
