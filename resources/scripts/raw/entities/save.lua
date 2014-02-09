--#extends entity

function initialize(this)		
        this.j:setAnimation("save_point_off")
        this.j:addCollision("guy")
        this.j:setDimX(0.75)
        this.j:setDimY(0.75)
end

function collided(this, entity, direction)
  if entity.j:hasTag("guy") then
    this.j:save()
  end
end
