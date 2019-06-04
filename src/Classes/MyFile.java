package Classes;

public class MyFile {
  public MyFile(String baseName, String extension) {
    this.baseName = baseName;
    this.extension = extension;
  }

  public String getBaseName() {
    return baseName;
  }

  public void setBaseName(String baseName) {
    this.baseName = baseName;
  }

  public String getExtension() {
    return extension;
  }

  public void setExtension(String extension) {
    this.extension = extension;
  }

  public String getName() { return this.baseName + "." + this.extension; }

  private String baseName;
  private String extension;

//  @Override
  public void open() {
    System.out.println("Opening file");
  }

    public String[] toCsvRow() {
    return new String[] {
        this.getBaseName(),
        this.getExtension(),
    };
  }

  public static String[] getCsvHeader() {
    return new String[] {
        "Base name",
        "Extension",
    };
  }
}
