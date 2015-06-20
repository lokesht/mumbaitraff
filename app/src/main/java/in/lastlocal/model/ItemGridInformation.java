package in.lastlocal.model;

/**
 * Created by USER on 20-Jun-15.
 */
public class ItemGridInformation {

    String menuItemName;
    String color;
    int menuIcon;

    public ItemGridInformation() {
    }

    public ItemGridInformation(String menuItemName, String color, int menuIcon) {
        this.menuItemName = menuItemName;
        this.color = color;
        this.menuIcon = menuIcon;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(int menuIcon) {
        this.menuIcon = menuIcon;
    }
}
