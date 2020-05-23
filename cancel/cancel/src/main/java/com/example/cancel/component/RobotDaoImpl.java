package com.example.cancel.component;

import com.example.cancel.data.RobotRepository;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.persistence.EntityManagerFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RobotDaoImpl implements RobotDao {

  @Autowired
  JdbcTemplate jdbcTemplate;


//  private final SessionFactory sessionFactory;
  private final EntityManagerFactory entityManagerFactory;
  private final RobotRepository robotRepository;
  private final ExecutorService executorService = Executors.newSingleThreadExecutor();;
  Session currentSession;


  public RobotDaoImpl(EntityManagerFactory entityManagerFactory,
    RobotRepository robotRepository) {
    this.entityManagerFactory = entityManagerFactory;
    this.robotRepository = robotRepository;
    if(entityManagerFactory.unwrap(SessionFactory.class) == null) {
      throw new NullPointerException("factory is not a hibernate factory");
    }
  }

  @Override
  public void run() {
    Session session1;

    synchronized (this) {
      SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
      session1 = sessionFactory.openSession();

      session1.beginTransaction();
      this.currentSession = session1;
    }

    executorService.submit(() ->
      {
        String s = "select 1,  wait_for_me(7)";
        List list = session1.createSQLQuery(s).list();
        session1.getTransaction().commit();
        session1.close();
        System.out.println("FINISHED QUERY");

    });
    System.out.println("FINISHED RUN");
  }

  @Override
  public synchronized void cancel() {
    currentSession.cancelQuery();
    System.out.println("CANCELED");
  }

  @Override
  public void query() {
    String query = "begin;\n"
      + "  insert into robot values(nextval('robot_seq'), 'my robot2');\n"
      + "  insert into robot values(nextval('robot_seq'), 'my robot3');\n"
      + "COMMIT;";
    jdbcTemplate.update(query);
  }

}
