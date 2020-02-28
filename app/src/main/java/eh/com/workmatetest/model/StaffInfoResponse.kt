package eh.com.workmatetest.model

data class StaffInfo(
    val application_counts: ApplicationCounts,
    val client: Client,
    val cover_image: String,
    val created_date: String,
    val description: String,
    val employment_counts: EmploymentCounts,
    val end_time: String,
    val fixed_location: Boolean,
    val gender: Any,
    val id: Int,
    val interview_info: Any,
    val interview_time: Any,
    val location: Location,
    val manager: Manager,
    val max_age: Int,
    val min_age: Int,
    val modified_date: String,
    val offer_counts: OfferCounts,
    val offer_statistics: OfferStatistics,
    val position: Position,
    val require_english: Boolean,
    val require_experience: Boolean,
    val schedules: List<Schedule>,
    val staff_required: Int,
    val start_time: String,
    val statistics: Statistics,
    val status: String,
    val timezone: String,
    val title: String,
    val uid: String,
    val wage_amount: String,
    val wage_type: String
)

data class ApplicationCounts(
    val applied: Int,
    val hired: Int,
    val pending_contract: Int,
    val pending_onboarding: Int,
    val rejected: Int,
    val withdrawn: Int
)

data class Client(
    val country: Country,
    val description: String,
    val id: Int,
    val logo: Any,
    val name: String,
    val status: String,
    val tier: String,
    val website: String
)

data class Country(
    val code: String,
    val currency_code: String,
    val dial_code: String,
    val id: Int,
    val name: String
)

data class EmploymentCounts(
    val active: Int,
    val cancelled: Int,
    val ended: Int
)

data class Location(
    val address: Address,
    val created_date: String,
    val id: Int,
    val modified_date: String,
    val name: String
)

data class Address(
    val area: Area,
    val country: CountryXX,
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val point: String,
    val province: Any,
    val street_1: String,
    val street_2: String,
    val zip: String
)

data class Area(
    val city: City,
    val id: Int,
    val name: String
)

data class City(
    val country: CountryX,
    val id: Int,
    val name: String,
    val timezone: String
)

data class CountryX(
    val code: String,
    val currency_code: String,
    val dial_code: String,
    val id: Int,
    val name: String
)

data class CountryXX(
    val code: String,
    val currency_code: String,
    val dial_code: String,
    val id: Int,
    val name: String
)

data class Manager(
    val email: String,
    val id: Int,
    val locations: List<Any>,
    val name: String,
    val phone: String,
    val role: String
)

data class OfferCounts(
    val applied: Int,
    val cancelled: Int,
    val confirmed: Int,
    val contract_ended: Int,
    val no_show: Int,
    val pending_contract: Int,
    val pending_onboarding: Int,
    val rejected: Int,
    val sent: Int,
    val viewed: Int,
    val withdrawn: Int
)

data class OfferStatistics(
    val applied: Int,
    val confirmed: Int,
    val sent: Int,
    val viewed: Int
)

data class Position(
    val active: Boolean,
    val id: Int,
    val name: String
)

data class Schedule(
    val duration: String,
    val end_time: String,
    val id: Int,
    val recurrences: String,
    val staff_request: Int,
    val start_date: String,
    val start_time: String
)

data class Statistics(
    val no_show: Int
)