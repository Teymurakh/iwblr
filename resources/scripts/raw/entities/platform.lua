#extends entity

function initialize(this)
  --entity:initialize()
  this.j:setAnimation("platform_wood")
  this.j:addTag("platform")
  this.j:addTag("physical")
  this.j:addCollision("guy")
  this.j:setRectangular(true)
  this.j:setPlatform(true)
  this.j:setDimX(1)
  this.j:setDimY(0.5)
  
  this.is_platform = true
end