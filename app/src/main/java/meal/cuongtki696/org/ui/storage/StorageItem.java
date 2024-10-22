package meal.cuongtki696.org.ui.storage;

import java.util.Date;

public class StorageItem {
    private String name;
    private int quantity;
    private String unit;
    private Date expirationDate;

    public StorageItem(String name, int quantity, String unit,Date expirationDate) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.expirationDate = expirationDate;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
    public String getUnit() {
        return unit;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }
}
