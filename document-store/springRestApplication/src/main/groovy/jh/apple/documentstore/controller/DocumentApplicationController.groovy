package jh.apple.documentstore.controller

import groovy.util.logging.Slf4j
import jh.apple.documentstore.domain.AdhocDocument
import jh.apple.documentstore.service.DocumentStoreServiceContract
import org.apache.commons.io.IOUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import javax.annotation.Nonnull
import javax.servlet.http.HttpServletRequest

@Slf4j
@Profile(["default"])
@RestController("documentController")
@RequestMapping("/storage")
class DocumentApplicationController{

    private DocumentStoreServiceContract documentStoreService

    @Autowired
    DocumentApplicationController(@Qualifier("documentStoreService") DocumentStoreServiceContract documentStoreService) {
        this.documentStoreService = documentStoreService
    }

    @GetMapping("findAll")
    List<AdhocDocument> findAll() {
        def candidate = documentStoreService.findAll()
        candidate
    }

    @GetMapping("documents/{lookupKey}")
    def findByLookupKey(@Nonnull @PathVariable String lookupKey) {
        Optional<AdhocDocument> optionalCandidate = documentStoreService.findByLookupKey(lookupKey)
        def present = optionalCandidate.isPresent()
        def candidatePayload = present ? optionalCandidate.get().payload : null
        def re = present ? ResponseEntity.status(HttpStatus.OK).body(candidatePayload) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        re
    }
    @DeleteMapping("documents/{lookupKey}")
    def deleteByLookupKey(@Nonnull @PathVariable String lookupKey) {
        def success = documentStoreService.deleteByLookupKey(lookupKey)
        def re = success ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        re
    }

    @PostMapping("documents")
    @ResponseBody
    def post(HttpServletRequest request) throws IOException {
        def contentType = request.getHeader(HttpHeaders.CONTENT_TYPE)
        def inputStream = request.getInputStream()
        byte[] requestBodyAsByteArray = IOUtils.toByteArray(inputStream)
        Map map = [ payload: requestBodyAsByteArray ]
        AdhocDocument documentPreImage = AdhocDocument.of(map)
        def storedImage = documentStoreService.save(documentPreImage)
        def lookupKey = storedImage.lookupKey
        def re = ResponseEntity.status(HttpStatus.CREATED).body(lookupKey)
        re
    }
    private final def NL = "\n"

}