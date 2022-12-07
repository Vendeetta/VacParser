package Parser.model;


import Parser.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HabrCareerStrategy implements Strategy {

    private static final String URL_FORMAT = "https://career.habr.com/vacancies?page=%d&q=java+%s&type=all";

    @Override
    public List<Vacancy> getVacancies(String searchString) throws IOException {
        List<Vacancy> result = new ArrayList<>();
        Document doc = null;
        int i = 1;
        do {
            doc = getDocument(searchString, i);
            Elements elements = doc.getElementsByClass("vacancy-card");
                for (Element e : elements) {
                    Element adressE = e.getElementsByClass("vacancy-card__meta").first().getElementsByClass("link-comp link-comp--appearance-dark").last();
                    String adress = adressE == null ? "" : adressE.text();
                    Element titleE = e.getElementsByClass("vacancy-card__title").first();
                    String title = titleE.text();
                    String url = "https://career.habr.com" + titleE.getElementsByTag("a").attr("href");
                    String companyName = e.getElementsByClass("vacancy-card__company-title").first().text();
                    Element salaryE = e.getElementsByClass("vacancy-card__salary").first();
                    String salary = salaryE == null ? "" : salaryE.text();
                    Vacancy vacancy = new Vacancy();
                    vacancy.setTitle(title);
                    vacancy.setSalary(salary);
                    vacancy.setCity(adress);
                    vacancy.setCompanyName(companyName);
                    vacancy.setSiteName("habr.ru");
                    vacancy.setUrl(url);
                    result.add(vacancy);
                }
            i++;
            }
        while (i <= 5);
        return result;
    }

    protected Document getDocument(String searchString, int page) throws IOException {

        return Jsoup.connect(String.format(URL_FORMAT, page, searchString))
                .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
                .referrer("https://career.habr.com/")
                .get();
    }
}
