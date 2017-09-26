package newHibernate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class MainApp {
	
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		
		SessionFactory factory;
		try {
			factory = new Configuration().configure("resources/hibernate.cfg.xml").buildSessionFactory();

			Session session = factory.openSession();
			session.beginTransaction();

			Person steve = new Person("Steve", "Jobs");
			Person donald = new Person("Donald", "Trump");

			Address valley = new Address("Steve P Jobs", "San Francisco", "11111");
			Address newyork = new Address("Trump Tower", "New York", "22222");
			Address chicago = new Address("Trump Tower", "Chicago", "33333");

			Book wings = new Book("Wings of Fire", 200L);
			System.out.println("" + wings.getName());
			Book srt = new Book("Millian Dreams", 250L);

			donald.getBooks().add(srt);
			steve.getBooks().add(wings);
			steve.getBooks().add(srt);
			steve.getAddresses().add(valley);
			donald.getAddresses().add(newyork);
			donald.getAddresses().add(chicago);

			/*
			 * System.out.println("Creating Person: " + steve.getFirstName());
			 * session.saveOrUpdate(steve); System.out.println(
			 * "Creating Person: " + donald.getFirstName());
			 * session.saveOrUpdate(donald);
			 */

			/**
			 * Disjuction Demo
			 **/

			/*
			 * @SuppressWarnings("deprecation") Criteria criteria =
			 * session.createCriteria(Person.class);
			 * 
			 * Disjunction disjunction = Restrictions.disjunction();
			 * disjunction.add(Restrictions.eq("person_id",93));
			 * disjunction.add(Restrictions.ilike("lastName","Trump"));
			 * criteria.add(disjunction);
			 * criteria.setProjection(Projections.property("firstName"));
			 * 
			 * List<String> persons= criteria.list();
			 * 
			 * 
			 * System.out.println("Persons:"+persons);
			 */

			/**
			 * NamedQueries Demo
			 **/

			Query query = session.getNamedQuery("findPersonByName");
			query.setParameter("id", 92L);
			List<Person> Ps = query.getResultList();
			System.out.println("" + Ps);
			/**
			 * Use of properties file.
			 * 
			 */
			Properties prop = new Properties();
			InputStream input = new FileInputStream("src/main/java/resources/test.properties");
			prop.load(input);
			System.out.println("Properties file data:" + prop.getProperty("lastName"));

			System.out.println("Keys:" + prop.keys().toString());
			Enumeration<Object> list = prop.keys();
			for (; list.hasMoreElements();) {
				System.out.println("Keys:" + list.nextElement().toString());
			}

			session.getTransaction().commit();
			session.close();

		} catch (HibernateException ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
