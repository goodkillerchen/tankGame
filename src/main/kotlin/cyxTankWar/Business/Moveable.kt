package cyxTankWar.Business


import cyxTankWar.Config
import cyxTankWar.enumClass.Direction
import cyxTankWar.modle.View

interface Moveable:View {
    val currentDirection:Direction
    val speed:Int
    fun willCollision(block:Blockable):Direction?{
        var x=this.x
        var y=this.y
        when(currentDirection){
            Direction.RIGHT->x=(x+speed)% Config.gameWidth
            Direction.DOWN->y=(y+speed)% Config.gameHeight
            Direction.UP->y=(y-speed+ Config.gameHeight)% Config.gameHeight
            Direction.LEFT->x=(x-speed+ Config.gameWidth)% Config.gameWidth
        }

        /*var judge=when{
            block.x+block.width<=x
                ->false
            block.x>=x+width
                ->false
            block.y+block.height<=y
                ->false
            else ->block.y<y+height
        }*/
        return if(checkCollision(x,y,width,height,block.x,block.y,block.width,block.height)) currentDirection else null
    }
    fun notifyCollision(direction:Direction?,block:Blockable?)
}