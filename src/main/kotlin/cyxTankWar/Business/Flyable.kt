package cyxTankWar.Business


import cyxTankWar.enumClass.Direction
import cyxTankWar.modle.View

interface Flyable: View {
    val speed : Int
    val direction:Direction
    fun fly(direction: Direction){}
}