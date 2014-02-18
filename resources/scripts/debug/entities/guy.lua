--
do
local newenv = {}
setAllowed(newenv)
take_from_parent(lib.loadentity("entity"), newenv)
local _ENV = newenv
_ENV.__index = _ENV

function new(javaObj)
  local this = setmetatable({}, _ENV)
  this.j = javaObj
  this.collision_damage = 0
  this.collision_selfdamage = 0
  this.max_health = 1
  this.health = 1
  this.is_invulnerable = false
  this.team = "neutral"
  this.timers = {}
  return this
end

function initialize(this)
  this.j:addTag("guy");
  this.j:addTag("physical")
  this.j:addTag("depth_collision");
  this.j:setHitbox("rectangle")
  this.j:addCollision("block");
  this.j:addCollision("spike");
  this.j:addCollision("platform");
  this.j:setRectangular(true);
		
  this.j:setAffectedByGravity(true);
		
  this.j:setDimX(0.3125)
  this.j:setDimY(0.625)

		
  this.j:addAnimation("idle", "guy_idle");
  this.j:addAnimation("walking", "guy_walking");
  this.j:addAnimation("jumping", "guy_jumping");
  this.j:addAnimation("falling", "guy_falling");
		
  this.j:switchAnimation("idle");
  this.maximumFallinglVelocity = -12.5
  
  this.firstJumpSpeed = 13.28125
  this.secondJumpSpeed = 10.9375
  
  this.flying_speed = 10
  this.walking_speed = 4.6875
  
  this.maximumJumps = 1
  this.jumpsLeft = 1
  
  this.max_first_jumps = 1
  this.first_jumps = 1
  
  this.max_second_jumps = 1
  this.second_jumps = 1
  
  this.standing_on = nil
  this.team = "guy"
  
  this.collided_list = {}
end

function death(this)
end


function die(this)

  this:playSound("guy_death")
  for i = 0, 50 do
    local usedAngle = math.random() * 360
	
    local posX = this.j:getPosX() + math.random()*this.j:getDimX()
    local posY = this.j:getPosY() - math.random()*this.j:getDimY()
	
    local dimX = 0.12
    local dimY = 0.12
			
    local velX = math.random()*14-7
    local velY = math.random()*14-7
  
    local new_e = lib.newEntity("particle_sticky")
    new_e.j:setAnimation("blood_1")
  
    this:shoot(new_e, posX, posY, velX, velY, dimX, dimY, rotation, 10)
  
  end
  
  this.j:die()
end

function update(this, delta)
  
  this:processJumps()
  
  if this.standing_on then 
    if this.j:getPosX() > this.standing_on.j:getPosX2() then
      this.standing_on = nil
    elseif this.j:getPosX2() < this.standing_on.j:getPosX() then
      this.standing_on = nil
    end
  end

  this:updateVel(delta)

  if this.j:getVelY() < this.maximumFallinglVelocity then
    this.j:setVelY(this.maximumFallinglVelocity);
  end

  if this.standing_on then
    this.first_jumps = this.max_first_jumps
    this.second_jumps = this.max_second_jumps
  end

  if this.j:isFlying() then
    this.j:setVelX(0)
    this.j:setVelY(0)

    if this.moving_up then this.j:setVelY(this.flying_speed) end
    if this.moving_down then this.j:setVelY(-this.flying_speed) end

    if this.moving_right then this.j:setVelX(this.flying_speed) end 
    if this.moving_left then this.j:setVelX(-this.flying_speed) end
  end



  this:updateRotation(delta)
  this:updatePos(delta)
  this:updateAnimation(delta)
  --print(this.j:getPosY())
  
  
  --this:process_collisions()
  --this.collision_list = {}
  
end

function printthis(this, what)
end



function updatePos(this, delta)
  
  local ground_vel_x = 0
  local ground_vel_y = 0

  if this.standing_on then
    ground_vel_x = this.standing_on.j:getVelX()
    ground_vel_y = this.standing_on.j:getVelY()
  end
  
  local total_vel_x = this.j:getVelX() + ground_vel_x
  local total_vel_y = this.j:getVelY() + ground_vel_y
  this.j:setPosX(this.j:getPosX() + total_vel_x * delta)
  this.j:setPosY(this.j:getPosY() + total_vel_y * delta)
end


function updateVel(this, delta)
  
  accX = this.j:getAccX()
  accY = this.j:getAccY()
  
  if this.j:isAffectedByGravity() then
    accX = accX + this.j:getGravityAccX()
    accY = accY + this.j:getGravityAccY()
  end
  
  this.j:setVelX(this.j:getVelX() + accX * delta)
  this.j:setVelY(this.j:getVelY() + accY * delta)
  
  this.j:setVelX(0)
  if this.moving_left then this.j:setVelX(-this.walking_speed) end
  if this.moving_right then this.j:setVelX(this.walking_speed) end
  
end

function updateRotation(this, delta)
  local rotation = this.j:getRotation()
  local rotationVel = this.j:getRotationVel()
  local rotationAcc = this.j:getRotationAcc()
  
  local new_rotation_vel = rotationVel + rotationAcc * delta
  local new_rotation = rotation + new_rotation_vel * delta
  
  this.j:setRotationVel(new_rotation_vel)
  this.j:setRotation(new_rotation)
end





function updateAnimation(this, delta) 
  if this.standing_on then
    if (this.moving_left or this.moving_right) then
      this.animation_state = "walking"
    else
      this.animation_state = "idle"
    end
  elseif (not this.grounded and this.j:getVelY() > 0) then
    this.animation_state = "jumping"
  elseif (this.j:getVelY() < 0) then
    this.animation_state = "falling"
  else 
    this.animation_state = "idle"
  end
		
  if this.moving_right then facingLeft = false end
  if this.moving_left then facingLeft = true end
		
  this.j:setFlipHorizontal(this.facingLeft)
		
  this.j:switchAnimation(this.animation_state)
end


function processJumps(this)
  
  if this.jump_pressed then
    if this.standing_on then
      this.standing_on = nil
      this:firstJump()
    elseif this.second_jumps > 0 then
      this.second_jumps = this.second_jumps - 1
      this:secondJump()   
    end
    this.jump_pressed = false
  end
  
  if this.jump_released then
    if this.j:getVelY() > 0 and this.j:getVelY() < this.firstJumpSpeed then
	  this.j:setVelY(this.j:getVelY()*0.45)
    end
    this.jump_released = false
  end
  
end





function collided(this, entity, direction)
  local coll = {}
  coll.entity = entity
  coll.direction = direction
  table.insert(this.collided_list, coll)
  
  this:super_collided(entity, direction)
  
  if not entity.is_platform then
    if direction == 0 then
      this.j:setPosX(entity.j:getPosX() - this.j:getDimX())
      if this.j:getVelX() > 0 then this.j:setVelX(0) end
    elseif direction == 90 then
      this.j:setPosY(entity.j:getPosY() - entity.j:getDimY())
      if this.j:getVelY() > 0 then this.j:setVelY(0) end
    elseif direction == 180 then
      this.j:setPosX(entity.j:getPosX() + entity.j:getDimX())
      if this.j:getVelX() < 0 then this.j:setVelX(0) end
    elseif direction == 270 then
      this.j:setPosY(entity.j:getPosY() + this.j:getDimY())
      if this.j:getVelY() < 0 then this.j:setVelY(0) end
      this.standing_on = entity
      this.first_jumps = this.max_first_jumps
      this.second_jumps = this.max_second_jumps
    end
  else
    if direction == 270 then
      this.j:setPosY(entity.j:getPosY() + this.j:getDimY())
      this.j:setVelY(0)
      
      this.standing_on = entity
      this.first_jumps = this.max_first_jumps
      this.second_jumps = this.max_second_jumps
    end
  end
  

end


function process_collisions(this)
	for k, v in pairs(this.collided_list) do
		local direction = v.direction
		local entity = v.entity


  		this:super_collided(entity, direction)
		if direction == 0 then
			this.j:setPosX(entity.j:getPosX() - this.j:getDimX())
			if this.j:getVelX() > 0 then this.j:setVelX(0) end
		elseif direction == 90 then
			this.j:setPosY(entity.j:getPosY() - entity.j:getDimY())
			if this.j:getVelY() > 0 then this.j:setVelY(0) end
		elseif direction == 180 then
			this.j:setPosX(entity.j:getPosX() + entity.j:getDimX())
			if this.j:getVelX() < 0 then this.j:setVelX(0) end
		elseif direction == 270 then
			this.j:setPosY(entity.j:getPosY() + this.j:getDimY())
			if this.j:getVelY() < 0 then this.j:setVelY(0) end
			this.standing_on = entity
			this.first_jumps = this.max_first_jumps
			this.second_jumps = this.max_second_jumps
		end
	end

end

------------------------------------------------


function jumpPressed(this)  
  this.jump_pressed = true
end

function jumpReleased(this) 
  this.jump_released = true
end


function firstJump(this)
  this:playSound("jump")
  this.j:setVelY(this.firstJumpSpeed)
  this.j:setPosY(this.j:getPosY())
end
	
function secondJump(this)
  this:playSound("second_jump")
  this.j:setVelY(this.secondJumpSpeed)
  this.j:setPosY(this.j:getPosY())
end

function setMovingUp(this, bool)
  this.moving_up = bool
end

function setMovingDown(this, bool)
  this.moving_down = bool
end

function setMovingRight(this, bool)
  this.moving_right = bool
end

function setMovingLeft(this, bool)
  this.moving_left = bool
end

function useWeapon(this, x, y)
  this:playSound("shoot")
  
  local offset_x
  if this.facingLeft then offset_x = -0.2 else offset_x = 0.35 end
  
  local offset_y = -0.2
  
  local this_x = this.j:getPosX() + offset_x
  local this_y = this.j:getPosY() + offset_y
  
  local size = 0.2
  
  if x and y then
    local angle = lib.angleBetween(this_x, this_y, x, y)
    local vel_x, vel_y = lib.angle(10, angle)
    this:shoot_new("projectile", this_x, this_y, vel_x, vel_y, size, size, 0, 3)
  else 
    local vel_x
    if this.facingLeft then vel_x = -10 else vel_x = 10 end
    local vel_y = 0
    
    this:shoot_new("projectile", this_x, this_y, vel_x, vel_y, size, size, 0, 3)
  end
  
  
end


function shoot_new(this, name, pos_x, pos_y, vel_x, vel_y, size_x, size_y, angle, life_time)
  local new_e = lib.newEntity(name);
  
  new_e.j:addCollision("guy")
  new_e.j:setVelX(vel_x)
  new_e.j:setVelY(vel_y)
		
  new_e.j:setDimX(size_x)
  new_e.j:setDimY(size_y)
  
  new_e.j:setRotation(angle)
  
  new_e:newTimer(life_time, function (this) this:die() end, "die")
  new_e.team = this.team
  new_e.collision_damage = 1
		
  this.j:createEntity(new_e.j, pos_x, pos_y)
end



return _ENV
end