package jackson;

import java.util.Map;
import java.util.Objects;

public class WrapperClass {

    private Map<String, Map<String, MyDto>> wrapperClass;

    public Map<String, Map<String, MyDto>> getWrapperClass() {
        return wrapperClass;
    }

    public void setWrapperClass(Map<String, Map<String, MyDto>> wrapperClass) {
        this.wrapperClass = wrapperClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WrapperClass that = (WrapperClass) o;
        return Objects.equals(wrapperClass, that.wrapperClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wrapperClass);
    }

    @Override
    public String toString() {
        return "WrapperClass{" +
                "wrapperClass=" + wrapperClass +
                '}';
    }
}
