package info.lmovse.netty4.protocol.support;

public class Person {
    private int age;
    private String name;

    public Person() {
    }

    public Person(final int age, final String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
