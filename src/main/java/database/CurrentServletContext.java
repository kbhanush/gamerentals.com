package database;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by arbose on 7/20/2016.
 */
public class CurrentServletContext implements ServletContextListener {

    private static CurrentServletContext _instance= null;

    private ServletContext _context = null;

    public ServletContext getContext(){
        return _context;
    }

    public static CurrentServletContext getInstance(){
        return _instance;
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        System.out.println("[SERVLET CONTEXT] CurrentServletContext instance created");

        _instance = this;
        _context = servletContextEvent.getServletContext();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

        System.out.println("[SERVLET CONTEXT] CurrentServletContext instance Destroyed");
        _context = null;
        _instance = null;
    }
}
