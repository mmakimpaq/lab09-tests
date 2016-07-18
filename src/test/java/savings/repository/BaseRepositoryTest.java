package savings.repository;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import common.db.LocalDatabaseConfiguration;
import savings.repository.impl.RepositoryConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        LocalDatabaseConfiguration.class, RepositoryConfiguration.class
})
@Transactional
abstract class BaseRepositoryTest {

}
