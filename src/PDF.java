public class PDF extends File{
  PDF(String name) {
    super(name, "PDF");
  }

  @Override
  public void open() {
    System.out.println("Opening PDF");
  }
}
