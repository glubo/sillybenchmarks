enum class FindTwoLargestImplementation(val impl: (Collection<Int>) -> Result) {
    FOLD_INNER_SORT({ input ->
        val currentLargest = input.fold(listOf<Int>()) { acc, it ->
            (acc + listOf(it)).sortedDescending().take(2)
        }
        Result(
            currentLargest.getOrNull(0),
            currentLargest.getOrNull(1),
        )
    }),
    FOLD_MANUAL({ input ->
        input.fold(Result(null, null)) { acc, it ->
            if (it > (acc.largest ?: kotlin.Int.MIN_VALUE)) {
                Result(it, acc.largest)
            } else if (it > (acc.secondLargest ?: kotlin.Int.MIN_VALUE)) {
                Result(acc.largest, it)
            } else {
                acc
            }
        }
    }),
    FOREACH_INNER_SORT({ input ->
        var currentLargest = listOf<Int>()
        input.forEach {
            currentLargest = (currentLargest + listOf(it)).sortedDescending().take(2)
        }
        Result(
            currentLargest.getOrNull(0),
            currentLargest.getOrNull(1),
        )
    }),
    FOREACH_MANUAL({ input ->
        var currentLargest = Result(null, null)
        input.forEach {
            if (it > (currentLargest.largest ?: Int.MIN_VALUE)) {
                currentLargest = Result(it, currentLargest.largest)
            } else if (it > (currentLargest.secondLargest ?: Int.MIN_VALUE)) {
                currentLargest = Result(currentLargest.largest, it)
            }
        }
        currentLargest
    }),
    SORT({ input ->
        val sorted = input.sortedDescending().take(2)
        Result(
            sorted.getOrNull(0),
            sorted.getOrNull(1),
        )
    })
}