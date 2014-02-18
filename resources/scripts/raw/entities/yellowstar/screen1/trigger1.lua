--#extends entity

function initialize(this)		
 		this.activated = false
        this.j:setAnimation("invisible")
        this.j:addCollision("guy")
        this.j:setDimX(1)
        this.j:setDimY(0.25)
end

function collided(this, entity, direction)
  if entity.j:hasTag("guy") then 
    if not this.activated then
      this.activated = true
      this:trigger_action()
      this:die()
    end
  end
end

function trigger_action(this)
  local new_e = lib.newEntity("block_connector");
  local pos_x = this.j:getPosX()	
  local pos_y = this.j:getPosY() + 1.75
		
  this.j:createEntity(new_e.j, pos_x, pos_y)
end