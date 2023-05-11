package serialization;

import java.util.List;

public class Order {
    private List<String> ingredients;

    // Конструктор для создания заказа
    public Order(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    // Дефолт конструктор
    public Order() {
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}