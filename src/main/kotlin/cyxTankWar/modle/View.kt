package cyxTankWar.modle

interface View {
    val x:Int
    val y:Int
    val width:Int
    val height:Int
    fun draw(){}
    fun checkCollision(x1:Int,y1:Int,w1:Int,h1:Int,x2:Int,y2:Int,w2:Int,h2:Int):Boolean{
        return when{
            x2+w2<=x1
            ->false
            x2>=x1+w1
            ->false
            y2+h2<=y1
            ->false
            else ->y2<y1+h1
        }
    }
}