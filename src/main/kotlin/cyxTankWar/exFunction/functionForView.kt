package cyxTankWar.exFunction

import cyxTankWar.modle.View

fun View.checkCollision(view:View):Boolean{
    return checkCollision(x,y,width,height,view.x,view.y,view.width,view.height)
}