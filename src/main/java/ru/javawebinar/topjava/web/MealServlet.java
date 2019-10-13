package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.MealRepoImpl;
import ru.javawebinar.topjava.util.MealsUtil;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealRepository mealRepo;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mealRepo = new MealRepoImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        Meal meal = new Meal(id.isEmpty() ? null : Integer.parseInt(id),
        LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")));
        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        mealRepo.save(meal);
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            log.info("getAll");
            request.setAttribute("mealList",
                    MealsUtil.getWithExceeded(mealRepo.getAll(), 2000));
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            int id = getId(request);
            log.info("Delete {}", id);
            mealRepo.delete(id);
            response.sendRedirect("meals");
        } else {
            final Meal meal = action.equals("create") ?
                    new Meal(LocalDateTime.now(), "", 0) :
                    mealRepo.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
        }
    }
    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

//        List<MealTo> list = MealsUtil.getFiltered(serviceMeal.getAllMeals(), LocalTime.MIN, LocalTime.MAX, MealsUtil.getDefaultCaloriesPerDay());
//        request.setAttribute("Meals", list);
//        request.getRequestDispatcher("/mealList.jsp").forward(request, response);

}
