package blackdot_test;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.plus.webapp.EnvConfiguration;
import org.eclipse.jetty.plus.webapp.PlusConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.FragmentConfiguration;
import org.eclipse.jetty.webapp.JettyWebXmlConfiguration;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.eclipse.jetty.webapp.WebXmlConfiguration;

public class TurnkeyMain {
    public static void main(String... args) throws Exception {
        System.out.println("hello, world");
        int port = 8080;
        Server server = new Server(port);

        //URI webResourceBase = findWebResourceBase(TurnkeyMain.class.getClassLoader());
        URI webResourceBase = URI.create("jar:file:/mnt/c/Users/simon/Documents/blackdot_test/build/libs/blackdot_test.war!/");
        System.err.println("Using BaseResource: " + webResourceBase);
        WebAppContext context = new WebAppContext();
        context.setBaseResource(Resource.newResource(webResourceBase));
        context.setConfigurations(new Configuration[] 
        { 
            new AnnotationConfiguration(),
            new WebInfConfiguration(), 
            new WebXmlConfiguration(),
            new MetaInfConfiguration(), 
            new FragmentConfiguration(), 
            new EnvConfiguration(),
            new PlusConfiguration(), 
            new JettyWebXmlConfiguration() 
        });

        context.setContextPath("/");
        context.setParentLoaderPriority(true);
        server.setHandler(context);
        server.start();
        //server.dump(System.err);
        server.join();
    }

    private static URI findWebResourceBase(ClassLoader classLoader)
    {
        String webResourceRef = "WEB-INF/web.xml";

        try
        {
            // Look for resource in classpath (best choice when working with archive jar/war file)
            URL webXml = classLoader.getResource(webResourceRef);
            if (webXml != null)
            {
                URI uri = webXml.toURI().resolve("..").normalize();
                System.out.println("Web xml: " + webXml.toURI().resolve(".."));
                System.err.printf("WebResourceBase (Using ClassLoader reference) %s%n", uri);
                return uri;
            }
        }
        catch (URISyntaxException e)
        {
            throw new RuntimeException("Bad ClassPath reference for: " + webResourceRef,e);
        }
        throw new UnsupportedOperationException();
    }
}