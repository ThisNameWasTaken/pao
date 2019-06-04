package Classes;

public class PDF extends MyFile {
  PDF(String name) {
    super(name, "pdf");
  }

  @Override
  public void open() {
    System.out.println("Opening pdf");
  }
}
