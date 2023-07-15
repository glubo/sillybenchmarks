import kotlin.random.Random
import kotlin.time.measureTime

data class Result(
    val largest: Int?,
    val secondLargest: Int?,
)


fun findTwoLargestLinearManual(input: List<Int>): Result {
    var currentLargest = Result(null, null)
    input.forEach {
        if (it > (currentLargest.largest ?: Int.MIN_VALUE)) {
            currentLargest = Result(it, currentLargest.largest)
        } else if (it > (currentLargest.secondLargest ?: Int.MIN_VALUE)) {
            currentLargest = Result(currentLargest.largest, it)
        }
    }
    return currentLargest
}

fun findTwoLargestLinearForeach(input: List<Int>): Result {
    var currentLargest = listOf<Int>()
    input.forEach {
        (currentLargest + listOf(it)).sortedDescending().take(2)
    }
    return Result(
        currentLargest.getOrNull(0),
        currentLargest.getOrNull(1),
    )
}

fun findTwoLargestLinearFold(input: List<Int>): Result {
    return input.fold(Result(null, null)) { acc, it ->
        return if (it > (acc.largest ?: Int.MIN_VALUE)) {
            Result(it, acc.largest)
        } else if (it > (acc.secondLargest ?: Int.MIN_VALUE)) {
             Result(acc.largest, it)
        } else {
            acc
        }
    }
}

fun findTwoLargestSort(input: List<Int>): Result {
    val sorted = input.sortedDescending().take(2)
    return Result(
        sorted.getOrNull(0),
        sorted.getOrNull(1),
    )
}






fun main(args: Array<String>) {
    val maximumSize = 1000000

    val iterations = 1000

    (1..iterations).forEach {
        val testLength = Random.nextInt(maximumSize)

        val testList = (1..testLength).map {
            Random.nextInt()
        }

        val timeLinearManual = measureTime {
            findTwoLargestLinearManual(testList)
        }

        val timeLinearFold = measureTime {
            findTwoLargestLinearFold(testList)
        }

        val timeLinearForeach = measureTime {
            findTwoLargestLinearForeach(testList)
        }

        val timeSort = measureTime {
            findTwoLargestSort(testList)
        }

        println("$testLength ${timeLinearManual.inWholeMicroseconds} ${timeLinearFold.inWholeMicroseconds} ${timeLinearForeach.inWholeMicroseconds} ${timeSort.inWholeMicroseconds}")
    }
}
