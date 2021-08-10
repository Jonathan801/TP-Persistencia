package TestDao;


import ar.edu.unq.epers.bichomon.backend.Dao.dao.DataDAO;
import ar.edu.unq.epers.bichomon.backend.Dao.impl.HibernateDataDAO;
import ar.edu.unq.epers.bichomon.backend.service.data.DataServiceImpl;
import ar.edu.unq.epers.bichomon.backend.service.serviceHibernate.ServiceHibernate;
import org.junit.After;
import org.junit.Before;

public class TestAbstract {
    protected DataServiceImpl dataServiceImpl;
    protected ServiceHibernate serviceHibernate;
    protected DataDAO dataDAO;


    @Before
    public void setUp(){
        dataDAO = new HibernateDataDAO();
        dataServiceImpl = new DataServiceImpl(dataDAO);
        serviceHibernate = new ServiceHibernate();
    }
    @After
    public void clear(){
        this.dataServiceImpl.eliminarDatos();
    }
}
