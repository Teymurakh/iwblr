#extends entity

function initialize(this)
  this.j:setAnimation("block_brick_1")
  this.j:addCollision("block")
  this.j:addCollision("spike")
  this.j:setAffectedByGravity(true)
end

function collided(this, entity, direction)
  if not (this.team == entity.team) then
    this.j:setVelX(0)
    this.j:setVelY(0)
    
    
  end
end