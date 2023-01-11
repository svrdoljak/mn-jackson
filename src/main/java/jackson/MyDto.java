package jackson;

import java.util.Objects;

public class MyDto {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyDto myDto = (MyDto) o;
        return Objects.equals(name, myDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "MyDto{" +
                "name='" + name + '\'' +
                '}';
    }
}
