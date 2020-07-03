package cyxTankWar.modle

import cyxTankWar.Business.Attackable
import cyxTankWar.Business.Blockable
import cyxTankWar.Business.Destroyable
import cyxTankWar.Business.Sufferable
import cyxTankWar.Config
import org.itheima.kotlin.game.core.Composer
import org.itheima.kotlin.game.core.Painter

class Wall(override val x: Int, override val y: Int) :Blockable,Sufferable,Destroyable {
    override val height: Int = Config.block
    override val width: Int = Config.block
    override var blood=3
    override fun draw() {
        Painter.drawImage("img/walls.gif",x,y)
    }

    override fun judgeDestroy(): Boolean = blood<=0

    override fun notifySuffer(attacker: Attackable):Array<View>? {
        blood-=attacker.attack
        Composer.play("img/hit.wav")
        //return arrayOf(Blast(x,y))
        return null
    }

    override fun showDestroy(): Array<View>? {
        return arrayOf(Blast(x,y))
    }
}