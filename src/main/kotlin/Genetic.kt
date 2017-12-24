import java.util.*
import kotlin.collections.ArrayList
import kotlin.streams.asSequence

class Genetic(val target: String) {

    val source = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    fun createInitialPopulation(totalPopulation: Int, length: Long): ArrayList<String>{
        val list = arrayListOf<String>()
        for (i in 0 until totalPopulation) {
            list.add(createRandomString(length))
        }
        return list
    }

    private fun createRandomString(length: Long): String{
        val result = Random().ints(length, 0, source.length)
                .asSequence()
                .map(source::get)
                .joinToString("")

        return result
    }

    fun calculateFitness(list: ArrayList<String>){
        var allFitness = 0f
        for (element in list) {
            allFitness += calculateFitnessForElement(element)
        }
        val averageFitness = allFitness/list.size
    }

    private fun calculateFitnessForElement(element: String): Float{
        var points = 0
        for ((index, letter) in target.withIndex()) {
            if(element[index] == letter)
                points+=1
        }
        return points.toFloat()/target.length
    }
}