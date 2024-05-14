package database;

//date model from database table
public class Category {
    private int categoryId;
    private String categoryName;

    //id
    public int getCategoryId() {
        return categoryId;
    }
    // public void setCategoryId(int categoryId) {
    //     this.categoryId = categoryId;
    // }

    //name
    public String getCategoryName() {
        return categoryName;
    }
    // public void setCategoryName(String categoryName) {
    //     this.categoryName = categoryName;
    // }

    //set both
    public Category(int categoryId, String categoryName){
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

}
