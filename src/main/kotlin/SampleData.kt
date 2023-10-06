data class SampleData(
    val code: Int,
    val data: List<Data>,
){
    data class Data(
        val key: String,
        val value: Int,
    )
}