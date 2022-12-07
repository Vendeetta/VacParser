package Parser;



import Parser.model.HHStrategy;
import Parser.model.HabrCareerStrategy;
import Parser.model.Model;
import Parser.model.Provider;
import Parser.view.HtmlView;

import java.io.IOException;

public class Aggregator {

    public static void main(String[] args) throws IOException {
        HtmlView view = new HtmlView();
        Model model = new Model(view, new Provider(new HabrCareerStrategy()));
        Controller controller = new Controller(model);
        view.setController(controller);
        view.userCitySelectEmulationMethod();
    }
}
