package cyxTankWar.Business

import cyxTankWar.modle.View

interface Destroyable: View {
    fun judgeDestroy():Boolean
    fun showDestroy():Array<View>?
}