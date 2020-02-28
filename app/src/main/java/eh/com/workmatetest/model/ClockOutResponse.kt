package eh.com.workmatetest.model

data class ClockOut(
    val require_feedback: Boolean,
    val timesheet: Timesheet
)

data class Timesheet(
    val clock_in_latitude: String,
    val clock_in_longitude: String,
    val clock_in_time: String,
    val clock_out_latitude: String,
    val clock_out_longitude: String,
    val clock_out_time: String,
    val id: Int,
    val notes: String,
    val partner: Partner,
    val schedule: Any,
    val staff_request: StaffRequest,
    val status: String,
    val timesheet: Int
)

