public class PDF extends MyFile {
  PDF(String name) {
    super(name, "PDF");
  }

  @Override
  public void open() {
    System.out.println("Opening PDF");
  }
}
