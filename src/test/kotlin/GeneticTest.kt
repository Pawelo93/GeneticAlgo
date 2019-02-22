import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class GeneticTest {

    var totalPopulation = 10
    var mutationRate = 0.01f

    lateinit var genetic: Genetic

    @Test
    fun calculateFitness(){
        var targetWord = "ab"
        var element = "aa"
//        genetic = Genetic(null, targetWord, totalPopulation, mutationRate)
//        assertEquals(0.5f, genetic.calculateFitnessForElement(element))
//
//        targetWord = "abc"
//        element = "aba"
//        genetic = Genetic(null, targetWord, totalPopulation, mutationRate)
//        assertEquals(2/3.toFloat(), genetic.calculateFitnessForElement(element))
//
//        targetWord = "abcd"
//        element = "adcy"
//        genetic = Genetic(null, targetWord, totalPopulation, mutationRate)
//        assertEquals(0.625f, genetic.calculateFitnessForElement(element))

        targetWord = "aca"
        element = "caa"
        // aabb
        // beea
        genetic = Genetic(null, targetWord, totalPopulation, mutationRate)
        assertEquals(2/3.toFloat(), genetic.calculateFitnessForElement(element))

//        targetWord = "aaabbb"
//        element = "abeeba"
//        // aabb
//        // beea
//        genetic = Genetic(null, targetWord, totalPopulation, mutationRate)
//        assertEquals(0.5f, genetic.calculateFitnessForElement(element))
    }
}