package org.example.skladok.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Socks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemColor;

    private Integer materialPercentage;

    private Long units;

    public Socks() {
    }

    public Socks(Long id, String itemColor, Integer materialPercentage, Long units) {
        this.id = id;
        this.itemColor = itemColor;
        this.materialPercentage = materialPercentage;
        this.units = units;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemColor() {
        return itemColor;
    }

    public void setItemColor(String itemColor) {
        this.itemColor = itemColor;
    }

    public Integer getMaterialPercentage() {
        return materialPercentage;
    }

    public void setMaterialPercentage(Integer materialPercentage) {
        this.materialPercentage = materialPercentage;
    }

    public Long getUnits() {
        return units;
    }

    public void setUnits(Long units) {
        this.units = units;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Socks socks = (Socks) o;
        return Objects.equals(id, socks.id) && Objects.equals(itemColor, socks.itemColor) && Objects.equals(materialPercentage, socks.materialPercentage) && Objects.equals(units, socks.units);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, itemColor, materialPercentage, units);
    }

    @Override
    public String toString() {
        return "Socks{" +
                "id=" + id +
                ", itemColor='" + itemColor + '\'' +
                ", materialPercentage=" + materialPercentage +
                ", units=" + units +
                '}';
    }

}
