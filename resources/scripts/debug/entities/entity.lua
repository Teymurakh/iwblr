--
do
local newenv = {}
setAllowed(newenv)
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
end

function death(this)
end

function update(this, delta)
  this:updateTimers(delta)
  this:updatePos(delta)
  this:updateVel(delta)
  this:updateRotation(delta)
end


function collided(this, entity, direction)
  if not (this.team == entity.team) then
    if not entity.is_invulnerable then
      entity:takeDamage(this.collision_damage)
    end
    this:takeDamage(this.collision_selfdamage)
  end
end

function updatePos(this, delta)
  this.j:setPosX(this.j:getPosX() + this.j:getVelX() * delta)
  this.j:setPosY(this.j:getPosY() + this.j:getVelY() * delta)
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

function takeDamage(this, damage)
  if not this.is_invulnerable then
    this.health = this.health - math.abs(damage);
    if this.health <= 0 then
      this:die()
    end
  end
end

function heal(this, heal)
  this.health = this.health + math.abs(damage);
end

function die(this)
  this.j:die()
end


function newTimer(object, time_left, action, name) 
  local timer = {}
  timer.object = object
  timer.time_left = time_left
  timer.action = action
  
  timer.update = function (this, name, delta)
    this.time_left = this.time_left - delta;
    if this.time_left <= 0 then
      this.action(this.object)
      this.object.timers[name] = nil
	end
  end
  
  if not name then 
    table.insert(object.timers, timer)
  else 
    object.timers[name] = timer
  end
  
  
end


function updateTimers(this, delta)
  for k in pairs(this.timers) do
      this.timers[k].update(this.timers[k], k, delta)
  end
  
end


function playSound(this, name)
  this.j:playSound(name)
end


function shoot(this, new_e, pos_x, pos_y, vel_x, vel_y, size_x, size_y, angle, life_time)
  
  new_e.j:addCollision("guy")
  new_e.j:setVelX(vel_x)
  new_e.j:setVelY(vel_y)
		
  new_e.j:setDimX(size_x)
  new_e.j:setDimY(size_y)
  
  new_e.j:setRotation(angle)
  
  new_e:newTimer(life_time, function (this) this:die() end, "die")
  new_e.team = this.team
		
  this.j:createEntity(new_e.j, pos_x, pos_y)
end


return _ENV
end