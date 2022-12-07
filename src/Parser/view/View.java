package Parser.view;



import Parser.Controller;
import Parser.vo.Vacancy;

import java.util.List;

public interface View {

    void update(List<Vacancy> vacancies);
    void setController(Controller controller);
}
