package Parser.model;



import Parser.vo.Vacancy;

import java.io.IOException;
import java.util.List;

public interface Strategy {
    public List<Vacancy> getVacancies(String searchString) throws IOException;
}
