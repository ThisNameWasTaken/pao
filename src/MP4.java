public class MP4 extends MyFile {
  MP4(String name) {
    super(name, "MP4");
  }

  @Override
  public void open() {
    System.out.println("Opening MP4");
    System.out.println("Playing " + this.getName());
    System.out.println("");
  }
}
