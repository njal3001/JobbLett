package jobblett.ui;

import jobblett.core.Workspace;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class TestServerStarter implements Runnable {
  private Server server;
  private Workspace workspace;

  public TestServerStarter(Workspace workspace) {
    this.workspace = workspace;
  }

  @Override public void run() {
    /*Inspired by https://stackoverflow.com/questions/27965207/
    creating-a-resourceconfig-that-behaves-the-same-way-as-default-jettys-jersey-re*/
    ResourceConfig config = new TestJobblettConfig(workspace);
    config.packages("jetty.practice.resources");
    ServletHolder jerseyServlet
        = new ServletHolder(new ServletContainer(config));

    server = new Server(8999);
    ServletContextHandler context
        = new ServletContextHandler(server, "/");
    context.addServlet(jerseyServlet, "/*");
    try {
      server.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      server.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void stopServer() {
    try {
      server.stop();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
