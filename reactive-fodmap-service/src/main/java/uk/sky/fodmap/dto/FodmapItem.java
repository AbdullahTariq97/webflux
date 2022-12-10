package uk.sky.fodmap.dto;

import java.util.Objects;

public class FodmapItem {

    private String vegetableName;

    public FodmapItem(String vegetableName){
        this.vegetableName = vegetableName;
    }

    public FodmapItem(){
    }

    public String getVegetableName() {
        return vegetableName;
    }

    public void setVegetableName(String vegetableName) {
        this.vegetableName = vegetableName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FodmapItem that = (FodmapItem) o;
        return vegetableName.equals(that.vegetableName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vegetableName);
    }

    @Override
    public String toString() {
        return "FodmapItem{" +
                "vegetableName='" + vegetableName + '\'' +
                '}';
    }
}
