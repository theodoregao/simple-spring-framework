package demo.pattern.singleton;

public class EnumStarvingSingleton {
  private EnumStarvingSingleton() {}

  public static EnumStarvingSingleton getInstance() {
    return ContainerHolder.HOLDER.instance;
  }

  private enum ContainerHolder {
    HOLDER;
    private final EnumStarvingSingleton instance;

    ContainerHolder() {
      instance = new EnumStarvingSingleton();
    }
  }
}
