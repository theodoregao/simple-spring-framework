package demo.generic;

import java.util.Random;

public class RobotFactory implements GenericIFactory<String, Integer> {
  private final String[] stringRobot = new String[] {"Hello", "Hi"};
  private final Integer[] integerRobot = new Integer[] {111, 000};

  public static void main(String[] args) {
    GenericIFactory<String, Integer> factory = new RobotFactory();
    System.out.println(factory.nextObject());
    System.out.println(factory.nextNumber());
  }

  @Override
  public String nextObject() {
    Random random = new Random();
    return stringRobot[random.nextInt(2)]; // [0,2)
  }

  @Override
  public Integer nextNumber() {
    Random random = new Random();
    return integerRobot[random.nextInt(2)];
  }
}
