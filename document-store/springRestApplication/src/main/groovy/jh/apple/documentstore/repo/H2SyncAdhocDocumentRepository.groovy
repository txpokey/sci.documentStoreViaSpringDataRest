package jh.apple.documentstore.repo

import jh.apple.documentstore.domain.AdhocDocument
//import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

import javax.annotation.Nonnull

@Repository
interface H2SyncAdhocDocumentRepository extends CrudRepository<AdhocDocument,Long>{

    Optional<AdhocDocument> findByLookupKey( @Nonnull uuid )
//    List<AdhocDocument> findAll()

}
