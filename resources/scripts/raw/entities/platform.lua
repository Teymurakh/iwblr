#extends entity

function initialize(this)
  --entity:initialize()
  this.j:setAnimation("block_brick_1")
  this.j:addTag("platform")
  this.j:addTag("physical")
  this.j:addCollision("guy")
  this.j:setRectangular(true)
  this.j:setPlatform(true)
  this.j:setVelX(3)
  this.j:setVelY(0)
  this.j:setDimX(1)
  this.j:setDimY(0.5)
end