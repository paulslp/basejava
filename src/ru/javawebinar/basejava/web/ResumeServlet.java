package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;
import ru.javawebinar.basejava.storage.SqlStorage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.DriverManager;
import java.util.List;
import java.util.Properties;

public class ResumeServlet extends HttpServlet {

    private SqlStorage storage;


    @Override
    public void init() {
        try (InputStream is = getServletContext().getResourceAsStream("WEB-INF\\config\\resumes.properties")) {
            Properties props = new Properties();
            props.load(is);
            storage = new SqlStorage(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file resumes.properties");
        }

        storage.save(new Resume("Resume 1"));
        storage.save(new Resume("Resume 2"));
        storage.save(new Resume("Resume 3"));

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        response.getWriter().write(generateMainPageContent() + generateAllResumeHtmlTable());
    }

    private String generateMainPageContent() {
        return "        <!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <link rel=\"stylesheet\" href=\"css/style.css\">\n" +
                "    <title>Курс JavaSE + Web.</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<header>Приложение вебинара <a href=\"http://javawebinar.ru/basejava/\" target=\"_blank\">Практика Java. Разработка Web\n" +
                "        приложения.\"</a></header>\n" +
                "                <h1>Курс JavaSE + Web</h1>\n" +
                "<footer>Приложение вебинара <a href=\"http://javawebinar.ru/basejava/\" target=\"_blank\">Практика Java. Разработка Web\n" +
                "        приложения.\"</a></footer>\n" +
                "                </body>\n" +
                "</html>\n<br>";
    }


    private String generateAllResumeHtmlTable() {
        String htmlTable = "<table border=\"1\"><tr><td><b>Resume_uuid</b></td><td><b>FullName</b></td></tr>";

        List<Resume> resumeList = storage.getAllSorted();
        for (Resume r : resumeList) {
            htmlTable = htmlTable + "<tr><td>" + r.getUuid() + "</td>" + "<td>" + r.getFullName() + "</td></tr>";

        }
        return htmlTable + "</table>";
    }

    private String generateResumeHtmlTable(String uuid) {

        return "";
    }
}