import org.jetbrains.letsPlot.export.ggsave
import org.jetbrains.letsPlot.geom.geomErrorBar
import org.jetbrains.letsPlot.geom.geomLine
import org.jetbrains.letsPlot.geom.geomPoint
import org.jetbrains.letsPlot.letsPlot
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.measureTime

fun main(args: Array<String>) {
    val maximumSize = 1000000

    val iterations = 100

    val results = mutableListOf<RoundResult>()

    var testListLength = 1L
    while (testListLength < maximumSize) {
        val iterations = (1..iterations).flatMap {
            val testList = (1..testListLength).map {
                Random.nextInt()
            }

            val tasks = FindTwoLargestImplementation.entries.shuffled()
            tasks.map {
                val duration = measureTime {
                    it.impl(testList)
                }
                RunResult(it, duration)
            }
        }.groupBy { it.implementation }

        results.addAll(
            iterations.map { iteration ->
                val durationsMicroSeconds = iteration.value.map { it.took.inWholeMicroseconds.toDouble() }
                RoundResult(
                    inputLength = testListLength,
                    implementation = iteration.key,
                    meanTimeMicroSeconds = durationsMicroSeconds.average(),
                    standardDeviationTimeMicroSeconds = standardDeviation(durationsMicroSeconds),
                )
            }
        )

        testListLength = nextTestLength(testListLength)
    }

    val resultData = mapOf(
        "x" to results.map { it.inputLength },
        "y" to results.map { it.meanTimeMicroSeconds },
        "ymin" to results.map { it.meanTimeMicroSeconds - it.standardDeviationTimeMicroSeconds * 0.5 },
        "ymax" to results.map { it.meanTimeMicroSeconds + it.standardDeviationTimeMicroSeconds * 0.5 },
        "implementation" to results.map { it.implementation.toString() }
    )

    val fig = letsPlot(resultData) {
        x = "x"; y = "y"
        label = "implementation"
        color = "implementation"
        ymin = "ymin"; ymax = "ymax"
    } + geomPoint(
        size = 1.0
    ) + geomLine(
        size = 0.5
    ) + geomErrorBar(width = 0.1) {}

    ggsave(fig, "plot.png")
}

private data class RunResult(
    val implementation: FindTwoLargestImplementation,
    val took: Duration,
)

private data class RoundResult(
    val inputLength: Long,
    val implementation: FindTwoLargestImplementation,
    val meanTimeMicroSeconds: Double,
    val standardDeviationTimeMicroSeconds: Double,
)

private fun nextTestLength(current: Long) = ((current + 1) * 1.5).toLong()

fun standardDeviation(data: Collection<Double>): Double {
    val mean = data.average()
    return data
        .fold(0.0) { accumulator, next -> accumulator + (next - mean).pow(2.0) }
        .let {
            sqrt(it / data.size)
        }
}