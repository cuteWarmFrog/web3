import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;

@ManagedBean
@Named("nodeJPA")
@SessionScoped
public class NodeJPA implements Serializable {


    private EntityManager entityManager;
}
