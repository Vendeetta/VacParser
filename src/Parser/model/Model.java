package Parser.model;



import Parser.view.View;
import Parser.vo.Vacancy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Model {

    private View view;
    private Provider[] providers;

    public Model(View view, Provider... providers) {
        if (providers == null || providers.length == 0 || view == null) {
            throw new IllegalArgumentException();
        }
        this.view = view;
        this.providers = providers;
    }

    public void selectCity(String city) {
        List<Vacancy> allProvidersVacancy = new ArrayList<>();
        try {
            for (Provider p : providers) {
                allProvidersVacancy.addAll(p.getJavaVacancies(city));
            }
        } catch (IOException e) {

        }
        view.update(allProvidersVacancy);
    }
}
