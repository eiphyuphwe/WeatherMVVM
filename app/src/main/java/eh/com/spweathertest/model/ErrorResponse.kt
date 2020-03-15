package eh.com.spweathertest.model

class ErrorResponse {
    var code = ""
    var detail = ""

    override fun toString(): String {
        return "ErrorResponse{" +
                "code='" + code + '\'' +
                ", detail='" + detail + '\'' +
                '}'
    }

    companion object {
        var error:String=""
    }
}