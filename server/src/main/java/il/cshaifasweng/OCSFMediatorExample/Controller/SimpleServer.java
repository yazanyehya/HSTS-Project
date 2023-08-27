package il.cshaifasweng.OCSFMediatorExample.Controller;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleChatClient;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import il.cshaifasweng.OCSFMediatorExample.Controller.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.Controller.ocsf.ConnectionToClient;
import il.cshaifasweng.OCSFMediatorExample.Controller.ocsf.SubscribedClient;
import javafx.application.Platform;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class SimpleServer extends AbstractServer {

	private static SessionFactory sessionFactory;
	private static Session session;
	private static int numberOfClinets = 0;
	private static ArrayList<SubscribedClient> SubscribersList = new ArrayList<>();
	static
	{
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(Student.class);
		configuration.addAnnotatedClass(Exam.class);
		configuration.addAnnotatedClass(Question.class);
		configuration.addAnnotatedClass(User.class);
		configuration.addAnnotatedClass(Teacher.class);
		configuration.addAnnotatedClass(Subject.class);
		configuration.addAnnotatedClass(Course.class);
		configuration.addAnnotatedClass(ReadyExam.class);
		configuration.addAnnotatedClass(Principle.class);
		configuration.addAnnotatedClass(ExtraTime.class);
		configuration.addAnnotatedClass(Notification.class);


		StandardServiceRegistry serviceRegistry= new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}
	public static void generate()
	{

		session.beginTransaction();

//		User user1 = new User("rami", "simaan", "ramisi7", "123", 0);
//		session.save(user1);
//		Question Q1 = new Question("what is 1+2", "0", "1","2","3");
//		session.save(Q1);
		//User user2 = new User("yazan", "yehya", "yazan9", "1234", 1);
		//User user3 = new User("ahmad", "ig", "ahmad", "1234", 1);
		//Subject math = new Subject("math");
		//Subject english = new Subject("English");
		//session.save(english);
		//User user4 = new Teacher("yazan", "yehya", "yazan9", "1234", 1,math);
		//User user5 = new Teacher("chris", "bumstead", "cbum", "5", 1,english);
		//session.save(user5);
//		Course electronics = new Course("electronic");
//		Subject physics = new Subject("physics");
//
//		Teacher teacher = new Teacher("raghad", "susan", "raghad7", "178",1,physics );
//		teacher.getCourses().add(electronics);
//		electronics.getListOfTeachers().add(teacher);
//		teacher.setSubject(physics);
//		session.save(physics);
//		session.save(electronics);
//		session.save(teacher);


		Subject sports = new Subject("Sports");
		Course football = new Course("Football");
		Course basketball = new Course("Basketball");
		Course golf = new Course("Golf");
		Course gym = new Course("Gym");
		sports.getCourses().add(gym);
		sports.getCourses().add(football);
		sports.getCourses().add(golf);
		sports.getCourses().add(basketball);
		football.setSubject(sports);
		basketball.setSubject(sports);
		golf.setSubject(sports);
		gym.setSubject(sports);


		Subject math = new Subject("Mathematics");
		Course lierarAlgebra = new Course("Linear Algebra");
		Course calculus = new Course("Calculus");
		Course geometry = new Course("Geometry");
		math.getCourses().add(lierarAlgebra);
		math.getCourses().add(calculus);
		math.getCourses().add(geometry);
		lierarAlgebra.setSubject(math);
		calculus.setSubject(math);
		geometry.setSubject(math);



		Subject computerScience = new Subject("Computer Science");
		Course hardware = new Course("Hardware");
		Course CLanguage = new Course("C Language");
		Course softwareEngineering = new Course("Software Engineering");
		Course OperatingSystem = new Course("Operating System");

		hardware.setSubject(computerScience);
		CLanguage.setSubject(computerScience);
		softwareEngineering.setSubject(computerScience);
		OperatingSystem.setSubject(computerScience);

		computerScience.getCourses().add(hardware);
		computerScience.getCourses().add(CLanguage);
		computerScience.getCourses().add(softwareEngineering);
		computerScience.getCourses().add(OperatingSystem);


		Subject physics = new Subject("Physics");
		Course mechanics = new Course("Mechanics");
		Course Astrophysics = new Course("Astrophysics");
		Course NuclearPhysics = new Course("Nuclear Physics");

		physics.getCourses().add(mechanics);
		physics.getCourses().add(Astrophysics);
		physics.getCourses().add(NuclearPhysics);

		NuclearPhysics.setSubject(physics);
		Astrophysics.setSubject(physics);
		mechanics.setSubject(physics);

		Subject economics = new Subject("Economics");
		Course PrinciplesOfEconomics = new Course("Principles of Economics");
		Course FinancialEconomics = new Course("Financial Economics");

		economics.getCourses().add(PrinciplesOfEconomics);
		economics.getCourses().add(FinancialEconomics);
		PrinciplesOfEconomics.setSubject(economics);
		FinancialEconomics.setSubject(economics);

		Teacher cr7 = new Teacher("Cristiano", "Ronaldo", "cr7", "123", 1, "000000001");

		cr7.getSubjects().add(sports);
		cr7.getSubjects().add(math);
		cr7.getCourses().add(lierarAlgebra);
		cr7.getCourses().add(geometry);
		cr7.getCourses().add(football);
		cr7.getSubjects().add(computerScience);
		cr7.getCourses().add(CLanguage);
		cr7.getCourses().add(OperatingSystem);
		cr7.getCourses().add(mechanics);
		cr7.getSubjects().add(physics);
		cr7.getCourses().add(FinancialEconomics);
		cr7.getCourses().add(PrinciplesOfEconomics);
		cr7.getSubjects().add(economics);

		physics.getListOfTeachers().add(cr7);
		sports.getListOfTeachers().add(cr7);
		football.getListOfTeachers().add(cr7);

		math.getListOfTeachers().add(cr7);
		lierarAlgebra.getListOfTeachers().add(cr7);
		geometry.getListOfTeachers().add(cr7);

		computerScience.getListOfTeachers().add(cr7);
		CLanguage.getListOfTeachers().add(cr7);
		OperatingSystem.getListOfTeachers().add(cr7);


		mechanics.getListOfTeachers().add(cr7);

		economics.getListOfTeachers().add(cr7);
		PrinciplesOfEconomics.getListOfTeachers().add(cr7);
		FinancialEconomics.getListOfTeachers().add(cr7);


		Teacher gBale = new Teacher("Gareth", "Bale", "bale11", "123", 1, "000000002");
		gBale.getSubjects().add(sports);
		gBale.getCourses().add(golf);
		gBale.getCourses().add(football);
		sports.getListOfTeachers().add(gBale);
		golf.getListOfTeachers().add(gBale);
		football.getListOfTeachers().add(gBale);
		physics.getListOfTeachers().add(gBale);
		gBale.getSubjects().add(physics);
		gBale.getCourses().add(NuclearPhysics);
		NuclearPhysics.getListOfTeachers().add(gBale);



		Teacher benzema = new Teacher("Karim", "Benzema", "kb9", "123", 1, "000000003");
		benzema.getSubjects().add(sports);
		benzema.getCourses().add(football);
		football.getListOfTeachers().add(benzema);
		sports.getListOfTeachers().add(benzema);
		math.getListOfTeachers().add(benzema);
		benzema.getSubjects().add(math);
		benzema.getCourses().add(calculus);
		calculus.getListOfTeachers().add(benzema);
		benzema.getCourses().add(softwareEngineering);
		benzema.getCourses().add(OperatingSystem);
		softwareEngineering.getListOfTeachers().add(benzema);
		OperatingSystem.getListOfTeachers().add(benzema);
		benzema.getSubjects().add(computerScience);
		computerScience.getListOfTeachers().add(benzema);
		physics.getListOfTeachers().add(benzema);
		benzema.getSubjects().add(physics);
		benzema.getCourses().add(Astrophysics);
		Astrophysics.getListOfTeachers().add(benzema);


		Teacher sam = new Teacher("Sam", "Sullek", "samS", "123", 1, "000000016");

		sam.getSubjects().add(sports);
		sam.getSubjects().add(math);
		sam.getCourses().add(lierarAlgebra);
		sam.getCourses().add(geometry);
		sam.getCourses().add(football);
		sam.getSubjects().add(computerScience);
		sam.getCourses().add(CLanguage);
		sam.getCourses().add(OperatingSystem);
		sam.getCourses().add(mechanics);
		sam.getSubjects().add(physics);

		physics.getListOfTeachers().add(sam);
		sports.getListOfTeachers().add(sam);
		football.getListOfTeachers().add(sam);

		math.getListOfTeachers().add(sam);
		lierarAlgebra.getListOfTeachers().add(sam);
		geometry.getListOfTeachers().add(sam);

		computerScience.getListOfTeachers().add(sam);
		CLanguage.getListOfTeachers().add(sam);
		OperatingSystem.getListOfTeachers().add(sam);

		physics.getListOfTeachers().add(sam);
		mechanics.getListOfTeachers().add(sam);

		Student lucas = new Student("Lucas", "Vazques", "lv17","123",0,"000000004");
		lucas.getCourses().add(football);
		football.getListOfStudents().add(lucas);
		lucas.getSubjects().add(sports);
		sports.getListOfStudents().add(lucas);
		calculus.getListOfStudents().add(lucas);
		lucas.getCourses().add(calculus);
		lucas.getCourses().add(lierarAlgebra);
		lierarAlgebra.getListOfStudents().add(lucas);
		lucas.getCourses().add(gym);
		lucas.getCourses().add(OperatingSystem);
		lucas.getCourses().add(softwareEngineering);
		lucas.getSubjects().add(computerScience);
		computerScience.getListOfStudents().add(lucas);
		gym.getListOfStudents().add(lucas);
		OperatingSystem.getListOfStudents().add(lucas);
		softwareEngineering.getListOfStudents().add(lucas);
		physics.getListOfStudents().add(lucas);
		lucas.getSubjects().add(physics);
		lucas.getCourses().add(mechanics);
		mechanics.getListOfStudents().add(lucas);
		economics.getListOfStudents().add(lucas);
		PrinciplesOfEconomics.getListOfStudents().add(lucas);
		FinancialEconomics.getListOfStudents().add(lucas);
		lucas.getSubjects().add(economics);
		lucas.getCourses().add(FinancialEconomics);
		lucas.getCourses().add(PrinciplesOfEconomics);

		Student mariano = new Student("Mariano", "Diaz", "md25","123",0, "000000005");
		mariano.getCourses().add(football);
		football.getListOfStudents().add(mariano);
		mariano.getCourses().add(golf);
		golf.getListOfStudents().add(mariano);
		mariano.getCourses().add(lierarAlgebra);
		lierarAlgebra.getListOfStudents().add(mariano);

		mariano.getSubjects().add(sports);
		mariano.getSubjects().add(math);
		sports.getListOfStudents().add(mariano);
		math.getListOfStudents().add(mariano);



		Student david = new Student("David", "Laid", "dl7","123",0,"000000007");
		david.getCourses().add(football);
		football.getListOfStudents().add(david);
		david.getSubjects().add(sports);
		sports.getListOfStudents().add(david);
		calculus.getListOfStudents().add(david);
		david.getCourses().add(calculus);
		david.getCourses().add(lierarAlgebra);
		lierarAlgebra.getListOfStudents().add(david);
		david.getCourses().add(gym);
		david.getCourses().add(OperatingSystem);
		david.getCourses().add(softwareEngineering);
		david.getSubjects().add(computerScience);
		computerScience.getListOfStudents().add(david);
		gym.getListOfStudents().add(david);
		OperatingSystem.getListOfStudents().add(david);
		softwareEngineering.getListOfStudents().add(david);
		physics.getListOfStudents().add(david);
		david.getSubjects().add(physics);
		david.getCourses().add(mechanics);
		mechanics.getListOfStudents().add(david);

		Student alex = new Student("Alex", "Eubank", "ae7","123",0,"000000008");
		alex.getCourses().add(football);
		football.getListOfStudents().add(alex);
		alex.getSubjects().add(sports);
		sports.getListOfStudents().add(alex);
		calculus.getListOfStudents().add(alex);
		alex.getCourses().add(calculus);
		alex.getCourses().add(lierarAlgebra);
		lierarAlgebra.getListOfStudents().add(alex);
		alex.getCourses().add(gym);
		alex.getCourses().add(OperatingSystem);
		alex.getCourses().add(softwareEngineering);
		alex.getSubjects().add(computerScience);
		computerScience.getListOfStudents().add(alex);
		gym.getListOfStudents().add(alex);
		OperatingSystem.getListOfStudents().add(alex);
		softwareEngineering.getListOfStudents().add(alex);
		physics.getListOfStudents().add(alex);
		alex.getSubjects().add(physics);
		alex.getCourses().add(mechanics);
		mechanics.getListOfStudents().add(alex);

		Student lexx = new Student("Lexx", "Little", "ll7","123",0,"000000009");
		lexx.getCourses().add(football);
		football.getListOfStudents().add(lexx);
		lexx.getSubjects().add(sports);
		sports.getListOfStudents().add(lexx);
		calculus.getListOfStudents().add(lexx);
		lexx.getCourses().add(calculus);
		lexx.getCourses().add(lierarAlgebra);
		lierarAlgebra.getListOfStudents().add(lexx);
		lexx.getCourses().add(gym);
		lexx.getCourses().add(OperatingSystem);
		lexx.getCourses().add(softwareEngineering);
		lexx.getSubjects().add(computerScience);
		computerScience.getListOfStudents().add(lexx);
		gym.getListOfStudents().add(lexx);
		OperatingSystem.getListOfStudents().add(lexx);
		softwareEngineering.getListOfStudents().add(lexx);
		physics.getListOfStudents().add(lexx);
		lexx.getSubjects().add(physics);
		lexx.getCourses().add(mechanics);
		mechanics.getListOfStudents().add(lexx);

		Student maldini = new Student("Paulo", "Maldini", "pm5","123",0,"000000010");
		maldini.getCourses().add(football);
		football.getListOfStudents().add(maldini);
		maldini.getSubjects().add(sports);
		sports.getListOfStudents().add(maldini);
		calculus.getListOfStudents().add(maldini);
		maldini.getCourses().add(calculus);
		maldini.getCourses().add(lierarAlgebra);
		lierarAlgebra.getListOfStudents().add(maldini);
		maldini.getCourses().add(gym);
		maldini.getCourses().add(OperatingSystem);
		maldini.getCourses().add(softwareEngineering);
		maldini.getSubjects().add(computerScience);
		computerScience.getListOfStudents().add(maldini);
		gym.getListOfStudents().add(maldini);
		OperatingSystem.getListOfStudents().add(maldini);
		softwareEngineering.getListOfStudents().add(maldini);
		physics.getListOfStudents().add(maldini);
		maldini.getSubjects().add(physics);
		maldini.getCourses().add(mechanics);
		mechanics.getListOfStudents().add(maldini);

		Student marco = new Student("Marco", "Asensio", "ms20","123",0,"000000011");
		marco.getCourses().add(football);
		football.getListOfStudents().add(marco);
		marco.getSubjects().add(sports);
		sports.getListOfStudents().add(marco);
		calculus.getListOfStudents().add(marco);
		marco.getCourses().add(calculus);
		marco.getCourses().add(lierarAlgebra);
		lierarAlgebra.getListOfStudents().add(marco);
		marco.getCourses().add(gym);
		marco.getCourses().add(OperatingSystem);
		marco.getCourses().add(softwareEngineering);
		marco.getSubjects().add(computerScience);
		computerScience.getListOfStudents().add(marco);
		gym.getListOfStudents().add(marco);
		OperatingSystem.getListOfStudents().add(marco);
		softwareEngineering.getListOfStudents().add(marco);
		physics.getListOfStudents().add(marco);
		marco.getSubjects().add(physics);
		marco.getCourses().add(mechanics);
		mechanics.getListOfStudents().add(marco);

		Student harry = new Student("Harry", "Kane", "hk9","123",0,"000000012");
		harry.getCourses().add(football);
		football.getListOfStudents().add(harry);
		harry.getSubjects().add(sports);
		sports.getListOfStudents().add(harry);
		calculus.getListOfStudents().add(harry);
		harry.getCourses().add(calculus);
		harry.getCourses().add(lierarAlgebra);
		lierarAlgebra.getListOfStudents().add(harry);
		harry.getCourses().add(gym);
		harry.getCourses().add(OperatingSystem);
		harry.getCourses().add(softwareEngineering);
		harry.getSubjects().add(computerScience);
		computerScience.getListOfStudents().add(harry);
		gym.getListOfStudents().add(harry);
		OperatingSystem.getListOfStudents().add(harry);
		softwareEngineering.getListOfStudents().add(harry);
		physics.getListOfStudents().add(harry);
		harry.getSubjects().add(physics);
		harry.getCourses().add(mechanics);
		mechanics.getListOfStudents().add(harry);

		Student ahmad = new Student("Ahmad", "Mohsen", "ahmadM","123",0,"000000013");
		ahmad.getCourses().add(football);
		football.getListOfStudents().add(ahmad);
		ahmad.getSubjects().add(sports);
		sports.getListOfStudents().add(ahmad);
		calculus.getListOfStudents().add(ahmad);
		ahmad.getCourses().add(calculus);
		ahmad.getCourses().add(lierarAlgebra);
		lierarAlgebra.getListOfStudents().add(ahmad);
		ahmad.getCourses().add(gym);
		ahmad.getCourses().add(OperatingSystem);
		ahmad.getCourses().add(softwareEngineering);
		ahmad.getSubjects().add(computerScience);
		computerScience.getListOfStudents().add(ahmad);
		gym.getListOfStudents().add(ahmad);
		OperatingSystem.getListOfStudents().add(ahmad);
		softwareEngineering.getListOfStudents().add(ahmad);
		physics.getListOfStudents().add(ahmad);
		ahmad.getSubjects().add(physics);
		ahmad.getCourses().add(mechanics);
		mechanics.getListOfStudents().add(ahmad);

		Student jamal = new Student("Jamal", "Browner", "jamalB","123",0,"000000014");
		jamal.getCourses().add(football);
		football.getListOfStudents().add(jamal);
		jamal.getSubjects().add(sports);
		sports.getListOfStudents().add(jamal);
		calculus.getListOfStudents().add(jamal);
		jamal.getCourses().add(calculus);
		jamal.getCourses().add(lierarAlgebra);
		lierarAlgebra.getListOfStudents().add(jamal);
		jamal.getCourses().add(gym);
		jamal.getCourses().add(OperatingSystem);
		jamal.getCourses().add(softwareEngineering);
		jamal.getSubjects().add(computerScience);
		computerScience.getListOfStudents().add(jamal);
		gym.getListOfStudents().add(jamal);
		OperatingSystem.getListOfStudents().add(jamal);
		softwareEngineering.getListOfStudents().add(jamal);
		physics.getListOfStudents().add(jamal);
		jamal.getSubjects().add(physics);
		jamal.getCourses().add(mechanics);
		mechanics.getListOfStudents().add(jamal);

		Student russel = new Student("Russel", "Orhi", "russelO","123",0,"000000015");
		russel.getCourses().add(football);
		football.getListOfStudents().add(russel);
		russel.getSubjects().add(sports);
		sports.getListOfStudents().add(russel);
		calculus.getListOfStudents().add(russel);
		russel.getCourses().add(calculus);
		russel.getCourses().add(lierarAlgebra);
		lierarAlgebra.getListOfStudents().add(russel);
		russel.getCourses().add(gym);
		russel.getCourses().add(OperatingSystem);
		russel.getCourses().add(softwareEngineering);
		russel.getSubjects().add(computerScience);
		computerScience.getListOfStudents().add(russel);
		gym.getListOfStudents().add(russel);
		OperatingSystem.getListOfStudents().add(russel);
		softwareEngineering.getListOfStudents().add(russel);
		physics.getListOfStudents().add(russel);
		russel.getSubjects().add(physics);
		russel.getCourses().add(mechanics);
		mechanics.getListOfStudents().add(russel);

		Principle principle1 = new Principle("Florentino", "Perez","fp00", "123",2, "000000006");
		principle1.getCourses().add(football);
		principle1.getCourses().add(basketball);
		principle1.getCourses().add(golf);
		principle1.getCourses().add(lierarAlgebra);
		principle1.getCourses().add(calculus);
		principle1.getCourses().add(geometry);
		principle1.getCourses().add(softwareEngineering);
		principle1.getCourses().add(gym);
		principle1.getCourses().add(CLanguage);
		principle1.getCourses().add(OperatingSystem);
		principle1.getCourses().add(hardware);
		principle1.getCourses().add(mechanics);
		principle1.getCourses().add(Astrophysics);
		principle1.getCourses().add(NuclearPhysics);
		principle1.getCourses().add(PrinciplesOfEconomics);
		principle1.getCourses().add(FinancialEconomics);

		principle1.getSubjects().add(computerScience);
		principle1.getSubjects().add(sports);
		principle1.getSubjects().add(math);
		principle1.getSubjects().add(physics);
		principle1.getSubjects().add(economics);

		principle1.getTeachers().add(cr7);
		principle1.getTeachers().add(gBale);
		principle1.getTeachers().add(benzema);
		principle1.getTeachers().add(sam);

		principle1.getStudents().add(lucas);
		principle1.getStudents().add(mariano);
		principle1.getStudents().add(lexx);
		principle1.getStudents().add(alex);
		principle1.getStudents().add(jamal);
		principle1.getStudents().add(russel);
		principle1.getStudents().add(harry);
		principle1.getStudents().add(ahmad);
		principle1.getStudents().add(david);
		principle1.getStudents().add(marco);
		principle1.getStudents().add(maldini);


		football.setPrinciple(principle1);
		basketball.setPrinciple(principle1);
		golf.setPrinciple(principle1);
		gym.setPrinciple(principle1);
		lierarAlgebra.setPrinciple(principle1);
		calculus.setPrinciple(principle1);
		geometry.setPrinciple(principle1);
		CLanguage.setPrinciple(principle1);
		hardware.setPrinciple(principle1);
		softwareEngineering.setPrinciple(principle1);
		OperatingSystem.setPrinciple(principle1);
		mechanics.setPrinciple(principle1);
		Astrophysics.setPrinciple(principle1);
		NuclearPhysics.setPrinciple(principle1);

		sports.setPrinciple(principle1);
		math.setPrinciple(principle1);
		computerScience.setPrinciple(principle1);
		physics.setPrinciple(principle1);
		economics.setPrinciple(principle1);

		cr7.setPrinciple(principle1);
		gBale.setPrinciple(principle1);
		benzema.setPrinciple(principle1);
		sam.setPrinciple(principle1);



		math.setIdd("01");
		sports.setIdd("02");
		computerScience.setIdd("03");
		physics.setIdd("04");
		economics.setIdd("05");


		calculus.setIdd("01");
		lierarAlgebra.setIdd("02");
		geometry.setIdd("03");
		football.setIdd("04");
		basketball.setIdd("05");
		golf.setIdd("06");
		gym.setIdd("07");
		hardware.setIdd("08");
		CLanguage.setIdd("09");
		OperatingSystem.setIdd("10");
		softwareEngineering.setIdd("11");
		mechanics.setIdd("12");
		NuclearPhysics.setIdd("13");
		Astrophysics.setIdd("14");
		FinancialEconomics.setIdd("15");
		PrinciplesOfEconomics.setIdd("16");


		//users
		session.save(cr7);
		session.save(gBale);
		session.save(benzema);
		session.save(lucas);
		session.save(mariano);
		session.save(principle1);
		session.save(david);
		session.save(alex);
		session.save(lexx);
		session.save(marco);
		session.save(maldini);
		session.save(russel);
		session.save(jamal);
		session.save(ahmad);
		session.save(harry);
		session.save(sam);



		//subjects
		session.save(sports);
		session.save(math);
		session.save(computerScience);
		session.save(physics);
		session.save(economics);

		//courses
		session.save(lierarAlgebra);
		session.save(calculus);
		session.save(geometry);
		session.save(football);
		session.save(basketball);
		session.save(golf);
		session.save(gym);
		session.save(CLanguage);
		session.save(softwareEngineering);
		session.save(hardware);
		session.save(OperatingSystem);
		session.save(mechanics);
		session.save(Astrophysics);
		session.save(NuclearPhysics);
		session.save(FinancialEconomics);
		session.save(PrinciplesOfEconomics);
		session.flush();


		session.getTransaction().commit();

	}
	public SimpleServer(int port)
	{
		super(port);
		SessionFactory sessionFactory = getSessionFactory();
		session = sessionFactory.openSession();
		//generate();
	}

	public User getUserByUsername(String username, String password,int type, Message resMessage) {
		try {
			Query query = session.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
			query.setParameter("username", username);

			User user = null;
			try {
				user = (User) query.getSingleResult();
			} catch (NoResultException ex) {
			}

			if (user != null && user.getPassword().equals(password) && !user.isLoggedIn() && user.getWhoAreYou() == type) {
				user.setLoggedIn(true);
				session.update(user);
				session.getTransaction().commit();
				return user;
			}
			else if (user != null && user.getPassword().equals(password) && user.getWhoAreYou() != type) {
				resMessage.setTitle("wrongType");
				return null;
			}
			else if (user != null && user.getPassword().equals(password) && user.isLoggedIn()) {
				resMessage.setTitle("already logged in");
				return null;
			} else {
				System.out.println("Incorrect username or password");
				return null;
			}
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw e;
		}
	}

	public List<Question> getQuestionsForCourse(Course course) {
		try (Session session = getSessionFactory().openSession()) {
			// Retrieve the course from the database to ensure it's managed
			Course managedCourse = session.get(Course.class, course.getId());
			Hibernate.initialize(managedCourse.getListOfQuestions());
			// Get the questions associated with the course
			List<Question> questions = managedCourse.getListOfQuestions();
			return questions;
		}
	}

	public List<Exam> getExamsForCourse(Course course) {
		// Create a Hibernate session
		try (Session session = getSessionFactory().openSession()) {
			// Create a query to fetch the questions for the given course ID
			String hql = "SELECT e FROM Exam e WHERE e.course.name = :courseName AND e.isClone = 'no'";
			Query query = session.createQuery(hql, Exam.class);
			query.setParameter("courseName", course.getName());

			// Execute the query and retrieve the list of questions
			List<Exam> exams = query.getResultList();
			return exams;
		}
	}
	public List<ReadyExam> getReadyExamsForCourse(Object[] objects) {
		// Create a Hibernate session

		try (Session session = getSessionFactory().openSession()) {
			// Create a query to fetch the ReadyExams with the associated Exam eagerly

			Course course = (Course) objects[0];
			Teacher teacher = (Teacher) objects[1];
			String hql = "SELECT re FROM ReadyExam re JOIN FETCH re.exam WHERE re.exam.course.name = :course AND re.isClone = 'no' AND re.username = : username";
			Query query = session.createQuery(hql, ReadyExam.class);
			query.setParameter("course", course.getName());
			query.setParameter("username",teacher.getUsername());

			// Execute the query and retrieve the list of ReadyExams
			List<ReadyExam> exams = query.getResultList();
			return exams;
		}
	}
	public List<ReadyExam> getReadyExamsToApprove(Object[] objects)
	{
		// Create a Hibernate session
		System.out.println("lolllkkkkkkkk");
		try (Session session = getSessionFactory().openSession()) {

			Course course = (Course) objects[0];
			Teacher teacher = (Teacher) objects[1];
			String hql = "SELECT re FROM ReadyExam re JOIN FETCH re.exam WHERE re.exam.course.name = :course AND re.isClone = 'yes' AND re.approved = 'no' AND re.username = : username and re.examType = 'Computerized'";
			Query query = session.createQuery(hql, ReadyExam.class);
			query.setParameter("course", course.getName());
			query.setParameter("username",teacher.getUsername());

			// Execute the query and retrieve the list of ReadyExams
			List<ReadyExam> exams = query.getResultList();
			return exams;
		}
	}
	public List<Question> getQuestionsForCoursePrinciple(Course course) {
		// Create a Hibernate session
		try (Session session = getSessionFactory().openSession()) {
			// Create a query to fetch the questions for the given course ID
			//String hql = "FROM Question q WHERE q.course. = :courseName";
			String hql = "select q from Question  q JOIN q.course c where c.id =:courseId";

			//String hql = "SELECT e FROM Exam e JOIN e.listOfStudents s WHERE s.id = :studentId";

			Query query = session.createQuery(hql, Question.class);
			query.setParameter("courseId", course.getName());

			// Execute the query and retrieve the list of questions
			List<Question> questions = query.getResultList();
			return questions;
		}
	}
	public List<ReadyExam> getExamsForStudent(Student student) {
		try (Session session = getSessionFactory().openSession()) {
			// Create a query to fetch the exams for the given teacher ID
			String hql = "SELECT e FROM ReadyExam e  WHERE e.examinee =: username and e.approved = 'yes' and e.isClone = 'yes'";
			Query query = session.createQuery(hql, ReadyExam.class);
			query.setParameter("username", student.getUsername());

			// Execute the query and retrieve the list of subjects
			List<ReadyExam> exams = query.getResultList();
			return exams;
		}
	}
	public List<ReadyExam> getExamsForCoursePrinciple(Course course) {
		try (Session session = getSessionFactory().openSession()) {
			// Create a query to fetch the exams for the given teacher ID
			String hql = "SELECT e FROM ReadyExam e where e.isClone = 'no' and e.course =: course and e.examType = 'Computerized'";
			Query query = session.createQuery(hql, ReadyExam.class);
			query.setParameter("course", course.getName());

			// Execute the query and retrieve the list of subjects
			List<ReadyExam> exams = query.getResultList();
			return exams;
		}
	}

	public List<ReadyExam> getExamsForTeacher(Teacher teacher) {
		// Create a Hibernate session
		System.out.println("get Exams For Teacher " + teacher.getFirstName() + teacher.getLastName());
		try (Session session = getSessionFactory().openSession()) {
			// Create a query to fetch the questions for the given teacher ID
			//String hql = "FROM Exam e WHERE e.username = :teacherName";
			String hql = "SELECT e FROM ReadyExam e WHERE e.username = :teacherName and e.isClone = 'no' and e.examType = 'Computerized'";

			Query query = session.createQuery(hql, ReadyExam.class);
			query.setParameter("teacherName", teacher.getUsername());

			// Execute the query and retrieve the list of questions
			List<ReadyExam> exams = query.getResultList();
			return exams;
		}
	}

	public List<Subject> getSubjectsForTeacher(Teacher teacher) {
		try (Session session = getSessionFactory().openSession()) {
			// Create a query to fetch the subjects for the given teacher ID
			String hql = "SELECT s FROM Subject s JOIN s.listOfTeachers t WHERE t.id = :teacherId";
			Query query = session.createQuery(hql, Subject.class);
			query.setParameter("teacherId", teacher.getId());

			// Execute the query and retrieve the list of subjects
			List<Subject> subjects = query.getResultList();
			return subjects;
		}
	}
	public List<Subject> getSubjectsForPrinciple(Principle principle) {
		try (Session session = getSessionFactory().openSession()) {
			// Create a query to fetch the subjects for the given principle ID
			//String hql = "SELECT s FROM Subject s JOIN s.principle p WHERE p.id = :principleId";
			String hql = "SELECT s FROM Subject s";
			Query query = session.createQuery(hql, Subject.class);
			//query.setParameter("principleId", principle.getId());

			// Execute the query and retrieve the list of subjects
			List<Subject> subjects = query.getResultList();
			return subjects;
		}
	}
	public List<Teacher> getTeachersForPrinciple(Principle principle) {
		try (Session session = getSessionFactory().openSession()) {
			// Create a query to fetch the subjects for the given principle ID
			String hql = " SELECT t FROM Teacher t";
			Query query = session.createQuery(hql, Teacher.class);

			// Execute the query and retrieve the list of subjects
			List<Teacher> teachers = query.getResultList();
			return teachers;
		}
	}

	public List<Course> getCoursesForPrinciple(Principle principle) {
		try (Session session = getSessionFactory().openSession()) {
			// Create a query to fetch the subjects for the given principle ID
			String hql = "SELECT c FROM Course c";
			Query query = session.createQuery(hql, Course.class);

			// Execute the query and retrieve the list of subjects
			List<Course> courses = query.getResultList();
			return courses;
		}
	}
	public List<Student> getStudentsForPrinciple(Principle principle) {
		try (Session session = getSessionFactory().openSession()) {
			// Create a query to fetch the subjects for the given principle ID
			String hql = "SELECT s FROM Student s";
			Query query = session.createQuery(hql, Student.class);

			// Execute the query and retrieve the list of subjects
			List<Student> students = query.getResultList();
			return students;
		}
	}
//	public List<Course> getCoursesForSubject(Subject subject) {
//		try (Session session = getSessionFactory().openSession()) {
//			// Create a query to fetch the courses for the given subject
//			String hql = "SELECT c FROM Course c WHERE c.subject = :subject";
//			Query query = session.createQuery(hql, Course.class);
//			query.setParameter("subject", subject);
//
//			// Execute the query and retrieve the list of courses
//			List<Course> courses = query.getResultList();
//			return courses;
//		}
//	}
	//	public User getUserByUsername(String username, String password) {
//		//Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//
//		try {
//
//			//session.beginTransaction();
//			System.out.println("lolll");
//			Query query = session.createQuery("FROM User WHERE username = :username", User.class);
//			System.out.println("ddddd");
//			query.setParameter("username", username);
//			System.out.println("3333666");
//			User user = (User)query.getSingleResult();
//			System.out.println("3333");
//			session.getTransaction().commit();
//			System.out.println("44444");
//			if (user != null && user.getPassword().equals(password)) {
//				System.out.println("5555");
//				return user;
//			} else {
//				System.out.println("666666");
//				return null;
//			}
//		}
//		catch (Exception e) {
//			session.getTransaction().rollback();
//			throw e;
//		} finally {
//			session.close();
//	}
//		}

	public List<Student> getStudentsForCourse(Course course)
	{
		try (Session session = getSessionFactory().openSession()) {
			// Create a query to fetch the subjects for the given teacher ID
			String hql = "SELECT s FROM Student s JOIN s.courses c WHERE c.id = :courseId";
			Query query = session.createQuery(hql, Student.class);
			query.setParameter("courseId", course.getId());

			// Execute the query and retrieve the list of subjects
			List<Student> students = query.getResultList();
			return students;
		}
	}
	//	public List<Subject> getSubjectsForTeacher(Teacher teacher) {
//		try (Session session = getSessionFactory().openSession()) {
//			// Create a query to fetch the subjects for the given teacher ID
//			String hql = "SELECT s FROM Subject s JOIN s.listOfTeachers t WHERE t.id = :teacherId";
//			Query query = session.createQuery(hql, Subject.class);
//			query.setParameter("teacherId", teacher.getId());
//
//			// Execute the query and retrieve the list of subjects
//			List<Subject> subjects = query.getResultList();
//			return subjects;
//		}
//	}
	public List<Course> getCoursesForSubject(Subject subject, Teacher teacher)
	{
		try (Session session = getSessionFactory().openSession()) {
			// Create a query to fetch the courses for the given subject
			String hql = "SELECT c FROM Course c JOIN c.listOfTeachers t WHERE t.id = :id and c.subject =: subject";
			Query query = session.createQuery(hql, Course.class);
			query.setParameter("subject", subject);
			query.setParameter("id", teacher.getId());

			// Execute the query and retrieve the list of courses
			List<Course> courses = query.getResultList();
			return courses;
		}
	}




	@Override
	public void handleMessageFromClient(Object msg, ConnectionToClient client) throws IOException {
		System.out.println("incline bench press111");
		Message message = (Message) msg;
		System.out.println(message.getTitle());
		if ("Login".equals(message.getTitle()))
		{
			try {
			if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
				session = getSessionFactory().openSession();
				session.beginTransaction();
			}
			Object[] body = (Object[]) message.getBody();
			String username = (String) body[0];
			String password = (String) body[1];
			int type = (Integer) body[2];
			Message msg1 = new Message("", null);
			User user = getUserByUsername(username, password,type, msg1);
			System.out.println(msg1.getTitle());
			if(msg1.getTitle().equals("wrongType")){
				message.setTitle("wrongType");
			}
			else if (msg1.getTitle().equals("already logged in")) {
				message.setTitle("already logged in");
			}
			System.out.println("2222");
			Message responseMessage = new Message(message.getTitle(), user);
			client.sendToClient(responseMessage);

		}
			catch (Exception e)
		{
			e.printStackTrace();
		}
			finally {
			if (session != null && session.isOpen()) {
				try {
					if (session.getTransaction() != null && session.getTransaction().isActive()) {
						session.getTransaction().commit();
					}
				} catch (Exception ex) {
					// Handle exceptions during commit if necessary.
				} finally {
					session.close();
				}
			}
			}
		}
		else if ("NewClient".equals(message.getTitle()))
		{
			SubscribedClient newSub = new SubscribedClient(client);
			//numberOfClinets++;
			SubscribersList.add(newSub);
		}
		else if("getSubjects".equals(message.getTitle()))
		{
			Teacher teacher = (Teacher)message.getBody();
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				List<Subject> list = getSubjectsForTeacher(teacher);
				for (Subject s : list)
				{
					System.out.println(s.getName() + "+ size: " + list.size());
				}
				Message resMessage = new Message("getSubjects", list);
				client.sendToClient(resMessage);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}

		}
		else if("showQuestions".equals(message.getTitle()) || "showQuestionsForPrinciple".equals(message.getTitle()) || "getAllQuestions".equals(message.getTitle()))
		{
			Course course = (Course) message.getBody();
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				List<Question> list = getQuestionsForCourse(course);
				//List<Question> questionList = new ArrayList<Question>();
				System.out.println("size of list = " + list.size());
//				if ("getAllQuestions".equals(message.getTitle()))
//				{
//					for (Question l : list)
//					{
//						l.setScore(0);
//					}
//				}
				Message resMessage = new Message(message.getTitle(), list);
				client.sendToClient(resMessage);

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if("showQuestionsForPrinciple".equals(message.getTitle()))
		{
			Course course = (Course) message.getBody();
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				List<Question> list = getQuestionsForCourse(course);
				//List<Question> list = getQuestionsForCoursePrinciple(course);
				//List<Question> questionList = new ArrayList<Question>();
				System.out.println("size of list = " + list.size());
//				if ("getAllQuestions".equals(message.getTitle()))
//				{
//					for (Question l : list)
//					{
//						l.setScore(0);
//					}
//				}
				Message resMessage = new Message(message.getTitle(), list);
				client.sendToClient(resMessage);

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if("showStudents".equals(message.getTitle()))
		{
			Course course = (Course) message.getBody();

			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive())
				{
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				List<Student> students = getStudentsForCourse(course);
				Message resMessage = new Message(message.getTitle(), students);
				client.sendToClient(resMessage);

			} catch (Exception e)
			{
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if("sendToStudent".equals(message.getTitle()))
		{
			Object[] objects = (Object[])message.getBody();
			List<String> students = (List<String>)objects[0];
			ReadyExam readyExam = (ReadyExam)objects[1];
			Teacher teacher = (Teacher) objects[2];
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
//				List<Question> questionList = new ArrayList<Question>();
//				for (Question q : readyExam.getExam().getListOfQuestions())
//				{
//					Question question = q.clone();
//					question.setScore(q.getScore());
//					questionList.add(question);
//					session.save(question);
//				}
//				Exam exam = readyExam.getExam().clone();
//				exam.setListOfQuestions(questionList);
//				session.save(exam);
//				ReadyExam readyExam1 = new ReadyExam(readyExam.getExamType(), readyExam.getCourse(), readyExam.getExecutionCode(), exam, "yes");
//				session.save(readyExam1);
				String hql = "SELECT re FROM ReadyExam re WHERE re.id = :id";
				Query query = session.createQuery(hql, ReadyExam.class);
				query.setParameter("id", readyExam.getId());
				ReadyExam re = (ReadyExam) query.getSingleResult();
				List<String> studentlistt = new ArrayList<String>();
				for (String str : students)
				{
					hql = "SELECT s FROM Student s WHERE s.username = :username";
					Query query1 = session.createQuery(hql, Student.class);
					query1.setParameter("username", str);
					Student student = (Student) query1.getSingleResult();

					if (!student.getReadyExams().contains(re))
					{
						student.getReadyExams().add(re);
						session.update(student);
						session.flush();

						Notification notification = new Notification("Exam has been sent to you by "+ teacher.getFirstName() + " " + teacher.getLastName() + ", ExecutionCode: " + readyExam.getExecutionCode(), LocalDateTime.now(), false);
						student.getNotificationList().add(notification);
						session.update(student);
						session.flush();

						re.getListOfStudents().add(student);
						session.update(re);
						session.flush();

						hql = "SELECT n from Notification n where n.user.id =: id and n.isRead = false";
						Query query4 = session.createQuery(hql, Notification.class);
						query4.setParameter("id", student.getId());
						List<Notification> notifications = (List<Notification>) query4.getResultList();
						System.out.println("studentid: " + student.getId());
						Object object = new Object[]{notifications,student.getId()};
						Message message1 = new Message("RefreshStudentBell", object);
						sendToAllClients(message1);

					}
					else
					{
						studentlistt.add(student.getFirstName() + " " + student.getLastName());

					}
				}
				if (studentlistt.size() == students.size())
				{
					re = null;
				}
				Object object = new Object[]{re, studentlistt};
				Message responseMessage = new Message("sendToStudent", object);
				client.sendToClient(responseMessage);


			} catch (HibernateException e)
			{
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}

		}
		else if("changeToStartExam".equals(message.getTitle()))
		{
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive())
				{
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				Object[] objects = (Object[]) message.getBody();
				String executionCode = (String) objects[0];
				String id = (String) objects[1];
				Student student = (Student) objects[2];

				String hql = "SELECT s FROM Student s WHERE s.id = :id";
				Query query4 = session.createQuery(hql, Student.class);
				query4.setParameter("id", student.getId());
				Student student1 = (Student) query4.getSingleResult();
				ReadyExam re = null;
				for (ReadyExam readyExam : student1.getReadyExams())
				{
					if(Objects.equals(readyExam.getExecutionCode(), executionCode))
					{
						re = readyExam;
					}
				}

//				hql = "SELECT e FROM ReadyExam e JOIN e.listOfStudents s WHERE s.username = :username";
//				Query query = session.createQuery(hql, ReadyExam.class);
//				query.setParameter("username", student.getUsername());
//				List<ReadyExam> readyExamList = query.getResultList();
//				for (ReadyExam readyExam : readyExamList)
//				{
//					if (Objects.equals(readyExam.getExecutionCode(), executionCode))
//					{
//						re = readyExam;
//					}
//				}
				if (re != null && Objects.equals(id, student.getIdd()))
				{
					Message resMessage = new Message("changeToStartExam", re);
					client.sendToClient(resMessage);
				}
				else
				{
					String s = "";
					if (re == null && Objects.equals(id, student.getIdd()))// no available exam
					{
						s = "1";
					}
					else if(re != null && !Objects.equals(id, student.getIdd())) // wrong id
					{
						s = "2";
					}
					else
					{
						s = "3";
					}
					Object obj = new Object[]{s};
					Message resMessage = new Message("startExamFailed", obj);
					client.sendToClient(resMessage);
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex)
					{

					} finally {
						session.close();
					}
				}
			}
		}
		else if("StartSolvingComputerizedExam".equals(message.getTitle()) || "StartSolvingManualExam".equals(message.getTitle()) )
		{
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}

				Object[] objects = (Object[]) message.getBody();
				ReadyExam readyExam = (ReadyExam)objects[0];
				Student student = (Student)objects[1];
				List<Question> questionList = new ArrayList<Question>();
				for (Question q : readyExam.getExam().getListOfQuestions()) {
					Question question = q.clone();
					question.setScore(q.getScore());
					questionList.add(question);
					session.save(question);
				}
				Exam exam = readyExam.getExam().clone();
				exam.setListOfQuestions(questionList);
				session.save(exam);
				session.flush();

				ReadyExam readyExam1 = new ReadyExam(readyExam.getExamType(), readyExam.getCourse(), exam.getSubject().getName(),readyExam.getExecutionCode(), exam, "yes", readyExam.getUsername(),readyExam.getCreatorFullName(), readyExam.getExam().getId(), student.getFirstName() + " " + student.getLastName() ,Integer.toString(student.getId()), null);
				readyExam1.setExaminee(student.getUsername());
				readyExam1.setOri_idd(readyExam.getIdd());
				readyExam1.setReadyExamOriginalID(readyExam.getId());
				readyExam1.setAddedTime(readyExam.getAddedTime());
				readyExam1.setExtraTimeApproved(readyExam.getExtraTimeApproved());
				session.save(readyExam1);
				String s = "";
				if (readyExam1.getId()/10 == 0)
				{
					s = "0"+readyExam1.getId();
				}
				else if(readyExam1.getId()/100 == 0)
				{
					s = Integer.toString(readyExam1.getId());
				}
				readyExam1.setIdd(exam.getSubject().getIdd() + " " + exam.getCourse().getIdd() + " " + s );
				session.update(readyExam1);
				session.flush();
				Message responseMessage = new Message(message.getTitle(), readyExam1);

				client.sendToClient(responseMessage);

			}catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
							ex.printStackTrace();
					} finally {
						session.close();
					}
				}
			}
		}
		else if("showExams".equals(message.getTitle()) || "showExamsAE".equals(message.getTitle()) || "showExamsForPrinciple".equals(message.getTitle()))
		{
			Course course = (Course) message.getBody();
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				List<Exam> list = getExamsForCourse(course);
				System.out.println("size of list = " + list.size());
				Message resMessage = new Message(message.getTitle(), list);
				client.sendToClient(resMessage);

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if("editSelectedQuestion".equals(message.getTitle()))
		{
			client.sendToClient(message);
		}
		else if ("getSubjectsForTeacher".equals(message.getTitle()) || "getSubjectsForTeacherEQ".equals(message.getTitle()) || "getSubjectsForTeacherExam".equals(message.getTitle()) || "getSubjectsForTeacherAE".equals(message.getTitle()) || "getSubjectsForTeacherSE".equals(message.getTitle()) || "getSubjectsForTeacherAPP".equals(message.getTitle()) || "viewGradesForTeacherSubjects".equals(message.getTitle()))
		{
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				Teacher teacher = (Teacher) message.getBody();
				List<Subject> subjects = getSubjectsForTeacher(teacher);
				Message resMessage = new Message(message.getTitle(), subjects);
				client.sendToClient(resMessage);

			} catch (Exception e)
			{
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if ("getCoursesForSubjects".equals(message.getTitle()) || "getCoursesForSubjectsEQ".equals(message.getTitle()) || "getCoursesForSubjectsExam".equals(message.getTitle()) || "getCourses".equals(message.getTitle()) || "getCoursesAE".equals(message.getTitle()) || "getCoursesSE".equals(message.getTitle()) || "getCoursesAPP".equals(message.getTitle()) || "viewGradesForTeacherCourses".equals(message.getTitle()))
		{
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				Object[] objects = (Object[]) message.getBody();

				Subject subject = (Subject) objects[0];
				Teacher teacher = (Teacher) objects[1];
				List<Course> courses = getCoursesForSubject(subject,teacher);
				Message resMessage = new Message(message.getTitle(), courses);
				client.sendToClient(resMessage);

			} catch (Exception e)
			{
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if("editSelectedExam".equals(message.getTitle()))
		{
			try
			{
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				System.out.println("editSelectedExam");
				Exam exam = (Exam) message.getBody();
				List<Question> list = exam.getListOfQuestions();
				for (Question l : list)
				{
					System.out.println(l.getQText() + "   score: ");
				}
				Message resMessage = new Message("editSelectedExam", exam);
				client.sendToClient(resMessage);

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if  ("Logout".equals(message.getTitle()) || "LogoutForStudent".equals(message.getTitle())|| "Logout principle".equals(message.getTitle())||"LogoutQB".equals(message.getTitle())
				||"LogoutEB".equals((message.getTitle()))||"LogoutEEB".equals((message.getTitle()))||"LogoutEQB".equals((message.getTitle()))||"LogoutAE".equals((message.getTitle()))
				||"LogoutSE".equals((message.getTitle()))||"LogoutET".equals((message.getTitle()))||"LogoutAP".equals((message.getTitle()))||"LogoutVG".equals((message.getTitle()))||
				"LogoutCE".equals((message.getTitle())) || "LogoutVGS".equals((message.getTitle())) || "LogoutNoti".equals(message.getTitle()))
		{
			User temp = (User) message.getBody();
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				User user = session.find(User.class, temp.getId());
				user.setLoggedIn(false);
				session.update(user);
				session.flush(); // Manually flush the session
				session.getTransaction().commit(); // Commit the transaction
				client.sendToClient(message);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
			System.out.println("logout");
		}
		else if ("createQuestion".equals(message.getTitle()) || "saveEditedQuestion".equals(message.getTitle())) {
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}

				List<String> list = (List<String>) message.getBody();
				Question question = null;
				if (list != null)
				{
					String hql1 = "SELECT s FROM Subject s WHERE s.name = :subject";
					Query query = session.createQuery(hql1, Subject.class);
					query.setParameter("subject", list.get(5));
					Subject subject = (Subject)query.getSingleResult();
					if (subject!= null)
					{
						System.out.println(subject.getId());
					}
					else
					{
						System.out.println("nukllllllll");
					}
					String jpql = "SELECT t FROM Teacher t WHERE t.username = :username";
					query = session.createQuery(jpql, Teacher.class);
					query.setParameter("username", list.get(6));
					Teacher teacher = (Teacher)query.getSingleResult();
					if (teacher!= null)
					{
						System.out.println(teacher.getId());
					}
					else
					{
						System.out.println("nukllllllll111");
					}
					//question = new Question(list.get(0),list.get(1), list.get(2), list.get(3),list.get(4), subject,teacher, list.get(7));
					List<Course> courses = new ArrayList<Course>();
					for (int i = 8 ; i < list.size(); i++)
					{
						String hql = "SELECT c FROM Course c WHERE c.name = :course";
						Query query1 = session.createQuery(hql, Course.class);
						query1.setParameter("course", list.get(i));
						Course course = (Course) query1.getSingleResult();
						if (course!= null)
						{
							System.out.println(course.getId());
						}
						else
						{
							System.out.println("nukllllllll222222");
						}
						courses.add(course);

						//course.getListOfQuestions().add(question);
					}
					System.out.println("here before creating quesuion");
					question = new Question(list.get(0),list.get(1), list.get(2), list.get(3),list.get(4), subject,teacher, list.get(7), courses, "no");
					System.out.println("here after creating quesuion");
					session.save(question);
					String s = "";
					if (question.getId()/10 == 0)
					{
						s = "00"+question.getId();
					}
					else if(question.getId()/100 == 0)
					{
						s = "0" +Integer.toString(question.getId());
					}
					else
					{
						s = Integer.toString(question.getId());
					}
					question.setIdd(question.getSubject().getIdd()  + " " + s );
					session.update(question);
					session.flush();
					System.out.println("lolllll");
//					String hql = "SELECT c FROM Course c WHERE c.name = :course";
//					Query query1 = session.createQuery(hql, Subject.class);
//					query1.setParameter("course", list.get(4));
					session.getTransaction().commit();
				}
				Message responseMessage = new Message(message.getTitle(), question);
				client.sendToClient(responseMessage);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if ("createExam".equals(message.getTitle())) {
			try {
				if (session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				System.out.println("we are here");
				List<Question> list = getQuestionsForCourse((Course) message.getBody());
				Message responseMessage = new Message(message.getTitle(), list);
				System.out.println("gggggggggg");
				client.sendToClient(responseMessage);
				System.out.println("gggggggggg");
			} catch (Exception e) {
				if (session.getTransaction() != null && session.getTransaction().isActive()) {
					session.getTransaction().rollback();
				}
				e.printStackTrace();
			}  finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if ("saveExam".equals(message.getTitle()) || "saveEditedExam".equals(message.getTitle()))
		{
			System.out.println("incline bench press2");
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				Object[] objects = (Object[])message.getBody();
				ExamHelper examHelper = (ExamHelper)objects[1];
				Teacher teacher = (Teacher) objects[0];


				if (examHelper.getQuestionHashMap() != null) {
					System.out.println(examHelper.getQuestionHashMap().size());
					System.out.println("raghad");
				}
				if (examHelper.getExamPeriod() != null) {
					System.out.println("raghad");
				}
				if (examHelper.getExamPeriod() == null ||
						examHelper.getQuestionHashMap() == null)
				{
					Message newMessage = new Message(message.getTitle(), null);
					client.sendToClient(newMessage);
					System.out.println("null exam");
				}
				else
				{
					String examPeriod = examHelper.getExamPeriod();
					System.out.println("incline bench press4");
					System.out.println(examHelper.getCourse());
					String hql1 = "SELECT s FROM Subject s WHERE s.name = :subject";
					Query query = session.createQuery(hql1, Subject.class);
					query.setParameter("subject", examHelper.getSubject());
					Subject subject = (Subject)query.getSingleResult();
					String hql = "SELECT c FROM Course c WHERE c.name = :course";
					Query query1 = session.createQuery(hql, Course.class);
					query1.setParameter("course", examHelper.getCourse());
					Course course = (Course) query1.getSingleResult();
					Exam exam = new Exam(examHelper.getUsername(), examHelper.getTeacherFullName(),examPeriod, subject, course, examHelper.teacherComments, examHelper.allComments, "no");
					System.out.println("incline bench press5");
					List<Question> list1 = new ArrayList<Question>(examHelper.getQuestionHashMap().keySet());
					List<Question> list2 = new ArrayList<Question>();
					System.out.println("list1.size" +list1.size());
					int j = 0;
					for (Question q : list1)
					{
						list2.add(q.clone());
						list2.get(j).setScore(q.getScore());
						j++;
					}
					System.out.println("list2.size" +list2.size());
					List<Question> newQuestions = new ArrayList<>();
					//exam.setListOfQuestions(list);
//					for (Question question : list1) {
//						if (examHelper.getQuestionHashMap().containsKey(question))
//						{
//							int score = examHelper.getQuestionHashMap().get(question);
//							question.setScore(score);
//						}
//						// Check if the question already exists in the database
//						if (question.getId() == 0)
//						{
//							// New question, save it to generate an ID
//							//question.getExams().add(exam);
//							session.save(question);
//							newQuestions.add(question);
//						}
//
//						// Set the score for the question
//
//						// Associate the question with the exam
//					}
//					for (int i = 0; i < list1.size(); i++)
//					{
//						if (examHelper.getQuestionHashMap().containsKey(list1.get(i)))
//						{
//							int score = examHelper.getQuestionHashMap().get(list1.get(i));
//							list2.get(i).setScore(score);
//							session.save(list2.get(i));
//						}
//					}
					exam.getListOfQuestions().addAll(list2);
					double sum = 0;
					boolean flag = true;
					for (Question q : exam.getListOfQuestions())
					{
						if (q.getScore() <= 0)
						{
							flag = false;
						}
						sum += q.getScore();
					}
					if (sum > 100 || sum < 100)
					{
						flag = false;
					}
					System.out.println("incline bench press6");
					if (flag)
					{
						hql = "SELECT t FROM Teacher t WHERE t.id = :id";
						Query query5 = session.createQuery(hql, Teacher.class);
						query5.setParameter("id", teacher.getId());
						Teacher teacher1 = (Teacher) query5.getSingleResult();

						session.save(exam);
						session.flush();
						String s = "";
						if (exam.getId()/10 == 0)
						{
							s = "0"+exam.getId();
						}
						else if(exam.getId()/100 == 0)
						{
							s = Integer.toString(exam.getId());
						}
						exam.setIdd(subject.getIdd() + " " + course.getIdd() + " "+ s );
						session.update(exam);
						session.flush();
						exam.getListOfTeachers().add(teacher1);
						session.update(exam);
						session.flush();

						teacher1.getListOfExams().add(exam);
						session.update(teacher1);
						session.flush();
					}
					System.out.println("incline bench press7");
					session.getTransaction().commit();
					System.out.println("saved");
					Message newMessage = new Message(message.getTitle(), exam);
					client.sendToClient(newMessage);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if (message.getTitle().equals("aquireExam"))
		{
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive())
				{
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				Object[] objects = (Object[])message.getBody();

				List<Integer> list = (List<Integer>)objects[0];
				String hql = "SELECT e FROM Exam e WHERE e.id = :id";
				Query query1 = session.createQuery(hql, Exam.class);
				query1.setParameter("id", list.get(0));
				Exam exam = (Exam) query1.getSingleResult();
				System.out.println(exam.getUsername());
				String examType = "";
				if (list.get(1) == -1)
				{
					examType = "Manual";
				}
				else
				{
					examType = "Computerized";
				}
				String executionCode = (String) objects[1];
				Teacher teacher = (Teacher)objects[2];
				ReadyExam readyExam = new ReadyExam(examType, exam.getCourse().getName(),exam.getSubject().getName(),executionCode,exam, "no", teacher.getUsername(), teacher.getFirstName() + " " + teacher.getLastName(),exam.getId(), "", "", null);
				if (readyExam.getExam() == null)
				{
					System.out.println("hello bitch");
				}
				String hql1 = "SELECT re from ReadyExam re where re.executionCode =: executionCode";
				Query query2 = session.createQuery(hql1, ReadyExam.class);
				query2.setParameter("executionCode", executionCode);
				List<ReadyExam> list1 = (List<ReadyExam>)query2.getResultList();
				if (list1.isEmpty())
				{
					session.save(readyExam);
					String s = "";
					if (readyExam.getId()/10 == 0)
					{
						s = "0"+readyExam.getId();
					}
					else if(readyExam.getId()/100 == 0)
					{
						s = Integer.toString(readyExam.getId());
					}
					readyExam.setIdd(exam.getSubject().getIdd() + " " + exam.getCourse().getIdd() + " " + s );
					session.update(readyExam);
					session.flush();

					readyExam.setReadyExamOriginalID(readyExam.getId());
					session.update(readyExam);
					session.flush();

					hql = "SELECT e FROM Exam  e where e.id =: id";
					Query query = session.createQuery(hql, Exam.class);
					query.setParameter("id", readyExam.getOriginalId());
					Exam exam1 = (Exam) query.getSingleResult();
					hql = "SELECT t FROM Teacher t WHERE t.id = :id";
					Query query5 = session.createQuery(hql, Teacher.class);
					query5.setParameter("id", teacher.getId());
					Teacher teacher1 = (Teacher) query5.getSingleResult();

					session.save(exam1);
					session.flush();

					exam1.getListOfTeachers().add(teacher1);
					session.update(exam);
					session.flush();

					teacher1.getListOfExams().add(exam1);
					session.update(teacher1);
					session.flush();
				}
				else
				{
					readyExam = null;
				}

				Message responseMessage  = new Message(message.getTitle(), readyExam);
				client.sendToClient(responseMessage);

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if (message.getTitle().equals("showReadyExams") || message.getTitle().equals("showReadyExamsAPP"))
		{
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive())
				{
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				Object[] objects = (Object[])message.getBody();
				List<ReadyExam> list = null;

				if (message.getTitle().equals("showReadyExams"))
				{
					list = getReadyExamsForCourse(objects);
				}
				else
				{
					System.out.println("lolllllll");
					list = getReadyExamsToApprove(objects);
				}
				Message resMessage = new Message(message.getTitle(), list);
				client.sendToClient(resMessage);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if(message.getTitle().equals("InTime") || message.getTitle().equals("NotInTime"))
		{
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive())
				{
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				Object[] objects = (Object[]) message.getBody();
				Map<Question,String> map = (Map<Question, String>) objects[0];
				int examId = Integer.parseInt((String) objects[1]);
				Student student = (Student) objects[2];
				String hql = "SELECT e FROM ReadyExam e WHERE e.id = :id";
				Query query1 = session.createQuery(hql, ReadyExam.class);
				query1.setParameter("id", examId);
				ReadyExam readyExam = (ReadyExam) query1.getSingleResult();
				readyExam.setOnGoing("no");
				System.out.println("secrets!");
				int sum = 0;
				for (Question q : map.keySet())
				{

					hql = "SELECT q FROM Question q WHERE q.id = :id";
					Query query2 = session.createQuery(hql, Question.class);
					query2.setParameter("id", q.getId());
					Question question = (Question) query2.getSingleResult();
					System.out.println(q.getQText() + ", answer: " + map.get(q));

					// updating student answer
					question.setStudentAnswer(map.get(q));
					if (question.getCorrectAnswer().equals(question.getStudentAnswer()))
					{
						sum += question.getScore();
						question.setAnsweredCorrectly("yes");
					}
					System.out.println("secrets!!");
					session.update(question);
					session.flush();
					System.out.println("secrets!!!");
				}
				readyExam.setGrade(sum);
				System.out.println("secrets!!!!");
				session.update(readyExam.getExam());
				session.flush();
				System.out.println("secrets!!!!!!");
				session.update(readyExam);
				session.flush();
				System.out.println("secrets!!!!!!!!");

				hql = "SELECT e FROM ReadyExam e WHERE e.id = :id";
				Query query3 = session.createQuery(hql, ReadyExam.class);
				query3.setParameter("id", readyExam.getReadyExamOriginalID());
				ReadyExam readyExam2 = (ReadyExam) query3.getSingleResult();

				hql = "SELECT s FROM Student s WHERE s.id = :id";
				Query query4 = session.createQuery(hql, Student.class);
				query4.setParameter("id", student.getId());
				Student student1 = (Student) query4.getSingleResult();

				student1.getReadyExams().remove(readyExam2);
				session.update(student1);
				session.flush();

				readyExam2.getListOfStudents().remove(student1);
				session.update(readyExam2);
				session.flush();

				Notification notification = new Notification("New exam has been submitted in course " + readyExam.getCourse() + ", go and approve it.", LocalDateTime.now(), false);
				hql = "SELECT t FROM Teacher t WHERE t.username = :username";
				Query query5 = session.createQuery(hql, Teacher.class);
				query5.setParameter("username", readyExam.getUsername());
				Teacher teacher = (Teacher) query5.getSingleResult();
				teacher.getNotificationList().add(notification);
				session.update(teacher);
				session.flush();

				hql = "SELECT n from Notification n where n.user.id =: id and n.isRead = false";
				Query query7 = session.createQuery(hql, Notification.class);
				query7.setParameter("id", teacher.getId());
				List<Notification> notifications = (List<Notification>) query7.getResultList();
				System.out.println("studentid: " + teacher.getId());
				Object object = new Object[]{notifications,teacher.getId()};
				Message message1 = new Message("RefreshTeacherBell", object);
				sendToAllClients(message1);
				client.sendToClient(message);

			} catch (Exception e)
			{
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if("SendToPreview".equals(message.getTitle()))
		{

			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				ReadyExam readyExam = (ReadyExam) message.getBody();
				String hql = "SELECT s FROM Student s WHERE s.username = :username";
				Query query1 = session.createQuery(hql, Student.class);
				query1.setParameter("username", readyExam.getExaminee());
				Student student = (Student) query1.getSingleResult();
				Object object = new Object[]{readyExam, student};
				Message message1 = new Message("SendToPreview", object);
				client.sendToClient(message1);

			} catch (Exception e)
			{
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if("approveExam".equals(message.getTitle()))
		{
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				Object[] objects = (Object[]) message.getBody();
				int id = (Integer) objects[0];
				int grade = Integer.parseInt((String)objects[1]);
				String teacherComments = (String)objects[2];
				String studentComments = (String) objects[3];
				Integer studentId = (Integer)objects[4];

				String hql = "SELECT re FROM ReadyExam re JOIN FETCH re.exam where re.id = :id";
				Query query1 = session.createQuery(hql, ReadyExam.class);
				query1.setParameter("id", id);
				ReadyExam readyExam = (ReadyExam) query1.getSingleResult();
				readyExam.setGrade(grade);
				session.flush();

				readyExam.getExam().setTeacherComments(teacherComments);
				session.flush();

				readyExam.getExam().setStudentComments(studentComments);
				session.update(readyExam.getExam());
				session.flush();

				readyExam.setApproved("yes");
				session.update(readyExam);
				session.flush();

				readyExam.getExam().getListOfReadyExams().add(readyExam);
				session.update(readyExam.getExam());
				session.flush();

				session.update(readyExam);
				session.flush();

				hql = "SELECT re FROM ReadyExam re JOIN FETCH re.exam where re.id = :id";
				Query query5 = session.createQuery(hql, ReadyExam.class);
				query5.setParameter("id", readyExam.getReadyExamOriginalID());
				ReadyExam readyExam1 = (ReadyExam) query5.getSingleResult();
				//calc average
				double avr = readyExam1.getAvg() * readyExam1.getSize();
				int size = readyExam1.getSize()+1;
				avr = (avr + grade) / (size);

				readyExam1.setAvg(avr);
				session.update(readyExam1);
				session.flush();

				readyExam1.getListOfGrades().add(grade);
				session.update(readyExam1);
				session.flush();

				readyExam1.setSize(size);
				session.update(readyExam1);
				session.flush();

				hql = "SELECT re FROM ReadyExam re JOIN FETCH re.exam where re.readyExamOriginalID = :id and re.approved = 'yes' and re.isClone = 'yes' AND re.examType = 'Computerized'";
				Query query6 = session.createQuery(hql, ReadyExam.class);
				query6.setParameter("id", readyExam1.getId());
				List<ReadyExam> readyExamList = (List<ReadyExam>) query6.getResultList();
				List<Double> list = new ArrayList<Double>();
				for (ReadyExam re : readyExamList)
				{
					double temp = re.getGrade();
					list.add(temp);
				}
				int sizE = readyExamList.size();
				int middle = sizE / 2;
				double median = 0;
				if (sizE % 2 == 0 )
				{
					// Step 3: If even number of elements, calculate the average of the two middle elements
					if (sizE > 0)
					{
						double median1 = list.get(middle - 1);
						double median2 = list.get(middle);
						median =  (median1 + median2) / 2;
					}
					else
					{
						median = 0;
					}
				}
				else
				{
					// Step 4: If odd number of elements, return the middle element
					median =  list.get(middle);
				}
				readyExam1.setMedian(median);
				session.update(readyExam1);
				session.flush();

				hql = "SELECT s FROM Student s where s.username =: username";
				Query query3 = session.createQuery(hql, Student.class);
				query3.setParameter("username", readyExam.getExaminee());
				Student student = (Student) query3.getSingleResult();
				readyExam.getExam().getListOfStudents().add(student);
				session.update(readyExam.getExam());
				session.flush();

				Notification notification = new Notification("New grade has been published", LocalDateTime.now(), false);
				session.save(notification);
				session.flush();

				student.getNotificationList().add(notification);
				session.update(student);
				session.flush();

				hql = "SELECT n from Notification n where n.user.id =: id and n.isRead = false";
				Query query7 = session.createQuery(hql, Notification.class);
				query7.setParameter("id", student.getId());
				List<Notification> notifications = (List<Notification>) query7.getResultList();
				System.out.println("studentid: " + student.getId());
				Object object = new Object[]{notifications,student.getId()};
				Message message1 = new Message("RefreshStudentBell", object);
				sendToAllClients(message1);

//				readyExam.getListOfStudents().add(student);
//				session.update(readyExam);
//				session.flush();
//
//				student.getReadyExams().add(readyExam);
//				session.update(student);
//				session.flush();

				hql = "SELECT e FROM Exam e where e.id =: id";
				Query query4 = session.createQuery(hql, Exam.class);
				query4.setParameter("id", readyExam.getOriginalId());
				Exam exam = (Exam) query4.getSingleResult();
				exam.getListOfStudents().add(student);
				session.update(exam);
				session.flush();
				client.sendToClient(message);
			}catch (Exception e)
			{
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if("viewGradesForStudent".equals(message.getTitle()))
		{

			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				Student student = (Student)message.getBody();
				String username = student.getUsername();

				String hql = "SELECT re FROM ReadyExam re JOIN FETCH re.exam where re.examinee = :username and re.approved = 'yes'";
				Query query1 = session.createQuery(hql, ReadyExam.class);
				query1.setParameter("username", username);
				List<ReadyExam> list = (List<ReadyExam>) query1.getResultList();
				Message resMessage = new Message(message.getTitle(), list);
				client.sendToClient(resMessage);

			}catch (Exception e)
			{
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if("showExamsForTeacherCourses".equals(message.getTitle()))
		{
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				Object[] objects = (Object[]) message.getBody();
				Course course = (Course) objects[1];
				Teacher teacher = (Teacher) objects[0];
				String username = teacher.getUsername();
				int id = 0;
				if (course != null)
				{
					id = course.getId();
				}
				String hql = "SELECT e FROM ReadyExam e where e.username = :username and e.course =: id and e.isClone = 'no' and e.examType = 'Computerized'";
				Query query1 = session.createQuery(hql, ReadyExam.class);
				query1.setParameter("username", username);
				query1.setParameter("id", course.getName());
				List<ReadyExam> list = (List<ReadyExam>) query1.getResultList();

				Message resMessage = new Message(message.getTitle(), list);
				client.sendToClient(resMessage);

			}catch (Exception e)
			{
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if("ShowExamsForTeacherSubjects".equals(message.getTitle()))
		{
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				Object[] objects = (Object[]) message.getBody();
				Subject subject = (Subject) objects[1];
				Teacher teacher = (Teacher) objects[0];
				String username = teacher.getUsername();
				int id = 0;
				if (subject != null)
				{
					id = subject.getId();
				}
				String hql = "SELECT e FROM ReadyExam e where e.username = :username and e.subject =: id and e.isClone = 'no' and e.examType = 'Computerized'";
				Query query1 = session.createQuery(hql, ReadyExam.class);
				query1.setParameter("username", username);
				query1.setParameter("id", subject.getName());
				List<ReadyExam> list = (List<ReadyExam>) query1.getResultList();

				Message resMessage = new Message(message.getTitle(), list);
				client.sendToClient(resMessage);

			}catch (Exception e)
			{
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if("ShowReadyExamsForViewGradesForTeacher".equals(message.getTitle()))
		{
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				ReadyExam exam = (ReadyExam) message.getBody();
				int id = exam.getId();
				String hql = "SELECT re FROM ReadyExam re where re.readyExamOriginalID =: id and re.approved = 'yes' and re.isClone = 'yes'";
				Query query1 = session.createQuery(hql, ReadyExam.class);
				query1.setParameter("id", id);
				List<ReadyExam> list = (List<ReadyExam>) query1.getResultList();

				Message resMessage = new Message(message.getTitle(), list);
				client.sendToClient(resMessage);

			}catch (Exception e)
			{
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if("GetExamsForCoursePrinciple".equals(message.getTitle()))
		{
			Course course = (Course) message.getBody();
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				List<ReadyExam> list = getExamsForCoursePrinciple(course);
				System.out.println("size of list = " + list.size());
				Message resMessage = new Message("GetExamsForCoursePrinciple", list);
				client.sendToClient(resMessage);

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if("GetExamsForStudentPrinciple".equals(message.getTitle()))
		{
			Student student = (Student) message.getBody();
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				List<ReadyExam> list = getExamsForStudent(student);
				System.out.println("size of list = " + list.size());
				Message resMessage = new Message("getExamsForStudentPrinciple", list);
				client.sendToClient(resMessage);

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if ("getSubjectsForPrincipleExams".equals(message.getTitle()) || "getSubjectsForPrinciple".equals(message.getTitle()))
		{
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				Principle principle = (Principle) message.getBody();
				List<Subject> subjects = getSubjectsForPrinciple(principle);
				Message resMessage = new Message(message.getTitle(), subjects);
				client.sendToClient(resMessage);

			} catch (Exception e)
			{
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if ("getTeachersForPrinciple".equals(message.getTitle()))
		{
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				Principle principle = (Principle) message.getBody();
				List<Teacher> teachers = getTeachersForPrinciple(principle);
				Message resMessage = new Message(message.getTitle(), teachers);
				client.sendToClient(resMessage);

			} catch (Exception e)
			{
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if ("getCoursesForPrinciple".equals(message.getTitle()))
		{
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				Principle principle = (Principle) message.getBody();
				List<Course> courses = getCoursesForPrinciple(principle);
				Message resMessage = new Message(message.getTitle(), courses);
				client.sendToClient(resMessage);

			} catch (Exception e)
			{
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if ("getStudentsForPrinciple".equals(message.getTitle()) || "getStudentsGradesForPrinciple".equals(message.getTitle()))
		{
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				Principle principle = (Principle) message.getBody();
				List<Student> students = getStudentsForPrinciple(principle);
				Message resMessage = new Message(message.getTitle(), students);
				client.sendToClient(resMessage);

			} catch (Exception e)
			{
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}

		else if ( "getCoursesForSubjects".equals(message.getTitle()) || "getCoursesForSubjectsEQ".equals(message.getTitle()) || "getCoursesForSubjectsExam".equals(message.getTitle()) || "getCourses".equals(message.getTitle()))
		{
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				Object[] objects = (Object[]) message.getBody();

				Subject subject = (Subject) objects[0];
				Teacher teacher = (Teacher) objects[1];
				List<Course> courses = getCoursesForSubject(subject, teacher);
				Message resMessage = new Message(message.getTitle(), courses);
				client.sendToClient(resMessage);

			} catch (Exception e)
			{
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if("getCoursesForSubjectsPrinciple".equals(message.getTitle()) || "GetCoursesForSubjectsPrincipleExams".equals(message.getTitle()))
		{
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				Object[] objects = (Object[]) message.getBody();

				Subject subject = (Subject) objects[0];
				String hql = "Select c from Course c where c.subject.name =: subject";
				Query query1 = session.createQuery(hql, Course.class);
				query1.setParameter("subject", subject.getName());
				List<Course> list = (List<Course>) query1.getResultList();

				Message resMessage = new Message(message.getTitle(),list);
				client.sendToClient(resMessage);

			} catch (Exception e)
			{
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if("getExamQuestions".equals(message.getTitle()))
		{
			try
			{
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				System.out.println("getExamQuestions");
				Exam exam = (Exam) message.getBody();
				List<Question> list = exam.getListOfQuestions();
				for (Question l : list)
				{
					System.out.println(l.getQText() + "   score: ");
				}
				Message resMessage = new Message("getExamQuestions", list);
				client.sendToClient(resMessage);

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if("GetExamsForTeacherPrinciple".equals(message.getTitle()))
		{
			Teacher teacher = (Teacher) message.getBody();
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				List<ReadyExam> list = getExamsForTeacher(teacher);
				System.out.println("size of list = " + list.size());
				Message resMessage = new Message(message.getTitle(), list);
				client.sendToClient(resMessage);

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if("SetOnGoingToTrue".equals(message.getTitle()))
		{
			Object[] objects = (Object[]) message.getBody();

			ReadyExam readyExam = (ReadyExam)objects[0];
			Student student = (Student) objects[1];

			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				String hql4 = "SELECT s FROM Student s where s.id =: id";
				Query query15 = session.createQuery(hql4, Student.class);
				query15.setParameter("id", student.getId());
				Student student1 = (Student) query15.getSingleResult();
//				if (!student1.getExams().contains(readyExam.getExam()))
//				{
//					student1.getExams().add(readyExam.getExam());
//					session.update(student1);
//					session.flush();
//				}
				int id = readyExam.getReadyExamOriginalID();
				readyExam.setOnGoing("yes");
				//System.out.println("size of list = " + list.size());
				//Message resMessage = new Message(message.getTitle(), list);
				session.update(readyExam);
				session.flush();

				String hql = "SELECT re FROM ReadyExam re where re.id =: id";
				Query query1 = session.createQuery(hql, ReadyExam.class);
				query1.setParameter("id", id);
				ReadyExam readyExam1 = (ReadyExam) query1.getSingleResult();
				System.out.println("readyExam1.getId" + readyExam1.getId());
				System.out.println(readyExam1.getNumOfOnGoingExams());
				readyExam1.setNumOfOnGoingExams(readyExam1.getNumOfOnGoingExams()+1);
				System.out.println(readyExam1.getNumOfOnGoingExams());
				session.update(readyExam1);
				session.flush();

				readyExam1.setOnGoing("yes");
				session.update(readyExam1);
				session.flush();

				hql = "SELECT re FROM ReadyExam re where re.username =: username AND re.OnGoing = 'yes' and re.isClone = 'no'";
				Query query3 = session.createQuery(hql, ReadyExam.class);
				query3.setParameter("username", readyExam.getUsername());
				List<ReadyExam> list = (List<ReadyExam>) query3.getResultList();
				Message responseMessage = new Message("refreshTable",list);
				System.out.println(responseMessage.getTitle());
				sendToAllClients(responseMessage);
				//client.sendToClient(responseMessage);

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if("SetOnGoingToFalse".equals(message.getTitle()))
		{
			String s = (String) message.getBody();
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				int id = Integer.parseInt(s);
				String hql = "SELECT re FROM ReadyExam re where re.id =: id";
				Query query1 = session.createQuery(hql, ReadyExam.class);
				query1.setParameter("id", id);
				ReadyExam readyExam = (ReadyExam) query1.getSingleResult();
				int id_ori = readyExam.getReadyExamOriginalID();
				readyExam.setOnGoing("no");
				//System.out.println("size of list = " + list.size());
				//Message resMessage = new Message(message.getTitle(), list);
				session.update(readyExam);
				session.flush();

				hql = "SELECT re FROM ReadyExam re where re.id =: id";
				Query query2 = session.createQuery(hql, ReadyExam.class);
				query2.setParameter("id", id_ori);
				ReadyExam readyExam1 = (ReadyExam) query2.getSingleResult();
				readyExam1.setNumOfOnGoingExams(readyExam1.getNumOfOnGoingExams()-1);
				session.update(readyExam1);
				session.flush();
				if (readyExam1.getNumOfOnGoingExams() == 0)
				{
					readyExam1.setOnGoing("no");
					session.update(readyExam1);
					session.flush();

				}

				hql = "SELECT re FROM ReadyExam re where re.username =: username AND re.OnGoing = 'yes' and re.isClone = 'no'";
				Query query3 = session.createQuery(hql, ReadyExam.class);
				query3.setParameter("username", readyExam.getUsername());
				List<ReadyExam> list = (List<ReadyExam>) query3.getResultList();
				Message responseMessage = new Message("refreshTable",list);
				System.out.println(responseMessage.getTitle());
				sendToAllClients(responseMessage);


			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if("GetOnGoingExamsForExtraTime".equals(message.getTitle()))
		{
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				String username = (String) message.getBody();
				String hql = "SELECT re FROM ReadyExam re where re.username =: username AND re.OnGoing = 'yes' and re.isClone = 'no'";
				Query query1 = session.createQuery(hql, ReadyExam.class);
				query1.setParameter("username", username);
				List<ReadyExam> list = (List<ReadyExam>) query1.getResultList();
				for (ReadyExam readyExam : list)
				{
					hql = "SELECT re FROM ReadyExam re where re.username =: username AND re.OnGoing = 'yes' and re.isClone = 'yes' and re.readyExamOriginalID =: id";
					Query query2 = session.createQuery(hql, ReadyExam.class);
					query2.setParameter("username", username);
					query2.setParameter("id", readyExam.getReadyExamOriginalID());
					List<ReadyExam> list_clones = (List<ReadyExam>) query2.getResultList();
					readyExam.setNumOfOnGoingExams(list_clones.size());
					session.update(readyExam);
					session.flush();
				}
				System.out.println("ready  " +list.size());
				System.out.println("1111ahmaddd");
				Object object = new Object[]{list};
				Message responseMessage = new Message(message.getTitle(), list);
				client.sendToClient(responseMessage);

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if("AskPrincipleForExtraTime".equals(message.getTitle()))
		{
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				Object[] objects = (Object[]) message.getBody();
				String timeAmount = (String) objects[0];
				String explanation = (String) objects[1];
				ReadyExam readyExam = (ReadyExam) objects[2];

				String hql = "SELECT re FROM ReadyExam re where re.id = :id and re.OnGoing = 'yes' and re.isClone = 'no'";
				Query query1 = session.createQuery(hql, ReadyExam.class);
				query1.setParameter("id", readyExam.getReadyExamOriginalID());
				ReadyExam readyExam1 = (ReadyExam) query1.getSingleResult();

				ExtraTime extraTime = new ExtraTime(readyExam1, timeAmount, explanation);
				session.save(extraTime);
				session.flush();
				Message responseMessage = new Message("AskPrincipleForExtraTime", message.getBody());
				client.sendToClient(responseMessage);

				hql = "SELECT et FROM ExtraTime et where et.extraTimeApproved = ''";
				Query query2 = session.createQuery(hql, ExtraTime.class);
				List<ExtraTime> list2 = (List<ExtraTime>) query2.getResultList();

				Message responseMessage1 = new Message("refreshTablePrinciple",list2);
				System.out.println(responseMessage.getTitle());
				sendToAllClients(responseMessage1);

				Notification notification = new Notification("New extra time request has been arrived for exam: " + extraTime.getReadyExam().getIdd(), LocalDateTime.now(), false);
				hql = "SELECT p FROM Principle p WHERE p.username = :username";
				Query query5 = session.createQuery(hql, Principle.class);
				query5.setParameter("username", "fp00");
				Principle principle = (Principle) query5.getSingleResult();
				principle.getNotificationList().add(notification);
				session.update(principle);
				session.flush();

				hql = "SELECT n from Notification n where n.user.id =: id and n.isRead = false";
				Query query7 = session.createQuery(hql, Notification.class);
				query7.setParameter("id", principle.getId());
				List<Notification> notifications = (List<Notification>) query7.getResultList();
				System.out.println("studentid: " + principle.getId());
				Object object = new Object[]{notifications,principle.getId()};
				Message message1 = new Message("RefreshPrincipleBell", object);
				sendToAllClients(message1);

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if("ExtraTimePrinciple".equals(message.getTitle()))
		{

			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				String hql = "SELECT et FROM ExtraTime et where et.extraTimeApproved = ''";
				Query query1 = session.createQuery(hql, ExtraTime.class);
				List<ExtraTime> list = (List<ExtraTime>) query1.getResultList();
				Message responseMessage = new Message("ExtraTimePrinciple", list);
				client.sendToClient(responseMessage);

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if ("ApproveExtraTimeRequest".equals(message.getTitle()))
		{
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				ExtraTime extraTime = (ExtraTime) message.getBody();
				extraTime.setExtraTimeApproved("Approved");
				session.update(extraTime);
				session.flush();

				extraTime.getReadyExam().setExtraTimeApproved("Approved");
				session.update(extraTime.getReadyExam());
				session.flush();

				extraTime.getReadyExam().setAddedTime(extraTime.getReadyExam().getAddedTime() + Integer.parseInt(extraTime.getTimeAmount()));
				session.update(extraTime.getReadyExam());
				session.flush();

				String hql = "SELECT re FROM ReadyExam re WHERE re.readyExamOriginalID =: id and re.isClone = 'yes'";
				Query query = session.createQuery(hql, ReadyExam.class);
				query.setParameter("id", extraTime.getReadyExam().getId());
				List<ReadyExam> readyExamList = (List<ReadyExam>) query.getResultList();
				for (ReadyExam readyExam : readyExamList)
				{
					readyExam.setAddedTime(extraTime.getReadyExam().getAddedTime());
					session.update(readyExam);
					session.flush();
				}

				hql = "SELECT re FROM ReadyExam re where re.username =: username AND re.OnGoing = 'yes' and re.isClone = 'no'";
				Query query3 = session.createQuery(hql, ReadyExam.class);
				query3.setParameter("username", extraTime.getReadyExam().getUsername());
				List<ReadyExam> list = (List<ReadyExam>) query3.getResultList();
				Message responseMessage = new Message("refreshTable",list);

				hql = "SELECT et FROM ExtraTime et where et.extraTimeApproved = ''";
				Query query1 = session.createQuery(hql, ExtraTime.class);
				List<ExtraTime> list2 = (List<ExtraTime>) query1.getResultList();

				Message responseMessage1 = new Message("refreshTablePrinciple",list2);

				Notification notification = new Notification("Extra time has been aprroved for exam " + extraTime.getReadyExam().getIdd(), LocalDateTime.now(), false);
				hql = "SELECT t FROM Teacher t WHERE t.username = :username";
				Query query5 = session.createQuery(hql, Teacher.class);
				query5.setParameter("username", extraTime.getTeacherId());
				Teacher teacher = (Teacher) query5.getSingleResult();
				teacher.getNotificationList().add(notification);
				session.update(teacher);
				session.flush();

				hql = "SELECT n from Notification n where n.user.id =: id and n.isRead = false";
				Query query7 = session.createQuery(hql, Notification.class);
				query7.setParameter("id", teacher.getId());
				List<Notification> notifications = (List<Notification>) query7.getResultList();
				System.out.println("studentid: " + teacher.getId());
				Object object = new Object[]{notifications,teacher.getId()};
				Message message1 = new Message("RefreshTeacherBell", object);
				sendToAllClients(message1);

				System.out.println(responseMessage.getTitle());
				sendToAllClients(responseMessage1);
				sendToAllClients(responseMessage);
				sendToAllClients(message);

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if("DenyExtraTimeRequest".equals(message.getTitle()))
		{
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				ExtraTime extraTime = (ExtraTime) message.getBody();
				extraTime.setExtraTimeApproved("Denied");
				session.update(extraTime);
				session.flush();

				extraTime.getReadyExam().setExtraTimeApproved("Denied");
				session.update(extraTime.getReadyExam());
				session.flush();

				String hql = "SELECT re FROM ReadyExam re where re.username =: username AND re.OnGoing = 'yes' and re.isClone = 'no'";
				Query query3 = session.createQuery(hql, ReadyExam.class);
				query3.setParameter("username", extraTime.getReadyExam().getUsername());
				List<ReadyExam> list = (List<ReadyExam>) query3.getResultList();
				Message responseMessage = new Message("refreshTable",list);
				System.out.println(responseMessage.getTitle());

				hql = "SELECT et FROM ExtraTime et where et.extraTimeApproved = ''";
				Query query1 = session.createQuery(hql, ExtraTime.class);
				List<ExtraTime> list2 = (List<ExtraTime>) query1.getResultList();

				Message responseMessage1 = new Message("refreshTablePrinciple",list2);

				Notification notification = new Notification("Extra time has been denied for exam " + extraTime.getReadyExam().getIdd(), LocalDateTime.now(), false);
				hql = "SELECT t FROM Teacher t WHERE t.username = :username";
				Query query5 = session.createQuery(hql, Teacher.class);
				query5.setParameter("username", extraTime.getTeacherId());
				Teacher teacher = (Teacher) query5.getSingleResult();
				teacher.getNotificationList().add(notification);
				session.update(teacher);
				session.flush();

				hql = "SELECT n from Notification n where n.user.id =: id and n.isRead = false";
				Query query7 = session.createQuery(hql, Notification.class);
				query7.setParameter("id", teacher.getId());
				List<Notification> notifications = (List<Notification>) query7.getResultList();
				System.out.println("studentid: " + teacher.getId());
				Object object = new Object[]{notifications,teacher.getId()};
				Message message1 = new Message("RefreshTeacherBell", object);
				sendToAllClients(message1);

				sendToAllClients(responseMessage1);
				sendToAllClients(responseMessage);
				sendToAllClients(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if("getReadyExamsForCoursesReports".equals(message.getTitle()) || "getReadyExamsForTeacherReports".equals(message.getTitle())|| "getReadyExamsForTeacherReportsIN".equals(message.getTitle()))
		{
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}

				ReadyExam exam = (ReadyExam) message.getBody();
				String hql = "SELECT re FROM ReadyExam re where re.readyExamOriginalID =: id and re.approved = 'yes' and re.isClone = 'yes'";
				Query query1 = session.createQuery(hql, ReadyExam.class);
				query1.setParameter("id", exam.getId());
				List<ReadyExam> list = (List<ReadyExam>) query1.getResultList();
				Message responseMessage = new Message(message.getTitle(), list);
				client.sendToClient(responseMessage);

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if("getReadyExamsForStudentReports".equals(message.getTitle()))
		{
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				Object[] objects = (Object[]) message.getBody();
				ReadyExam exam = (ReadyExam) objects[0];
				Student student = (Student) objects[1];
				String hql = "SELECT re FROM ReadyExam re join re.listOfStudents s  where s.id =: student_id and  re.readyExamOriginalID =: readyExam_id and re.approved = 'yes' and re.isClone = 'yes'";
				Query query1 = session.createQuery(hql, ReadyExam.class);
				query1.setParameter("readyExam_id", exam.getId());
				query1.setParameter("student_id", student.getId());
				List<ReadyExam> list = (List<ReadyExam>) query1.getResultList();
				Message responseMessage = new Message(message.getTitle(), list);
				client.sendToClient(responseMessage);

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if("getListGradeForCourse".equals(message.getTitle()) || "getListGradeForTeacher".equals(message.getTitle()) || "getListGradeForTeacherIN".equals(message.getTitle()))
		{
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}

				ReadyExam exam = (ReadyExam) message.getBody();
				String hql = "SELECT re FROM ReadyExam re where re.id =: id ";
				Query query1 = session.createQuery(hql, ReadyExam.class);
				query1.setParameter("id", exam.getId());
				ReadyExam readyExam = (ReadyExam)query1.getSingleResult();
				List<Integer> median_list = new ArrayList<>();
				for (Integer integer : readyExam.getListOfGrades())
				{
					median_list.add(integer);
				}
				Object object = new Object[]{median_list, readyExam.getId()};
				Message responseMessage = new Message(message.getTitle(), object);
				client.sendToClient(responseMessage);

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if("getListGradeForStudent".equals(message.getTitle()))
		{
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				Object[] objects = (Object[]) message.getBody();
				Student student = (Student)objects[1];
				ReadyExam exam = (ReadyExam)objects[0];
				String hql = "SELECT re FROM ReadyExam re where re.readyExamOriginalID =: id and re.approved = 'yes' and re.isClone = 'yes' and re.examinee =: student_usr";
				Query query1 = session.createQuery(hql, ReadyExam.class);
				query1.setParameter("id", exam.getId());
				query1.setParameter("student_usr",student.getUsername());
				List<ReadyExam> readyExamList = (List<ReadyExam>) query1.getResultList();
				System.out.println("size of its lisy" + readyExamList.size());
				List<Integer> median_list = new ArrayList<>();
				for (ReadyExam readyExam : readyExamList)
				{
					median_list.add(readyExam.getGrade());
				}
				Object object = new Object[]{median_list, exam.getId()};
				Message responseMessage = new Message(message.getTitle(), object);
				client.sendToClient(responseMessage);

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if("saveManualExam".equals(message.getTitle()))
		{
			Object[] objects = (Object[]) message.getBody();

			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				File file = (File) objects[0];
				int id = (Integer) objects[1];
				Student student = (Student) objects[2];

				String hql = "SELECT re FROM ReadyExam re where re.id =: id ";
				Query query1 = session.createQuery(hql, ReadyExam.class);
				query1.setParameter("id", id);
				ReadyExam readyExam = (ReadyExam)query1.getSingleResult();

				hql = "SELECT re FROM ReadyExam re where re.id =: id ";
				Query query3 = session.createQuery(hql, ReadyExam.class);
				query3.setParameter("id", readyExam.getReadyExamOriginalID());
				ReadyExam readyExam1 = (ReadyExam)query3.getSingleResult();

				hql = "SELECT s FROM Student s where s.id =: id ";
				Query query2 = session.createQuery(hql, Student.class);
				query2.setParameter("id", student.getId());
				Student student1 = (Student)query2.getSingleResult();


				System.out.println("before saving file");
				readyExam.setFile(file);
				session.update(readyExam);
				session.flush();

				readyExam1.getListOfStudents().remove(student1);
				session.update(readyExam1);
				session.flush();

				student1.getReadyExams().remove(readyExam1);
				session.update(student1);
				session.flush();

				System.out.println("after saving file");
				client.sendToClient(message);

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if("getStudentsToViewGradePrinciple".equals(message.getTitle()))
		{
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				Student student = (Student) message.getBody();
				String hql = "SELECT re FROM ReadyExam re where re.examinee =: id and re.approved = 'yes' and re.isClone = 'yes'";
				Query query1 = session.createQuery(hql, ReadyExam.class);
				query1.setParameter("id", student.getUsername());
				List<ReadyExam> list = (List<ReadyExam>)query1.getResultList();
				Message message1 = new Message(message.getTitle(), list);
				client.sendToClient(message1);

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if("getQuestionsForSubject".equals(message.getTitle()) || "getQuestionsForSubjectSE".equals(message.getTitle()))
		{
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				Subject subject = (Subject) message.getBody();
				String hql = "SELECT q FROM Question q where q.subject.name =: subject";
				Query query1 = session.createQuery(hql, Question.class);
				query1.setParameter("subject", subject.getName());
				List<Question> list = (List<Question>)query1.getResultList();
				Message message1 = new Message(message.getTitle(), list);
				client.sendToClient(message1);

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if("getQuestionsForSubjectAndCourse".equals(message.getTitle()))
		{
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				Course course = (Course) message.getBody();
				String hql = "select q from Question  q JOIN q.course c where c.name =:name";
				Query query1 = session.createQuery(hql, Question.class);
				query1.setParameter("name", course.getName());
				List<Question> list = (List<Question>)query1.getResultList();
				Message message1 = new Message(message.getTitle(), list);
				client.sendToClient(message1);

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if ("timeIsUp".equals(message.getTitle()))
		{
			client.sendToClient(message);
		}
		else if("getStudentNotificationList".equals(message.getTitle()) || "getTeacherNotificationList".equals(message.getTitle()) || "getPrincipleNotificationList".equals(message.getTitle()))
		{
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				User user = (User) message.getBody();
				String hql = "select n from Notification  n where n.user.id =: id and n.isRead = false ";
				Query query1 = session.createQuery(hql, Notification.class);
				query1.setParameter("id", user.getId());
				List<Notification> list = (List<Notification>)query1.getResultList();
				Message resMessage = new Message(message.getTitle(), list);
				client.sendToClient(resMessage);

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if ("getNotificationForStudent".equals(message.getTitle()) || "getNotificationForTeacher".equals(message.getTitle()) || "getNotificationForPrinciple".equals(message.getTitle())) {
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				User user = (User) message.getBody();
				String hql = "select n from Notification n where n.user.id = :id";
				Query query1 = session.createQuery(hql, Notification.class);
				query1.setParameter("id", user.getId());
				List<Notification> list = (List<Notification>) query1.getResultList();
				Message resMessage = new Message(message.getTitle(), list);
				client.sendToClient(resMessage);
			} catch (Exception e) {
				e.printStackTrace(); // Consider using a logging framework here.
			} finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
		else if(message.getTitle().equals("setToRead") || message.getTitle().equals("setToReadTeacher") || message.getTitle().equals("setToReadPrinciple"))
		{
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}

				Object[] objects = (Object[]) message.getBody();
				User user = (User) objects[0];
				Notification notification = (Notification) objects[1];

				String hql = "select u from User  u where u.id =: id";
				Query query1 = session.createQuery(hql, User.class);
				query1.setParameter("id", user.getId());
				User user1  = (User) query1.getSingleResult();


				hql = "select n from Notification  n where n.id =: id";
				Query query3 = session.createQuery(hql, Notification.class);
				query3.setParameter("id", notification.getId());
				Notification notification1 = (Notification)query3.getSingleResult();

				notification1.setRead(true);
				session.update(notification1);
				session.flush();

				hql = "select n from Notification  n where n.user.id =: id and n.isRead = false";
				Query query2 = session.createQuery(hql, Notification.class);
				query2.setParameter("id", user1.getId());
				List<Notification> list = (List<Notification>)query2.getResultList();

				Message resMessage = new Message(message.getTitle(), list);
				client.sendToClient(resMessage);

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					try {
						if (session.getTransaction() != null && session.getTransaction().isActive()) {
							session.getTransaction().commit();
						}
					} catch (Exception ex) {
						// Handle exceptions during commit if necessary.
					} finally {
						session.close();
					}
				}
			}
		}
//		if ("Get all Students".equals(message.getOperation())) {
//			try {
//
//				session.beginTransaction();
//
//// Fetch the student data from your data source or database
//				List<Student> students = getAllStudentsFromDatabase();
//
//// Create a new Message with the student data and send it back to the client
//				int msgId = 0;
//				Message responseMessage = new Message("Get all Students", students);
//				client.sendToClient(responseMessage);
//				session.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} else if ("get grades".equals(message.getOperation())) {
//			try {
//
//				session.beginTransaction();
//				Student studentsel = (Student) message.getMessage();
//				int studentId = studentsel.getId();
//				Student student = getStudentFromDatabase(studentId);
//				Message responseMessage = new Message("get grades", student);
//				client.sendToClient(responseMessage);
//				session.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}else if("update".equals(message.getOperation())){
//			try {
//
//
//				session.beginTransaction();
//				Student studentsel =(Student) message.getMessage();
//				int studentId = studentsel.getId();
//				Student student = updateStudentGradeInDatabase(studentId,message.getSubject(), message.getNewScore());
//				Message responseMessage = new Message("update",student);
//				client.sendToClient(responseMessage);
//
//				session.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}

	}


	public static SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}

	// Declare the Scanner as an instance variable
	private Scanner inputScanner = new Scanner(System.in);

	@Override
	protected synchronized void clientDisconnected(ConnectionToClient client) {
		System.out.println("Client disconnected from server");
		super.clientDisconnected(client);
		System.out.println("Number of connected client(s): " + --numberOfClinets + "\n");

		if (numberOfClinets == 0) {
			System.out.print("Do you want to close the server? (Yes \\ No): ");

			String stringInput = inputScanner.nextLine().toLowerCase();
			if (stringInput.equals("yes")) {
				try {
					this.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("Server ready!");
			}
		}
	}

	@Override
	protected void clientConnected(ConnectionToClient client) {
		System.out.println("New client connected.");
		super.clientConnected(client);
		System.out.println("Number of connected client(s): " + ++numberOfClinets + "\n");
	}

}