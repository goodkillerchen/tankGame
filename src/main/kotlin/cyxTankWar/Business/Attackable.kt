package cyxTankWar.Business

import cyxTankWar.modle.View

interface Attackable:View {
    val owner:View
    val attack:Int
    fun isCollision(Sufferer:Sufferable):Boolean
    fun notifyAttack(Sufferer: Sufferable)
}