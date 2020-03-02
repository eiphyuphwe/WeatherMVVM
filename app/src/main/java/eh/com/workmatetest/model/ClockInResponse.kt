package eh.com.workmatetest.model

data class ClockInResponse(
    val clock_in_latitude: String,
    val clock_in_longitude: String,
    val clock_in_time: String,
    val clock_out_latitude: Any,
    val clock_out_longitude: Any,
    val clock_out_time: Any,
    val id: Int,
    val notes: Any,
    val partner: Partner,
    val schedule: Any,
    val staff_request: StaffRequest,
    val status: String,
    val timesheet: Int
)

data class Partner(
    val first_name: String,
    val id: Int,
    val image: String,
    val last_name: String,
    val mobile: String
)

data class StaffRequest(
    val client: Client,
    val id: Int,
    val location: Location,
    val position: Position,
    val status: String,
    val title: String,
    val uid: String
)


