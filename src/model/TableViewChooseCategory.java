package model;

public class TableViewChooseCategory {

    private String fullName;
    private boolean choose;

    public TableViewChooseCategory(String fullName, boolean choose) {
        this.fullName = fullName;
        this.choose = choose;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isChoose() {
        return choose;
    }

    public void setChoose(boolean choose) {
        this.choose = choose;
    }
}
