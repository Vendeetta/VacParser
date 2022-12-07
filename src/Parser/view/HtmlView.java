package Parser.view;


import Parser.Controller;
import Parser.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HtmlView implements View {

    private Controller controller;

    private final String filePath = "/home/spaikk/IdeaProjects/VacParser/src/Parser/view/vacancies.html";

    @Override
    public void update(List<Vacancy> vacancies) {
        try {
            String newContent = getUpdatedFileContent(vacancies);
            updateFile(newContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void userCitySelectEmulationMethod() {
        controller.onCitySelect("Moscow");
    }

    private String getUpdatedFileContent(List<Vacancy> vacancies) {
        Document doc = null;
        try {
            doc = getDocument();
            Elements template = doc.getElementsByClass("template");
            Element docCopy = template.clone().removeAttr("style").removeClass("template").get(0);
//            docCopy.removeAttr("style");
//            docCopy.removeClass("template");

            Elements vac = doc.getElementsByClass("vacancy");
            for (Element el : vac){
                if (!el.hasClass("template")){
                    el.remove();
                }
            }


            for (Vacancy v : vacancies) {
               Element e =  docCopy.clone();
               e.getElementsByClass("city").first().appendText(v.getCity());
               e.getElementsByClass("companyName").first().appendText(v.getCompanyName());
               e.getElementsByClass("salary").first().appendText(v.getSalary());
               e.getElementsByTag("a").first().text(v.getTitle()).attr("href", v.getUrl());
               template.before(e.outerHtml());

            }
        } catch (Exception e){
            e.printStackTrace();
            return "Some exception occurred";
        }
        return doc.html();
    }

    private void updateFile(String s) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(s);
        }
    }

    protected Document getDocument() throws  IOException {
        Document doc = Jsoup.parse(new File(filePath), "UTF-8");
        System.out.println(doc.text());
        return doc;
    }
}
