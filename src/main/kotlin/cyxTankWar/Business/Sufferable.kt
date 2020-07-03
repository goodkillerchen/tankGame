package cyxTankWar.Business

import cyxTankWar.modle.View

interface Sufferable:View {
    val blood:Int
    fun notifySuffer(attacker:Attackable):Array<View>?
}