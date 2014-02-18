--#extends entity

function initialize(this)		
 		this.activated = false
        this.j:setAnimation("invisible")
        this.j:addCollision("guy")
        this.j:setDimX(1)
        this.j:setDimY(1)
end

function placed(this)
  this.spike1 = lib.newEntity("spike270");
  this.spike2 = lib.newEntity("spike90");
		
  local displacement_x = 14
  local displacement_y = 14	
		
  this.j:createEntity(this.spike1.j, this.j:getPosX() + displacement_x, this.j:getPosY() + displacement_y)
  this.j:createEntity(this.spike2.j, this.j:getPosX() + displacement_x, this.j:getPosY() + displacement_y-1)
  
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
  this.spike1:die()
  this.spike2:die()
  
  local platform = lib.newEntity("platform");
  this.j:createEntity(platform.j, this.j:getPosX() + 13, this.j:getPosY() + 3)
end