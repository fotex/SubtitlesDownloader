import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.util.HashMap;

public class Menu {

    private Text title;
    private HashMap<String, ImageView> menuList = new HashMap<>();

    public Menu(Text title) {
        this.title = title;
    }

    public String changeFocus(ImageView menu) {

        String name = "";

        for (String key : menuList.keySet()) {
            if (menuList.get(key) == menu) {
                menuList.get(key).getStyleClass().clear();
                menuList.get(key).getStyleClass().add("menu-active");
                title.setText(key);
                name = key;
            } else {
                menuList.get(key).getStyleClass().clear();
                menuList.get(key).getStyleClass().add("menu");
            }
        }

        return name;
    }

    public void add(String name, ImageView menu) {
        menuList.put(name, menu);
    }
}
