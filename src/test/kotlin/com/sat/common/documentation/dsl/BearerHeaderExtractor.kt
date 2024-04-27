
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultHandler

class BearerHeaderExtractor : ResultHandler {
    private var _request: HttpServletRequest? = null

    override fun handle(result: MvcResult) {
        _request = result.request
    }

    fun hasBearer(): Boolean {
        val header = _request!!.getHeader(HttpHeaders.AUTHORIZATION)
        return !(header.isNullOrBlank() || !header.startsWith("Bearer "))
    }
}
