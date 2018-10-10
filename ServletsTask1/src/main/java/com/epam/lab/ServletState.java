package com.epam.lab;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

public class ServletState extends HttpServlet {
    private List<String> states = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        incrementAndSetCount(req);
        req.setAttribute("lastView", getAndUpdateTime(req));
        req.setAttribute("states", states);
        req.getRequestDispatcher("/WEB-INF/jsp/XMLdata.jsp").forward(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        states.add("First");
        states.add("Second");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        incrementAndSetCount(req);
        req.setAttribute("lastView", getAndUpdateTime(req));
        String parameter = req.getParameter("newState");
        if (parameter != null) {
            if (parameter.isEmpty()) {
                states.add("Empty");
            } else {
                states.add(parameter);
            }
        }
        req.setAttribute("states", states);
        req.getRequestDispatcher("/WEB-INF/jsp/XMLdata.jsp").forward(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        incrementAndSetCount(req);
        req.setAttribute("lastView", getAndUpdateTime(req));
        Map<String, String> params = parseParametersFromRequest(req);
        String newState = params.get("newState");
        int index = Integer.parseInt(params.get("id"));
        if (newState != null) {
            if (newState.isEmpty()) {
                newState = "Empty";
            }
            if (index >= 0 && index < states.size()) {
                states.set(index, newState);
            }
        }
        req.setAttribute("states", states);
        req.getRequestDispatcher("/WEB-INF/jsp/XMLdata.jsp").forward(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        incrementAndSetCount(req);
        req.setAttribute("lastView", getAndUpdateTime(req));
        Map<String, String> params = parseParametersFromRequest(req);
        int index = Integer.parseInt(params.get("id"));
        if (index >= 0 && index < states.size()) {
            states.remove(index);
        }
        req.setAttribute("states", states);
        req.getRequestDispatcher("/WEB-INF/jsp/XMLdata.jsp").forward(req, resp);
    }

    private Map<String, String> parseParametersFromRequest(HttpServletRequest req) throws IOException {
        Map<String, String> params = new HashMap<>();

        String line = (req.getReader().readLine());
            String[] arguments = line.split("&");
            for (String argPair : arguments) {
                String[] arg = argPair.split("=");
                String key = arg[0];
                String value;
                if (arg.length < 2){
                    value = "";
                } else {
                    value = arg[1];
                }
                params.put(key, value);
            }
        return params;
    }

    private void incrementAndSetCount(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Object countAttrib = session.getAttribute("countViews");
        int count;
        if (countAttrib == null) {
            count = 1;
        } else {
            count = (Integer)countAttrib + 1 ;
        }
        session.setAttribute("countViews", count);
    }

    private Date getAndUpdateTime(HttpServletRequest req) {

        HttpSession session = req.getSession();
        Object dateAttrib = session.getAttribute("lastView");
        Date last;
        if (dateAttrib == null) {
            last = new Date();
        } else {
            last = (Date)dateAttrib;
        }
        session.setAttribute("lastView", new Date());
        return last;
    }


}
