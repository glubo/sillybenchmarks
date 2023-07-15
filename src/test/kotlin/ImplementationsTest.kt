import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ImplementationsTest {
    @ParameterizedTest
    @MethodSource("implementationArguments")
    fun `empty input`(implementation: FindTwoLargestImplementation) {
        val result = implementation.impl(listOf())
        assertAll(
            { assertNull(result.largest) },
            { assertNull(result.secondLargest) },
        )
    }

    @ParameterizedTest
    @MethodSource("implementationArguments")
    fun `single element input`(implementation: FindTwoLargestImplementation) {
        val result = implementation.impl(listOf(5))
        assertAll(
            { assertEquals(5, result.largest) },
            { assertNull(result.secondLargest) },
        )
    }

    @ParameterizedTest
    @MethodSource("implementationArguments")
    fun `two elements input`(implementation: FindTwoLargestImplementation) {
        val result = implementation.impl(listOf(5, 6))
        assertAll(
            { assertEquals(6, result.largest) },
            { assertEquals(5, result.secondLargest) },
        )
    }

    @ParameterizedTest
    @MethodSource("implementationArguments")
    fun `many elements input`(implementation: FindTwoLargestImplementation) {
        val result = implementation.impl(listOf(1, 2, 3, 20, 23, 4, 5, 6, 7))
        assertAll(
            { assertEquals(23, result.largest) },
            { assertEquals(20, result.secondLargest) },
        )
    }

    @ParameterizedTest
    @MethodSource("implementationArguments")
    fun `huge input`(implementation: FindTwoLargestImplementation) {
        val input = (1..10002)+(10001..1)
        val result = implementation.impl(input)
        assertAll(
            { assertEquals(10002, result.largest) },
            { assertEquals(10001, result.secondLargest) },
        )
    }

    companion object {
        @JvmStatic
        fun implementationArguments() = FindTwoLargestImplementation.entries.map {
            Arguments.of(it)
        }
    }
}