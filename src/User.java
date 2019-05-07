import java.util.ArrayList;
import java.util.List;

public class User {
  private List<Group> groups;
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public User(String name, List<Group> groups) {
    this(name);
    this.groups = groups;
  }

  public User(String name) {
    this.name = name;
    this.groups = new ArrayList<Group>();
  }

  public List<Group> getGroups() {
    return groups;
  }

//  public void setGroups(List<Group> groups) {
//    this.groups = groups;
//  }

  public void joinGroup(Group group) {
    this.groups.add(group);
  }

  public void leaveGroup(Group group) {
    this.groups.remove(group);
  }

  @Override
  public String toString() {
    return "User: {" +
        "name: '" + name + "'," +
        "groups: " + (this.groups.toString()) + "," +
        "}";
  }


  public String[] toCsvRow() {
    String groupNamesString = new String();

    for(Group group : this.groups) {
      groupNamesString += (group.getName() + ';');
    }

    return new String[] {
        this.getName(),
        groupNamesString,
    };
  }

  public static String[] getCsvHeader() {
    return new String[] {
        "Name",
        "Groups",
    };
  }
}
