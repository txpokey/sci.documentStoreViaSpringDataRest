package jh.apple.documentstore

import groovy.util.logging.Slf4j
import jh.apple.documentstore.repo.H2SyncAdhocDocumentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.annotations.Test

@Test
@Slf4j
@SpringBootTest
class DocumentStoreApplicationTests extends DocumentStoreConfigTest {

    void sanityCheck() {
        super.sanityCheck()
    }
}
