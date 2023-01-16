package jackson;

import java.util.Objects;
import java.util.Map;

public class MyDto {

    private Map<String, String> mapOfStrings;

    private String anotherValue;

    public Map<String, String> getMapOfStrings() {
        return mapOfStrings;
    }

    public void setMapOfStrings(Map<String, String> mapOfStrings) {
        this.mapOfStrings = mapOfStrings;
    }

    public String getAnotherValue() {
        return anotherValue;
    }

    public void setAnotherValue(String anotherValue) {
        this.anotherValue = anotherValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyDto myDto = (MyDto) o;
        return Objects.equals(mapOfStrings, myDto.mapOfStrings) && Objects.equals(anotherValue, myDto.anotherValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mapOfStrings, anotherValue);
    }

    @Override
    public String toString() {
        return "MyDto{" +
                "mapOfStrings=" + mapOfStrings +
                ", anotherValue='" + anotherValue + '\'' +
                '}';
    }
}
