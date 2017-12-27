interface Display {

    fun showPhrase(text: String)

    fun setInfo(totalGenerations: Int, averageFitness: Int, totalPopulation: Int, mutationRate: Float)
}