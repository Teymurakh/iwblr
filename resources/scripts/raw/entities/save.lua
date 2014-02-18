--#extends entity

function initialize(this)		
  this.j:setAnimation("save_point_off")
  this.j:addCollision("guy")
  this.j:setDimX(28/32)
  this.j:setDimY(31/32)
end

function collided(this, entity, direction)
  if entity.j:hasTag("guy") then
    this:save()
  end
end

function save(this) 
  if not this.on then
    this.j:save()
    this:turnOn()
  end
end

function turnOn(this)
    this.j:setAnimation("save_point_on")
    this:newTimer(1, function (this) this:turnOff() end, "turn_off")
    this.on = true
end

function turnOff(this) 
    this.j:setAnimation("save_point_off")
    this.on = false
end