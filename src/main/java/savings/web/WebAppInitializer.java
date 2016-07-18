package savings.web;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import common.db.EmbeddedDatabaseConfiguration;
import common.db.LocalDatabaseConfiguration;
import common.json.JsonMapperConfiguration;
import savings.repository.impl.RepositoryConfiguration;
import savings.service.impl.ServiceConfiguration;
import savings.web.impl.WebConfiguration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * This can be viewed as a replacement of 'web.xml' in Servlet 3 environment in a Spring application.
 * under the hood Spring will call this class from his implementation of {@link javax.servlet.ServletContainerInitializer}.
 */
public class WebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) throws ServletException {
        /**
         * First we create a {@link org.springframework.web.context.WebApplicationContext}.
         */
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        /**
         * Than we register our {@link org.springframework.context.annotation.Configuration} classes.
         * Alternatively we can use {@link AnnotationConfigWebApplicationContext#scan(String...)} method.
         */
        rootContext.register(
                JsonMapperConfiguration.class,
                LocalDatabaseConfiguration.class,
                RepositoryConfiguration.class,
                ServiceConfiguration.class,
                WebConfiguration.class);
        /**
         * Next we register a {@link ContextLoaderListener} to hook to the servlet lifecycle and load the Spring context.
         */
        container.addListener(new ContextLoaderListener(rootContext));
        /**
         * Next we register the Spring {@link javax.servlet.Servlet} implementation that will handle all the requests.
         * We pass to it the applicationContext we created earlier - this is optional, as {@link DispatcherServlet}
         * will anyway find it in a place where {@link ContextLoaderListener} publishes it, but this way it's more
         * explicit.
         */
        ServletRegistration.Dynamic dispatcherServlet =
                container.addServlet("dispatcher", new DispatcherServlet(rootContext));
        /**
         * Now all that's left is to mark the servlet to be loaded on application startup and assign a servlet mapping.
         */
        dispatcherServlet.setLoadOnStartup(1);
        dispatcherServlet.addMapping("/");
        /**
         * You can add all kinds of {@link javax.servlet.Servlet}s, {@link javax.servlet.ServletContextListener}s,
         * {@link javax.servlet.Filter}s and all other stuff you did in your 'web.xml'. Explore the Servlet 3 API
         * and have fun with it ;)
         */
    }

}
