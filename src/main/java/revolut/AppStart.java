package revolut;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.sql.*;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class AppStart {
	public static final URI BASE_URI = URI.create("http://localhost:2222/");
	private static final Logger LOGGER = Logger.getLogger(AppStart.class.getName());

	public static void main(String[] args) {
		try {
			final ResourceConfig resourceConfig = new ResourceConfig();
			resourceConfig.packages("revolut");
			final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resourceConfig);
			server.start();

//			Class.forName("org.h2.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:h2:mem:./db",
					"sa", "");
			Statement st = null;
			st = conn.createStatement();
			st.execute("create table test (id bigint auto_increment ,name varchar(50) not null)");
			st.execute("INSERT INTO TEST VALUES(default,'HELLO')");
			st.execute("INSERT INTO TEST(NAME) VALUES('JOHN')");
			String name1 = "Jack";
			String q = "insert into TEST(name) values(?)";
			PreparedStatement st1 = null;

			st1 = conn.prepareStatement(q);
			st1.setString(1, name1);
			st1.execute();

			ResultSet result;
			result = st.executeQuery("SELECT * FROM TEST");
			while (result.next()) {
				String name = result.getString("NAME");
				System.out.println(result.getString("ID")+" "+name);
			}

			System.in.read();





		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
