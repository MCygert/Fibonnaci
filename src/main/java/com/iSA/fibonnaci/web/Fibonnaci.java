package com.iSA.fibonnaci.web;

import com.iSA.fibonnaci.cdi.FibonnaciBean;
import com.iSA.fibonnaci.freemarker.TemplateProvider;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = "/aplikacja")
public class Fibonnaci extends HttpServlet {
    private final Logger LOG = LoggerFactory.getLogger(SimpleForm.class);
    private Pattern onlyPlusNumbers = Pattern.compile("^[0-9][0-9]*$");
    private static final String TEMPLATE_FORM = "form";
    private static final String TEMPLATE_BAD_REQUEST = "mistake";
    private static final String TEMPLATE_NUMBERS = "numbers";
    @Inject
    private TemplateProvider templateProvider;
    @Inject
    private FibonnaciBean fibonnaciBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        Map<String, Object> dataModel = new HashMap<>();

        Template template = templateProvider.getTemplate(getServletContext(),TEMPLATE_FORM );
        try {
            template.process(dataModel, resp.getWriter());
        } catch (TemplateException e) {
            LOG.error("Template processing" + e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        Map<String, Object> dataModel = new HashMap<>();
        String number = req.getParameter("number");
        Template template;
        if (number == null || !onlyPlusNumbers.matcher(number).matches()) {
             template = templateProvider.getTemplate(getServletContext(), TEMPLATE_BAD_REQUEST);
        } else {
             template = templateProvider.getTemplate(getServletContext(), TEMPLATE_NUMBERS);
            Integer intNumber = Integer.valueOf(number);
            List<BigDecimal> listOfNumber =  fibonnaciBean.fibonacciNumbers(intNumber);
            dataModel.put("fibonnaciNumbers", listOfNumber);
            dataModel.put("finalNumber", intNumber);
        }
        try {
            template.process(dataModel, resp.getWriter());
        } catch (TemplateException e) {
            LOG.error("Template processing" + e);
        }

    }
}
