package cyxTankWar.modle

import cyxTankWar.Business.Attackable
import cyxTankWar.Business.Blockable
import cyxTankWar.Business.Sufferable
import cyxTankWar.Config
import org.itheima.kotlin.game.core.Painter

class Steel(override val x: Int, override val y: Int):Blockable,Sufferable {
    override val blood: Int = Int.MAX_VALUE
    override val width = Config.block
    override val height = Config.block
    override fun draw() {
        Painter.drawImage("img/steels.gif",x,y)
    }

    override fun notifySuffer(attacker: Attackable): Array<View>? {
        return null
    }
}