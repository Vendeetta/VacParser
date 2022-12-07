package Parser.model;

import Parser.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HHStrategy implements Strategy {

    private static final String URL_FORMAT = "https://dolgoprudny.hh.ru/search/vacancy?text=java+%s&page=%d&customDomain=1&hhtmFrom=vacancy_search_list";

    @Override
    public List<Vacancy> getVacancies(String searchString) throws IOException {
        List<Vacancy> result = new ArrayList<>();
        int i = 1;

        do {
            Document doc = getDocument(searchString, i);
            Elements elements = doc.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy vacancy-serp__vacancy_standard");
                for (Element e : elements) {
                    Element adressE = e.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-address").first();
                    String adress = adressE.text();
                    Element titleE = e.getElementsByAttributeValue("data-qa","serp-item__title").first();
                    String title = titleE.text();
                    String url = titleE.attr("href");
                    String companyName = e.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer").first().text();
                    Element salaryE = e.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-compensation").first();
                    String salary = salaryE == null ? "" : salaryE.text();
                    Vacancy vacancy = new Vacancy();
                    vacancy.setTitle(title);
                    vacancy.setSalary(salary);
                    vacancy.setCity(adress);
                    vacancy.setCompanyName(companyName);
                    vacancy.setSiteName("hh.ru");
                    vacancy.setUrl(url);
                    result.add(vacancy);

            }
                i++;
        } while (i<=10);
        return result;
    }

    protected Document getDocument(String searchString, int page) throws IOException {
    String url = String.format(URL_FORMAT, searchString, page);
        return Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
                .referrer("https://hh.ru/")
                .get();
    }
}
