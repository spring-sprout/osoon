package io.osoon.helper;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author whiteship
 */
@Component
public class DatabaseTestHelper {

    @Autowired
    protected SessionFactory sessionFactory;

    public void removeAllData() {
        // Remove all relation first. Node can not remove when relation exist between nodes.
        sessionFactory.openSession().query("MATCH (a)-[r]-(b) DELETE r", new HashMap<>());
        sessionFactory.openSession().query("MATCH (a) DELETE a", new HashMap<>());
    }
}
