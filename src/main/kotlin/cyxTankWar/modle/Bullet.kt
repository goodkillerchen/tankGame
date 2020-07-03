package cyxTankWar.modle

import cyxTankWar.Business.Attackable
import cyxTankWar.Business.Destroyable
import cyxTankWar.Business.Flyable
import cyxTankWar.Business.Sufferable
import cyxTankWar.Config
import cyxTankWar.enumClass.Direction
import cyxTankWar.exFunction.checkCollision
import org.itheima.kotlin.game.core.Painter

class Bullet(
    override val owner: View,tankSpeed : Int, direction: Direction,
    create:(width:Int, height:Int)->Pair<Number,Number>):Flyable,Destroyable,Attackable,Sufferable {
    override var x: Int=0
    override var y: Int=0
    override var blood=1
    override val width: Int
    override val height: Int
    override val speed:Int=tankSpeed
    override val direction:Direction=direction
    override val attack: Int=1
    private var haveAttacked=false
    init{
        val imageSize= Painter.size("img/tankmissile.gif")
        width=imageSize[0]+1
        height=imageSize[1]+1
        var p=create.invoke(width,height)
        x=p.first as Int
        y=p.second as Int
    }

    override fun draw() {
        Painter.drawImage("img/tankmissile.gif",x,y)
    }

    override fun fly(direction:Direction){
        when(direction){
            Direction.LEFT->{
                    x-=this.speed
            }
            Direction.RIGHT->{
                    x+=this.speed
            }
            Direction.DOWN->{
                    y+=this.speed
            }
            Direction.UP->{
                    y-=this.speed
            }
        }
    }

    override fun judgeDestroy():Boolean {
        if(haveAttacked)
            return true
        return (x<-width || x>Config.gameWidth+width || y<-height || y>Config.gameHeight+height)
    }

    override fun isCollision(sufferer: Sufferable): Boolean {
      return checkCollision(sufferer)
    }

    override fun notifyAttack(sufferer: Sufferable) {
        haveAttacked=true
    }

    override fun notifySuffer(attacker: Attackable): Array<View>? {
        blood-=attacker.attack
        if(blood<=0)
            return null
        return arrayOf(Blast(x,y))
    }

    override fun showDestroy(): Array<View>? {
        return null
    }
}