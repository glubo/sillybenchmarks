import kotlin.random.Random
import kotlin.time.Duration


fun main(args: Array<String>) {
    val maximumSize = 10000

    val iterations = 100

    var testListLength = 1
    while (testListLength < maximumSize) {
        val testList = (1..testListLength).map {
            Random.nextInt()
        }

//        println("$testListLength ${timeLinearManual.inWholeMicroseconds} ${timeLinearFold.inWholeMicroseconds} ${timeLinearForeach.inWholeMicroseconds} ${timeSort.inWholeMicroseconds}")
        testListLength = nextTestLength(testListLength)
    }
}

private data class RunResult(
    val implementation: FindTwoLargestImplementation,
    val took: Duration,
)

private data class RoundResult(
    val implementation: FindTwoLargestImplementation,
    val meanTimeMicroSeconds: Double,
    val standardDeviationTimeMicroSeconds: Double,
)

private fun nextTestLength(current: Int) = ((current + 1) * 1.2).toInt()

private fun RoundResult.toGnuplot() = "${this.meanTimeMicroSeconds} ${this.standardDeviationTimeMicroSeconds}"
